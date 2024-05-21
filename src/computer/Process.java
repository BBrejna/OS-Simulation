package computer;

import tools.Tripple;

import java.util.ArrayList;
import java.util.Random;

public class Process {

    private int id;
    private int cpuTime;
    private int arrivalTime;
    private int remainingTime;
    public int finishTime;
    int state = 0;

    ArrayList<Tripple<Integer,Integer,Integer>> memoryRequests = new ArrayList<>();
//    ArrayList<Pair<Integer,Integer>> ramRequests = new ArrayList<>();

    void generateRequests(int SIZE) {
        Random random = new Random();

        for (int i = 0; i < remainingTime; i++) {
            if (random.nextInt(10) < 5) { // todo < 3
                if (random.nextInt(3) < 1) { //todo < 2
                    // RAM request
                } else {
                    // MEM request
                    memoryRequests.add(new Tripple(i, random.nextInt(SIZE+1), random.nextInt(10)<8 ? -1 : i+random.nextInt(SIZE/2)));
                }
            }
        }
    }

//    public Process(int id, int cpuTime) {
//        this.arrivalTime = COMPUTER.curTime; // todo -1
//
//        generateRequests();
//    }
    public Process(int id, int cpuTime, int arrivalTime, int SIZE) {
        this.id = id;
        this.cpuTime = cpuTime;
        this.remainingTime = cpuTime;
        this.arrivalTime = arrivalTime;
        this.finishTime = (cpuTime == 0 ? arrivalTime : -1);

        generateRequests(SIZE);
    }

    public void getMemoryAnswer(Integer answer) {
//        System.out.println("Process "+id+" received "+answer+" from HDD");
        state = 0;
    }

    public void doStep() {
        if (state != 0) return;
        remainingTime--;
        while (!memoryRequests.isEmpty() && memoryRequests.get(0).first == cpuTime-remainingTime) {
            COMPUTER.getMemoryScheduler().getMemoryRequest(this, memoryRequests.get(0).second, COMPUTER.curTime+memoryRequests.get(0).third);
            memoryRequests.remove(0);
            state = 1;
        }
        if (remainingTime == 0) finishTime = COMPUTER.curTime;
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
        return finishTime - arrivalTime;
    }
    public int getWaitTime() {
        if (!isDone()) return -1;
        return getTurnAroundTime() - cpuTime;
    }

    @Override
    public String toString() {
        return id+" "+arrivalTime+" "+cpuTime+" "+finishTime+" "+getTurnAroundTime()+" "+getWaitTime()+" "+state;
    }
}
