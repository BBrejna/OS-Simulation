import computer.*;
import computer.Process;
import algorithms.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        CPU cpu = new CPU(new Scheduler(new ProcessProvider()));
        cpu.scheduler.setCurAlgorithm(new FCFS(cpu.scheduler.activeList));
        cpu.active = true;

        Scanner scan = new Scanner(System.in);

        String cur;
        while (!Objects.equals(cur = scan.next(), "q")) {

            if (cur.equals("start")) {
                cpu.start();
            }
            else if (cur.equals("stop")) {
                cpu.interrupt();
                writeStats(cpu);
            }
            else if (cur.equals("start-stop")) {
                int t = scan.nextInt();
                cpu.start();
                Thread.sleep(t);
                cpu.interrupt();
                writeStats(cpu);
            }
            else if (cur.equals("switch-algorithm")) {
                String name = scan.next();
                ArrayList<Process> processes = cpu.scheduler.activeList;
                switch (name) {
                    case "FCFS":
                        cpu.scheduler.setCurAlgorithm(new FCFS(processes));
                        break;
                    case "SRTF":
                        cpu.scheduler.setCurAlgorithm(new SRTF(processes));
                        break;
                    case "SJF":
                        cpu.scheduler.setCurAlgorithm(new SJF(processes));
                        break;
                    case "RR":
                        cpu.scheduler.setCurAlgorithm(new RR(processes));
                        break;
                    default:
                        System.out.println("Given name of algorithm is incorrect!");

                    cpu.scheduler.clearLists();
                }
            }
            else if (cur.equals("load-list")) {
                String fileName = scan.next();
                try {
                    cpu.scheduler = new Scheduler(new ProcessProviderFile(fileName));
                } catch (FileNotFoundException e) {
                    System.out.println("Given file does not exist!");
                }
                cpu.scheduler.setCurAlgorithm(new FCFS(cpu.scheduler.activeList));
            }
            else if (cur.equals("set-generator")) {
                cpu.scheduler = new Scheduler(new ProcessProvider());
                cpu.scheduler.setCurAlgorithm(new FCFS(cpu.scheduler.activeList));
            }
            else if (cur.equals("show-stats")) {
                writeStats(cpu);
            }
            else if (cur.equals("configure-generator")) {
                ProcessProvider.maxCpuTime = scan.nextInt();
                ProcessProvider.maxDeltaTime = scan.nextInt();
            }
            else if (cur.equals("configure-RR")) {
                RR.deltaTime = scan.nextInt();
            }
        }

    }

    public static void writeStats(CPU cpu) {
        double avg_wait = 0, avg_turnaround = 0;
        ArrayList<Process> processes = cpu.scheduler.finishedList;
        for (Process p : processes) {
            System.out.println(p);
            avg_wait += p.getWaitTime();
            avg_turnaround += p.getTurnAroundTime();
        }
        if (!processes.isEmpty()) {
            avg_wait /= processes.size();
            avg_turnaround /= processes.size();
        }
        System.out.println("Avg turnaround time: " + avg_turnaround);
        System.out.println("Avg wait time: " + avg_wait);
        System.out.println("# of processes: " + processes.size());
        System.out.println("Cycles done: " + cpu.scheduler.workTime);
    }
}