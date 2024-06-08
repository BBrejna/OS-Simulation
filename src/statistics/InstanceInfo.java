package statistics;

public record InstanceInfo (int processesNumber,
        int cyclesDone,
        String balancerAlgo,
        String cpuAlgo,
        String memAlgo,
        String ramAlgo,
        String frameAlgo,
        int balancerLoadQueries,
        int balancerMigrations,
        double avgProcessorsLoad,
        double avgProcessorsLoadStdDev,
        double avgTurnaround,
        double avgWaitTime,
        int memStepsDone,
        int memRejected,
        int ramPageErrors) {


    public double getScore() {
        double waitTimeContribution = 0.85;
        double memRejectedContribution = 0.05;
        double ramPageErrorsContribution = 0.1;

        if (IterationStatisticsHandler.maxAvgWaitTime != 0) {
            waitTimeContribution = avgWaitTime/ IterationStatisticsHandler.maxAvgWaitTime*waitTimeContribution;
        }

        if (IterationStatisticsHandler.maxRejectedMemRequests != 0) {
            memRejectedContribution = 1.*memRejected/ IterationStatisticsHandler.maxRejectedMemRequests*memRejectedContribution;
        }

        if (IterationStatisticsHandler.maxNoPageErrors != 0) {
            ramPageErrorsContribution = 1.*ramPageErrors/ IterationStatisticsHandler.maxNoPageErrors*ramPageErrorsContribution;
        }

        return waitTimeContribution+memRejectedContribution+ramPageErrorsContribution;
    }

}
