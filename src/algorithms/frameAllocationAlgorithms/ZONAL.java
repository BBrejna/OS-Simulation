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
        ArrayList<ArrayList<Process>> processesList = COMPUTER.activeList;
        COMPUTER.ramSch.processFrameMap.clear();
        Map<Process, ArrayList<Integer>> processFrameMap = COMPUTER.ramSch.processFrameMap;
        frameCounter = 0;

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
                if (processGroup.contains(pair.first)) {
                    totalWSS += pair.second;
                }
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
            } else {

            }


        }

        COMPUTER.ramSch.algorithm.resetAlgorithm();
    }
}
