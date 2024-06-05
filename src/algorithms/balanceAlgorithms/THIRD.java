package algorithms.balanceAlgorithms;

import computer.Process;
import simulation.SimulationParameters;

import java.util.ArrayList;
import java.util.Random;

public class THIRD extends BalanceAlgorithm {
    private final double p = SimulationParameters.UPPER_PROCESSOR_LOAD_BOUND;
    private final double r = SimulationParameters.LOWER_PROCESSOR_LOAD_BOUND;
    private final Random random = new Random();
    @Override
    public void handleNewProcess(int processorId, Process process) {
        ArrayList<Integer> notSearchedProcessors = new ArrayList<>();
        for (int i = 0; i < totalProcessors; i++) {
            if (i == processorId) continue;
            notSearchedProcessors.add(i);
        }

        if (getProcessorLoad(processorId) <= p) {

        }
    }
}
