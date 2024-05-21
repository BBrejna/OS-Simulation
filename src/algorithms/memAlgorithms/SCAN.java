package algorithms.memAlgorithms;

public class SCAN extends MemAlgorithm {

    public SCAN(int size, int MODE) {
        super(size, MODE);
    }
    public SCAN(int size, int MODE, int position) {
        super(size, MODE, position);
    }

    public void doNormal(){

        /*for(int i = 0; i < waitingTasks.size(); i++)
            if (position == waitingTasks.get(i).cylinder) {
                System.out.println("Zakończono zadanie " + waitingTasks.get(i));
                waitingTasks.remove(i);
            }*/


        if (waitingTasks.get(0).cylinder == position) {
            //System.out.println("Zakończono zadanie priorytetowe " + waitingTasks.get(0));
            answerTask(waitingTasks, 0);
//            waitingTasks.remove(0);
        }

        if (!waitingTasks.isEmpty()) {

            if (beginToEnd)
                position++;
            else
                position--;

            if (position == SIZE && beginToEnd) {
                beginToEnd = false;
                MemTask.compareCurrentPositionB(waitingTasks, position);

            }
            if (position == 0 && !beginToEnd) {
                beginToEnd = true;
                MemTask.compareCurrentPositionF(waitingTasks, position);

            }
            steps++;
        }
    }


}
