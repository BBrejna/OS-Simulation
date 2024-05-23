package algorithms.frameAllocationAlgorithms;

import computer.COMPUTER;
import computer.Process;
import tools.Tripple;

import java.util.ArrayList;
import java.util.Map;

public class EQUAL extends FrameAllocationAlgorithm {

    public EQUAL(int totalFrames) {
        super(totalFrames);
    }

    @Override
    public void allocateFrames(Map<Process, ArrayList<Integer>> processFrameMap, Tripple<Process, Integer, Integer> tempTriple) {
        ArrayList<Process> processesList = COMPUTER.activeList;

        int processesNum = processesList.size();
        if (processesNum == 0) return;

        int frameToAllocate = totalFrames / processesNum;
        int frameCounter = 1;

        for (Process p : processesList) {
            ArrayList<Integer> frames = new ArrayList<>();
            for (int i = 0; i < frameToAllocate; i++) {
                frames.add(frameCounter);
                frameCounter++;
            }
            processFrameMap.remove(p);
            processFrameMap.put(p, frames);
        }
    }
}
