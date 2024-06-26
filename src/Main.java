import algorithms.balanceAlgorithms.*;
import algorithms.cpuAlgorithms.*;
import algorithms.ramAlgorithms.RamAlgorithm;
import computer.*;
import algorithms.memAlgorithms.*;
import simulation.SimulationParameters;
import statistics.IterationStatisticsHandler;
import algorithms.ramAlgorithms.*;
import algorithms.frameAllocationAlgorithms.*;
import statistics.SimulationStatisticsHandler;

import java.time.Duration;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        ProcessProvider pp = new ProcessProvider();

        BalanceAlgorithm[] balanceAlgorithms = new BalanceAlgorithm[]{new FIRST(), new SECOND(),new THIRD()};
        CpuAlgorithm[] cpuAlgorithms = new CpuAlgorithm[]{new algorithms.cpuAlgorithms.FCFS(), new RR(), new SJF(), new SRTF()};
        MemAlgorithm[] memAlgorithms = new MemAlgorithm[]{
//                new algorithms.memAlgorithms.FCFS(memSize, 0), new SSTF(memSize, 0), new SCAN(memSize, 0), new CSCAN(memSize, 0),
                new algorithms.memAlgorithms.FCFS(1), new SSTF(1), new SCAN(1), new CSCAN(1),
                new algorithms.memAlgorithms.FCFS(2), new SSTF(2), new SCAN(2), new CSCAN(2)};
        RamAlgorithm[] ramAlgorithms = new RamAlgorithm[]{new FIFO(), new RAND(), new LRU(), new AppLRU()};
        FrameAllocationAlgorithm[] frameAllocationAlgorithms = new FrameAllocationAlgorithm[]{new EQUAL(),new PROP(),new MANUAL(),new ZONAL()};

        int numOfCombinations = balanceAlgorithms.length * cpuAlgorithms.length * memAlgorithms.length * ramAlgorithms.length * frameAllocationAlgorithms.length;

        Instant start = Instant.now();

        for (int iterationNumber = 1; iterationNumber <= SimulationParameters.ITERATIONS_NUMBER; iterationNumber++) {
            int iterator=0;
            for (BalanceAlgorithm balanceAlgo : balanceAlgorithms) {
                for (CpuAlgorithm cpuAlgo : cpuAlgorithms) {
                    for (MemAlgorithm memAlgo : memAlgorithms) {
                        for (RamAlgorithm ramAlgo : ramAlgorithms) {
                            for (FrameAllocationAlgorithm frameAlgo : frameAllocationAlgorithms) {
                                CpuScheduler cpuSch = new CpuScheduler(cpuAlgo, balanceAlgo);
                                MemScheduler memSch = new MemScheduler(memAlgo);
                                FrameAllocator frameAllocator = new FrameAllocator(frameAlgo);

                                RamScheduler ramSch = new RamScheduler(ramAlgo, frameAllocator);

                                COMPUTER computer = new COMPUTER(pp, cpuSch, memSch, ramSch);
//                System.out.println("pre-work");
                                try {
                                    computer.doWork();
                                } catch (Exception e) {
                                    System.out.println("NEW EXCEPTION IN COMPUTER WORK");
                                    System.out.println("COMPUTER CONFIG.: ");
                                    System.out.println("\tBALANCE: " + balanceAlgo.getClass().getSimpleName());
                                    System.out.println("\tCPU: " + cpuAlgo.getClass().getSimpleName());
                                    System.out.println("\tMEM: " + memAlgo.getClass().getSimpleName());
                                    System.out.println("\tRAM: " + ramAlgo.getClass().getSimpleName());
                                    System.out.println("\tFRAMES: " + frameAlgo.getClass().getSimpleName());
                                    throw e;
                                }

//                System.out.println("post-work");
                                computer.writeStats();
                                computer.restartTime();

                                Instant middle = Instant.now();

                                System.out.println("ITERATION "+iterationNumber+"/"+SimulationParameters.ITERATIONS_NUMBER+":\t"+(++iterator) + "/" + numOfCombinations + " DONE. TIME ELAPSED: " + Duration.between(start, middle));
                            }
                        }
                    }
                }
            }

            Instant finish = Instant.now();

//            System.out.println("\n\nFINISHED!");
//            System.out.println("TIME ELAPSED: " + Duration.between(start, finish));
            IterationStatisticsHandler.printStatistics(5);
            IterationStatisticsHandler.flushIterationStatistics();
            pp.restartProvider();
        }

        Instant finish = Instant.now();
        System.out.println("\n\nFINISHED!");
        System.out.println("TIME ELAPSED: " + Duration.between(start, finish));
        SimulationStatisticsHandler.printStatistics();
    }


}