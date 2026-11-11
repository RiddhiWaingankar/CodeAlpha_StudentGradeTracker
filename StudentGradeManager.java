import java.util.ArrayList;
import java.util.Scanner;

class Student {
    private String name;
    private String studentId;
    private ArrayList<Double> grades;

    public Student(String name, String studentId) {
        this.name = name;
        this.studentId = studentId;
        this.grades = new ArrayList<>();
    }

    public void addGrade(double grade) {
        grades.add(grade);
    }

    public String getName() {
        return name;
    }

    public String getStudentId() {
        return studentId;
    }

    public ArrayList<Double> getGrades() {
        return grades;
    }

    public double getAverage() {
        if (grades.isEmpty()) return 0.0;
        double sum = 0.0;
        for (double grade : grades) {
            sum += grade;
        }
        return sum / grades.size();
    }

    public double getHighest() {
        if (grades.isEmpty()) return 0.0;
        double max = grades.get(0);
        for (double grade : grades) {
            if (grade > max) {
                max = grade;
            }
        }
        return max;
    }

    public double getLowest() {
        if (grades.isEmpty()) return 0.0;
        double min = grades.get(0);
        for (double grade : grades) {
            if (grade < min) {
                min = grade;
            }
        }
        return min;
    }
}

public class StudentGradeManager {
    private static ArrayList<Student> studentList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            printMenu();
            choice = getValidIntInput("Enter your choice (1-4): ");
            
            switch (choice) {
                case 1:
                    addStudentWithGrades();
                    break;
                case 2:
                    displaySummaryReport();
                    break;
                case 3:
                    displayClassStatistics();
                    break;
                case 4:
                    System.out.println("\nExiting Student Grade Manager. Goodbye!");
                    break;
                default:
                    System.out.println("\n[Error] Invalid choice. Please select between 1 and 4.");
            }
        } while (choice != 4);
        
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n========================================");
        System.out.println("      STUDENT GRADE MANAGER SYSTEM      ");
        System.out.println("========================================");
        System.out.println("1. Add New Student & Grades");
        System.out.println("2. Display Summary Report");
        System.out.println("3. Display Class-Wide Statistics");
        System.out.println("4. Exit");
        System.out.println("----------------------------------------");
    }

    private static void addStudentWithGrades() {
        System.out.println("\n--- Add Student ---");
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine().trim();

        Student student = new Student(name, id);

        System.out.print("Enter the number of grades to add for this student: ");
        int numGrades = getValidIntInput("");

        for (int i = 1; i <= numGrades; i++) {
            double grade = getValidDoubleInput("Enter grade #" + i + " (0 - 100): ", 0.0, 100.0);
            student.addGrade(grade);
        }

        studentList.add(student);
        System.out.println("\n[Success] Student " + name + " added successfully!");
    }

    private static void displaySummaryReport() {
        if (studentList.isEmpty()) {
            System.out.println("\n[Notice] No student records found. Please add students first.");
            return;
        }

        System.out.println("\n=========================================================================");
        System.out.println("                         STUDENT SUMMARY REPORT                          ");
        System.out.println("=========================================================================");
        System.out.printf("%-12s | %-20s | %-25s | %-8s | %-8s | %-8s\n", 
                          "ID", "Name", "Grades", "Avg", "High", "Low");
        System.out.println("-------------------------------------------------------------------------");

        for (Student s : studentList) {
            String gradesStr = s.getGrades().toString();
            // Trim brackets for cleaner display if desired
            if (gradesStr.length() > 25) {
                gradesStr = gradesStr.substring(0, 22) + "...";
            }
            
            System.out.printf("%-12s | %-20s | %-25s | %-8.2f | %-8.2f | %-8.2f\n",
                              s.getStudentId(),
                              s.getName(),
                              gradesStr,
                              s.getAverage(),
                              s.getHighest(),
                              s.getLowest());
        }
        System.out.println("=========================================================================");
    }

    private static void displayClassStatistics() {
        if (studentList.isEmpty()) {
            System.out.println("\n[Notice] No student records available to calculate statistics.");
            return;
        }

        double totalScoreSum = 0;
        int totalGradesCount = 0;
        double classHighest = Double.MIN_VALUE;
        double classLowest = Double.MAX_VALUE;

        for (Student s : studentList) {
            for (double g : s.getGrades()) {
                totalScoreSum += g;
                totalGradesCount++;
                if (g > classHighest) classHighest = g;
                if (g < classLowest) classLowest = g;
            }
        }

        double overallAverage = totalGradesCount > 0 ? (totalScoreSum / totalGradesCount) : 0.0;

        System.out.println("\n========================================");
        System.out.println("        CLASS-WIDE STATISTICS           ");
        System.out.println("========================================");
        System.out.println("Total Students Registered: " + studentList.size());
        System.out.println("Total Grades Recorded:     " + totalGradesCount);
        System.out.printf("Overall Class Average:     %.2f\n", overallAverage);
        System.out.printf("Highest Score in Class:    %.2f\n", classHighest);
        System.out.printf("Lowest Score in Class:     %.2f\n", classLowest);
        System.out.println("========================================");
    }

    private static int getValidIntInput(String prompt) {
        while (true) {
            if (!prompt.isEmpty()) {
                System.out.print(prompt);
            }
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("[Error] Please enter a valid integer: ");
            }
        }
    }

    private static double getValidDoubleInput(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("[Error] Grade must be between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("[Error] Please enter a valid numeric grade.");
            }
        }
    }
}