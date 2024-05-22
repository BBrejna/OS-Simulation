package computer;

import algorithms.frameAllocation.FrameAllocation;

import java.util.ArrayList;

public class FrameAllocator {
    public final FrameAllocation algorithm;
    private ArrayList<Process> processList;
    private int previousProcessCount;
    private boolean needsRecalculation;

    public FrameAllocator(FrameAllocation algorithm) {
        this.algorithm = algorithm;
        fetchProcessList();
        this.previousProcessCount =processList.size();
        this.needsRecalculation =true;
    }
    public void allocate() {
        fetchProcessList();
        if (needsRecalculation) {
            algorithm.allocateFrames();
            needsRecalculation = false;
            previousProcessCount = processList.size();
        }
    }

    public void doStep() {
        ArrayList<Process> prev = processList;
        fetchProcessList();
        if (processList.size() != previousProcessCount || processList.equals(prev)) {
            needsRecalculation = true;
        }
        if (needsRecalculation) {
            allocate();
        }
    }



    private void fetchProcessList() {
        processList = COMPUTER.activeList;
        //finishedList = COMPUTER.finishedList;
    }
}
