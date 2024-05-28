package computer;

import algorithms.cpuAlgorithms.CpuAlgorithm;
import algorithms.memAlgorithms.MemAlgorithm;

public class MemScheduler {
    public final MemAlgorithm algorithm;
    private final int MODE;
    public final int SIZE;


    public MemScheduler(MemAlgorithm algorithm) {
        this.algorithm=algorithm;
        this.MODE = algorithm.MODE;
        this.SIZE = algorithm.SIZE;
    }

    public void doStep() {
        if (MODE == 0) algorithm.normal();
        else if (MODE == 1) algorithm.edf();
        else if (MODE == 2) algorithm.fdScan();
    }

    public void restartTime() {
        algorithm.restartTime();
    }

    public void getMemoryRequest(Process p, int position, int toDoTime) {
        algorithm.registerTask(p, position, toDoTime, toDoTime!=-1 && MODE!=0);
    }
    public void getMemoryRequest(Process p, int position) {
        getMemoryRequest(p, position, -1);
    }
}
