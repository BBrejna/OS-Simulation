package algorithms.ramAlgorithms;

import computer.COMPUTER;

import java.util.*;

import computer.Process;

public class AppLRU extends RamAlgorithm {
    Map<Process, Queue<Integer>> appLruDelQueue = new HashMap<>();
    Map<Process, Map<Integer, Boolean>> referenceBitMap = new HashMap<>();

    @Override
    public void normalRun(RamTask curTask) {

        int possibleFrame = checkPage(curTask);
        if (possibleFrame != -1) {
            referenceBitMap.get(curTask.p).put(possibleFrame, true);
            if (!appLruDelQueue.get(curTask.p).contains(possibleFrame)) appLruDelQueue.get(curTask.p).add(possibleFrame);
            return;
        }
        if (checkUsed() < checkSize(curTask.p)) {
            int frameNumber = setFreeFrame(curTask.pageNumber);
            resultCounter++;
            appLruDelQueue.get(curTask.p).add(frameNumber);
            referenceBitMap.get(curTask.p).put(frameNumber, true);
            return;
        }

        Integer toDel = null;
        while (!appLruDelQueue.get(curTask.p).isEmpty()) {
            toDel = appLruDelQueue.get(curTask.p).poll();
            if (referenceBitMap.get(curTask.p).get(toDel)) {
                referenceBitMap.get(curTask.p).put(toDel, false);
                appLruDelQueue.get(curTask.p).add(toDel);
                continue;
            }
            break;
        }

        appLruDelQueue.get(curTask.p).add(toDel);
        referenceBitMap.get(curTask.p).put(toDel, true);
        COMPUTER.frames.set(toDel, curTask.pageNumber);
        resultCounter++;
    }

    @Override
    public void resetAlgorithm() {
        for (int processorId = 0; processorId < COMPUTER.processorsNumber; processorId++) {

            for (Process p : COMPUTER.activeList.get(processorId)) {
                if (p.isDone()) {
                    appLruDelQueue.remove(p);
                    referenceBitMap.remove(p);
                    continue;
                }
                if (!appLruDelQueue.containsKey(p)) {
                    appLruDelQueue.put(p, new LinkedList<>());
                    referenceBitMap.put(p, new HashMap<>());
                }

                ArrayList<Integer> toDel = new ArrayList<>();
                for (Integer frame : appLruDelQueue.get(p)) {
                    if (!COMPUTER.ramSch.processFrameMap.get(p).contains(frame)) {
                        toDel.add(frame);
                    }
                }

                for (Integer i : toDel) {
                    appLruDelQueue.get(p).remove(i);
                    referenceBitMap.get(p).remove(i);
                }

                for (Integer frame : COMPUTER.ramSch.processFrameMap.get(p)) {
                    if (!appLruDelQueue.get(p).contains(frame) && COMPUTER.frames.get(frame) != -1) {
                        appLruDelQueue.get(p).add(frame);
                        referenceBitMap.get(p).put(frame, false);
                    }
                }


            }
        }
    }
    @Override
    public void clearProcess(Process p) {
        appLruDelQueue.remove(p);
        referenceBitMap.remove(p);
    }

    @Override
    public void restartTime() {
        super.restartTime();
        appLruDelQueue.clear();
        referenceBitMap.clear();

    }
}
