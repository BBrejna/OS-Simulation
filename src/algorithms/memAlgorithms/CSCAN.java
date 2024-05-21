package algorithms.memAlgorithms;

public class CSCAN extends Algorithm{
    boolean lockedReturn = false;

    public CSCAN(int size, int MODE) {
        super(size, MODE);
    }
    public CSCAN(int size, int MODE, int position) {
        super(size, MODE, position);
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
            Task.compareCurrentPositionF(waitingTasks, position);
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

}
