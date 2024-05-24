package algorithms.memAlgorithms;

public class FCFS extends MemAlgorithm {

    public FCFS(int MODE) {
        super(MODE);
    }
    public FCFS(int MODE, int position) {
        super(MODE, position);
    }

    public void doNormal(){
        //fcfs
        while (!waitingTasks.isEmpty() && position == waitingTasks.get(0).cylinder) {
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