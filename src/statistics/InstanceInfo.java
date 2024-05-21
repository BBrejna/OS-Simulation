package statistics;

public class InstanceInfo {
    int processesNumber;
    int cyclesDone;
    String cpuAlgo;
    String memAlgo;
    double avgTurnaround;
    double avgWaitTime;
    int memStepsDone;
    int memRejected;

    public InstanceInfo(int processesNumber, int cyclesDone, String cpuAlgo, String memAlgo, double avgTurnaround, double avgWaitTime, int memStepsDone, int memRejected) {
        this.processesNumber = processesNumber;
        this.cyclesDone = cyclesDone;
        this.cpuAlgo = cpuAlgo;
        this.memAlgo = memAlgo;
        this.avgTurnaround = avgTurnaround;
        this.avgWaitTime = avgWaitTime;
        this.memStepsDone = memStepsDone;
        this.memRejected = memRejected;
    }

    public double costValue() {
        return cyclesDone;
    }

}
