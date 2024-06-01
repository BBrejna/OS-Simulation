package computer;

import java.util.*;

import algorithms.cpuAlgorithms.CpuAlgorithm;

public class CpuScheduler {
    public final CpuAlgorithm algorithm;
//    public final CpuBalanceAlgorithm balanceAlgorithm;

    public CpuScheduler(CpuAlgorithm algorithm) {
        this.algorithm=algorithm;
    }

    public void doStep() {
        ArrayList<Integer> randomPermutation = new ArrayList<>();
        for (int i = 0; i < COMPUTER.processorsNumber; i++) {
            randomPermutation.add(i);
        }
        java.util.Collections.shuffle(randomPermutation);

        for (int processorId : randomPermutation) {

//            balanceAlgorithm.balance(processorId);

            ArrayList<Process> activeList = COMPUTER.activeList.get(processorId);
            ArrayList<Process> finishedList = COMPUTER.finishedList.get(processorId);


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
    }

}
