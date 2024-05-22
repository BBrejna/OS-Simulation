package algorithms.frameAllocationAlgorithms;

import computer.COMPUTER;
import computer.Process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EQUAL extends FrameAllocationAlgorithm {

    public EQUAL(int totalFrames) {
        super(totalFrames);
    }

    @Override
    public void allocateFrames() {
        ArrayList<Process> processesList = COMPUTER.activeList;

        Map<Process, Integer> allocationMap = new HashMap<>();
        int processesNum = processesList.size();
        if (processesNum == 0) return ;

        int frameToAllocate = totalFrames / processesNum;
        for (Process p : processesList) {
            allocationMap.put(p, frameToAllocate);
        }

        return ;
    }
}
