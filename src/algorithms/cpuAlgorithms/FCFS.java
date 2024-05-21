package algorithms.cpuAlgorithms;

import java.util.*;
import computer.Process;

public class FCFS extends CpuAlgorithm {

    Queue<Process> processesQueue;

    public FCFS(ArrayList<Process> processList) {
        super(processList);
        processesQueue = new LinkedList<>(processList);
    }
    public FCFS() {
        this(new ArrayList<>());
    }

    public Process getActiveProcess() {
        return processesQueue.peek();
    }

    @Override
    public void updateList(ArrayList<Process> processList) {
        processesQueue = new LinkedList<>(processList);
    }
}
