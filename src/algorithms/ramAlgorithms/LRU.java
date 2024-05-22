package algorithms.ramAlgorithms;

import computer.COMPUTER;
import computer.Process;

import java.util.*;

public class LRU extends RamAlgorithm {
    Map<Process, ArrayList<Integer>> lruFramesOrder = new HashMap<>();

    @Override
    public void normalRun(RamTask curTask) {
        if (!lruFramesOrder.containsKey(curTask.p)) lruFramesOrder.put(curTask.p, new ArrayList<>());

        int possibleFrame = checkPage(curTask);
        if (possibleFrame != -1) {
            lruFramesOrder.get(curTask.p).remove(possibleFrame);
            lruFramesOrder.get(curTask.p).add(possibleFrame);
            return;
        }
        if (checkUsed(curTask.p) < checkSize(curTask.p)) {
            int frameNumber = setFreeFrame(curTask);
            resultCounter++;
            lruFramesOrder.get(curTask.p).add(frameNumber);
            return;
        }

        int toDel = lruFramesOrder.get(curTask.p).get(0);
        lruFramesOrder.get(curTask.p).remove(0);
        lruFramesOrder.get(curTask.p).add(toDel);

        COMPUTER.frames.set(toDel, curTask);
        resultCounter++;

    }

    @Override
    public void clearProcess(Process p) {
        lruFramesOrder.remove(p);
    }

    @Override
    public void resetAlgorithm() {
        for (Process p : lruFramesOrder.keySet()) {

            ArrayList<Integer> toDel = new ArrayList<>();
            for (Integer frame : lruFramesOrder.get(p)) {
                if (!COMPUTER.ramSch.processFrameMap.get(p).contains(frame)) {
                    toDel.add(frame);
                }
            }

            for (Integer i : toDel) {
                lruFramesOrder.get(p).remove((Object) i);
            }


        }
    }
}
