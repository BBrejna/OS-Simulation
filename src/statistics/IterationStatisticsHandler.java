package statistics;

import java.util.ArrayList;
import java.util.Comparator;

public class IterationStatisticsHandler {
    public static double maxAvgWaitTime=0;
    public static int maxRejectedMemRequests=0;
    public static int maxNoPageErrors=0;
    static ArrayList<InstanceInfo> results = new ArrayList<>();

    public static void checkMaxValues(InstanceInfo info) {
        maxAvgWaitTime = Math.max(maxAvgWaitTime, info.avgWaitTime());
        maxRejectedMemRequests = Math.max(maxRejectedMemRequests, info.memRejected());
        maxNoPageErrors = Math.max(maxNoPageErrors, info.ramPageErrors());
    }

    public static void registerInstance(InstanceInfo info) {
        results.add(info);
        checkMaxValues(info);
    }

    public static void printStatistics(Integer topInstances) {
        results.sort(Comparator.comparing(InstanceInfo::getScore));

        StringBuilder separator = new StringBuilder();
        separator.append("-".repeat(192));
        separator.append("%n");

        String format = "| %-8s | %7s | %7s | %6s | %8s | %-6s | %-15s | %-8s | %-8s | %5s | %7s | %9s | %9s | %9s | %4s | %7s | %9s | %4s |%n";

        System.out.printf(separator.toString());
        System.out.printf(" Iteration statistics %n");
        System.out.printf(" Top "+topInstances+" best combinations %n");

        System.out.printf(separator.toString());
        System.out.printf(format, "BALANCER", "BAL L_Q", "BAL MIG", "P LOAD", "P L SDEV", "CPU", "MEM", "RAM", "FRAMES", "PROC.", "CYCLES", "TURNAR.", "WAIT T.", "HDD ST.", "REJ.", "NO PAGE", "SCORE", "RANK");
        System.out.printf(separator.toString());

        int rank = 1;
        for (InstanceInfo info : results) {
            if (rank > topInstances) break;
            System.out.printf(format, info.balancerAlgo(), info.balancerLoadQueries(), info.balancerMigrations(), String.format("%.4f", info.avgProcessorsLoad()), String.format("%.4f", info.avgProcessorsLoadStdDev()), info.cpuAlgo(), info.memAlgo(), info.ramAlgo(), info.frameAlgo(), info.processesNumber(), info.cyclesDone(), String.format("%.1f", info.avgTurnaround()), String.format("%.1f", info.avgWaitTime()), info.memStepsDone(), info.memRejected(), info.ramPageErrors(), String.format("%.4f", info.getScore()), rank++);
        }

        System.out.printf(separator.toString());

    }
    public static void printStatistics() { printStatistics(results.size()); }

    public static void flushIterationStatistics() {
        for (InstanceInfo info : results) {
            SimulationStatisticsHandler.registerInstance(new InstanceRecord(info.balancerAlgo(), info.cpuAlgo(), info.memAlgo(), info.ramAlgo(), info.frameAlgo()), info.getScore());
        }
        maxAvgWaitTime = 0.;
        maxRejectedMemRequests = 0;
        maxNoPageErrors = 0;
        results.clear();
    }
}
