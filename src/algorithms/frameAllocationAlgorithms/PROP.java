package algorithms.frameAllocationAlgorithms;

import computer.COMPUTER;
import computer.Process;
import tools.Pair;

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;

public class PROP extends FrameAllocationAlgorithm {

    private static final Logger LOGGER = Logger.getLogger(PROP.class.getName());

    @Override
    public void allocateFrames(ArrayList<Pair<Process, Integer>> Triples, boolean needsTriple) {
        ArrayList<ArrayList<Process>> processesList = COMPUTER.activeList;
        Map<Process, ArrayList<Integer>> processFrameMap = COMPUTER.ramSch.processFrameMap;
        processFrameMap.clear();

        int usedFrames = 0;
        for (ArrayList<Process> processGroup : processesList) {
            int framesForGroup = totalFrames / processesList.size();
            int totalPagesInGroup = 0;

            for (Process process : processGroup) {
                totalPagesInGroup += process.getPageCount();
            }

            for (Process process : processGroup) {
                ArrayList<Integer> frames = new ArrayList<>();

                if (usedFrames < totalFrames) {
                    frames.add(usedFrames);
                    usedFrames++;
                } else {
                    LOGGER.warning("Not enough frames to allocate for all processes.");
                    break;
                }

                processFrameMap.put(process, frames);
                framesForGroup--;
            }


            for (Process process : processGroup) {
                ArrayList<Integer> frames = processFrameMap.get(process);
                int additionalFrames = (framesForGroup * process.getPageCount()) / totalPagesInGroup;

                for (int i = 0; i < additionalFrames; i++) {
                    if (usedFrames < totalFrames) {
                        frames.add(usedFrames);
                        usedFrames++;
                    } else {
                        LOGGER.warning("Not enough frames to allocate for all processes.");
                        break;
                    }
                }
            }
        }
        COMPUTER.ramSch.algorithm.resetAlgorithm();

    }
}
