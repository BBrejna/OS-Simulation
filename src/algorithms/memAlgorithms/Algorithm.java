package algorithms.memAlgorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import computer.Process;

public abstract class Algorithm {

    int steps = 0;
    int position = 0;
    int currentTime = 0;
    int rejected = 0;
    int tasksLeft = 0;
    boolean beginToEnd = true;
    int SIZE;
    int MODE;

    ArrayList<Integer> memory = new ArrayList<>();

    ArrayList<Task> waitingTasks = new ArrayList<Task>();
    ArrayList<Task> waitingPriorityTasks = new ArrayList<Task>();

    public Algorithm(int SIZE, int MODE) {
        this(SIZE, MODE, 0);
    }
    public Algorithm(int SIZE, int MODE, int position) {
        this.SIZE = SIZE;
        this.MODE = MODE;
        this.position = position;

        Random random = new Random();
        for (int i = 0; i <= SIZE; i++) {
            memory.set(0,random.nextInt((int)1e9));
        }
    }

    public void doStep() {
        if (MODE == 0) normal();
        else if (MODE == 1) edf();
        else if (MODE == 2) fdScan();
    }

    public void registerTask(Process p, int cylinder, int toDoTime) {
        if (toDoTime == -1 || MODE == 0) {
            waitingTasks.add(new Task(p,cylinder,toDoTime));
        } else {
            waitingPriorityTasks.add(new Task(p,cylinder,toDoTime));
        }
    }
    public void registerTask(Process p, int cylinder) {
        registerTask(p, cylinder, -1);
    }

    protected void answerTask(Task t) {
        t.p.getMemoryAnswer(memory.get(position));
    }
    protected void answerTask(ArrayList<Task> tasksList, int id) {
        Task t = tasksList.get(id);
        answerTask(t);
        tasksList.remove(id);
    }

    protected void doPriorityEdf(){

        Collections.sort(waitingPriorityTasks, Task.toDoTimeComparator);
        if (!waitingPriorityTasks.isEmpty() && position == waitingPriorityTasks.get(0).cylinder)
            if (waitingPriorityTasks.get(0).toDoTime < Math.abs(waitingPriorityTasks.get(0).cylinder - position))
                rejected++;

        if (waitingPriorityTasks.get(0).cylinder == position) {
            //System.out.println("Zakończono zadanie priorytetowe " + waitingPriorityTasks.get(0));
            answerTask(waitingPriorityTasks,0);
        }

        Collections.sort(waitingPriorityTasks, Task.toDoTimeComparator);

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

        Collections.sort(waitingPriorityTasks, Task.toDoTimeComparator);
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

        Collections.sort(waitingPriorityTasks, Task.toDoTimeComparator);

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
}
