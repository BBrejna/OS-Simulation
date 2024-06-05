package algorithms.balanceAlgorithms;

import simulation.SimulationParameters;

public abstract class   BalanceAlgorithm {
    protected int totalProcessors;

    public BalanceAlgorithm() {
        this.totalProcessors = SimulationParameters.PROCESSORS_NUMBER;
    }
    public abstract void balance(int processorId);
}
