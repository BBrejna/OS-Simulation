package algorithms.memAlgorithms;

public class FCFS extends Algorithm {

    public FCFS(int size, int MODE) {
        super(size, MODE);
    }
    public FCFS(int size, int MODE, int position) {
        super(size, MODE, position);
    }

    public void doNormal(){
        //fcfs
        if (position == waitingTasks.get(0).cylinder) {
            //System.out.println("Zakończono zadanie " + waitingTasks.get(0));
            answerTask(waitingTasks,0);
//            waitingTasks.remove(0);
        }
        if (!waitingTasks.isEmpty()) {
            //Collections.sort(waitingTasks, Task.arriveTimeComparator);
            if (waitingTasks.get(0).cylinder > position)
                position++;
            else
                position--;
            steps++;
        }
    }

}