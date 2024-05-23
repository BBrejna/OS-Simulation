package algorithms.frameAllocationAlgorithms;

import computer.COMPUTER;
import computer.Process;
import tools.Tripple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EQUAL extends FrameAllocationAlgorithm {

    public EQUAL(int totalFrames) {
        super(totalFrames);
    }

    @Override
    public void allocateFrames(Tripple<Process, Integer, Integer> tempTriple) {
        ArrayList<Process> processesList = COMPUTER.activeList;
        COMPUTER.ramSch.processFrameMap.clear();
        Map<Process, ArrayList<Integer>> processFrameMap = COMPUTER.ramSch.processFrameMap;

        int processesNum = processesList.size();
        if (processesNum == 0) return;

        int frameToAllocate = totalFrames / processesNum;
        int frameCounter = 0;

        for (Process p : processesList) {
            ArrayList<Integer> frames = new ArrayList<>();
            for (int i = 0; i < frameToAllocate; i++) {
                frames.add(frameCounter);
                frameCounter++;
            }
            processFrameMap.remove(p);
            processFrameMap.put(p, frames);
        }
        COMPUTER.ramSch.algorithm.resetAlgorithm();
    }
}
