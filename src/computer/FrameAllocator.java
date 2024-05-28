package computer;

import algorithms.frameAllocationAlgorithms.FrameAllocationAlgorithm;
import algorithms.frameAllocationAlgorithms.PROP;
import simulation.SimulationParameters;
import tools.Pair;
import tools.Tripple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FrameAllocator {
    public final FrameAllocationAlgorithm algorithm;
    private ArrayList<Process> processList;
    private int previousProcessCount;
    private boolean needsRecalculation;
    public Map<Process, ArrayList<Integer>> processFrameMap = new HashMap<>();
    public ArrayList<Pair<Process, Integer>> processesStats = new ArrayList<>();
    private Map<Process, Integer> processesStatsMap = new HashMap<>();
    Tripple<Process, Integer, Integer> tempTriple;
    ArrayList<Process> prev;
    private Map<Process, Set<Integer>> pageAccessMap = new HashMap<>();
    private Map<Process, Integer> pageFaultsMap = new HashMap<>();
    private Map<Process, Set<Integer>> zonalPageAccessMap = new HashMap<>();
    private int zonalCounter = 0;
    boolean needsTriple = false;
    int counter;
    int window_manual = SimulationParameters.MANUAL_WINDOW;
    int window_zonal = SimulationParameters.ZONAL_WINDOW;


    public FrameAllocator(FrameAllocationAlgorithm algorithm) {
        fetchProcessList();
        this.algorithm = algorithm;
        this.previousProcessCount = processList.size();
        this.prev = new ArrayList<>();
    }

    public void allocate() {
        if(algorithm instanceof algorithms.frameAllocationAlgorithms.EQUAL  || algorithm instanceof algorithms.frameAllocationAlgorithms.PROP){
            algorithm.allocateFrames(processesStats, needsTriple);
            processesStats.clear();
            previousProcessCount = processList.size();
        }

        if (algorithm instanceof algorithms.frameAllocationAlgorithms.MANUAL) {
            processesStats.clear();
            for (Map.Entry<Process, Integer> entry : processesStatsMap.entrySet()) {
                processesStats.add(new Pair<>(entry.getKey(), entry.getValue()));
            }
            algorithm.allocateFrames(processesStats, needsTriple);
            processesStats.clear();
            needsTriple = false;
            previousProcessCount = processList.size();

        } else if (algorithm instanceof algorithms.frameAllocationAlgorithms.ZONAL) {
            processesStats.clear();
            for (Map.Entry<Process, Set<Integer>> entry : zonalPageAccessMap.entrySet()) {
                processesStats.add(new Pair<>(entry.getKey(), entry.getValue().size()));
            }
            algorithm.allocateFrames(processesStats, needsTriple);
            needsTriple = false;
            previousProcessCount = processList.size();
        }
    }

    public void doStep() {
        fetchProcessList();
        if (!processList.equals(prev)) {
            needsRecalculation = true;
        }
        if (needsRecalculation || needsTriple ) {
            allocate();
            needsRecalculation = false;
        }
        prev = new ArrayList<>(processList);
    }

    private void fetchProcessList() {
        processList = COMPUTER.activeList;
    }
    public void registerAnsweredRequest(Process p, int pageNumber, int curTime) {
        if (algorithm instanceof algorithms.frameAllocationAlgorithms.MANUAL) {
            counter++;
            tempTriple = new Tripple<>(p, pageNumber, curTime);

            if (!pageAccessMap.containsKey(p)) {
                pageAccessMap.put(p, new HashSet<>());
            }

            Set<Integer> accessedPages = pageAccessMap.get(p);
            if (!accessedPages.contains(pageNumber)) {
                accessedPages.add(pageNumber);
                pageFaultsMap.put(p, pageFaultsMap.getOrDefault(p, 0) + 1);
            }

            processesStatsMap.put(p, pageFaultsMap.get(p));

            if (counter >= window_manual) {
                needsTriple = true;
                counter = 0;
            }
        } else if (algorithm instanceof algorithms.frameAllocationAlgorithms.ZONAL) {
            zonalCounter++;
            tempTriple = new Tripple<>(p, pageNumber, curTime);
            if (!zonalPageAccessMap.containsKey(p)) {
                zonalPageAccessMap.put(p, new HashSet<>());
            }
            Set<Integer> accessedPages = zonalPageAccessMap.get(p);
            accessedPages.add(pageNumber);

            if (zonalCounter >= window_zonal) {
                zonalCounter = 0;
                needsTriple = true;
            }
        }
    }
}
