package algorithms.ramAlgorithms;

import computer.COMPUTER;
import computer.Process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class RamAlgorithm {
    RamTask nextTask;
    int resultCounter;
    ArrayList<Integer> frames;

    public RamAlgorithm() {
        resultCounter = 0;
    }

    public void registerTask(Process p, int page) {
        nextTask = new RamTask(p, page);
    }

    public void answer() {
        if (nextTask == null) return;
        RamTask curTask = nextTask;

        frames = COMPUTER.ramSch.processFrameMap.get(curTask.p);

        nextTask = null;
        COMPUTER.ramSch.getFrameAllocator().registerAnsweredRequest(curTask.p, curTask.pageNumber, COMPUTER.curTime);
        normalRun(curTask);
    };

    public boolean checkPage(RamTask t) {
        for (Integer frame : frames) {
            if (COMPUTER.frames.get(frame) == t) {
                return true;
            }
        }
        return false;
    }
    public int checkUsed(Process p) {
        int used=0;
        for (Integer frame : frames) {
            if (COMPUTER.frames.get(frame).p == p) {
                used++;
            }
        }
        return used;
    }
    public int checkSize(Process p) {
        return COMPUTER.ramSch.processFrameMap.get(p).size();
    }

    public int setFreeFrame(RamTask t) {
        for (Integer frame : frames) {
            if (COMPUTER.frames.get(frame).p != t.p) {
                COMPUTER.frames.set(frame, t);
                return frame;
            }
        }
        return -1;
    }

    public abstract void normalRun(RamTask curTask);
    public abstract void clearProcess(Process p);
    public abstract void resetAlgorithm();
}
