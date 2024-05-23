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
    public void allocateFrames( Tripple<Process, Integer, Integer> tempTriple) {
        ArrayList<Process> processesList = COMPUTER.activeList;
        Map<Process, ArrayList<Integer>> processFrameMap = COMPUTER.ramSch.processFrameMap;
        int pages = 0;

        for (Process process : processesList) {
            pages += process.getPageCount();
        }
        int frameCounter =0 ;
        for (Process p : processesList) {
            ArrayList<Integer> frames = new ArrayList<>();
            int framesToAllocate = (totalFrames * p.getPageCount() / pages);
            for (int i = 0; i < framesToAllocate; i++) {
                frames.add(frameCounter);
                frameCounter++;
            }
            processFrameMap.remove(p);
            processFrameMap.put(p, frames);
        }
        COMPUTER.ramSch.algorithm.resetAlgorithm();
    }
}
