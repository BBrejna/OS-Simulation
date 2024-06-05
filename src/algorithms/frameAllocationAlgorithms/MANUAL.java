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
    boolean first = true;
    int last;

    @Override
    public void allocateFrames(ArrayList<Pair<Process, Integer>> triples, boolean needsTriple) {
        ArrayList<ArrayList<Process>> processesList = COMPUTER.activeList;
        COMPUTER.ramSch.processFrameMap.clear();

        int k = 0;
        frameCounter = 0;
        Map<Process, ArrayList<Integer>> processFrameMap = COMPUTER.ramSch.processFrameMap;

        for (ArrayList<Process> processGroup : processesList) {
            int frameToAllocateForGroup = totalFrames / processesList.size();
            max /= processesList.size();
            int processesNum = processGroup.size();
            if (processesNum == 0) continue;

            for (Process p : processGroup) {
                ArrayList<Integer> frames = new ArrayList<>();
                frames.add(k);
                k++;
                frameToAllocateForGroup--;
                processFrameMap.put(p, frames);
            }

            int remainingFrames = frameToAllocateForGroup;

            int totalPagesInGroup = 0;
            for (Process process : processGroup) {
                totalPagesInGroup += process.getPageCount();
            }

            for (Process p : processGroup) {
                ArrayList<Integer> frames = processFrameMap.get(p);
                int additionalFrames = (int) Math.round((double) remainingFrames * p.getPageCount() / totalPagesInGroup);
                for (int i = 0; i < additionalFrames && frameCounter < totalFrames; i++) {
                    frames.add(frameCounter);
                    frameCounter++;
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
                    adjustFrameAllocation(processGroup, triples, totalFrames - frameCounter);
                }
            }
        }

        COMPUTER.ramSch.algorithm.resetAlgorithm();
    }

    private void adjustFrameAllocation(ArrayList<Process> processesList, ArrayList<Pair<Process, Integer>> triples, int freeFrames) {
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

            if (ppf > upperThreshold && freeFrames > 0) {
                processFrames.add(frameCounter);
                frameCounter++;
                freeFrames--;
                // System.out.println("plus");
            } else if (ppf < lowerThreshold && processFrames.size() > 1) {
                processFrames.remove(processFrames.size() - 1);
                freeFrames++;
                frameCounter--;
                // System.out.println("minus");
            }
        }
    }
}
