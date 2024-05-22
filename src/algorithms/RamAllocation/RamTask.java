package algorithms.RamAllocation;

import computer.Process;

public class RamTask {
    public Process p;
    int frameNumber;

    public RamTask(Process p, int frameNumber) {
        this.p = p;
        this.frameNumber = frameNumber;
    }


}
