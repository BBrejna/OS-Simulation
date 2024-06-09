package algorithms.cpuAlgorithms;

import java.util.ArrayList;
import computer.Process;

public class SRTF extends CpuAlgorithm {

    public SRTF() {
        super();
    }

    public Process getActiveProcess(Integer processorId) {
        Process shortest = processesList.get(processorId).getFirst();
        for (Process p : processesList.get(processorId)) {
            if (p.getRemainingTime() < shortest.getRemainingTime()) {
                shortest = p;
            }
        }

        return shortest;
    }

}
