import algorithms.cpuAlgorithms.CpuAlgorithm;
import algorithms.cpuAlgorithms.RR;
import algorithms.cpuAlgorithms.SJF;
import algorithms.cpuAlgorithms.SRTF;
import algorithms.frameAllocationAlgorithms.*;
import algorithms.memAlgorithms.CSCAN;
import algorithms.memAlgorithms.MemAlgorithm;
import algorithms.memAlgorithms.SCAN;
import algorithms.memAlgorithms.SSTF;
import algorithms.ramAlgorithms.*;
import computer.*;
import simulation.SimulationParameters;
import statistics.StatisticsHandler;

import java.time.Duration;
import java.time.Instant;

public class MainTask4 {

        public static void main(String[] args) {
            ProcessProvider pp = new ProcessProvider();

            CpuAlgorithm cpuAlgo = new SRTF();
            MemAlgorithm memAlgo = new CSCAN(2);
            RamAlgorithm ramAlgo = new LRU();
            FrameAllocationAlgorithm[] frameAllocationAlgorithms = new FrameAllocationAlgorithm[]{new EQUAL(),new PROP(),new MANUAL(),new ZONAL()};

            int numOfCombinations = frameAllocationAlgorithms.length;
            int iterator=0;

            Instant start = Instant.now();

            for (FrameAllocationAlgorithm frameAlgo : frameAllocationAlgorithms) {
                CpuScheduler cpuSch = new CpuScheduler(cpuAlgo);
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
                    System.out.println("\tCPU: "+cpuAlgo.getClass().getSimpleName());
                    System.out.println("\tMEM: "+memAlgo.getClass().getSimpleName());
                    System.out.println("\tRAM: "+ramAlgo.getClass().getSimpleName());
                    System.out.println("\tFRAMES: "+frameAlgo.getClass().getSimpleName());
                    throw e;
                }
//                System.out.println("post-work");
                computer.writeStats();
                computer.restartTime();
                System.out.println(++iterator+"/"+numOfCombinations+" DONE");
            }

            Instant finish = Instant.now();

            System.out.println("\n\nFINISHED!");
            System.out.println("TIME ELAPSED: "+ Duration.between(start, finish));
            StatisticsHandler.printStatistics();
    }
}
