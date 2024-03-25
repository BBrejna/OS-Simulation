import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Test_gen {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String cur;

        int op = 0;
        int mod = 4;

        String fname = "";
        int numOfProcesses = 0;
        int maxCpuTime = 0;
        int maxDeltaTime = 0;

        cur = "go";

        while (!Objects.equals(cur, "q")) {
            if (op == 0) {
                System.out.println("Enter test file name:");
                fname = scan.next();
            }
            else if (op == 1) {
                System.out.println("Enter # of processes:");
                numOfProcesses = scan.nextInt();
            }
            else if (op == 2) {
                System.out.println("Enter max cpu time of a process:");
                maxCpuTime = scan.nextInt();
            }
            else if (op == 3) {
                System.out.println("Enter max delta time of a process [difference between every pair of processes arival times]:");
                maxDeltaTime = scan.nextInt();

                System.out.println(fname + " " + numOfProcesses + " " + maxCpuTime + " " + maxDeltaTime);
                try {
                    generateTest(fname, numOfProcesses, maxCpuTime, maxDeltaTime);
                    System.out.println("Test generated succesfully!");
                } catch (IOException e) {
                    System.out.println("Error in writing to file");
                }
                System.out.println("Write q to quit, anything else to cycle");
                cur = scan.next();
            }
            op = (op+1)%mod;

        }

    }

    private static void generateTest(String fname, int numOfProcesses, int maxCpuTime, int maxDeltaTime) throws IOException {
        FileWriter fileWriter = new FileWriter("tests/"+fname+".txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);

        Random random = new Random();

        ArrayList<Integer> arrivalTimes = new ArrayList<>();
        int prev_arrival = 0;
        for (int i = 0; i < numOfProcesses; i++) {
            prev_arrival = random.nextInt(prev_arrival, prev_arrival+maxDeltaTime);
            arrivalTimes.add(prev_arrival);
        }
        Collections.shuffle(arrivalTimes);

        for (int i = 1; i <= numOfProcesses; i++) {
            printWriter.print(i+" ");
            printWriter.print(random.nextInt(maxCpuTime)+" ");
            printWriter.println(arrivalTimes.get(i-1));
        }

        printWriter.close();
    }
}
