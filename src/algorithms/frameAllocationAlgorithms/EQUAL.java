package algorithms.frameAllocationAlgorithms;

import computer.COMPUTER;
import computer.Process;
import tools.Pair;

import java.util.ArrayList;
import java.util.Map;

public class EQUAL extends FrameAllocationAlgorithm {

    @Override
    public void allocateFrames(ArrayList<Pair<Process, Integer>> triples, boolean needsTriple) {
        ArrayList<ArrayList<Process>> processesList = COMPUTER.activeList;
        COMPUTER.ramSch.processFrameMap.clear();
        Map<Process, ArrayList<Integer>> processFrameMap = COMPUTER.ramSch.processFrameMap;
        int frameCounter = 0;
        for (ArrayList<Process> processGroup : processesList) {
            int processesNum = processGroup.size();
            if (processesNum == 0) return;
            int frameToAllocate = totalFrames/processesList.size() / processesNum; // tu totalFrames trzeba zmienic
            for (Process p : processGroup) {
                    ArrayList<Integer> frames = new ArrayList<>();
                    for (int i = 0; i < frameToAllocate; i++) {
                        frames.add(frameCounter);
                        frameCounter++;
                    }
                    processFrameMap.put(p, frames);
                }
        }
        COMPUTER.ramSch.algorithm.resetAlgorithm();
    }
}
