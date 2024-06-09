package algorithms.cpuAlgorithms;

import computer.Process;
import simulation.SimulationParameters;

import java.util.ArrayList;

public class SJF extends CpuAlgorithm {

    ArrayList<Process> active = new ArrayList<>();

    public SJF() {
        super();
        for (int i = 0; i < SimulationParameters.PROCESSORS_NUMBER; i++) {
            active.add(null);
        }
    }

    public Process getActiveProcess(Integer processorId) {
        Process activeP = active.get(processorId);
        if (activeP != null && !activeP.isDone()) {
            return activeP;
        }

        activeP = processesList.get(processorId).getFirst();
        for (Process p : processesList.get(processorId)) {
            if (p.getRemainingTime() < activeP.getRemainingTime()) {
                activeP = p;
            }
        }

        active.set(processorId, activeP);

        return activeP;
    }

    @Override
    public void updateList(Integer processorId, ArrayList<Process> processesList) {
        super.updateList(processorId, processesList);
        if (!this.processesList.get(processorId).contains(active.get(processorId)))
            active.set(processorId, null);
    }

    @Override
    public void restartTime() {
        active.clear();
        for (int i = 0; i < SimulationParameters.PROCESSORS_NUMBER; i++) {
            active.add(null);
        }
    }
}
