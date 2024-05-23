package computer;

import algorithms.ramAlgorithms.RamAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RamScheduler {
    public final RamAlgorithm algorithm;
    public final FrameAllocator frameAllocator;
    public Map<Process, ArrayList<Integer>> processFrameMap = new HashMap<>();
    ArrayList<Integer> freeFrames;
    public final int SIZE;

    public RamScheduler(RamAlgorithm algorithm, FrameAllocator frameAllocator, int SIZE) {
        this.algorithm = algorithm;
        this.frameAllocator = frameAllocator;
        this.SIZE = SIZE;
    }

    // registerTask
    public void getRamRequest(Process p,int page){
        algorithm.registerTask(p,page);
    }
/*
    public void assignFrame(Process p, int frame) {
        if (p != null && 0 <= frame && frame <= SIZE) {
            if (!processFrameMap.containsKey(p)) processFrameMap.put(p,new ArrayList<>());
            processFrameMap.get(p).add(frame);
            freeFrames.remove((Object) frame);
        }
    }
    public void stealFrame(Process p, int frame) {
        if (p != null && 0 <= frame && frame <= SIZE) {
            processFrameMap.get(p).remove(frame);
            freeFrames.add(frame);
        }
    }*/
    public void clearProcessFrames(Process p) {
        if (p != null) {
            algorithm.clearProcess(p);
        }
    }

    public FrameAllocator getFrameAllocator() { return frameAllocator; }

    public void doStep() {
        frameAllocator.doStep();
        algorithm.answer();
    }
}



