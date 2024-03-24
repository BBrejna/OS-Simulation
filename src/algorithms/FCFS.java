package algorithms;

import java.util.*;
import computer.Process;

public class FCFS extends Algorithm {

    Queue<Process> processesQueue;

    public FCFS(ArrayList<Process> processList) {
        super(processList);
        processesQueue = new LinkedList<>(processList);
    }

    public Process getActiveProcess() {
        return processesQueue.peek();
    }

    @Override
    public void updateList(ArrayList<Process> processList) {
        processesQueue = new LinkedList<>(processList);
    }
}
