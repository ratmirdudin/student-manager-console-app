package com.company;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

class Student {
    private final String name;
    private int solvedTasks;
    private final int enoughSolvedTasks;

    public Student(String name) {
        this(name, 5);
    }

    public Student(String name, int enoughSolvedTasks) {
        this.name = name;
        this.solvedTasks = 0;
        this.enoughSolvedTasks = enoughSolvedTasks;
    }

    public String getName() {
        return name;
    }

    public int getSolvedTasks() {
        return solvedTasks;
    }

    public void addSolvedTask() {
        this.solvedTasks += 1;
    }

    public int getEnoughSolvedTasks() {
        return enoughSolvedTasks;
    }

}

public class Main {
    public static void printMenu(String actionMessage) {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("==========================================");
        if (!actionMessage.isEmpty()) {
            System.out.print(actionMessage);
        }
        System.out.println("Menu:");
        System.out.println(" 1) ADD STUDENT");
        System.out.println(" 2) SHOW STUDENTS");
        System.out.println(" 3) SHOW STUDENTS HAVE ENOUGH SOLVED TASKS");
        System.out.println(" 4) Select student to action");
        System.out.println(" 0) EXIT");
    }

    public static void printStudentMenu(String actionMessage, String name) {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n==========================================");
        if (!actionMessage.isEmpty()) {
            System.out.print(actionMessage);
        }
        System.out.println("Menu:");
        System.out.println("Selected student is " + name);
        System.out.println("   1) ADD SOLVED TASK TO STUDENT");
        System.out.println("   2) SHOW INFO ABOUT STUDENT");
        System.out.println(" 0) GO BACK");
    }

    public static String readName() {
        Scanner in = new Scanner(System.in);
        String message = "";
        String name;
        boolean correctInput = false;
        do {
            if (!message.isEmpty()) {
                System.out.println(message);
            }
            System.out.print("Enter student's name: ");
            name = in.nextLine();
            boolean isDigit = false;
            char[] chars = name.toCharArray();
            for (char c : chars) {
                if (Character.isDigit(c)) {
                    isDigit = true;
                    message = "Message:\n" +
                            "Please, enter student's name without digits\n";
                    break;
                }
            }
            if (!isDigit) {
                correctInput = true;
            }
        } while (!correctInput);
        return name;
    }

    public static String showStudents(Map<String, Student> studentMap) {
        if (studentMap.isEmpty()) {
            return "There are no students\n";
        }
        String message = "Message:\n";
        int index = 0;
        for (String name : studentMap.keySet()) {
            int tasks = studentMap.get(name).getSolvedTasks();
            message += " " + (index + 1) + ") " + name + ": " + tasks + " solved tasks\n";
            index += 1;
        }
        return message;
    }

    public static String showStudentsHaveEnoughSolvedTasks(Map<String, Student> studentMap) {
        if (studentMap.isEmpty()) {
            return "There are no students\n";
        }
        String message = "Message:\n";
        boolean noOneHasEnoughTasksFlag = true;
        int index = 0;
        for (String name : studentMap.keySet()) {
            int enoughSolvedTasks = studentMap.get(name).getEnoughSolvedTasks();
            int tasks = studentMap.get(name).getSolvedTasks();
            if (tasks >= enoughSolvedTasks) {
                noOneHasEnoughTasksFlag = false;
                message += " " + (index + 1) + ") " + name + ": " + tasks + " solved tasks\n";
            }
            index += 1;
        }
        if (noOneHasEnoughTasksFlag) {
            message = "No one has enough solved tasks\n";
        }
        return message;
    }

    public static String selectStudentToAction(Map<String, Student> studentMap) {
        Scanner in = new Scanner(System.in);
        String message = "";
        ArrayList<String> studentNameList = new ArrayList<>(studentMap.keySet());
        int indexOfStudent = 0;
        String namesStr = "";
        do {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n==========================================");
            if (!message.isEmpty()) {
                System.out.println(message);
            }
            System.out.println("Select student to action: ");
            for (String nameOfStudent :
                    studentMap.keySet()) {
                int tasks = studentMap.get(nameOfStudent).getSolvedTasks();
                namesStr += " " + (indexOfStudent + 1) + ") " + nameOfStudent + ": " + tasks + " solved tasks\n";
                indexOfStudent += 1;
            }
            System.out.println(namesStr);
            String selectStudent = in.nextLine();
            try {
                indexOfStudent = Integer.parseInt(selectStudent) - 1;
                if (indexOfStudent >= 0 && indexOfStudent < studentMap.size()) {
                    break;
                }
            } catch (NumberFormatException e) {
                message = "Message:\n" +
                        "Please, enter 1.." + studentMap.size() + " to select necessary student\n";
            }
        } while (true);
        return studentNameList.get(indexOfStudent);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Map<String, Student> studentMap = new LinkedHashMap<>();
        String name;
        String actionMessage = "";
        boolean finishFlag = false;
        do {
            printMenu(actionMessage);
            actionMessage = "";
            String selectAction = in.nextLine();
            switch (selectAction) {
                case "1":// Add student
                    name = readName();
                    Student student = new Student(name);
                    studentMap.put(name, student);
                    actionMessage = "Message:\n" +
                            "Student " + name + " added in list\n";
                    break;
                case "2":// Show students
                    actionMessage = showStudents(studentMap);
                    break;
                case "3":// Show students have enough solved tasks
                    actionMessage = showStudentsHaveEnoughSolvedTasks(studentMap);
                    break;
                case "4":// Select student to action
                    if (studentMap.isEmpty()) {
                        actionMessage = "Message:\n" +
                                "There are no students\n";
                        break;
                    } else {
                        name = selectStudentToAction(studentMap);
                    }

                    boolean goBackFlag = false;
                    do {
                        printStudentMenu(actionMessage, name);
                        actionMessage = "";
                        selectAction = in.nextLine();
                        switch (selectAction) {
                            case "1":// Add solved task to student
                                studentMap.get(name).addSolvedTask();
                                actionMessage = "Message:\n" +
                                        "Added solved task to " + name + "(" + studentMap.get(name).getSolvedTasks() + ")" + "\n";
                                break;
                            case "2":// Show info about student
                                actionMessage = "Message:\n"
                                        + name + " has " + studentMap.get(name).getSolvedTasks() + " solved tasks\n";
                                break;
                            case "0":// Go back
                                goBackFlag = true;
                                break;
                            default:// Incorrect input
                                actionMessage = "Message:\n" +
                                        "Please, enter 0..2 to select necessary action\n";
                                break;
                        }
                    } while (!goBackFlag);
                    actionMessage = "";
                    break;
                case "0":// Finish program
                    System.out.println("Have a nice day!");
                    finishFlag = true;
                    break;
                default:// Incorrect input
                    actionMessage = "Message:\n" +
                            "Please, enter 0..5 to select necessary action\n";
                    break;
            }
        } while (!finishFlag);
        in.close();
    }
}
