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

    public void restartTime() {
        resultCounter = 0;
        nextTask = null;
        frames = null;
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
        try {
            normalRun(curTask);
        } catch (Exception e) {
            System.out.println("ERROR CAUGHT IN RAM ALGORITHM "+curTask.p.getId()+" "+frames+" "+checkPage(curTask)+" "+checkUsed()+" "+checkSize(curTask.p));
            throw e;
        }
        COMPUTER.ramAnswer(curTask.p, curTask.pageNumber);
    };

    public int checkPage(RamTask t) {
        for (Integer frame : frames) {
            if (COMPUTER.frames.get(frame) == t.pageNumber) {
                return frame;
            }
        }
        return -1;
    }
    public int checkUsed() {
        int used=0;
        for (Integer frame : frames) {
            if (COMPUTER.frames.get(frame) != -1) {
                used++;
            }
        }
        return used;
    }
    public int checkSize(Process p) {
        return COMPUTER.ramSch.processFrameMap.get(p).size();
    }

    public int setFreeFrame(int pageNumber) {
        for (Integer frame : frames) {
            if (COMPUTER.frames.get(frame) == -1) {
                COMPUTER.frames.set(frame, pageNumber);
                return frame;
            }
        }
        return -1;
    }

    public int getPageErrorsNumber() {
        return resultCounter;
    }

    public abstract void normalRun(RamTask curTask);
    public abstract void clearProcess(Process p);
    public abstract void resetAlgorithm();
}
