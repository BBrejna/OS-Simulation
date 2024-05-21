package algorithms.cpuAlgorithms;

import java.util.*;
import computer.Process;

public abstract class CpuAlgorithm {

    ArrayList<Process> processesList;

    public CpuAlgorithm(ArrayList<Process> processesList) {
        this.processesList = processesList;
    }

    public abstract Process getActiveProcess();

    public void updateList(ArrayList<Process> processesList) {
        this.processesList = processesList;
    }
}
