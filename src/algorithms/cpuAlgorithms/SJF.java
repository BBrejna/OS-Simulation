package algorithms.cpuAlgorithms;

import computer.Process;

import java.util.ArrayList;

public class SJF extends Algorithm {

    Process active;

    public SJF(ArrayList<Process> processesList) {
        super(processesList);
    }
    public SJF() {
        this(new ArrayList<>());
    }

    public Process getActiveProcess() {
        if (active != null && !active.isDone()) {
            return active;
        }

        active = processesList.getFirst();
        for (Process p : processesList) {
            if (p.getRemainingTime() < active.getRemainingTime()) {
                active = p;
            }
        }

        return active;
    }

    @Override
    public void updateList(ArrayList<Process> processesList) {
        super.updateList(processesList);
        if (!this.processesList.contains(active))
            active = null;
    }
}
