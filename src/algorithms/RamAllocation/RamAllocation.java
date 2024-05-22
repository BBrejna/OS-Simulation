package algorithms.RamAllocation;

import computer.Process;
import java.util.HashMap;
import java.util.Map;

public abstract class RamAllocation {
    protected Map<Process, Integer> processFrameMap;

    public RamAllocation() {
        processFrameMap = new HashMap<>();
    }

    public void registerTask(Process p, int frame) {
        if (p != null && frame >= 0) {
            processFrameMap.put(p, frame);
        }
    }
}
