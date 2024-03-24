package algorithms;

import java.util.*;
import computer.Process;

public abstract class Algorithm {

    ArrayList<Process> processesList;

    public Algorithm(ArrayList<Process> processesList) {
        this.processesList = processesList;
    }

    public abstract Process getActiveProcess() throws InterruptedException;

    public void updateList(ArrayList<Process> processesList) {
        this.processesList = processesList;
    }
}
