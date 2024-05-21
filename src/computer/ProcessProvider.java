package computer;

import java.util.ArrayList;
import java.util.Random;

public class ProcessProvider {
    public static int maxCpuTime = 30;
    public static int maxDeltaTime = 30;
    public int totalProcessesNumber;
    public int processesGenerated=0;
    public int timeNext=0;
    private int SIZE;

    private ArrayList<Process> processes = new ArrayList<>();

    private void generateProcesses() {
        processes.clear();
        Random random = new Random();
        int curTime = 0;
        for (int i = 0; i < totalProcessesNumber; i++) {
            int cpuTime = random.nextInt(1,maxCpuTime+1);
            curTime = curTime + random.nextInt(maxDeltaTime);
            processes.add(new Process(i, cpuTime, curTime, SIZE));
        }
    }

    public void restartTime() {
        for (int i = 0; i < totalProcessesNumber; i++) {
            int cpuTime = processes.get(i).getCpuTime();
            int arrivalTime = processes.get(i).getArrivalTime();
            processes.set(i, new Process(i,cpuTime,arrivalTime,SIZE));
        }
        timeNext = processes.get(0).getArrivalTime();
        processesGenerated = 0;
    }

    public void restartProvider() {
        generateProcesses();
        restartTime();
    }

    public ProcessProvider(int totalProcessesNumber, int SIZE) {
        this.totalProcessesNumber = totalProcessesNumber;
        this.SIZE = SIZE;

        restartProvider();
    }

    private void newProcess() {
        Process p = processes.get(processesGenerated++);

        if (processesGenerated == totalProcessesNumber) {
            timeNext = -1;
        } else {
            timeNext = processes.get(processesGenerated).getArrivalTime();
        }

        COMPUTER.registerProcess(p);
    }


    public void doStep() {
        if (processesGenerated == totalProcessesNumber) return;
        while (COMPUTER.curTime == timeNext) newProcess();
    }

}
