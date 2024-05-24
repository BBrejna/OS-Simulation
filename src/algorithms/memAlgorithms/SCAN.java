package algorithms.memAlgorithms;

public class SCAN extends MemAlgorithm {

    public SCAN(int MODE) {
        super(MODE);
    }
    public SCAN(int MODE, int position) {
        super(MODE, position);
    }

    public void doNormal(){

        /*for(int i = 0; i < waitingTasks.size(); i++)
            if (position == waitingTasks.get(i).cylinder) {
                System.out.println("Zakończono zadanie " + waitingTasks.get(i));
                waitingTasks.remove(i);
            }*/

        for(int i = 0; i < waitingTasks.size(); i++)
            if (position == waitingTasks.get(i).cylinder) {
                //System.out.println("Zakończono zadanie " + waitingTasks.get(i));
                answerTask(waitingTasks,i);
                i--;
            }

        if (!waitingTasks.isEmpty()) {
            if (position == SIZE && beginToEnd) {
                beginToEnd = false;
                MemTask.compareCurrentPositionB(waitingTasks, position);

            }
            if (position == 0 && !beginToEnd) {
                beginToEnd = true;
                MemTask.compareCurrentPositionF(waitingTasks, position);

            }

            if (beginToEnd)
                position++;
            else
                position--;

            steps++;
        }
    }


}
