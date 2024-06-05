package algorithms.balanceAlgorithms;

import computer.COMPUTER;
import computer.Process;

import java.util.ArrayList;

public class FIRST extends BalanceAlgorithm {
    @Override
    public void balance(int processorId) {
        ArrayList<Process> waitingList = COMPUTER.waitingList.get(processorId);
        ArrayList<Process> activeList = COMPUTER.activeList.get(processorId);
        while (!waitingList.isEmpty()) {
            Process process = waitingList.remove(0);
            activeList.add(process);
        }
    }
}
