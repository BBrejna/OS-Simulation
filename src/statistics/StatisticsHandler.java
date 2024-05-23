package statistics;

import java.util.ArrayList;
import java.util.Comparator;

public class StatisticsHandler {
    static ArrayList<InstanceInfo> results = new ArrayList<>();

    public static void registerInstance(InstanceInfo info) {
        results.add(info);
    }

    public static void printStatistics() {
        results.sort(Comparator.comparing(InstanceInfo::costValue));

        System.out.printf("---------------------------------------------------------------------------------------------------------------------%n");
        System.out.printf(" Project statistics %n");
        System.out.printf(" Each possible combination %n");

        System.out.printf("---------------------------------------------------------------------------------------------------------------------%n");
        System.out.printf("| %-6s | %-15s | %-8s | %-8s | %5s | %7s | %8s | %8s | %7s | %4s | %7s |%n", "CPU", "MEM", "RAM", "FRAMES", "PROC.", "CYCLES", "TURNAR.", "WAIT T.", "HDD ST.", "REJ.", "NO PAGE");
        System.out.printf("---------------------------------------------------------------------------------------------------------------------%n");

        for (InstanceInfo info : results) {
            System.out.printf("| %-6s | %-15s | %-8s | %-8s | %5s | %7s | %8s | %8s | %7s | %4s | %7s |%n", info.cpuAlgo, info.memAlgo, info.ramAlgo, info.frameAlgo, info.processesNumber, info.cyclesDone, info.avgTurnaround, info.avgWaitTime, info.memStepsDone, info.memRejected, info.ramPageErrors);
        }

        System.out.printf("---------------------------------------------------------------------------------------------------------------------%n");

    }
}
