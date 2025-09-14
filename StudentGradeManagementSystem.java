
package studentgrademanagementsystem;

import java.util.Scanner;


public class StudentGradeManagementSystem {
            
        // Variable Declaration 
    
        static final int MAX_STUDENTS = 50;
        
       // Array to store the student data
        static int[] rollNumbers = new int[MAX_STUDENTS];
        static String[] names = new String[MAX_STUDENTS];
        static int[][] marks = new int[MAX_STUDENTS][3]; // 3 subjects
        static int studentCount = 0;
        
        static Scanner scanner = new Scanner(System.in);
        
   
    public static void main(String[] args) {
        System.out.println("=== Welcome to Student Grade Management System ===");
        System.out.println("Riphah International University, I14, Islamabad Campus\n");
        
        int choice;
        do {
            displayMenu();
            choice = getValidChoice();
            
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    updateMarks();
                    break;
                case 3:
                    removeStudent();
                    break;
                case 4:
                    viewAllStudents();
                    break;
                case 5:
                    searchStudent();
                    break;
                case 6:
                    findHighestScorer();
                    break;
                case 7:
                    calculateClassAverage();
                    break;
                case 8:
                    exitProgram();
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
            
            if (choice != 8) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
            
        } while (choice != 8);
    }
    
    static void displayMenu() {
        System.out.println("\n=============== MENU ===============");
        System.out.println("1. Add Student");
        System.out.println("2. Update Marks");
        System.out.println("3. Remove Student");
        System.out.println("4. View All Students");
        System.out.println("5. Search Student");
        System.out.println("6. Highest Scorer");
        System.out.println("7. Class Average");
        System.out.println("8. Exit");
        System.out.println("===================================");
        System.out.print("Choose an option: ");
    }
       
    
    
