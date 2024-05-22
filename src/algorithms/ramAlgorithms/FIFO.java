package algorithms.ramAlgorithms;

import computer.COMPUTER;

import java.util.*;

import computer.Process;

public class FIFO extends RamAlgorithm {
    Map<Process, Queue<Integer>> fifoDelQueue = new HashMap<>();

    @Override
    public void normalRun(RamTask curTask) {
        if (!fifoDelQueue.containsKey(curTask.p)) fifoDelQueue.put(curTask.p, new LinkedList<>());

        if (checkPage(curTask) != -1) return;
        if (checkUsed(curTask.p) < checkSize(curTask.p)) {
            int frameNumber = setFreeFrame(curTask);
            resultCounter++;
            fifoDelQueue.get(curTask.p).add(frameNumber);
            return;
        }

        int toDel = fifoDelQueue.get(curTask.p).poll();
        fifoDelQueue.get(curTask.p).add(toDel);
        COMPUTER.frames.set(toDel, curTask);
        resultCounter++;
    }

    @Override
    public void resetAlgorithm() {
        for (Process p : fifoDelQueue.keySet()) {

            ArrayList<Integer> toDel = new ArrayList<>();
            for (Integer frame : fifoDelQueue.get(p)) {
                if (!COMPUTER.ramSch.processFrameMap.get(p).contains(frame)) {
                    toDel.add(frame);
                }
            }

            for (Integer i : toDel) {
                fifoDelQueue.get(p).remove((Object) i);
            }


        }
    }
    @Override
    public void clearProcess(Process p) {
        fifoDelQueue.remove(p);
    }
}
