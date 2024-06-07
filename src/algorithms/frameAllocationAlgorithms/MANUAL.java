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

    @Override
    public void allocateFrames(ArrayList<Pair<Process, Integer>> triples, boolean needsTriple) {
        //System.out.println("jeden");
        ArrayList<ArrayList<Process>> processesList = COMPUTER.activeList;
        Map<Process, ArrayList<Integer>> processFrameMap = COMPUTER.ramSch.processFrameMap;
        COMPUTER.ramSch.processFrameMap.clear();
        frameCounter = 0;
        for (ArrayList<Process> processGroup : processesList) {
            int frameToAllocateForGroup = totalFrames / processesList.size();
            for (Process p : processGroup) {
                ArrayList<Integer> frames = new ArrayList<>();
                if (frameCounter < totalFrames) {
                    frames.add(frameCounter);
                    frameCounter++;
                }
                frameToAllocateForGroup--;
                processFrameMap.put(p, frames);
            }


            for (Process p : processGroup) {
                ArrayList<Integer> frames = processFrameMap.get(p);
                int additionalFrames = frameToAllocateForGroup / processGroup.size();
                for (int i = 0; i < additionalFrames && frameCounter < totalFrames; i++) {
                    if (frameToAllocateForGroup > 0) {
                        frames.add(frameCounter);
                        frameCounter++;
                        frameToAllocateForGroup--;
                    }
                }
            }

            if (needsTriple) {
                boolean found = false;
                for (Process process : processGroup) {
                    for (Pair<Process, Integer> triple : triples) {
                        if (triple.first.getId() == process.getId()) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        break;
                    }
                }

                if (found) {
                    adjustFrameAllocation(processGroup, triples, frameToAllocateForGroup);
                }
            }
        }

        COMPUTER.ramSch.algorithm.resetAlgorithm();
    }

    private void adjustFrameAllocation(ArrayList<Process> processesList, ArrayList<Pair<Process, Integer>> triples, int freeFrames) {
        if (freeFrames > 0) {

            for (Process p : processesList) {
                int index = -1;
                for (int j = 0; j < triples.size(); j++) {
                    if (triples.get(j).first.getId() == p.getId()) {
                        index = j;
                        break;
                    }
                }
                if (index == -1) {
                    continue;
                }

                Pair<Process, Integer> para = triples.get(index);
                int pageFaults = para.second;
                double ppf = (double) pageFaults / window;
                ArrayList<Integer> processFrames = COMPUTER.ramSch.processFrameMap.get(p);

                if (ppf > upperThreshold && freeFrames > 0 && frameCounter < totalFrames) {
                    frameCounter++;
                    processFrames.add(frameCounter);
                     frameCounter++;

                    freeFrames--;
                    //System.out.println("si");

                } else if (ppf < lowerThreshold && processFrames.size() > 1) {
                    processFrames.removeFirst();
                    freeFrames++;
                    //System.out.println("si");

                }
            }
        }
    }
}
