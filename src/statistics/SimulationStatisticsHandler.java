package statistics;

import simulation.SimulationParameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationStatisticsHandler {
    static HashMap<InstanceRecord, Double> instances = new HashMap<>();
    static Integer iterationsNumber = SimulationParameters.ITERATIONS_NUMBER;

    public static void registerInstance(InstanceRecord instance, Double value) {
        instances.put(instance, instances.getOrDefault(instance, 0.)+value);
    }

    public static void printStatistics() {
        for (InstanceRecord instance : instances.keySet()) {
            instances.put(instance, instances.get(instance)/iterationsNumber);
        }

        List<HashMap.Entry<InstanceRecord, Double>> sortedEntries = new ArrayList<>(instances.entrySet());
        sortedEntries.sort(HashMap.Entry.comparingByValue());

        String separator = "--------------------------------------------------------------------------------%n";

        String format = "| %-8s | %-6s | %-15s | %-8s | %-8s | %9s | %4s |%n";

        System.out.printf(separator);
        System.out.printf(" Project statistics %n");
        System.out.printf(" Each possible combination, average over "+iterationsNumber+" iterations%n");

        System.out.printf(separator);
        System.out.printf(format, "BALANCER", "CPU", "MEM", "RAM", "FRAMES", "SCORE", "RANK");
        System.out.printf(separator);

        int rank = 1;
        for (Map.Entry<InstanceRecord, Double> entry : sortedEntries) {
            InstanceRecord instance = entry.getKey();
            Double value = entry.getValue();
            System.out.printf(format, instance.balancerAlgo(), instance.cpuAlgo(), instance.memAlgo(), instance.ramAlgo(), instance.frameAlgo(), String.format("%.4f", value), rank++);
        }
        System.out.printf(separator);
    }
}
