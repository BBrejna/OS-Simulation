package algorithms.frameAllocationAlgorithms;

import computer.COMPUTER;
import computer.Process;
import simulation.SimulationParameters;
import tools.Pair;
import tools.Tripple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EQUAL extends FrameAllocationAlgorithm {

    @Override
    public void allocateFrames(ArrayList<Pair<Process, Integer>> Triples, boolean needsTriple) {
        ArrayList<Process> processesList = COMPUTER.activeList;
        COMPUTER.ramSch.processFrameMap.clear();
        Map<Process, ArrayList<Integer>> processFrameMap = COMPUTER.ramSch.processFrameMap;
        // dodanie po jednej rameczce dla kazde
        int processesNum = processesList.size();
        if (processesNum == 0) return;

        int frameToAllocate = totalFrames / processesNum;
        int frameCounter = 0;

        for (Process p : processesList) {
            ArrayList<Integer> frames = new ArrayList<>();
            for (int i = 0; i < frameToAllocate; i++) {
                frames.add(frameCounter);
                frameCounter++;
            }
            processFrameMap.remove(p);
            processFrameMap.put(p, frames);
        }
        COMPUTER.ramSch.algorithm.resetAlgorithm();
    }
}
