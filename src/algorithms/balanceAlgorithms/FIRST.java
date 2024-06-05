package algorithms.balanceAlgorithms;

import computer.Process;
import simulation.SimulationParameters;

import java.util.Random;

public class FIRST extends BalanceAlgorithm {
    private final double p = SimulationParameters.UPPER_PROCESSOR_LOAD_BOUND;
    private final int z = SimulationParameters.FINDING_OTHER_PROCESSOR_TRIES;
    private final Random random = new Random();

    @Override
    public void handleNewProcess(int processorId, Process process) {
        int tries = 0;
        boolean processAssigned = false;

        while (tries < z && !processAssigned) {
            int randomProcessorId = random.nextInt(totalProcessors);
            if (randomProcessorId != processorId) {
                double randomProcessorLoad = getProcessorLoad(randomProcessorId);
                if (randomProcessorLoad < p) {
                    assignProcessToProcessor(randomProcessorId, process);
                    processAssigned = true;
                }
            }
            tries++;
        }

        if (!processAssigned) {
            assignProcessToProcessor(processorId, process);
        }
    }
}
