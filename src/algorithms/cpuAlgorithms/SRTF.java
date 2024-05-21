package algorithms.cpuAlgorithms;

import java.util.ArrayList;
import computer.Process;

public class SRTF extends CpuAlgorithm {

    public SRTF(ArrayList<Process> processesList) {
        super(processesList);
    }
    public SRTF() {
        this(new ArrayList<>());
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
