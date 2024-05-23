package algorithms.frameAllocationAlgorithms;

import computer.COMPUTER;
import computer.Process;
import tools.Tripple;

import java.util.ArrayList;
import java.util.Map;

public class PROPORTIONAL extends FrameAllocationAlgorithm {
    public PROPORTIONAL(int totalFrames) {
        super(totalFrames);
    }

    @Override
    public void allocateFrames(Map<Process, ArrayList<Integer>> processFrameMap, Tripple<Process, Integer, Integer> tempTriple) {
        ArrayList<Process> processesList = COMPUTER.activeList;

        int totalUniquePages = 0;

        for (Process process : processesList) {
            totalUniquePages += process.getUniquePage();
        }
        int frameCounter = 1;
        for (Process p : processesList) {
            ArrayList<Integer> frames = new ArrayList<>();
            int framesToAllocate = (int) Math.floor(totalFrames * p.getUniquePage() / totalUniquePages);
            for (int i = 0; i < framesToAllocate; i++) {
                frames.add(frameCounter);
                frameCounter++;
            }
            processFrameMap.remove(p);
            processFrameMap.put(p, frames);
        }
    }
}
