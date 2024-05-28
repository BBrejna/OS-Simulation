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

    public double getScore() {
        double waitTimeContribution = 0;
        double memRejectedContribution = 0;
        double ramPageErrorsContribution = 0;

        if (StatisticsHandler.maxAvgWaitTime != 0) {
            waitTimeContribution = avgWaitTime/StatisticsHandler.maxAvgWaitTime*0.7;
        }
        if (StatisticsHandler.maxRejectedMemRequests != 0) {
            memRejectedContribution = 1.*memRejected/StatisticsHandler.maxRejectedMemRequests*0.1;
        }
        if (StatisticsHandler.maxNoPageErrors != 0) {
            ramPageErrorsContribution = 1.*ramPageErrors/StatisticsHandler.maxNoPageErrors*0.2;
        }

        return waitTimeContribution+memRejectedContribution+ramPageErrorsContribution;
    }

}
