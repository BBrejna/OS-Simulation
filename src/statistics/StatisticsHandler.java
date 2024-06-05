package statistics;

import java.util.ArrayList;
import java.util.Comparator;

public class StatisticsHandler {
    public static double maxAvgWaitTime=0;
    public static int maxRejectedMemRequests=0;
    public static int maxNoPageErrors=0;
    static ArrayList<InstanceInfo> results = new ArrayList<>();

    public static void checkMaxValues(InstanceInfo info) {
        maxAvgWaitTime = Math.max(maxAvgWaitTime, info.avgWaitTime);
        maxRejectedMemRequests = Math.max(maxRejectedMemRequests, info.memRejected);
        maxNoPageErrors = Math.max(maxNoPageErrors, info.ramPageErrors);
    }

    public static void registerInstance(InstanceInfo info) {
        results.add(info);
        checkMaxValues(info);
    }

    public static void printStatistics() {
        results.sort(Comparator.comparing(InstanceInfo::getScore));

        String separator = "-------------------------------------------------------------------------------------------------------------------------------------------------------%n";

        String format = "| %-8s | %-6s | %-15s | %-8s | %-8s | %5s | %7s | %9s | %9s | %9s | %4s | %7s | %9s | %4s |%n";

        System.out.printf(separator);
        System.out.printf(" Project statistics %n");
        System.out.printf(" Each possible combination %n");

        System.out.printf(separator);
        System.out.printf(format, "BALANCER", "CPU", "MEM", "RAM", "FRAMES", "PROC.", "CYCLES", "TURNAR.", "WAIT T.", "HDD ST.", "REJ.", "NO PAGE", "SCORE", "RANK");
        System.out.printf(separator);

        int rank = 1;
        for (InstanceInfo info : results) {
            System.out.printf(format, info.balancerAlgo, info.cpuAlgo, info.memAlgo, info.ramAlgo, info.frameAlgo, info.processesNumber, info.cyclesDone, String.format("%.1f", info.avgTurnaround), String.format("%.1f", info.avgWaitTime), info.memStepsDone, info.memRejected, info.ramPageErrors, String.format("%.4f", info.getScore()), rank++);
        }

        System.out.printf(separator);

    }
}
