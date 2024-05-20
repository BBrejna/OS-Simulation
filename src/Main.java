import algorithms.cpuAlgorithms.*;
import computer.*;

public class Main {
    public static void main(String[] args) {
        ProcessProvider pp = new ProcessProvider(10);

        Algorithm[] cpuAlgorithms = new Algorithm[]{new FCFS(), new RR(), new SJF(), new SRTF()};

        for (Algorithm cpuAlgo : cpuAlgorithms) {
            CpuScheduler cpuSch = new CpuScheduler(cpuAlgo);
            COMPUTER computer = new COMPUTER(pp, cpuSch);
            computer.doWork();
            computer.writeStats();
            computer.restartTime();
        }

    }

}