package computer;

import algorithms.memAlgorithms.MemTask;
import simulation.SimulationParameters;
import tools.Pair;
import tools.Tripple;

import java.util.*;

public class Process {
    private int id;
    private final int cpuId;
    public double loadOnProcessor;
    private int cpuTime;
    private int arrivalTime;
    private int remainingTime;
    public int finishTime;
    int state = 0;
    //frame allocation
    int pageCount;
    int ramRequestId=0;
    int memRequestId=0;

    ArrayList<Tripple<Integer,Integer,Integer>> memoryRequests = new ArrayList<>();
    ArrayList<Pair<Integer,Integer>> ramRequests = new ArrayList<>();

    public void restartTime() {
        ramRequestId=0;
        memRequestId=0;
        state=0;
        remainingTime=cpuTime;
        finishTime=(cpuTime == 0 ? arrivalTime : -1);
    }

    void generateDetails(int SIZE) {
        Random random = new Random();

        loadOnProcessor = random.nextDouble(SimulationParameters.MIN_PROCESS_LOAD_ON_PROCESSOR, SimulationParameters.MAX_PROCESS_LOAD_ON_PROCESSOR);

        pageCount = 1+random.nextInt(2, SimulationParameters.MAX_PROCESS_PAGE_NUMBER);
        int localityFactor = pageCount / 2;

        for (int i = 0; i < cpuTime-1; i++) {
            if (random.nextDouble(1) <= SimulationParameters.PROCESS_REQUEST_PROBABILITY_ON_TICK) {
                // ANY REQUEST
                if (random.nextDouble(1) <= SimulationParameters.PROCESS_REQUEST_PROBABILITY_RAM) {
                    // RAM request
                    int potentialRequest = random.nextInt(pageCount);
                    if (!ramRequests.isEmpty()) {
                        potentialRequest = ramRequests.getLast().second + random.nextInt(localityFactor) - (localityFactor / 2);
                    }
                    int request = Math.min(pageCount, Math.max(1,potentialRequest));

                    ramRequests.add(new Pair(i,request));


                } else {
                    // MEM request
                    memoryRequests.add(new Tripple(i, random.nextInt(SIZE+1), random.nextDouble(1) <= SimulationParameters.PROCESS_MEM_REQUEST_PRIORITY_PROBABILITY ? i+random.nextInt(SIZE/4) : -1));
                }
            }
        }

    }

    public Process(int id, int cpuId, int cpuTime, int arrivalTime, int SIZE) {
        this.id = id;
        this.cpuId = cpuId;
        this.cpuTime = cpuTime;
        this.remainingTime = cpuTime;
        this.arrivalTime = arrivalTime;
        this.finishTime = (cpuTime == 0 ? arrivalTime : -1);
        generateDetails(SIZE);
    }

    public int getCpuId() {
        return cpuId;
    }

    public void getMemoryAnswer(Integer answer) {
//        System.out.println("Process "+id+" received "+answer+" from HDD");
        state = 0;
    }
    public void getRamAnswer(Integer answer) {
//        System.out.println("Process "+id+" received "+answer+" from HDD");
        state = 0;
    }

    public void doStep() {
        if (state != 0) return;
        if (memRequestId < memoryRequests.size()) {
            Tripple<Integer, Integer, Integer> memRequest = memoryRequests.get(memRequestId);
            if (memRequest.first == cpuTime - remainingTime) {
                memRequestId++;
                COMPUTER.getMemoryScheduler().getMemoryRequest(this, memRequest.second, memRequest.third + (memRequest.third == -1 ? 0 : COMPUTER.curTime));
//            memoryRequests.remove(0);
                state = 1;
            }
        }
        if (ramRequestId < ramRequests.size()) {
            Pair<Integer, Integer> ramRequest = ramRequests.get(ramRequestId);
            if (ramRequest.first == cpuTime - remainingTime) {
                ramRequestId++;
                COMPUTER.getRamScheduler().getRamRequest(this, ramRequest.second);
//            ramRequests.remove(0);
                state = 2;
            }
        }
        remainingTime--;
        if (remainingTime == 0) {
            finishTime = COMPUTER.curTime;
            COMPUTER.ramSch.clearProcessFrames(this);
        }

    }

    public boolean isDone() {
        return remainingTime < 1;
    }

    public int getId() {
        return id;
    }
    public int getFinishTime() {
        return finishTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }
    public int getCpuTime() {
        return cpuTime;
    }

    public int getTurnAroundTime() {
        if (!isDone()) return -1;
        return finishTime - arrivalTime+1;
    }
    public int getWaitTime() {
        if (!isDone()) return -1;
        return getTurnAroundTime() - cpuTime;
    }


    //ame allocation


    public int getPageCount() {
        return pageCount;
    }

    @Override
    public String toString() {
        return id+" "+arrivalTime+" "+cpuTime+" "+finishTime+" "+getTurnAroundTime()+" "+getWaitTime()+" "+state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Process process = (Process) o;
        return id == process.id;
    }

}
