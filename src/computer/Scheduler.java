package computer;

import java.util.*;
import algorithms.*;

public class Scheduler {
    private Algorithm curAlgorithm;
    public ArrayList<Process> activeList;
    public ArrayList<Process> finishedList;
    private ProcessProvider provider;

    public int workTime = 0;

    public Scheduler(ProcessProvider p) {
        curAlgorithm = null;
        activeList = new ArrayList<>();
        finishedList = new ArrayList<>();
        provider = p;
    }

    public void doWork() throws InterruptedException {
        workTime++;
        fetchFromProvider();
        for (int i = 0; i < activeList.size(); i++) {
            Process p = activeList.get(i);
            if (p.isDone()) {
                finishedList.add(p);
                activeList.remove(i);
                i--;
            }
        }
        curAlgorithm.updateList(activeList);

        if (!activeList.isEmpty()) {
            Process p = curAlgorithm.getActiveProcess();
//            System.out.println(CPU.curTime + " " + p.getId());
            p.doStep();
        }
    }

    public void fetchFromProvider() {
        while (ProcessProvider.isActive() && provider.isReady()) {
            activeList.add(provider.getNextProcess());
        }
    }

    public void setCurAlgorithm(Algorithm algorithm) {
        this.curAlgorithm = algorithm;
        workTime = 0;
        finishedList.clear();
        provider.id = 0;
    }

    public void clearLists() {
        finishedList.clear();
        activeList.clear();
    }
}
