package computer;

import java.util.Random;

public class ProcessProvider {
    public int id = 0;
    static boolean active;
    static int timeNext;
    public static int maxCpuTime = 30;
    public static int maxDeltaTime = 30;

    public ProcessProvider() {
        active = true;
    }

    public Process getNextProcess() {
        Random random = new Random();
        Process p = new Process(id++, random.nextInt(maxCpuTime));

        timeNext = random.nextInt(maxDeltaTime);

        return p;
    }

    public boolean isReady() {
        if (!active) return false;
        return timeNext <= 0;
    }

    public static boolean isActive() {
        return active;
    }

    public static void switchOn() {
        active = true;
    }
    public static void switchOff() {
        active = false;
    }

    public static void doWait() {
        timeNext--;
    }
}
