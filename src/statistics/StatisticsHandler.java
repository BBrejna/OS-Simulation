package statistics;

import java.util.ArrayList;
import java.util.Comparator;

public class StatisticsHandler {
    static ArrayList<InstanceInfo> results = new ArrayList<>();

    public static void registerInstance(InstanceInfo info) {
        results.add(info);
    }

    public static void printStatistics() {
        results.sort(Comparator.comparing(InstanceInfo::getScore));

        String separator = "--------------------------------------------------------------------------------------------------------------------------------------------%n";

        String format = "| %-6s | %-15s | %-8s | %-8s | %5s | %7s | %9s | %9s | %9s | %4s | %7s | %9s | %4s |%n";

        System.out.printf(separator);
        System.out.printf(" Project statistics %n");
        System.out.printf(" Each possible combination %n");

        System.out.printf(separator);
        System.out.printf(format, "CPU", "MEM", "RAM", "FRAMES", "PROC.", "CYCLES", "TURNAR.", "WAIT T.", "HDD ST.", "REJ.", "NO PAGE", "SCORE", "RANK");
        System.out.printf(separator);

        int rank = 1;
        for (InstanceInfo info : results) {
            System.out.printf(format, info.cpuAlgo, info.memAlgo, info.ramAlgo, info.frameAlgo, info.processesNumber, info.cyclesDone, String.format("%.1f", info.avgTurnaround), String.format("%.1f", info.avgWaitTime), info.memStepsDone, info.memRejected, String.format("%.1f", info.ramPageErrors), String.format("%.1f", info.getScore()), rank++);
        }

        System.out.printf(separator);

    }
}
