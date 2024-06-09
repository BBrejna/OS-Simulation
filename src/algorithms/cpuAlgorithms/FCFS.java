package algorithms.cpuAlgorithms;

import java.util.*;
import computer.Process;
import simulation.SimulationParameters;

public class FCFS extends CpuAlgorithm {

    ArrayList<Queue<Process>> processesQueue = new ArrayList<>();

    public FCFS() {
        for (int i = 0; i < SimulationParameters.PROCESSORS_NUMBER; i++) {
            processesQueue.add(new LinkedList<>());
        }
    }

    public Process getActiveProcess(Integer processorId) {
        return processesQueue.get(processorId).peek();
    }

    @Override
    public void updateList(Integer processorId, ArrayList<Process> processList) {
        processesQueue.set(processorId, new LinkedList<>(processList));
    }

    @Override
    public void restartTime() {
        for (int i = 0; i < SimulationParameters.PROCESSORS_NUMBER; i++) {
            processesQueue.get(i).clear();
        }
    }
}
