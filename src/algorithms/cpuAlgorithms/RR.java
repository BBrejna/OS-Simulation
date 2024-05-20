package algorithms.cpuAlgorithms;

import java.util.ArrayList;
import computer.Process;

public class RR extends Algorithm {
    public static int deltaTime = 1;
    int index;
    int remainingDelta;

    public RR(ArrayList<Process> processesList) {
        super(processesList);
        index = 0;
        remainingDelta = deltaTime;
    }
    public RR() {
        this(new ArrayList<>());
    }

    public Process getActiveProcess() {
        if (index >= processesList.size()) {
            index = 0;
            remainingDelta = deltaTime;
        }

        if (remainingDelta == 0) {
            remainingDelta = deltaTime;
            index++;
        }

        if (index >= processesList.size()) index = 0;
        remainingDelta--;
        return processesList.get(index);
    }
}
