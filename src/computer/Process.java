package computer;

public class Process {

    private int id;
    private int cpuTime;
    private int arrivalTime;
    private int remainingTime;
    public int finishTime;

    public Process() {
        id=-1;
        cpuTime = 0;
        remainingTime = 0;
        finishTime = 0;
    }
    public Process(int id, int cpuTime) {
        this.id = id;
        this.cpuTime = cpuTime;
        this.remainingTime = cpuTime;
        this.arrivalTime = COMPUTER.curTime-1;
        this.finishTime = (cpuTime == 0 ? arrivalTime : -1);
    }
    public Process(int id, int cpuTime, int arrivalTime) {
        this(id, cpuTime);
        this.arrivalTime = arrivalTime;
        if (cpuTime == 0) this.finishTime = arrivalTime;
    }

    public void doStep() {
        remainingTime--;
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
        return id+" "+arrivalTime+" "+cpuTime+" "+finishTime+" "+getTurnAroundTime()+" "+getWaitTime();
    }
}
