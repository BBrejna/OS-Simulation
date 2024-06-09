package algorithms.cpuAlgorithms;

import java.util.*;
import computer.Process;
import simulation.SimulationParameters;

public abstract class CpuAlgorithm {

    ArrayList<ArrayList<Process>> processesList = new ArrayList<>();

    public CpuAlgorithm() {
        for (int i = 0; i < SimulationParameters.PROCESSORS_NUMBER; i++) {
            processesList.add(new ArrayList<>());
        }
    }

    public abstract Process getActiveProcess(Integer processorId);

    public void updateList(Integer processorId, ArrayList<Process> processesList) {
        this.processesList.set(processorId, processesList);
    }

    public void restartTime() {
        for (int i = 0; i < SimulationParameters.PROCESSORS_NUMBER; i++) {
            processesList.get(i).clear();
        }
    }
}
