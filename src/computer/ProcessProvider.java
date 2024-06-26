package computer;

import java.util.ArrayList;
import java.util.Random;
import simulation.SimulationParameters;

public class ProcessProvider {
    public static int maxCpuTime = SimulationParameters.MAX_PROCESS_CPU_TIME;
    public static int maxDeltaTime = SimulationParameters.MAX_PROCESS_DELTA_TIME;
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

            int cpuId = random.nextInt(SimulationParameters.PROCESSORS_NUMBER);
            processes.add(new Process(i, cpuId, cpuTime, curTime, SIZE));
        }
    }

    public void restartTime() {
        for (int i = 0; i < totalProcessesNumber; i++) {
//            int cpuTime = processes.get(i).getCpuTime();
//            int arrivalTime = processes.get(i).getArrivalTime();
//            processes.set(i, new Process(i,cpuTime,arrivalTime,SIZE));
            processes.get(i).restartTime();
        }
        timeNext = processes.get(0).getArrivalTime();
        processesGenerated = 0;
    }

    public void restartProvider() {
        generateProcesses();
        restartTime();
    }

    public ProcessProvider() {
        this.totalProcessesNumber = SimulationParameters.PROCESSES_NUMBER;
        this.SIZE = SimulationParameters.MEM_SIZE;

        restartProvider();
    }

    private void newProcess() {
        Process p = processes.get(processesGenerated++);

        if (processesGenerated == totalProcessesNumber) {
            timeNext = -1;
        } else {
            timeNext = processes.get(processesGenerated).getArrivalTime();
        }

        COMPUTER.registerProcess(p, p.getCpuId());
    }


    public void doStep() {
        if (processesGenerated == totalProcessesNumber) return;
        while (COMPUTER.curTime == timeNext) newProcess();
    }

}
