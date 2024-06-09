package computer;

import java.util.*;

import algorithms.balanceAlgorithms.BalanceAlgorithm;
import algorithms.balanceAlgorithms.FIRST;
import algorithms.cpuAlgorithms.CpuAlgorithm;

public class CpuScheduler {
    public final CpuAlgorithm algorithm;
    public final BalanceAlgorithm balanceAlgorithm;

    public CpuScheduler(CpuAlgorithm algorithm, BalanceAlgorithm balanceAlgorithm) {
        this.algorithm=algorithm;
        this.balanceAlgorithm = balanceAlgorithm;
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
            algorithm.updateList(processorId, activeList);

            if (!activeList.isEmpty()) {
                Process p = algorithm.getActiveProcess(processorId);
                p.doStep();
            }

        }

        for (int i = 0; i < COMPUTER.processorsNumber; i++) {
            Double curAvg = COMPUTER.avgCpuLoad.get(i);

            int curTime = COMPUTER.curTime+1;

            curAvg *= 1.*(curTime-1)/curTime;

            curAvg += 1.*COMPUTER.cpuLoad.get(i)/curTime;

            COMPUTER.avgCpuLoad.set(i, curAvg);
        }
    }

    public void restartTime() {
        algorithm.restartTime();
        balanceAlgorithm.restartTime();
    }

}
