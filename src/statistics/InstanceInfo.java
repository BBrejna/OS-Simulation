package statistics;

public class InstanceInfo {
    int processesNumber;
    int cyclesDone;
    String balancerAlgo;
    String cpuAlgo;
    String memAlgo;
    String ramAlgo;
    String frameAlgo;
    double avgTurnaround;
    double avgWaitTime;
    int memStepsDone;
    int memRejected;
    int ramPageErrors;

    public InstanceInfo(int processesNumber, int cyclesDone, String balancerAlgo, String cpuAlgo, String memAlgo, String ramAlgo, String frameAlgo, double avgTurnaround, double avgWaitTime, int memStepsDone, int memRejected, int ramPageErrors) {
        this.processesNumber = processesNumber;
        this.cyclesDone = cyclesDone;
        this.balancerAlgo = balancerAlgo;
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

        if (IterationStatisticsHandler.maxAvgWaitTime != 0) {
            waitTimeContribution = avgWaitTime/ IterationStatisticsHandler.maxAvgWaitTime*0.85;
        } else waitTimeContribution = 0.85;

        if (IterationStatisticsHandler.maxRejectedMemRequests != 0) {
            memRejectedContribution = 1.*memRejected/ IterationStatisticsHandler.maxRejectedMemRequests*0.05;
        } else memRejectedContribution = 0.05;

        if (IterationStatisticsHandler.maxNoPageErrors != 0) {
            ramPageErrorsContribution = 1.*ramPageErrors/ IterationStatisticsHandler.maxNoPageErrors*0.1;
        } else ramPageErrorsContribution = 0.1;

        return waitTimeContribution+memRejectedContribution+ramPageErrorsContribution;
    }

}
