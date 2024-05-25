package algorithms.frameAllocationAlgorithms;

import computer.COMPUTER;
import computer.Process;
import simulation.SimulationParameters;
import tools.Pair;

import java.util.ArrayList;
import java.util.Map;

public class ZONAL extends FrameAllocationAlgorithm {
    int frameCounter;
    int freeFrames;

    @Override
    public void allocateFrames(ArrayList<Pair<Process, Integer>> Triples, boolean needsTriple) {
        ArrayList<Process> processesList = COMPUTER.activeList;
        COMPUTER.ramSch.processFrameMap.clear();
        Map<Process, ArrayList<Integer>> processFrameMap = COMPUTER.ramSch.processFrameMap;

        // Dodanie po jednej ramce dla każdego procesu
        int processesNum = processesList.size();
        if (processesNum == 0) return;
        int k = 0;
        freeFrames = totalFrames;
        for (Process p : processesList) {
            ArrayList<Integer> frames = new ArrayList<>();
            frames.add(k);
            k++;
            freeFrames--;
            processFrameMap.put(p, frames);
        }
        frameCounter = k;
        int totalWSS = 0;
        for (Pair<Process, Integer> pair : Triples) {
            totalWSS += pair.second;
        }

        if (totalWSS <= freeFrames) {
          //  System.out.println("TUuu:");
            for (Process process : processesList) {
                for (Pair<Process, Integer> t : Triples) {
                    if (t.first.equals(process)) {
                        int allocate = t.second;
                        for (int i = 0; i < allocate; i++) {
                            processFrameMap.get(process).add(frameCounter);
                            frameCounter++;
                        }
                    }
                }
            }
        } else {
            Pair<Process, Integer> maxWSSPair = Triples.get(0);
            for (Pair<Process, Integer> pair : Triples) {
                if (pair.second > maxWSSPair.second) {
                    maxWSSPair = pair;
                }
            }
            Process maxWSSProcess = maxWSSPair.first;


            ArrayList<Integer> removedFrames = processFrameMap.get(maxWSSProcess);
            if (removedFrames != null && removedFrames.size() > 1) {
                freeFrames += removedFrames.size() - 1;
                ArrayList<Integer> keepOneFrame = new ArrayList<>();
                keepOneFrame.add(removedFrames.get(0));
                processFrameMap.put(maxWSSProcess, keepOneFrame);
            }
            //System.out.println("free: "+freeFrames);

            for (Process process : processesList) {
                if (!process.equals(maxWSSProcess)) {
                    int allocate = frameCounter/processesList.size();
                    //int allocate = (int) (maxWSSPair.second * ((double) processFrameMap.get(process).size() / totalWSS));
                   // System.out.println("CXD:"+allocate);
                    for (int i = 0; i < allocate; i++) {
                        if (freeFrames > 0) {
                            processFrameMap.get(process).add(frameCounter);
                            frameCounter++;
                            freeFrames--;
                        }
                    }
                }
            }
        }

        COMPUTER.ramSch.algorithm.resetAlgorithm();
    }
}
