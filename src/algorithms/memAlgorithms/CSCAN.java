package algorithms.memAlgorithms;

public class CSCAN extends MemAlgorithm {
    boolean lockedReturn = false;

    public CSCAN(int MODE) {
        super(MODE);
    }

    public void doNormal(){
        if (lockedReturn) {
            if (position == 0) {
                lockedReturn = false;
            }
            position--;
            steps++;
            return;
        }

        for(int i = 0; i < waitingTasks.size(); i++)
            if (position == waitingTasks.get(i).cylinder) {
                //System.out.println("Zakończono zadanie " + waitingTasks.get(i));
                answerTask(waitingTasks,i);
                i--;
            }

        if (!waitingTasks.isEmpty()) {
            MemTask.compareCurrentPositionF(waitingTasks, position);
            if (position == SIZE) {
                lockedReturn = true;
                position--;
                steps++;
                return;
//                position = 0;
//                steps += SIZE-2;
            }
            position++;
            steps++;
        }
    }

    @Override
    public void restartTime() {
        super.restartTime();
        lockedReturn = false;
    }

}
