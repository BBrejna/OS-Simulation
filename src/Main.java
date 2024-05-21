import algorithms.cpuAlgorithms.*;
import computer.*;
import algorithms.memAlgorithms.*;
import statistics.StatisticsHandler;

public class Main {
    public static int memSize = 50;

    public static void main(String[] args) {
        ProcessProvider pp = new ProcessProvider(10, memSize);

        CpuAlgorithm[] cpuAlgorithms = new CpuAlgorithm[]{new algorithms.cpuAlgorithms.FCFS(), new RR(), new SJF(), new SRTF()};
        MemAlgorithm[] memAlgorithms = new MemAlgorithm[]{
//                new algorithms.memAlgorithms.FCFS(memSize, 0), new SSTF(memSize, 0), new SCAN(memSize, 0), new CSCAN(memSize, 0),
                new algorithms.memAlgorithms.FCFS(memSize, 1), new SSTF(memSize, 1), new SCAN(memSize, 1), new CSCAN(memSize, 1),
                new algorithms.memAlgorithms.FCFS(memSize, 2), new SSTF(memSize, 2), new SCAN(memSize, 2), new CSCAN(memSize, 2)};

        for (CpuAlgorithm cpuAlgo : cpuAlgorithms) {
            for (MemAlgorithm memAlgo : memAlgorithms) {
                CpuScheduler cpuSch = new CpuScheduler(cpuAlgo);
                MemScheduler memSch = new MemScheduler(memAlgo);
                COMPUTER computer = new COMPUTER(pp, cpuSch, memSch);
//                System.out.println("pre-work");
                computer.doWork();
//                System.out.println("post-work");
                computer.writeStats();
                computer.restartTime();
            }
        }

        System.out.println("\n\nFINISHED!");
        StatisticsHandler.printStatistics();
    }


}