package algorithms.frameAllocationAlgorithms;

import computer.COMPUTER;
import computer.Process;
import simulation.SimulationParameters;
import tools.Pair;

import java.util.*;

public class ZONAL extends FrameAllocationAlgorithm {
    int frameCounter;

    @Override
    public void allocateFrames(ArrayList<Pair<Process, Integer>> Triples, boolean needsTriple) {
        ArrayList<ArrayList<Process>> processesList = COMPUTER.activeList;
        Map<Process, ArrayList<Integer>> processFrameMap = COMPUTER.ramSch.processFrameMap;
        COMPUTER.ramSch.processFrameMap.clear();
        frameCounter = 0;
        Random random = new Random();

        for (ArrayList<Process> processGroup : processesList) {
            int frameToAllocateForGroup = totalFrames / processesList.size();
            int initialFrameCount = processGroup.size();
            frameToAllocateForGroup -= initialFrameCount;

            for (Process p : processGroup) {
                ArrayList<Integer> frames = new ArrayList<>();
                if (frameCounter < totalFrames) {
                    frames.add(frameCounter);
                    frameCounter++;
                }
                processFrameMap.put(p, frames);
            }

            int totalWSS = 0;
            for (Pair<Process, Integer> pair : Triples) {
                totalWSS += pair.second;
            }

            if (totalWSS == 0) continue;

            Collections.sort(Triples, Comparator.comparing(t -> t.first.getId()));

            if (totalWSS <= frameToAllocateForGroup) {
                for (Pair<Process, Integer> pair : Triples) {
                    if (processGroup.contains(pair.first)) {
                        Process process = pair.first;
                        int allocate = pair.second;
                        ArrayList<Integer> frames = processFrameMap.get(process);
                        for (int i = 0; i < allocate; i++) {
                            if (frameCounter < totalFrames) {
                                frames.add(frameCounter);
                                frameCounter++;
                            }
                        }
                    }
                }
            }else {
                Pair<Process, Integer> maxWSSPair = null;
                for (Pair<Process, Integer> pair : Triples) {
                    if (processGroup.contains(pair.first)) {
                        if (maxWSSPair == null || pair.second > maxWSSPair.second) {
                            maxWSSPair = pair;
                        }
                    }
                }
                if (maxWSSPair != null) {
                    Process processMax = maxWSSPair.first;
                    int maxWSS = maxWSSPair.second;
                    ArrayList<Integer> frames = processFrameMap.get(processMax);
                    if(frames.size()>1){
                        for (int i = 0; i < frames.size()-1; i++) {
                        processFrameMap.get(processMax).removeLast();
                        }
                    }
                    while (frameCounter < frames.size()-1) {
                        Process randomProcess = processGroup.get(random.nextInt(processGroup.size()));
                        ArrayList<Integer> framess = processFrameMap.get(randomProcess);
                        framess.add(frameCounter);
                        frameCounter++;
                    }
                }
            }
        }

        COMPUTER.ramSch.algorithm.resetAlgorithm();
    }
}
