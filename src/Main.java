import algorithms.cpuAlgorithms.*;
import computer.*;

public class Main {
    public static void main(String[] args) {
        ProcessProvider pp = new ProcessProvider(10);

        Algorithm[] cpuAlgorithms = new Algorithm[]{new FCFS(), new RR(), new SJF(), new SRTF()};

        for (Algorithm cpuAlgo : cpuAlgorithms) {
            CpuScheduler cpuSch = new CpuScheduler(cpuAlgo);
            COMPUTER computer = new COMPUTER(pp, cpuSch);
            computer.doWork();
            computer.writeStats();
            computer.restartTime();
        }

//        Scanner scan = new Scanner(System.in);
//
//        String cur;
//        while (!Objects.equals(cur = scan.next(), "q")) {
//
//            if (cur.equals("start")) {
//                cpu.start();
//            }
//            else if (cur.equals("stop")) {
//                cpu.interrupt();
//                writeStats(cpu);
//            }
//            else if (cur.equals("start-stop")) {
//                int t = scan.nextInt();
//                cpu.start();
//                Thread.sleep(t);
//                cpu.interrupt();
//                writeStats(cpu);
//            }
//            else if (cur.equals("switch-algorithm")) {
//                String name = scan.next();
//                ArrayList<Process> processes = cpu.scheduler.activeList;
//                switch (name) {
//                    case "FCFS":
//                        cpu.scheduler.setCurAlgorithm(new FCFS(processes));
//                        break;
//                    case "SRTF":
//                        cpu.scheduler.setCurAlgorithm(new SRTF(processes));
//                        break;
//                    case "SJF":
//                        cpu.scheduler.setCurAlgorithm(new SJF(processes));
//                        break;
//                    case "RR":
//                        cpu.scheduler.setCurAlgorithm(new RR(processes));
//                        break;
//                    default:
//                        System.out.println("Given name of algorithm is incorrect!");
//
//                    cpu.scheduler.clearLists();
//                }
//            }
//            else if (cur.equals("set-generator")) {
//                cpu.scheduler = new CpuScheduler(new ProcessProvider());
//                cpu.scheduler.setCurAlgorithm(new FCFS(cpu.scheduler.activeList));
//            }
//            else if (cur.equals("show-stats")) {
//                writeStats(cpu);
//            }
//            else if (cur.equals("configure-generator")) {
//                ProcessProvider.maxCpuTime = scan.nextInt();
//                ProcessProvider.maxDeltaTime = scan.nextInt();
//            }
//            else if (cur.equals("configure-RR")) {
//                RR.deltaTime = scan.nextInt();
//            }
//        }

    }

}