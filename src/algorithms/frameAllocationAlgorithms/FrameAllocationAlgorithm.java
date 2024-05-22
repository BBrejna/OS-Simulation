package algorithms.frameAllocationAlgorithms;

import java.util.*;
import computer.Process;

public abstract class FrameAllocationAlgorithm {
    protected int totalFrames;

    public FrameAllocationAlgorithm(int totalFrames) {
        this.totalFrames = totalFrames;
    }

    public abstract void allocateFrames();

}
