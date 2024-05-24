import algorithms.cpuAlgorithms.*;
import algorithms.ramAlgorithms.RamAlgorithm;
import computer.*;
import algorithms.memAlgorithms.*;
import statistics.StatisticsHandler;
import algorithms.ramAlgorithms.*;
import algorithms.frameAllocationAlgorithms.*;

public class Main {
    public static int memSize = 50;
    public static int ramSize = 10;

    public static void main(String[] args) {
        ProcessProvider pp = new ProcessProvider(10, memSize);

        CpuAlgorithm[] cpuAlgorithms = new CpuAlgorithm[]{new algorithms.cpuAlgorithms.FCFS(), new RR(), new SJF(), new SRTF()};
        MemAlgorithm[] memAlgorithms = new MemAlgorithm[]{
//                new algorithms.memAlgorithms.FCFS(memSize, 0), new SSTF(memSize, 0), new SCAN(memSize, 0), new CSCAN(memSize, 0),
                new algorithms.memAlgorithms.FCFS(memSize, 1), new SSTF(memSize, 1), new SCAN(memSize, 1), new CSCAN(memSize, 1),
                new algorithms.memAlgorithms.FCFS(memSize, 2), new SSTF(memSize, 2), new SCAN(memSize, 2), new CSCAN(memSize, 2)};
        RamAlgorithm[] ramAlgorithms = new RamAlgorithm[]{new FIFO(), new RAND(), new LRU()};
        FrameAllocationAlgorithm[] frameAllocationAlgorithms = new FrameAllocationAlgorithm[]{new EQUAL(ramSize),new MANUAL(ramSize)};

        for (CpuAlgorithm cpuAlgo : cpuAlgorithms) {
            for (MemAlgorithm memAlgo : memAlgorithms) {
                for (RamAlgorithm ramAlgo : ramAlgorithms) {
                    for (FrameAllocationAlgorithm frameAlgo : frameAllocationAlgorithms) {
                        CpuScheduler cpuSch = new CpuScheduler(cpuAlgo);
                        MemScheduler memSch = new MemScheduler(memAlgo);
                        FrameAllocator frameAllocator = new FrameAllocator(frameAlgo);

                        RamScheduler ramSch = new RamScheduler(ramAlgo, frameAllocator, ramSize);

                        COMPUTER computer = new COMPUTER(pp, cpuSch, memSch, ramSch);
//                System.out.println("pre-work");
                        computer.doWork();
//                System.out.println("post-work");
                        computer.writeStats();
                        computer.restartTime();
                    }
                }
            }
        }

        System.out.println("\n\nFINISHED!");
        StatisticsHandler.printStatistics();
    }


}