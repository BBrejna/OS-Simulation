package algorithms.balanceAlgorithms;

import computer.Process;
import simulation.SimulationParameters;

import java.util.ArrayList;
import java.util.Random;

public class SECOND extends BalanceAlgorithm {
    private final double p = SimulationParameters.UPPER_PROCESSOR_LOAD_BOUND;
    private final Random random = new Random();

    @Override
    public void handleNewProcess(int processorId, Process process) {
        if (getProcessorLoad(processorId) > p) {
            ArrayList<Integer> triedProcessors = new ArrayList<>();
            boolean allProcessorsOverloaded = true;

            for (int i = 0; i < totalProcessors; i++) {
                if (i != processorId) {
                    triedProcessors.add(i);
                }
            }

            while (!triedProcessors.isEmpty()) {
                int randomIndex = random.nextInt(triedProcessors.size());
                int randomProcessorId = triedProcessors.remove(randomIndex);

                if (getProcessorLoad(randomProcessorId) <= p) {
                    assignProcessToProcessor(randomProcessorId, process);
                    allProcessorsOverloaded = false;
                    break;
                }
            }

            if (allProcessorsOverloaded) {
                assignProcessToProcessor(processorId, process);
            }
        } else {
            assignProcessToProcessor(processorId, process);
        }
    }
}
