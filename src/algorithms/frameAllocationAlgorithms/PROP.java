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

        int k = 0;

        for (ArrayList<Process> processGroup : processesList) {
            int frameToAllocateForGroup = totalFrames / processesList.size();
            int groupSize = processGroup.size();

            // Distribute one frame to each process initially
            for (Process p : processGroup) {
                ArrayList<Integer> frames = new ArrayList<>();
                frames.add(k);
                k++;
                frameToAllocateForGroup--;
                processFrameMap.remove(p);
                processFrameMap.put(p, frames);
            }

            // Calculate the total number of pages in the group
            int totalPagesInGroup = 0;
            for (Process process : processGroup) {
                totalPagesInGroup += process.getPageCount();
            }

            int frameCounter = k;
            for (Process p : processGroup) {
                ArrayList<Integer> frames = processFrameMap.get(p);
                int additionalFrames = (int) Math.round((double) frameToAllocateForGroup * p.getPageCount() / totalPagesInGroup);
                for (int i = 0; i < additionalFrames; i++) {
                    frames.add(frameCounter);
                    frameCounter++;
                }
            }
        }

        COMPUTER.ramSch.algorithm.resetAlgorithm();
    }

}
