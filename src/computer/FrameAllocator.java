package computer;

import algorithms.frameAllocationAlgorithms.FrameAllocationAlgorithm;
import algorithms.frameAllocationAlgorithms.PROP;
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
    int window = 10;

    public FrameAllocator(FrameAllocationAlgorithm algorithm) {
        this.algorithm = algorithm;
        fetchProcessList();
        this.previousProcessCount = processList.size();
        this.needsRecalculation = true;
        this.prev = new ArrayList<>();
    }

    public void allocate() {
        processesStats.clear();
        for (Map.Entry<Process, Integer> entry : processesStatsMap.entrySet()) {
            processesStats.add(new Pair<>(entry.getKey(), entry.getValue()));
        }

        if (algorithm instanceof algorithms.frameAllocationAlgorithms.MANUAL) {
            algorithm.allocateFrames(processesStats, needsTriple);
            processesStats.clear();
            needsTriple = false;
            previousProcessCount = processList.size();


        } else if (algorithm instanceof algorithms.frameAllocationAlgorithms.EQUAL
                || algorithm instanceof PROP) {
            algorithm.allocateFrames(processesStats, needsTriple);
            processesStats.clear();
            previousProcessCount = processList.size();


        } else if (algorithm instanceof algorithms.frameAllocationAlgorithms.ZONAL) {

            processesStats.clear();
            for (Map.Entry<Process, Set<Integer>> entry : zonalPageAccessMap.entrySet()) {
                processesStats.add(new Pair<>(entry.getKey(), entry.getValue().size()));
            }
            algorithm.allocateFrames(processesStats, needsTriple);
            zonalPageAccessMap.clear();;
            needsTriple = false;
        }
    }

    public void doStep() {
        fetchProcessList();
        if (!processList.equals(prev)) {
            needsRecalculation = true;
        }
        if (needsRecalculation) {
            allocate();
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

            if (counter == window) {
                needsTriple = true;
                counter = 0;
            }
            if (needsTriple) {
                allocate();
            }
        } else if (algorithm instanceof algorithms.frameAllocationAlgorithms.ZONAL) {
            zonalCounter++;
            tempTriple = new Tripple<>(p, pageNumber, curTime);

            if (!zonalPageAccessMap.containsKey(p)) {
                zonalPageAccessMap.put(p, new HashSet<>());
            }

            Set<Integer> accessedPages = zonalPageAccessMap.get(p);
            accessedPages.add(pageNumber);

            if (zonalCounter == window) {
                zonalCounter = 0;
                needsTriple=true;
                allocate();
            }
        }
    }
}
