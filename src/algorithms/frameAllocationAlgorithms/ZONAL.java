package algorithms.frameAllocationAlgorithms;

import computer.COMPUTER;
import computer.Process;
import simulation.SimulationParameters;
import tools.Pair;

import java.util.ArrayList;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;

public class ZONAL extends FrameAllocationAlgorithm {
    int frameCounter;
    @Override
    public void allocateFrames(ArrayList<Pair<Process, Integer>> Triples, boolean needsTriple) {
        //ArrayList<Process> processesList = COMPUTER.activeList;
        ArrayList<ArrayList<Process>> processesList = COMPUTER.activeList;
        COMPUTER.ramSch.processFrameMap.clear();
        Map<Process, ArrayList<Integer>> processFrameMap = COMPUTER.ramSch.processFrameMap;
        int k = 0;
        for (ArrayList<Process> processGroup : processesList) {
            int frameToAllocateForGroup = totalFrames/processesList.size();
            int processesNum = processGroup.size();
            if (processesNum == 0) continue;
            for (Process p : processGroup) {
                ArrayList<Integer> frames = new ArrayList<>();
                frames.add(k);
                k++;
                frameToAllocateForGroup--;
                processFrameMap.put(p, frames);
            }
            frameCounter = k;
            int totalWSS = 0;

            for (Pair<Process, Integer> pair : Triples) {
                if (processGroup.contains(pair.first)) {
                    totalWSS += pair.second;
                }
            }
            Collections.sort(Triples, Comparator.comparing(t -> t.first.getId()));

            if (totalWSS <= frameToAllocateForGroup) {
                // System.out.println("TUuu:");
                int triplesIndex = 0;
                for (Process process : processGroup) {
                    if (triplesIndex < Triples.size() && Triples.get(triplesIndex).first.equals(process)) {
                        int allocate = Triples.get(triplesIndex).second;
                        for (int i = 0; i < allocate; i++) {
                            processFrameMap.get(process).add(frameCounter);
                            frameCounter++;
/*                            System.out.println("xd");*/
                        }
                        triplesIndex++;
                    }
                }
            } else {
                Pair<Process, Integer> maxWSSPair = Triples.get(0);
                for (Pair<Process, Integer> pair : Triples) {
                    if (processGroup.contains(pair.first) && pair.second > maxWSSPair.second) {
                        maxWSSPair = pair;
                    }
                }
                Process maxWSSProcess = maxWSSPair.first;
                ArrayList<Integer> removedFrames = processFrameMap.get(maxWSSProcess);
                if (removedFrames != null && removedFrames.size() > 1) {
                    frameToAllocateForGroup += removedFrames.size() - 1;
                    ArrayList<Integer> keepOneFrame = new ArrayList<>();
                    keepOneFrame.add(removedFrames.get(0));
                    processFrameMap.put(maxWSSProcess, keepOneFrame);
                }

                for (Process process : processGroup) {
                    if (!process.equals(maxWSSProcess)) {
                        int allocate = frameToAllocateForGroup / (processGroup.size() - 1);
                        ArrayList<Integer> frames = processFrameMap.getOrDefault(process, new ArrayList<>());
                        for (int i = 0; i < allocate; i++) {
                            if (frameToAllocateForGroup > 0) {
                                frames.add(frameCounter);
                                frameCounter++;
/*                                System.out.println("xddd");*/
                                frameToAllocateForGroup--;
                            }
                        }
                        processFrameMap.put(process, frames);
                    }
                }
            }
        }

        COMPUTER.ramSch.algorithm.resetAlgorithm();
    }
}
