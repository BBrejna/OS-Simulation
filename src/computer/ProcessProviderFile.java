package computer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.util.Collections.sort;

class ProcessFile implements Comparable<ProcessFile> {
    Process p;
    int arrivalTime;

    public ProcessFile(Process p, int arrivalTime) {
        this.p = p;
        this.arrivalTime = arrivalTime;
    }

    public String toString() {
        return arrivalTime + " " + p;
    }

    @Override
    public int compareTo(ProcessFile other) {
        return Integer.compare(arrivalTime, other.arrivalTime);
    }
}

public class ProcessProviderFile extends ProcessProvider {

    int id = 0;
    ArrayList<ProcessFile> processesList;

    public ProcessProviderFile(String filename) throws FileNotFoundException {
        active = true;
        Scanner scan = new Scanner(new File(filename));
        processesList = new ArrayList<>();
        int id, cpuTime, arrivalTime;

        while (scan.hasNext()) {
            id = scan.nextInt();
            cpuTime = scan.nextInt();
            arrivalTime = scan.nextInt()+CPU.curTime-1;
            processesList.add(new ProcessFile(new Process(id,cpuTime,arrivalTime), arrivalTime));
        }

        sort(processesList);
//        System.out.println(processesList);

        makeTimeNext();
    }

    private void makeTimeNext() {
        int curArrivalTime = processesList.get(id).arrivalTime;
        int prevArrivalTime = (id > 0 ? processesList.get(id-1).arrivalTime : CPU.curTime-2);
        timeNext = curArrivalTime - prevArrivalTime;
    }
    private boolean isEmpty() {
        return id >= processesList.size();
    }

    @Override
    public Process getNextProcess() {
        if (isEmpty()) {
            switchOff();
            return null;
        }

        Process p = processesList.get(id++).p;
        if (isEmpty()) {
            switchOff();
            timeNext = 0;
        } else {
            makeTimeNext();
        }

        return p;
    }

}
