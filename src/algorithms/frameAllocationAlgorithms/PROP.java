package algorithms.frameAllocationAlgorithms;

import computer.COMPUTER;
import computer.Process;
import tools.Pair;

import java.util.ArrayList;
import java.util.Map;

public class PROP extends FrameAllocationAlgorithm {


    @Override
    public void allocateFrames(ArrayList<Pair<Process, Integer>> Triples, boolean needsTriple){
        ArrayList<ArrayList<Process>> processesList = COMPUTER.activeList;
        Map<Process, ArrayList<Integer>> processFrameMap = COMPUTER.ramSch.processFrameMap;
        int k =0;
        for (ArrayList<Process> processGroup : processesList) {
            int frameToAllocateForGroup = totalFrames/processesList.size();
            for (Process p : processGroup) {
                ArrayList<Integer> frames = new ArrayList<>();
                frames.add(k);
                k++;
                frameToAllocateForGroup--;
                processFrameMap.remove(p);
                processFrameMap.put(p, frames);
            }

            int pages = 0;

            for (Process process : processGroup) {
                pages += process.getPageCount();
            }
            int frameCounter = k;
            for (Process p : processGroup) {
                ArrayList<Integer> frames = new ArrayList<>();
                int framesToAllocate = (frameToAllocateForGroup * (p.getPageCount() / pages));
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
        }

        COMPUTER.ramSch.algorithm.resetAlgorithm();
    }
}
