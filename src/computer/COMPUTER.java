package computer;

import algorithms.frameAllocation.FrameAllocation;
import algorithms.memAlgorithms.MemAlgorithm;
import statistics.InstanceInfo;
import statistics.StatisticsHandler;

import java.util.ArrayList;
import java.util.Random;

public class COMPUTER {
    public static ProcessProvider provider;
    private final CpuScheduler cpuSch;
    private static MemScheduler memSch;

    //frame allocation
    private static FrameAllocator frmSch;

    public static int curTime=0;
    public static ArrayList<Process> activeList = new ArrayList<>();
    public static ArrayList<Process> finishedList = new ArrayList<>();
    public static ArrayList<Integer> memory = new ArrayList<>();

    //frame allocation
    public static ArrayList<Integer> frame = new ArrayList<>();

    public COMPUTER(ProcessProvider provider, CpuScheduler cpuSch, MemScheduler memSch) {
        COMPUTER.provider = provider;
        this.cpuSch = cpuSch;
        COMPUTER.memSch = memSch;



        int SIZE = memSch.SIZE;
        Random random = new Random();
        for (int i = 0; i <= SIZE; i++) {
            memory.add(random.nextInt((int)1e9));
        }
    }

    private void doStep() {
        provider.doStep();
        cpuSch.doStep();
        memSch.doStep();
        frmSch.doStep();
    }

    public void doWork() {
        while (!activeList.isEmpty() || provider.processesGenerated < provider.totalProcessesNumber) {
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
    public static void memAnswer(Process p, int answer) {
        p.getMemoryAnswer(answer);
    }

    public static void registerProcess(Process p) {
        activeList.add(p);
    }

    private void clearLists() {
        activeList.clear();
        finishedList.clear();
    }
    public void restartTime() {
        clearLists();
        provider.restartTime();
        curTime=0;
    }

    public void writeStats() {
        String cpuAlgoName = cpuSch.algorithm.getClass().getSimpleName();
        String memAlgoName = memSch.algorithm.getClass().getSimpleName();
        if (memSch.algorithm.MODE == 1) memAlgoName += " + edf";
        else if (memSch.algorithm.MODE == 2) memAlgoName += " + fd-scan";
        int memStepsDone = COMPUTER.memSch.algorithm.getSteps();
        int memRejected = COMPUTER.memSch.algorithm.getRejected();


//        System.out.println("-------------------------------------------");
//        System.out.println("CPU Algorithm: "+cpuSch.algorithm.getClass().getSimpleName());
//        System.out.println("MEM Algorithm: "+memSch.algorithm.getClass().getSimpleName());
//        System.out.print("MEM Algorithm mode: ");
//        if (memSch.algorithm.MODE == 0) System.out.println("normal");
//        else if (memSch.algorithm.MODE == 1) System.out.println("edf");
//        else if (memSch.algorithm.MODE == 2) System.out.println("fd-scan");

        double avg_wait = 0, avg_turnaround = 0;
        ArrayList<Process> processes = COMPUTER.finishedList;
        for (Process p : processes) {
//            System.out.println(p);
            avg_wait += p.getWaitTime();
            avg_turnaround += p.getTurnAroundTime();
        }
        if (!processes.isEmpty()) {
            avg_wait /= processes.size();
            avg_turnaround /= processes.size();
        }
//        System.out.println("Avg turnaround time: " + avg_turnaround);
//        System.out.println("Avg wait time: " + avg_wait);
//        System.out.println("# of processes: " + processes.size());
//        System.out.println("Cycles done: " + COMPUTER.curTime);
//        System.out.println("HDD steps done: " + COMPUTER.memSch.algorithm.getSteps());
//        System.out.println("HDD rejected priority: " + COMPUTER.memSch.algorithm.getRejected());

        StatisticsHandler.registerInstance(new InstanceInfo(processes.size(), COMPUTER.curTime, cpuAlgoName, memAlgoName, avg_turnaround, avg_wait, memStepsDone, memRejected));
    }
}
