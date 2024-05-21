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

        System.out.printf("-------------------------------------------------------------------------------------%n");
        System.out.printf(" Project statistics         %n");
        System.out.printf(" Each possible pair         %n");

        System.out.printf("-------------------------------------------------------------------------------------%n");
        System.out.printf("| %-6s | %-15s | %5s | %7s | %8s | %8s | %7s | %4s |%n", "CPU", "MEM", "PROC.", "CYCLES", "TURNAR.", "WAIT T.", "HDD ST.", "REJ.");
        System.out.printf("-------------------------------------------------------------------------------------%n");

        for (InstanceInfo info : results) {
            System.out.printf("| %-6s | %-15s | %5s | %7s | %8s | %8s | %7s | %4s |%n", info.cpuAlgo, info.memAlgo, info.processesNumber, info.cyclesDone, info.avgTurnaround, info.avgWaitTime, info.memStepsDone, info.memRejected);
        }

        System.out.printf("-------------------------------------------------------------------------------------%n");

    }
}
