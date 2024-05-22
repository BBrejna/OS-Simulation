package algorithms.ramAlgorithms;

import computer.COMPUTER;
import computer.Process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class RamAlgorithm {
    ArrayList<RamTask> tasks;

    public RamAlgorithm() {

    }

    public void registerTask(Process p, int page) {
        tasks.add(new RamTask(p, page));
    }

    public void answer() {
        if (tasks.isEmpty()) return;
        RamTask curTask = tasks.getFirst();
        COMPUTER.ramSch.getFrameAllocator().registerAnsweredRequest(curTask.p, curTask.pageNumber, COMPUTER.curTime);
        // todo continue
    };
}
