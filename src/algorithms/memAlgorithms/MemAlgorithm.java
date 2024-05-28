package algorithms.memAlgorithms;

import java.util.ArrayList;
import java.util.Collections;

import computer.COMPUTER;
import computer.Process;
import simulation.SimulationParameters;

public abstract class MemAlgorithm {

    int steps = 0;
    int position = 0;
    int currentTime = 0;
    int rejected = 0;
    int tasksLeft = 0;
    boolean beginToEnd = true;
    public int SIZE;
    public int MODE;


    ArrayList<MemTask> waitingTasks = new ArrayList<MemTask>();
    ArrayList<MemTask> waitingPriorityTasks = new ArrayList<MemTask>();

    public MemAlgorithm(int MODE) {
        this(MODE, 0);
    }
    public MemAlgorithm(int MODE, int position) {
        this.SIZE = SimulationParameters.MEM_SIZE;
        this.MODE = MODE;
        this.position = position;

    }

    public void restartTime() {
        position=0;
        rejected=0;
        steps=0;
    }

    public void registerTask(Process p, int cylinder, int toDoTime, boolean priority) {
        if (priority) {
            waitingPriorityTasks.add(new MemTask(p,cylinder,toDoTime));
        } else {
            waitingTasks.add(new MemTask(p,cylinder,toDoTime));
        }
    }

    protected void answerTask(ArrayList<MemTask> tasksList, int id) {
        MemTask t = tasksList.get(id);
        COMPUTER.memAnswer(t.p, COMPUTER.memory.get(position));
        tasksList.remove(id);
    }

    protected void doPriorityEdf(){

        Collections.sort(waitingPriorityTasks, MemTask.toDoTimeComparator);
        if (!waitingPriorityTasks.isEmpty() && position == waitingPriorityTasks.get(0).cylinder)
            if (waitingPriorityTasks.get(0).toDoTime < Math.abs(waitingPriorityTasks.get(0).cylinder - position))
                rejected++;

        if (waitingPriorityTasks.get(0).cylinder == position) {
            //System.out.println("Zakończono zadanie priorytetowe " + waitingPriorityTasks.get(0));
            answerTask(waitingPriorityTasks,0);
        }

        Collections.sort(waitingPriorityTasks, MemTask.toDoTimeComparator);

        if (!waitingPriorityTasks.isEmpty() ) {

            if (waitingPriorityTasks.get(0).cylinder > position)
                position++;
            else
                position--;

            steps++;

            for (int i = 0; i < waitingPriorityTasks.size(); i++)
                waitingPriorityTasks.get(i).toDoTime--;
        }
    }

    protected void doPriorityFdScan(){

        Collections.sort(waitingPriorityTasks, MemTask.toDoTimeComparator);
        if (!waitingPriorityTasks.isEmpty() && position == waitingPriorityTasks.get(0).cylinder)
            if (waitingPriorityTasks.get(0).toDoTime < Math.abs(waitingPriorityTasks.get(0).cylinder - position))
                rejected++;

        for (int i = 0; i < waitingPriorityTasks.size(); i++) {
            waitingPriorityTasks.get(i).toDoTime--;
            if (position == waitingPriorityTasks.get(i).cylinder) {
                //System.out.println("Zakończono zadanie real time " + waitingPriorityTasks.get(i));
                answerTask(waitingPriorityTasks,i);
                i--;
            }
        }

        for(int i = 0; i < waitingTasks.size(); i++)
            if (waitingTasks.get(i).cylinder == position) {
                //System.out.println("Zakończono zadanie priorytetowe " + waitingPriorityTasks.get(0));
                answerTask(waitingTasks,i);
                i--;
            }

        Collections.sort(waitingPriorityTasks, MemTask.toDoTimeComparator);

        if (!waitingPriorityTasks.isEmpty()) {
            if (waitingPriorityTasks.get(0).cylinder > position)
                position++;
            else
                position--;
            steps++;
        }
    }


    abstract void doNormal();

    public void normal(){
        if (!waitingTasks.isEmpty()) {
            doNormal();
        }
    }

    public void edf() {

        if (!waitingPriorityTasks.isEmpty())
            doPriorityEdf();
        else if (!waitingTasks.isEmpty())
            doNormal();

        currentTime++;

    }

    public void fdScan() {
        if (!waitingPriorityTasks.isEmpty())
            doPriorityFdScan();
        else if (!waitingTasks.isEmpty())
            doNormal();

        currentTime++;
    }

    public int getRejected() {
        return rejected;
    }
    public int getSteps() { return steps; }

    @Override
    public String toString() {
        return "MemAlgorithm{" +
                "steps=" + steps +
                ", position=" + position +
                ", currentTime=" + currentTime +
                ", rejected=" + rejected +
                ", tasksLeft=" + tasksLeft +
                ", beginToEnd=" + beginToEnd +
                ", SIZE=" + SIZE +
                ", MODE=" + MODE +
                ", waitingTasks=" + waitingTasks +
                ", waitingPriorityTasks=" + waitingPriorityTasks +
                '}';
    }
}
