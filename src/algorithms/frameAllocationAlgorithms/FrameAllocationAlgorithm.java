package algorithms.frameAllocationAlgorithms;

import java.util.*;

import computer.Process;
import tools.Pair;
import tools.Tripple;
import simulation.SimulationParameters;
public abstract class FrameAllocationAlgorithm {
    protected int totalFrames;

    public FrameAllocationAlgorithm() {
        this.totalFrames = SimulationParameters.RAM_SIZE;
    }

    public abstract void allocateFrames(ArrayList<Pair<Process, Integer>> Triples, boolean needsTriple);

}
