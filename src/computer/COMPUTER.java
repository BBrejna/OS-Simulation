package computer;

import java.util.ArrayList;

public class COMPUTER {
    public static ProcessProvider provider;
    private final CpuScheduler cpuSch;

    public static int curTime=0;
    public static ArrayList<Process> activeList = new ArrayList<>();
    public static ArrayList<Process> finishedList = new ArrayList<>();

    public COMPUTER(ProcessProvider provider, CpuScheduler cpuSch) {
        this.cpuSch = cpuSch;
        COMPUTER.provider = provider;
    }

    private void doStep() {
        provider.doStep();
        cpuSch.doStep();
    }

    public void doWork() {
        while (!activeList.isEmpty() || provider.processesGenerated < provider.totalProcessesNumber) {
            doStep();
            curTime++;
        }
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

    public void restartComputer() {
        clearLists();
        provider.restartProvider();
        curTime=0;
    }

    public void writeStats() {
        System.out.println("-------------------------------------------");
        System.out.println("Algorithm: "+cpuSch.algorithm.getClass().getSimpleName());
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
        System.out.println("Avg turnaround time: " + avg_turnaround);
        System.out.println("Avg wait time: " + avg_wait);
        System.out.println("# of processes: " + processes.size());
        System.out.println("Cycles done: " + COMPUTER.curTime);
    }
}
