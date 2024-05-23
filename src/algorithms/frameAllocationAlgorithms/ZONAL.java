package algorithms.frameAllocationAlgorithms;

import computer.COMPUTER;
import computer.Process;
import tools.Tripple;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

public class ZONAL extends FrameAllocationAlgorithm{
    public ZONAL(int totalFrames) {
        super(totalFrames);
    }

    @Override
    public void allocateFrames(Tripple<Process, Integer, Integer> tempTriple) {

        COMPUTER.ramSch.algorithm.resetAlgorithm();

    }
}
