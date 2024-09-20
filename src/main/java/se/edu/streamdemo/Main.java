package se.edu.streamdemo;

import se.edu.streamdemo.data.Datamanager;
import se.edu.streamdemo.task.Deadline;
import se.edu.streamdemo.task.Task;

import java.util.ArrayList;

import static java.util.stream.Collectors.toList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Task manager (using streams)");
        Datamanager dataManager = new Datamanager("./data/data.txt");
        ArrayList<Task> tasksData = dataManager.loadData();

        System.out.println("Printing all data ...");
        printAllData(tasksData);
        printDataWithStreams(tasksData);

        System.out.println("Printing deadlines ...");
        printDeadlines(tasksData);
        printSortedDeadlinesWithStreams(tasksData);

        System.out.println("Total number of deadlines: " + countDeadlines(tasksData));

        ArrayList<Task> filteredList = filterTasksByString(tasksData, "11");
        printAllData(filteredList);
    }

    private static int countDeadlines(ArrayList<Task> tasksData) {
        int count = 0;
        for (Task t : tasksData) {
            if (t instanceof Deadline) {
                count++;
            }
        }
        return count;
    }

    private static int countDeadlinesWithStreams(ArrayList<Task> tasksData) {
        return (int) tasksData.stream()             // create stream
                .filter(t -> t instanceof Deadline) // lambda function
                .count();                           // terminal operation; aggregate operation
    }

    public static void printAllData(ArrayList<Task> tasksData) {
        System.out.println("Printing data with iteration");
        for (Task t : tasksData) {
            System.out.println(t);
        }
    }

    public static void printDataWithStreams(ArrayList<Task> tasksData) {
        System.out.println("Printing data with streams");
        tasksData.stream()                      // create stream
                .forEach(System.out::println);  // terminal operator
    }

    public static void printDeadlines(ArrayList<Task> tasksData) {
        System.out.println("Printing deadlines with iteration");
        for (Task t : tasksData) {
            if (t instanceof Deadline) {
                System.out.println(t);
            }
        }
    }

    public static void printSortedDeadlinesWithStreams(ArrayList<Task> tasksData) {
        System.out.println("Printing deadlines with streams");
        tasksData.stream()                                                          // create stream
                .filter(t -> t instanceof Deadline)                                 // filter deadlines
                .sorted((t1, t2) ->                                                 // sorts with comparator compareToIgnoreCase
                    t1.getDescription().compareToIgnoreCase(t2.getDescription())
                )
                .forEach(System.out::println);                                      // print each
    }

    public static ArrayList<Task> filterTasksByString(ArrayList<Task> tasksData, String filterString) {
        return (ArrayList<Task>) tasksData.stream()             // create stream
                .filter(t ->                                    // filter tasks with filterString
                    t.getDescription().contains(filterString)
                )
                .collect(toList());                             // collect List and cast to ArrayList<Task>
    }
}
