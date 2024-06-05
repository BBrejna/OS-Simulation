package computer;

import algorithms.frameAllocationAlgorithms.FrameAllocationAlgorithm;
import simulation.SimulationParameters;
import tools.Pair;
import tools.Tripple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FrameAllocator {
    int activeSize;
    public final FrameAllocationAlgorithm algorithm;
    private ArrayList<Process> processList;
    private ArrayList<ArrayList<Process>> processListSecond;

    private Map<Integer, ArrayList<Process>> processMap = new HashMap<>();
    private Map<Integer, ArrayList<Process>> processPrevMap = new HashMap<>();

    //private Map<Integer, ArrayList<Process>> processPrevMap = new HashMap<>();

    private int previousProcessCount;
    private boolean needsRecalculation;
    public ArrayList<Pair<Process, Integer>> processesStats = new ArrayList<>();
    private Map<Process, Integer> processesStatsMap = new HashMap<>();
    private Tripple<Process, Integer, Integer> tempTriple;
    private Map<Process, Set<Integer>> pageAccessMap = new HashMap<>();
    private Map<Process, Integer> pageFaultsMap = new HashMap<>();
    private Map<Process, Set<Integer>> zonalPageAccessMap = new HashMap<>();
    private int zonalCounter = 0;
    private boolean needsTriple = false;
    private int counter;
    private int window_manual = SimulationParameters.MANUAL_WINDOW;
    private int window_zonal = SimulationParameters.ZONAL_WINDOW;
    private boolean firstRun = true;

    public FrameAllocator(FrameAllocationAlgorithm algorithm) {
        this.algorithm = algorithm;
        fetchProcessList();
    }

    public void allocate() {
        if (algorithm instanceof algorithms.frameAllocationAlgorithms.EQUAL || algorithm instanceof algorithms.frameAllocationAlgorithms.PROP) {
            algorithm.allocateFrames(processesStats, needsTriple);
            processesStats.clear();
        }

        if (algorithm instanceof algorithms.frameAllocationAlgorithms.MANUAL) {
            processesStats.clear();
            for (Map.Entry<Process, Integer> entry : processesStatsMap.entrySet()) {
                processesStats.add(new Pair<>(entry.getKey(), entry.getValue()));
            }
            algorithm.allocateFrames(processesStats, needsTriple);
            processesStats.clear();
            needsTriple = false;
        } else if (algorithm instanceof algorithms.frameAllocationAlgorithms.ZONAL) {
            processesStats.clear();
            for (Map.Entry<Process, Set<Integer>> entry : zonalPageAccessMap.entrySet()) {
                processesStats.add(new Pair<>(entry.getKey(), entry.getValue().size()));
            }
            algorithm.allocateFrames(processesStats, needsTriple);
            needsTriple = false;
        }
    }

    public void doStep() {
        fetchProcessList();
        int k = 0;
        for (ArrayList<Process> list : processListSecond) {
            ArrayList<Process> prevList = processPrevMap.get(k);
            if (!list.equals(prevList)) {
                needsRecalculation = true;
            }
            if (needsRecalculation || needsTriple) {
                allocate();
                needsRecalculation = false;
            }
            processPrevMap.put(k, new ArrayList<>(list));
            k++;
        }
    }

    private void fetchProcessList() {
        processListSecond = COMPUTER.activeList;
        if (firstRun) {
            int k = 0;
            for (ArrayList<Process> list : processListSecond) {
                processPrevMap.put(k, new ArrayList<>(list));
                k++;
            }
            firstRun = false;
        }
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
    public void restartTime() {
        processListSecond = new ArrayList<>();
        processPrevMap.clear();
        needsRecalculation = false;
        processesStats.clear();
        processesStatsMap.clear();
        tempTriple = null;
        pageAccessMap.clear();
        pageFaultsMap.clear();
        zonalPageAccessMap.clear();
        zonalCounter = 0;
        needsTriple = false;
        counter = 0;
        firstRun = true;

    }
}
