package algorithms;

import java.util.ArrayList;
import computer.Process;

public class SRTF extends Algorithm {

    public SRTF(ArrayList<Process> processesList) {
        super(processesList);
    }

    public Process getActiveProcess() {
        Process shortest = processesList.getFirst();
        for (Process p : processesList) {
            if (p.getRemainingTime() < shortest.getRemainingTime()) {
                shortest = p;
            }
        }

        return shortest;
    }

}
