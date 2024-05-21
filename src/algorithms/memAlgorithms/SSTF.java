package algorithms.memAlgorithms;

import computer.Process;

public class SSTF extends MemAlgorithm {

    public SSTF(int size, int MODE) {
        super(size, MODE);
    }
    public SSTF(int size, int MODE, int position) {
        super(size, MODE, position);
    }

    @Override
    public void registerTask (Process p, int cylinder, int toDoTime, boolean priority){
        super.registerTask(p, cylinder, toDoTime, priority);

        MemTask.compareTasks(waitingTasks, position);
    }


    public void doNormal(){
        if (position == waitingTasks.get(0).cylinder) {
            //System.out.println("Zakończono zadanie " + waitingTasks.get(0));
            answerTask(waitingTasks, 0);
//            waitingTasks.remove(0);
        }

        if (waitingTasks.size() != 0) {
            MemTask.compareTasks(waitingTasks, position);
            if (waitingTasks.get(0).cylinder > position) {
                position++;
            }else if(waitingTasks.get(0).cylinder < position) {
                position--;
            }
            steps++;
        }
    }

}