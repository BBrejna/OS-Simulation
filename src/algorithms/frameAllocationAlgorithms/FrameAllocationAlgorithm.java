package algorithms.frameAllocationAlgorithms;

import java.util.*;

import computer.Process;
import tools.Tripple;

public abstract class FrameAllocationAlgorithm {
    protected int totalFrames;

    public FrameAllocationAlgorithm(int totalFrames) {
        this.totalFrames = totalFrames;
    }

    public abstract void allocateFrames(Tripple<Process, Integer, Integer> tempTriple);

}
