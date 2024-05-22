package computer;

import algorithms.RamAllocation.RamAllocation;
import algorithms.RamAllocation.RamTask;
import algorithms.frameAllocation.FrameAllocation;
import algorithms.memAlgorithms.MemAlgorithm;

public class RamScheduler {
    public final RamAllocation algorithm;


    public RamScheduler(RamAllocation algorithm) {
        this.algorithm = algorithm;
    }

    // registerTask
    public void getRamRequest(Process p,int frame){
        algorithm.registerTask(p,frame);
    }

}



