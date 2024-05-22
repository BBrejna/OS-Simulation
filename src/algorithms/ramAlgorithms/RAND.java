package algorithms.ramAlgorithms;

import computer.COMPUTER;
import computer.Process;

import java.util.Random;

public class RAND extends RamAlgorithm {
    @Override
    public void normalRun(RamTask curTask) {
        if (checkPage(curTask) != -1) return;
        if (checkUsed(curTask.p) < checkSize(curTask.p)) {
            setFreeFrame(curTask);
            resultCounter++;
            return;
        }

        Random random = new Random();
        int id = random.nextInt(frames.size());
        COMPUTER.frames.set(frames.get(id), curTask);
        resultCounter++;
    }

    @Override
    public void clearProcess(Process p) {

    }

    @Override
    public void resetAlgorithm() {

    }
}
