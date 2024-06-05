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

//        System.out.println("BALANCING "+processorId+" AFTER "+process.getId());
//        System.out.println("MY LOAD: "+getProcessorLoad(processorId));

        if (getProcessorLoad(processorId) <= p) {
            assignProcessToProcessor(processorId, process);

//            System.out.println("MY LOAD: "+getProcessorLoad(processorId));

            if (getProcessorLoad(processorId) <= r) {
                java.util.Collections.shuffle(notSearchedProcessors);

                for (int loadedProcessorId : notSearchedProcessors) {
//                    System.out.println(getProcessorLoad(loadedProcessorId)+" "+getProcessorLoad(processorId));
                    while (getProcessorLoad(loadedProcessorId) > p && getProcessorLoad(processorId) <= p/2) {
                        migrateRandomProcess(loadedProcessorId, processorId);
                    }
                }

            }
            return;
        }

        java.util.Collections.shuffle(notSearchedProcessors);
        int destinationProcessor = processorId;
        for (int loadedProcessor : notSearchedProcessors) {
            if (getProcessorLoad(loadedProcessor) <= p) {
                destinationProcessor = loadedProcessor;
                break;
            }
        }

        assignProcessToProcessor(destinationProcessor, process);
    }
}
