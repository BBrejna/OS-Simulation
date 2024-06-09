package algorithms.cpuAlgorithms;

import java.util.ArrayList;
import computer.Process;
import simulation.SimulationParameters;

public class RR extends CpuAlgorithm {
    public static int deltaTime = SimulationParameters.RR_DELTA_TIME;
    ArrayList<Integer> index = new ArrayList<>();
    ArrayList<Integer> remainingDelta = new ArrayList<>();

    public RR() {
        super();
        for (int i = 0; i < SimulationParameters.PROCESSORS_NUMBER; i++) {
            index.add(0);
            remainingDelta.add(deltaTime);
        }
    }

    public Process getActiveProcess(Integer processorId) {
        Integer indexP = index.get(processorId);
        Integer remainingDeltaP = remainingDelta.get(processorId);
        ArrayList<Process> processesListP = processesList.get(processorId);

        if (indexP >= processesListP.size()) {
            indexP = 0;
            remainingDeltaP = deltaTime;
        }

        if (remainingDeltaP == 0) {
            remainingDeltaP = deltaTime;
            indexP++;
        }

        if (indexP >= processesListP.size()) indexP = 0;

        remainingDeltaP--;

        index.set(processorId, indexP);
        remainingDelta.set(processorId, remainingDeltaP);

        return processesListP.get(indexP);
    }

    @Override
    public void restartTime() {
        super.restartTime();
        index = new ArrayList<>();
        remainingDelta = new ArrayList<>();
        for (int i = 0; i < SimulationParameters.PROCESSORS_NUMBER; i++) {
            index.add(0);
            remainingDelta.add(deltaTime);
        }
    }
}
