package algorithms.frameAllocationAlgorithms;

import java.util.*;
import computer.Process;

public abstract class FrameAllocationAlgorithm {
    protected int totalFrames;
    protected ArrayList<Process> processesList;

    public FrameAllocationAlgorithm(int totalFrames, ArrayList<Process> processesList) {
        this.totalFrames = totalFrames;
        this.processesList = processesList;
    }

    public abstract void allocateFrames();

}
