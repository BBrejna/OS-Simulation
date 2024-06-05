package algorithms.balanceAlgorithms;

import computer.COMPUTER;
import computer.Process;
import simulation.SimulationParameters;

import java.util.ArrayList;

public abstract class   BalanceAlgorithm {
    protected int totalProcessors;

    public BalanceAlgorithm() {
        this.totalProcessors = SimulationParameters.PROCESSORS_NUMBER;
    }

    public void balance(int processorId) {
        ArrayList<Process> waitingList = COMPUTER.waitingList.get(processorId);
        while (!waitingList.isEmpty()) {
            Process process = waitingList.remove(0);
            handleNewProcess(processorId, process);
        }
    }

    public abstract void handleNewProcess(int processorId, Process process);
}
