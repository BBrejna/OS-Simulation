package algorithms.frameAllocationAlgorithms;

import java.util.*;

import computer.Process;
import tools.Pair;
import tools.Tripple;

public abstract class FrameAllocationAlgorithm {
    protected int totalFrames;

    public FrameAllocationAlgorithm(int totalFrames) {
        this.totalFrames = totalFrames;
    }

    public abstract void allocateFrames(ArrayList<Pair<Process, Integer>> Triples, boolean needsTriple);

}