        // This part of code prevent user to enter invalid input
    static int getValidChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            return choice;
        } catch (Exception e) {
            scanner.nextLine(); 
            return -1;
        }
    }
    
    static void addStudent() {
        System.out.println("\n--- Add New Student ---");
        
        if (studentCount >= MAX_STUDENTS) {
            System.out.println("Error: Maximum student limit (50) reached!");
            return;
        }
        
        // Get roll number
        int rollNo = getValidRollNumber();
        if (rollNo == -1) return;
        
        // Check if roll number already exists
        if (findStudentIndex(rollNo) != -1) {
            System.out.println("Error: Roll number " + rollNo + " already exists!");
            return;
        }
        
        // Get name
        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Error: Name cannot be empty!");
            return;
        }
        
        // Get marks for 3 subjects
        int[] studentMarks = new int[3];
        for (int i = 0; i < 3; i++) {
            studentMarks[i] = getValidMarks("Subject " + (i + 1));
            if (studentMarks[i] == -1) return;
        }
        
        // Add student data
        rollNumbers[studentCount] = rollNo;
        names[studentCount] = name;
        for (int i = 0; i < 3; i++) {
            marks[studentCount][i] = studentMarks[i];
        }
        studentCount++;
        
        System.out.println("Student added successfully!");
    }
    
    static void updateMarks() {
        System.out.println("\n--- Update Student Marks ---");
        
        if (studentCount == 0) {
            System.out.println("No students found!");
            return;
        }
        
        int rollNo = getValidRollNumber();
        if (rollNo == -1) return;
        
        int index = findStudentIndex(rollNo);
        if (index == -1) {
            System.out.println("Error: Student with roll number " + rollNo + " not found!");
            return;
        }
        
        System.out.println("Current marks for " + names[index] + ":");
        for (int i = 0; i < 3; i++) {
            System.out.println("Subject " + (i + 1) + ": " + marks[index][i]);
        }
        
        System.out.println("\nEnter new marks:");
        for (int i = 0; i < 3; i++) {
            int newMark = getValidMarks("Subject " + (i + 1));
            if (newMark == -1) return;
            marks[index][i] = newMark;
        }
        
        System.out.println("Marks updated successfully!");
    }
    
    static void removeStudent() {
        System.out.println("\n--- Remove Student ---");
        
        if (studentCount == 0) {
            System.out.println("No students found!");
            return;
        }
        
        int rollNo = getValidRollNumber();
        if (rollNo == -1) return;
        
        int index = findStudentIndex(rollNo);
        if (index == -1) {
            System.out.println("Error: Student with roll number " + rollNo + " not found!");
            return;
        }
        
        String studentName = names[index];
        
        // Shift all elements left from the index to remove
        for (int i = index; i < studentCount - 1; i++) {
            rollNumbers[i] = rollNumbers[i + 1];
            names[i] = names[i + 1];
            for (int j = 0; j < 3; j++) {
                marks[i][j] = marks[i + 1][j];
            }
        }
        
        studentCount--;
        System.out.println("Student '" + studentName + "' removed successfully!");
    }
    
    static void viewAllStudents() {
        System.out.println("\n--- All Students ---");
        
        if (studentCount == 0) {
            System.out.println("No students found!");
            return;
        }
        
        System.out.println();
        System.out.printf("%-8s %-15s %-8s %-8s %-8s %-8s %-10s%n", 
                         "Roll No", "Name", "Sub 1", "Sub 2", "Sub 3", "Total", "Average");
        System.out.println("------------------------------------------------------------------------");
        
        for (int i = 0; i < studentCount; i++) {
            int total = calculateTotal(i);
            double average = total / 3.0;
            
            System.out.printf("%-8d %-15s %-8d %-8d %-8d %-8d %-10.2f%n",
                             rollNumbers[i], names[i], marks[i][0], marks[i][1], 
                             marks[i][2], total, average);
        }
    }
    
    static void searchStudent() {
        System.out.println("\n--- Search Student ---");
        
        if (studentCount == 0) {
            System.out.println("No students found!");
            return;
        }
        
        int rollNo = getValidRollNumber();
        if (rollNo == -1) return;
        
        int index = findStudentIndex(rollNo);
        if (index == -1) {
            System.out.println("Error: Student with roll number " + rollNo + " not found!");
            return;
        }
        
        int total = calculateTotal(index);
        double average = total / 3.0;
        
        System.out.println("\n--- Student Details ---");
        System.out.println("Roll Number: " + rollNumbers[index]);
        System.out.println("Name: " + names[index]);
        System.out.println("Subject 1: " + marks[index][0]);
        System.out.println("Subject 2: " + marks[index][1]);
        System.out.println("Subject 3: " + marks[index][2]);
        System.out.println("Total Marks: " + total);
        System.out.printf("Average: %.2f%n", average);
    }
    
    static void findHighestScorer() {
        System.out.println("\n--- Highest Scorer ---");
        
        if (studentCount == 0) {
            System.out.println("No students found!");
            return;
        }
        
        int highestIndex = 0;
        int highestTotal = calculateTotal(0);
        
        for (int i = 1; i < studentCount; i++) {
            int currentTotal = calculateTotal(i);
            if (currentTotal > highestTotal) {
                highestTotal = currentTotal;
                highestIndex = i;
            }
        }
        
        double average = highestTotal / 3.0;
        
        System.out.println("Highest scoring student:");
        System.out.println("Roll Number: " + rollNumbers[highestIndex]);
        System.out.println("Name: " + names[highestIndex]);
        System.out.println("Subject 1: " + marks[highestIndex][0]);
        System.out.println("Subject 2: " + marks[highestIndex][1]);
        System.out.println("Subject 3: " + marks[highestIndex][2]);
        System.out.println("Total Marks: " + highestTotal);
        System.out.printf("Average: %.2f%n", average);
    }
    
    static void calculateClassAverage() {
        System.out.println("\n--- Class Average ---");
        
        if (studentCount == 0) {
            System.out.println("No students found!");
            return;
        }
        
        int totalMarks = 0;
        for (int i = 0; i < studentCount; i++) {
            totalMarks += calculateTotal(i);
        }
        
        double classAverage = (double) totalMarks / (studentCount * 3);
        
        System.out.println("Total Students: " + studentCount);
        System.out.println("Total Marks of All Students: " + totalMarks);
        System.out.printf("Class Average: %.2f%n", classAverage);
    }
    
    static void exitProgram() {
        System.out.println("\n--- Program Summary ---");
        
        if (studentCount > 0) {
            int totalMarks = 0;
            for (int i = 0; i < studentCount; i++) {
                totalMarks += calculateTotal(i);
            }
            double classAverage = (double) totalMarks / (studentCount * 3);
            
            System.out.println("Total Students Managed: " + studentCount);
            System.out.printf("Final Class Average: %.2f%n", classAverage);
        } else {
            System.out.println("No students were managed in this session.");
        }
        
        System.out.println("\nThank you for using Student Grade Management System!");
        System.out.println("Riphah International University, I14, Islamabad Campus");
    }
    
    // Helper methods
    static int getValidRollNumber() {
        System.out.print("Enter Roll No: ");
        try {
            int rollNo = scanner.nextInt();
            scanner.nextLine(); 
            
            if (rollNo <= 0) {
                System.out.println("Error: Roll number must be positive!");
                return -1;
            }
            return rollNo;
        } catch (Exception e) {
            scanner.nextLine(); 
            System.out.println("Error: Invalid roll number format!");
            return -1;
        }
    }
    
    static int getValidMarks(String subject) {
        System.out.print("Enter Marks in " + subject + " (0-100): ");
        try {
            int mark = scanner.nextInt();
            scanner.nextLine(); 
            
            if (mark < 0 || mark > 100) {
                System.out.println("Error: Marks must be between 0 and 100!");
                return -1;
            }
            return mark;
        } catch (Exception e) {
            scanner.nextLine(); 
            System.out.println("Error: Invalid marks format!");
            return -1;
        }
    }
    
    static int findStudentIndex(int rollNo) {
        for (int i = 0; i < studentCount; i++) {
            if (rollNumbers[i] == rollNo) {
                return i;
            }
        }
        return -1;
    }
    
    static int calculateTotal(int index) {
        return marks[index][0] + marks[index][1] + marks[index][2];
    }
}
        
          
    
    

       