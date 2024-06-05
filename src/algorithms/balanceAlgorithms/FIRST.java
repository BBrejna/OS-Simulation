package algorithms.balanceAlgorithms;

import computer.COMPUTER;
import computer.Process;

import java.util.ArrayList;

public class FIRST extends BalanceAlgorithm {
    @Override
    public void handleNewProcess(int processorId, Process process) {
        assignProcessToProcessor(processorId, process);
    }
}
