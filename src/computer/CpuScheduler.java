package computer;

import java.util.*;

import algorithms.cpuAlgorithms.CpuAlgorithm;

public class CpuScheduler {
    public final CpuAlgorithm algorithm;
    private ArrayList<Process> activeList = null;
    private ArrayList<Process> finishedList = null;

    public CpuScheduler(CpuAlgorithm algorithm) {
        this.algorithm=algorithm;
        fetchProcessLists();
    }

    public void doStep() {
        fetchProcessLists();

        for (int i = 0; i < activeList.size(); i++) {
            Process p = activeList.get(i);
            if (p.isDone()) {
                finishedList.add(p);
                activeList.remove(i);
                i--;
            }
        }
        algorithm.updateList(activeList);

        if (!activeList.isEmpty()) {
            Process p = algorithm.getActiveProcess();
            p.doStep();
        }
    }

    private void fetchProcessLists() {
        activeList = COMPUTER.activeList;
        finishedList = COMPUTER.finishedList;
    }
}
