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
    double ramPageErrors;

    public InstanceInfo(int processesNumber, int cyclesDone, String cpuAlgo, String memAlgo, String ramAlgo, String frameAlgo, double avgTurnaround, double avgWaitTime, int memStepsDone, int memRejected, double ramPageErrors) {
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

    public double getScore() {
        return avgWaitTime + memRejected*0.1 + ramPageErrors*0.2;
    }

}
