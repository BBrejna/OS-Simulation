package algorithms.frameAllocationAlgorithms;

import computer.COMPUTER;
import computer.Process;
import simulation.SimulationParameters;
import tools.Pair;

import java.util.ArrayList;
import java.util.Map;

public class MANUAL extends FrameAllocationAlgorithm {


    int window = SimulationParameters.MANUAL_WINDOW;
    double upperThreshold = 0.5;
    double lowerThreshold = 0.2;
    int frameCounter;
    int max = totalFrames;
    boolean first =true;
    int last;
    @Override
    public void allocateFrames(ArrayList<Pair<Process, Integer>> triples, boolean needsTriple) {
       // ArrayList<Process> processesList = COMPUTER.activeList;
        ArrayList<ArrayList<Process>> processesList = COMPUTER.activeList;
        COMPUTER.ramSch.processFrameMap.clear();
        int k=0;
        frameCounter = 0;
        Map<Process, ArrayList<Integer>> processFrameMap = COMPUTER.ramSch.processFrameMap;
        for (ArrayList<Process> processGroup : processesList) {
            int frameToAllocateForGroup = totalFrames/processesList.size();
            max /=processesList.size();
            int processesNum = processGroup.size();
            if (processesNum == 0) return;
            for (Process p : processGroup) {
                  ArrayList<Integer> framess = new ArrayList<>();
                    framess.add(k);
                    k++;
                    frameToAllocateForGroup--;
                    processFrameMap.remove(p);
                    processFrameMap.put(p, framess);


            }
            frameCounter = k;
            int frameToAllocate = frameToAllocateForGroup / processesList.size()/processesNum; // totalFrames trzeba zmienić
            for (Process p : processGroup) {
                ArrayList<Integer> frames = new ArrayList<>();
                for (int i = 0; i < frameToAllocate; i++) {
                    frames.add(frameCounter);
                    frameCounter++;
                    frameToAllocate--;
                }
                for (int i = 0; i < frames.size(); i++) {
                    ArrayList<Integer> processFrames = processFrameMap.get(p);
                    if (processFrames == null) {
                        processFrames = new ArrayList<>();
                        processFrames.add(frameCounter);
                        processFrameMap.put(p, processFrames);
                    }
                    processFrames.add(frames.get(i));
                }
            }

            if (needsTriple) {
                adjustFrameAllocation(processGroup, triples,frameToAllocate);
            }
        }

        COMPUTER.ramSch.algorithm.resetAlgorithm();
    }

    private void adjustFrameAllocation(ArrayList<Process> processesList, ArrayList<Pair<Process, Integer>> triples,int freeFrames) {
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
            //System.out.println("Czy wolne:");
            //System.out.println(freeFrames>0);
            ArrayList<Integer> processFrames = COMPUTER.ramSch.processFrameMap.get(p);
          // System.out.println(ppf);
            if (ppf > upperThreshold && freeFrames > 0  && frameCounter< max) {
                //System.out.println(ppf);
                processFrames.add(frameCounter);
                frameCounter++;
                freeFrames--;
              // System.out.println("jeden");
              //  System.out.println("Adding frame to process " + p.getId());
            } else if (ppf < lowerThreshold && processFrames.size()>1 && frameCounter < max){
                processFrames.removeLast();
                freeFrames++;
                frameCounter--;
              //  System.out.println("Dwa");
               // System.out.println("Removing frame from process " + p.getId());
            }
        }
    }
}
