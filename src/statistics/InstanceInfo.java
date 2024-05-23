package statistics;

public class InstanceInfo {
    int processesNumber;
    int cyclesDone;
    String cpuAlgo;
    String memAlgo;
    String ramAlgo;
    String frameAlgo;
    double avgTurnaround;
    double avgWaitTime;
    int memStepsDone;
    int memRejected;
    int ramPageErrors;

    public InstanceInfo(int processesNumber, int cyclesDone, String cpuAlgo, String memAlgo, String ramAlgo, String frameAlgo, double avgTurnaround, double avgWaitTime, int memStepsDone, int memRejected, int ramPageErrors) {
        this.processesNumber = processesNumber;
        this.cyclesDone = cyclesDone;
        this.cpuAlgo = cpuAlgo;
        this.memAlgo = memAlgo;
        this.ramAlgo = ramAlgo;
        this.frameAlgo = frameAlgo;
        this.avgTurnaround = avgTurnaround;
        this.avgWaitTime = avgWaitTime;
        this.memStepsDone = memStepsDone;
        this.memRejected = memRejected;
        this.ramPageErrors = ramPageErrors;
    }

    public double costValue() {
        return cyclesDone;
    }

}
