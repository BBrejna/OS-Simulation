package algorithms.balanceAlgorithms;

import computer.COMPUTER;
import computer.Process;
import simulation.SimulationParameters;

import java.util.ArrayList;

public abstract class BalanceAlgorithm {
    protected int totalProcessors;
    public int loadQueries;
    public int migrationsNumber;

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

    public void removeProcessFromProcessor(int processorId, Process process) {
        COMPUTER.activeList.get(processorId).remove(process);
        COMPUTER.cpuLoad.set(processorId, COMPUTER.cpuLoad.get(processorId) - process.loadOnProcessor);
    }
    public void assignProcessToProcessor(int processorId, Process process) {
        COMPUTER.activeList.get(processorId).add(process);
        COMPUTER.cpuLoad.set(processorId, COMPUTER.cpuLoad.get(processorId) + process.loadOnProcessor);
        if (processorId != process.getCpuId() || process.getCpuTime() != process.getRemainingTime()) migrationsNumber++;
    }
    public void migrateRandomProcess(int sourceProcessorId, int destinationProcessorId) {
        Process process = COMPUTER.activeList.get(sourceProcessorId).getLast();
        removeProcessFromProcessor(sourceProcessorId, process);
        assignProcessToProcessor(destinationProcessorId, process);
    }

    public void restartTime() {
        totalProcessors = SimulationParameters.PROCESSORS_NUMBER;
        loadQueries = 0;
        migrationsNumber = 0;
    }

    public double getProcessorLoad(int processorId) {
        loadQueries++;
        return COMPUTER.cpuLoad.get(processorId);
    }

    public abstract void handleNewProcess(int processorId, Process process);
}
