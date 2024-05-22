package computer;

import algorithms.frameAllocationAlgorithms.FrameAllocationAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FrameAllocator {
    public final FrameAllocationAlgorithm algorithm;
    private ArrayList<Process> processList;
    private int previousProcessCount;
    private boolean needsRecalculation;
    public Map<Process, ArrayList<Integer>> processFrameMap = new HashMap<>();

    public FrameAllocator(FrameAllocationAlgorithm algorithm) {
        this.algorithm = algorithm;
        fetchProcessList();
        this.previousProcessCount =processList.size();
        this.needsRecalculation =true;
    }
    public void allocate() {
        fetchProcessList();
        if (needsRecalculation) {
            algorithm.allocateFrames(processFrameMap);
            needsRecalculation = false;
            previousProcessCount = processList.size();
        }
    }

    public void doStep() {
//        ArrayList<Process> prev = processList;
//        fetchProcessList();
//        if (processList.size() != previousProcessCount || processList.equals(prev)) {
//            needsRecalculation = true;
//        }
//        if (needsRecalculation) {
//            allocate();
//        }
    }



    private void fetchProcessList() {
        processList = COMPUTER.activeList;
        //finishedList = COMPUTER.finishedList;
    }


    public void registerAnsweredRequest(Process p, int pageNumber, int curTime) {
        return;
    }
}
