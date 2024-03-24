package computer;

public class CPU implements Runnable {
    public boolean active = false;
    public Scheduler scheduler;
    public Thread t;
    public static int curTime = 1;

    public CPU(Scheduler scheduler) {
        this.scheduler = scheduler;
        ProcessProvider.switchOn();
    }

    public void doStep() throws InterruptedException {
        if (ProcessProvider.isActive()) ProcessProvider.doWait();
        scheduler.doWork();
        curTime++;
        Thread.sleep(10);
    }

    @Override
    public void run() {
        while (active) {
            try {
                doStep();
            } catch (InterruptedException e) {
                if (active) {
                    System.out.println("CPU THREAD HAS BEEN INTERRUPTED!");
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public void interrupt() {
        active = false;
        System.out.println("STOPPING CPU THREAD!");
        t.interrupt();
    }

    public void start() {
        t = new Thread(this, "CPU-sim");
        active = true;
        t.start();
    }
}
