package algorithms.memAlgorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import computer.Process;

public class Task {
    public Process p;
    int cylinder;
    int toDoTime;

    public Task(Process p, int cylinder, int toDoTime) {
        this.p = p;
        this.cylinder = cylinder;
        this.toDoTime = toDoTime;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Task))
            return false;
        Task task = (Task) o;
        return p == task.p &&
                cylinder == task.cylinder &&
                toDoTime == task.toDoTime;
    }

//    public static Comparator<Task> arriveTimeComparator = new Comparator<Task>() {
//        public int compare(Task task1, Task task2) {
//            return task1.arriveTime - task2.arriveTime;
//        }
//    };

    public static  Comparator<Task> closestTaskComparator = new Comparator<Task>() {
        public int compare(Task task1, Task task2) {
            return task1.cylinder - task2.cylinder;
        }
    };

    public static Comparator<Task> toDoTimeComparator = new Comparator<Task>() {
        public int compare(Task task1, Task task2) {
            return task1.toDoTime - task2.toDoTime;
        }
    };

    public static void compareCurrentPositionF(ArrayList tasks, int position) {
        Comparator<Task> closestTaskComparator = new Comparator<Task>() {
            public int compare(Task task1, Task task2) {
                if (task1.cylinder - position > 0) {
                    if (task2.cylinder - position > 0)
                        return (task1.cylinder - position) - (task2.cylinder - position);
                    return -1;
                } else if (task2.cylinder - position > 0)
                    return 1;
                else
                    return 0;
            }
        };
        Collections.sort(tasks, closestTaskComparator);
    }

    public static void compareCurrentPositionB(ArrayList tasks, int position) {
        Comparator<Task> closestTaskComparator = new Comparator<Task>() {
            public int compare(Task task1, Task task2) {
                if (task1.cylinder - position < 0) {
                    if (task2.cylinder - position < 0)
                        return Math.abs(task1.cylinder - position) - Math.abs(task2.cylinder - position);
                    return -1;
                } else if (task2.cylinder - position < 0)
                    return 1;
                else
                    return 0;
            }
        };
        Collections.sort(tasks, closestTaskComparator);
    }

    public static void compareTasks(ArrayList tasks, int position) {
        Comparator<Task> closestTaskComparator = new Comparator<Task>() {
            public int compare(Task task1, Task task2) {
                return Math.abs(task1.cylinder - position) - Math.abs(task2.cylinder - position);
            }
        };
        Collections.sort(tasks,closestTaskComparator);
    }

   /*public String toString() {
     //   return "time: " + arriveTime + " cylinder " + cylinder + " deadline " + toDoTime;
    }*/

   /*
    public static Comparator<Task> timeComparator = new Comparator<Task>() {
        public int compare(Task task1, Task task2) {
            if(task1.toDoTime != task2.toDoTime)
                return  task1.toDoTime - task2.toDoTime;
            else
                return task1.arriveTime - task2.arriveTime;
        }
    };

    public static void compareCylinderCurrentPosition(ArrayList tasks, int position) {
        Comparator<Task> closestTaskToCylinderComparator = new Comparator<Task>() {
            public int compare(Task task1, Task task2) {
                return Math.abs(task1.cylinder - position) - Math.abs(task2.cylinder - position);
            }
        };
        Collections.sort(tasks, closestTaskToCylinderComparator);
    }
*/
}

