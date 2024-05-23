package algorithms.ramAlgorithms;

import computer.COMPUTER;

import java.util.*;

import computer.Process;

public class FIFO extends RamAlgorithm {
    Map<Process, Queue<Integer>> fifoDelQueue = new HashMap<>();

    @Override
    public void normalRun(RamTask curTask) {
        System.out.println("NORMAL"+" "+ curTask.p.getId()+ " "+ curTask.pageNumber);

        if (checkPage(curTask) != -1) return;
        if (checkUsed() < checkSize(curTask.p)) {
            int frameNumber = setFreeFrame(curTask.pageNumber);
            resultCounter++;
            fifoDelQueue.get(curTask.p).add(frameNumber);
            return;
        }
        System.out.println(fifoDelQueue.get(curTask.p));

        int toDel = fifoDelQueue.get(curTask.p).poll();
        fifoDelQueue.get(curTask.p).add(toDel);
        COMPUTER.frames.set(toDel, curTask.pageNumber);
        resultCounter++;
    }

    @Override
    public void resetAlgorithm() {

        for (Process p : COMPUTER.activeList) {
            if (!fifoDelQueue.containsKey(p)) fifoDelQueue.put(p, new LinkedList<>());

            ArrayList<Integer> toDel = new ArrayList<>();
            for (Integer frame : fifoDelQueue.get(p)) {
                if (!COMPUTER.ramSch.processFrameMap.get(p).contains(frame)) {
                    toDel.add(frame);
                }
            }

            for (Integer i : toDel) {
                fifoDelQueue.get(p).remove((Object) i);
            }

            for (Integer frame : COMPUTER.ramSch.processFrameMap.get(p)) {
                if (!fifoDelQueue.get(p).contains(frame) && COMPUTER.frames.get(frame) != -1){
                    fifoDelQueue.get(p).add(frame);
                }
            }


        }
    }
    @Override
    public void clearProcess(Process p) {
        fifoDelQueue.remove(p);
    }
}
