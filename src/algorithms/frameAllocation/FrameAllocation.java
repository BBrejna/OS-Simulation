package algorithms.frameAllocation;

import java.util.*;
import computer.Process;

public abstract class FrameAllocation {
    protected int totalFrames;
    protected ArrayList<Process> processesList;

    public FrameAllocation(int totalFrames, ArrayList<Process> processesList) {
        this.totalFrames = totalFrames;
        this.processesList = processesList;
    }

    public abstract void allocateFrames();
}
