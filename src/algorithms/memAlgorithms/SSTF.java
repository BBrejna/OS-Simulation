package algorithms.memAlgorithms;

import computer.Process;

public class SSTF extends MemAlgorithm {

    public SSTF(int MODE) {
        super(MODE);
    }

    @Override
    public void registerTask (Process p, int cylinder, int toDoTime, boolean priority){
        super.registerTask(p, cylinder, toDoTime, priority);

        MemTask.compareTasks(waitingTasks, position);
    }


    public void doNormal(){
        MemTask.compareTasks(waitingTasks, position);
        while (!waitingTasks.isEmpty() && position == waitingTasks.get(0).cylinder) {
            //System.out.println("Zakończono zadanie " + waitingTasks.get(0));
            answerTask(waitingTasks, 0);
//            waitingTasks.remove(0);
        }

        if (!waitingTasks.isEmpty()) {
            if (waitingTasks.get(0).cylinder > position) {
                position++;
            }else if(waitingTasks.get(0).cylinder < position) {
                position--;
            }
            steps++;
        }
    }

}