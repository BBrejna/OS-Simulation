package algorithms.frameAllocationAlgorithms;

import computer.COMPUTER;
import computer.Process;
import tools.Pair;
import tools.Tripple;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

public class ZONAL extends FrameAllocationAlgorithm{

    int frameCounter;

    @Override
    public void allocateFrames(ArrayList<Pair<Process, Integer>> Triples, boolean needsTriple) {
        ArrayList<Process> processesList = COMPUTER.activeList;
        COMPUTER.ramSch.processFrameMap.clear();
        Map<Process, ArrayList<Integer>> processFrameMap = COMPUTER.ramSch.processFrameMap;
        int processesNum = processesList.size();
        if (processesNum == 0) return;

        int frameToAllocate = totalFrames / processesNum;
        frameCounter = 0;

        for (Process p : processesList) {
            ArrayList<Integer> frames = new ArrayList<>();
            for (int i = 0; i < frameToAllocate; i++) {
                frames.add(frameCounter);
                frameCounter++;
            }
            processFrameMap.put(p, frames);
        }

        if (needsTriple) {
            //adjustFrameAllocation(processesList, Triples);
        }

        COMPUTER.ramSch.algorithm.resetAlgorithm();


    }
}
