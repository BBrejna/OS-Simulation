package algorithms.frameAllocationAlgorithms;

import computer.COMPUTER;
import computer.Process;
import tools.Pair;

import java.util.ArrayList;
import java.util.Map;

public class MANUAL extends FrameAllocationAlgorithm {
    public MANUAL(int totalFrames) {
        super(totalFrames);
    }

    int window = 10;
    double upperThreshold = 0.5;
    double lowerThreshold = 0.2;
    int frameCounter;

    @Override
    public void allocateFrames(ArrayList<Pair<Process, Integer>> triples, boolean needsTriple) {
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
            adjustFrameAllocation(processesList, triples);
        }

        COMPUTER.ramSch.algorithm.resetAlgorithm();
    }

    private void adjustFrameAllocation(ArrayList<Process> processesList, ArrayList<Pair<Process, Integer>> triples) {
        //System.out.println("XD");
        int freeFrames = totalFrames - frameCounter;
        //System.out.println(triples);
       // System.out.println("Rozmiae: "+triples.size());
        for (Process p : processesList) {
            int index = -1;
            for (int j = 0; j < triples.size(); j++) {
                if (triples.get(j).first.getId() == p.getId()) {
                    index = j;
                   // System.out.println(index);
                    break;
                }
            }
            if (index == -1) {
                continue;
            }
            //System.out.println("dsf");

            Pair<Process, Integer> para = triples.get(index);
            int pageFaults = para.second;
            double ppf = (double) pageFaults / window;
            //System.out.println("PPF for process " + p.getId() + ": " + ppf);

            ArrayList<Integer> processFrames = COMPUTER.ramSch.processFrameMap.get(p);
           // System.out.println(ppf);
            if (ppf > upperThreshold && freeFrames > 0) {
                //System.out.println(ppf);
                processFrames.add(frameCounter);
                frameCounter++;
                freeFrames--;
               // System.out.println("jeden");
              //  System.out.println("Adding frame to process " + p.getId());
            } else if (ppf < lowerThreshold && processFrames.size()>1) {
                processFrames.remove(processFrames.size() - 1);
                freeFrames++;
                frameCounter--;
                //System.out.println("Dwa");
               // System.out.println("Removing frame from process " + p.getId());
            }
        }
    }
}
