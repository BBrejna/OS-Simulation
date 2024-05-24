import algorithms.cpuAlgorithms.*;
import algorithms.ramAlgorithms.RamAlgorithm;
import computer.*;
import algorithms.memAlgorithms.*;
import simulation.SimulationParameters;
import statistics.StatisticsHandler;
import algorithms.ramAlgorithms.*;
import algorithms.frameAllocationAlgorithms.*;

public class Main {
    public static int memSize = SimulationParameters.MEM_SIZE;
    public static int ramSize = SimulationParameters.RAM_SIZE;

    public static void main(String[] args) {
        ProcessProvider pp = new ProcessProvider(SimulationParameters.PROCESSES_NUMBER, memSize);

        CpuAlgorithm[] cpuAlgorithms = new CpuAlgorithm[]{new algorithms.cpuAlgorithms.FCFS(), new RR(), new SJF(), new SRTF()};
        MemAlgorithm[] memAlgorithms = new MemAlgorithm[]{
//                new algorithms.memAlgorithms.FCFS(memSize, 0), new SSTF(memSize, 0), new SCAN(memSize, 0), new CSCAN(memSize, 0),
                new algorithms.memAlgorithms.FCFS(1), new SSTF(1), new SCAN(1), new CSCAN(1),
                new algorithms.memAlgorithms.FCFS(2), new SSTF(2), new SCAN(2), new CSCAN(2)};
        RamAlgorithm[] ramAlgorithms = new RamAlgorithm[]{new FIFO(), new RAND(), new LRU(), new AppLRU()};
        FrameAllocationAlgorithm[] frameAllocationAlgorithms = new FrameAllocationAlgorithm[]{new EQUAL(ramSize), new PROP(ramSize), new MANUAL(ramSize)};

        int numOfCombinations = cpuAlgorithms.length * memAlgorithms.length * ramAlgorithms.length * frameAllocationAlgorithms.length;
        int iterator=0;

        for (CpuAlgorithm cpuAlgo : cpuAlgorithms) {
            for (MemAlgorithm memAlgo : memAlgorithms) {
                for (RamAlgorithm ramAlgo : ramAlgorithms) {
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
                }
            }
        }

        System.out.println("\n\nFINISHED!");
        StatisticsHandler.printStatistics();
    }


}