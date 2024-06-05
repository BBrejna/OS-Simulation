package computer;

import java.util.*;

import algorithms.balanceAlgorithms.BalanceAlgorithm;
import algorithms.balanceAlgorithms.FIRST;
import algorithms.cpuAlgorithms.CpuAlgorithm;

public class CpuScheduler {
    public final CpuAlgorithm algorithm;
    /*SOMETHING*/
    private BalanceAlgorithm balanceAlgorithm = new FIRST();
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

            balanceAlgorithm.balance(processorId);

            ArrayList<Process> activeList = COMPUTER.activeList.get(processorId);
            ArrayList<Process> finishedList = COMPUTER.finishedList.get(processorId);

            for (int i = 0; i < activeList.size(); i++) {
                Process p = activeList.get(i);
                if (p.isDone()) {
                    COMPUTER.finishProcess(p, processorId);
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
