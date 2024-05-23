package algorithms.ramAlgorithms;

import computer.COMPUTER;
import computer.Process;

import java.util.Random;

public class RAND extends RamAlgorithm {
    @Override
    public void normalRun(RamTask curTask) {
        if (checkPage(curTask) != -1) return;
        if (checkUsed() < checkSize(curTask.p)) {
            setFreeFrame(curTask.pageNumber);
            resultCounter++;
            return;
        }

        Random random = new Random();
        int id = random.nextInt(frames.size());
        COMPUTER.frames.set(frames.get(id), curTask.pageNumber);
        resultCounter++;
    }

    @Override
    public void clearProcess(Process p) {

    }

    @Override
    public void resetAlgorithm() {

    }
}
