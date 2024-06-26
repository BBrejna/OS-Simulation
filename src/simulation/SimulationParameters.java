package simulation;

public class SimulationParameters {
    public static int ITERATIONS_NUMBER = 2;
    public static int PROCESSORS_NUMBER = 20;
    public static int UPPER_PROCESSOR_LOAD_BOUND = 60;
    public static int LOWER_PROCESSOR_LOAD_BOUND = 20;
    public static int FINDING_OTHER_PROCESSOR_TRIES = 5;
    public static int PROCESSES_NUMBER = 1000;
    public static int MAX_PROCESS_CPU_TIME = 30;
    public static int MAX_PROCESS_DELTA_TIME = 5;
    public static double MAX_PROCESS_LOAD_ON_PROCESSOR = 25;
    public static double MIN_PROCESS_LOAD_ON_PROCESSOR = 10;
    public static int MEM_SIZE = 50;
    public static int RAM_SIZE = 2000;
    public static int MAX_PROCESS_PAGE_NUMBER = 30;
    public static int RR_DELTA_TIME = 2;
    public static int MANUAL_WINDOW = 10;
    public static int ZONAL_WINDOW = 10;
    public static double PROCESS_REQUEST_PROBABILITY_ON_TICK = 0.4;
    public static double PROCESS_REQUEST_PROBABILITY_RAM = 2.0/3;
    public static double PROCESS_MEM_REQUEST_PRIORITY_PROBABILITY = 0.3;
}
