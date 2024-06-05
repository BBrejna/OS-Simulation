package computer;

import algorithms.ramAlgorithms.RamTask;
import simulation.SimulationParameters;
import statistics.InstanceInfo;
import statistics.StatisticsHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class COMPUTER {
    public static ProcessProvider provider;
    private final CpuScheduler cpuSch;
    private static MemScheduler memSch;

    //frame allocation
    public static RamScheduler ramSch;

    public static int finishedProcesses=0;
    public static int processorsNumber;
    public static int curTime=0;
    public static ArrayList<ArrayList<Process>> activeList = new ArrayList<>();
    public static ArrayList<ArrayList<Process>> finishedList = new ArrayList<>();



    public static ArrayList<Integer> memory = new ArrayList<>();

    //frame allocation
    public static ArrayList<Integer> frames = new ArrayList<>();

    /*SOMETHING*/
    public static ArrayList<ArrayList<Process>> waitingList = new ArrayList<>();



    public COMPUTER(ProcessProvider provider, CpuScheduler cpuSch, MemScheduler memSch, RamScheduler ramSch) {
        COMPUTER.provider = provider;
        this.cpuSch = cpuSch;
        COMPUTER.memSch = memSch;
        COMPUTER.ramSch = ramSch;
        processorsNumber = SimulationParameters.PROCESSORS_NUMBER;
        for (int i = 0; i < processorsNumber; i++) {
            activeList.add(new ArrayList<>());
            finishedList.add(new ArrayList<>());

            /*SOMETHING*/
            waitingList.add(new ArrayList<>());
        }

        memory = new ArrayList<>();
        int SIZE = memSch.SIZE;
        Random random = new Random();
        for (int i = 0; i <= SIZE; i++) {
            memory.add(random.nextInt((int)1e9));
        }

        frames = new ArrayList<>();
        SIZE = ramSch.SIZE;
        for (int i = 0; i <= SIZE; i++) {
            frames.add(-1);
        }
    }

    private void doStep() {
        provider.doStep();
        cpuSch.doStep();
        memSch.doStep();
        ramSch.doStep();
    }

    public void doWork() {
        while (finishedProcesses < provider.processesGenerated || provider.processesGenerated < provider.totalProcessesNumber) {
            doStep();
            curTime++;
//            System.out.println("Post "+curTime+" "+activeList.size()+" "+provider.processesGenerated+" "+provider.totalProcessesNumber);
//            for (Process p : activeList) {
//                System.out.println(p);
//            }
//            System.out.println(memSch.algorithm);
        }
    }

    public static MemScheduler getMemoryScheduler() { return memSch; }
    public static RamScheduler getRamScheduler() { return ramSch; }
    public static void memAnswer(Process p, int answer) {
        p.getMemoryAnswer(answer);
    }
    public static void ramAnswer(Process p, int answer) {
        p.getRamAnswer(answer);
    }

    public static void registerProcess(Process p, int processorId) {
        //activeList.get(processorId).add(p);

        /*SOMETHING*/
        waitingList.get(processorId).add(p);
    }

    private void clearLists() {
        activeList.clear();
        finishedList.clear();
        finishedProcesses=0;
        /*SOMETHING*/
        waitingList.clear();
    }
    public void restartTime() {
        clearLists();
        provider.restartTime();
        memSch.restartTime();
        ramSch.restartTime();
        curTime=0;
    }

    public static void finishProcess(Process p, Integer processorId) {
        finishedList.get(processorId).add(p);
        activeList.get(processorId).remove(p);
        finishedProcesses++;
    }

    public void writeStats() {
        String cpuAlgoName = cpuSch.algorithm.getClass().getSimpleName();
        String memAlgoName = memSch.algorithm.getClass().getSimpleName();
        if (memSch.algorithm.MODE == 1) memAlgoName += " + edf";
        else if (memSch.algorithm.MODE == 2) memAlgoName += " + fd-scan";
        String ramAlgoName = ramSch.algorithm.getClass().getSimpleName();
        String frameAllocatorName = ramSch.frameAllocator.algorithm.getClass().getSimpleName();
        int memStepsDone = COMPUTER.memSch.algorithm.getSteps();
        int memRejected = COMPUTER.memSch.algorithm.getRejected();
        int ramPageErrors = COMPUTER.ramSch.algorithm.getPageErrorsNumber();


//        System.out.println("-------------------------------------------");
//        System.out.println("CPU Algorithm: "+cpuSch.algorithm.getClass().getSimpleName());
//        System.out.println("MEM Algorithm: "+memSch.algorithm.getClass().getSimpleName());
//        System.out.print("MEM Algorithm mode: ");
//        if (memSch.algorithm.MODE == 0) System.out.println("normal");
//        else if (memSch.algorithm.MODE == 1) System.out.println("edf");
//        else if (memSch.algorithm.MODE == 2) System.out.println("fd-scan");

        double avg_wait = 0, avg_turnaround = 0;

        int processesNumber = 0;

        for (int i = 0; i < processorsNumber; i++) {
            ArrayList<Process> processes = COMPUTER.finishedList.get(i);
            processesNumber += processes.size();
            for (Process p : processes) {
    //            System.out.println(p);
                avg_wait += p.getWaitTime();
                avg_turnaround += p.getTurnAroundTime();
            }
        }
        if (processesNumber > 0) {
            avg_wait /= processesNumber;
            avg_turnaround /= processesNumber;
        }
//        System.out.println("Avg turnaround time: " + avg_turnaround);
//        System.out.println("Avg wait time: " + avg_wait);
//        System.out.println("# of processes: " + processes.size());
//        System.out.println("Cycles done: " + COMPUTER.curTime);
//        System.out.println("HDD steps done: " + COMPUTER.memSch.algorithm.getSteps());
//        System.out.println("HDD rejected priority: " + COMPUTER.memSch.algorithm.getRejected());

        StatisticsHandler.registerInstance(new InstanceInfo(processesNumber, COMPUTER.curTime, cpuAlgoName, memAlgoName, ramAlgoName, frameAllocatorName, avg_turnaround, avg_wait, memStepsDone, memRejected, ramPageErrors));
    }
}
