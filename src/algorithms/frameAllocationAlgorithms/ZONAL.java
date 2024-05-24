package algorithms.frameAllocationAlgorithms;

import computer.COMPUTER;
import computer.Process;
import tools.Pair;
import tools.Tripple;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

public class ZONAL extends FrameAllocationAlgorithm{
    public ZONAL(int totalFrames) {
        super(totalFrames);
    }

    @Override
    public void allocateFrames(ArrayList<Pair<Process, Integer>> Triples, boolean needsTriple) {

        COMPUTER.ramSch.algorithm.resetAlgorithm();

    }
}
