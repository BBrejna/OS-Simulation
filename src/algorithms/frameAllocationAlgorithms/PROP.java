package algorithms.frameAllocationAlgorithms;

import computer.COMPUTER;
import computer.Process;
import tools.Pair;

import java.util.ArrayList;
import java.util.Map;

public class PROP extends FrameAllocationAlgorithm {


    int freeFramesToAllocate = totalFrames;

    @Override
    public void allocateFrames(ArrayList<Pair<Process, Integer>> Triples, boolean needsTriple){
        ArrayList<Process> processesList = COMPUTER.activeList;
        Map<Process, ArrayList<Integer>> processFrameMap = COMPUTER.ramSch.processFrameMap;
        // dodanie po jednej rameczce dla kazdego
        int k =0;
        for (Process p : processesList) {
            ArrayList<Integer> frames = new ArrayList<>();
            frames.add(k);
            k++;
            freeFramesToAllocate--;
            processFrameMap.remove(p);
            processFrameMap.put(p, frames);
        }

        int pages = 0;

        for (Process process : processesList) {
            pages += process.getPageCount();
        }
        int frameCounter =k ;
        for (Process p : processesList) {
            ArrayList<Integer> frames = new ArrayList<>();
            int framesToAllocate = (freeFramesToAllocate * (p.getPageCount() / pages));
           // System.out.println("dr: "+framesToAllocate);
            for (int i = 0; i < framesToAllocate; i++) {
                //System.out.println("zssd: "+frameCounter);
                frames.add(frameCounter);
                frameCounter++;
            }
           // processFrameMap.remove(p);
            for (int i = 0; i < frames.size(); i++) {
                processFrameMap.get(p).add(frames.get(i));
            }
        }
        COMPUTER.ramSch.algorithm.resetAlgorithm();
    }
}
