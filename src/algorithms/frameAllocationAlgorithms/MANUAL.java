package algorithms.frameAllocationAlgorithms;

import computer.Process;
import tools.Tripple;

import java.util.ArrayList;
import java.util.Map;

public class MANUAL extends FrameAllocationAlgorithm {
    public MANUAL(int totalFrames) {
        super(totalFrames);
    }
    int window =5;
    @Override
    public void allocateFrames(Map<Process, ArrayList<Integer>> processFrameMap, Tripple<Process, Integer, Integer> tempTriple) {

    }

}

