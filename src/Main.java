import models.Student;
import services.*;
import utils.*;
import java.time.LocalDate;
import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentService studentService = new StudentService();
    private static AnalyticsService analyticsService = new AnalyticsService();

    public static void main(String[] args) {
        System.out.println("\n");
        System.out.println("╔═══════════════════════════════════════════════════════════════════╗");
        System.out. println("║                                                                   ║");
        System.out.println("║        📚  STUDENT MANAGEMENT SYSTEM  📚                         ║");
        System.out.println("║                                                                   ║");
        System.out.println("║           Manage Students Efficiently                             ║");
        System.out.println("║                                                                   ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════╝");

        while (true) {
            showMainMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addNewStudent();
                    break;
                case 2:
                    viewStudent();
                    break;
                case 3:
                    viewAllStudents();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    addStudentMarks();
                    break;
                case 7:
                    viewMarksheet();
                    break;
                case 8:
                    searchStudents();
                    break;
                case 9:
                    analyticsMenu();
                    break;
                case 10:
                    System.out.println("\n╔═══════════════════════════════════════════════════════════════════╗");
                    System.out.println("║     Thank you for using Student Management System.  Goodbye!  👋    ║");
                    System. out.println("╚═══════════════════════════════════════════════════════════════════╝");
                    System.exit(0);
                default:
                    System.out. println("\n⚠ Invalid choice. Please try again.");
            }
        }
    }

    private static void showMainMenu() {
        System.out. println("\n┌───────────────────────────────────────────┐");
        System.out. println("│              MAIN MENU                    │");
        System.out.println("├───────────────────────────────────────────┤");
        System.out.println("│  1.  ➕ Add New Student                   │");
        System.out. println("│  2.  👤 View Student Details              │");
        System.out. println("│  3.  📋 View All Students                 │");
        System.out.println("│  4.  ✏️  Update Student                    │");
        System.out. println("│  5.  🗑️  Delete Student                    │");
        System.out. println("│  6.  📝 Add/Update Marks                  │");
        System.out.println("│  7.  📊 View Marksheet                    │");
        System.out.println("│  8.  🔍 Search Students                   │");
        System.out. println("│  9.  📈 Analytics & Reports               │");
        System.out.println("│  10. 🚪 Exit                              │");
        System.out. println("└───────────────────────────────────────────┘");
    }

    private static void addNewStudent() {
        System.out.println("\n╔═══════════════════════════════════════════╗");
        System.out. println("║           ADD NEW STUDENT                 ║");
        System.out.println("╚═══════════════════════════════════════════╝");

        System.out.print("Enter Full Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Phone (10 digits): ");
        String phone = scanner.nextLine();

        System.out.println("Enter Date of Birth:");
        int day = getIntInput("  Day (1-31): ");
        int month = getIntInput("  Month (1-12): ");
        int year = getIntInput("  Year (1980-2010): ");
        
        if (! InputValidator.isValidDate(day, month, year)) {
            System. out.println("\n⚠ Invalid date of birth.");
            return;
        }
        LocalDate dob = LocalDate.of(year, month, day);

        System.out.println("Select Gender:");
        System.out.println("  1. Male");
        System.out.println("  2. Female");
        System.out.println("  3. Other");
        int genderChoice = getIntInput("Enter choice: ");
        String gender = genderChoice == 1 ? "Male" : genderChoice == 2 ? "Female" : "Other";

        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        System.out.println("\nSelect Course:");
        String[] courses = Constants.AVAILABLE_COURSES;
        for (int i = 0; i < courses.length; i++) {
            System.out.println("  " + (i + 1) + ". " + courses[i]);
        }
        int courseChoice = getIntInput("Enter choice: ");
        
        if (courseChoice < 1 || courseChoice > courses.length) {
            System.out.println("\n⚠ Invalid course selection.");
            return;
        }
        String course = courses[courseChoice - 1];

        Student student = studentService.addStudent(name, email, phone, dob, gender, address, course);
        
        if (student != null) {
            System.out.println("\n✓ Student Added Successfully!");
            System.out. println("  Student ID: " + student.getStudentId());
            System.out.println(student);
        }
    }

    private static void viewStudent() {
        System.out.print("\nEnter Student ID: ");
        String studentId = scanner.nextLine();
        
        Student student = studentService.getStudentById(studentId);
        if (student != null) {
            System.out.println(student);
        }
    }

    private static void viewAllStudents() {
        studentService.displayAllStudents();
    }

    private static void updateStudent() {
        System.out.println("\n╔═══════════════════════════════════════════╗");
        System.out. println("║           UPDATE STUDENT                  ║");
        System.out.println("╚═══════════════════════════════════════════╝");

        System.out.print("Enter Student ID:  ");
        String studentId = scanner.nextLine();

        Student student = studentService.getStudentById(studentId);
        if (student == null) return;

        System.out.println(student);

        System.out. println("\nWhat do you want to update?");
        System.out.println("  1. Name");
        System.out.println("  2. Email");
        System.out.println("  3. Phone");
        System.out.println("  4. Address");
        System.out.println("  5. Course");
        System.out.println("  6. Semester");
        System.out.println("  7. Activate/Deactivate");
        System.out.println("  8. Cancel");

        int choice = getIntInput("Enter choice: ");

        switch (choice) {
            case 1:
                System. out.print("Enter new Name: ");
                studentService.updateStudentDetails(studentId, "name", scanner.nextLine());
                break;
            case 2:
                System.out.print("Enter new Email: ");
                studentService.updateStudentDetails(studentId, "email", scanner.nextLine());
                break;
            case 3:
                System. out.print("Enter new Phone: ");
                studentService.updateStudentDetails(studentId, "phone", scanner.nextLine());
                break;
            case 4:
                System.out.print("Enter new Address: ");
                studentService.updateStudentDetails(studentId, "address", scanner.nextLine());
                break;
            case 5:
                System. out.println("\nSelect new Course:");
                String[] courses = Constants.AVAILABLE_COURSES;
                for (int i = 0; i < courses.length; i++) {
                    System. out.println("  " + (i + 1) + ". " + courses[i]);
                }
                int courseChoice = getIntInput("Enter choice: ");
                if (courseChoice >= 1 && courseChoice <= courses.length) {
                    studentService.updateStudentDetails(studentId, "course", courses[courseChoice - 1]);
                }
                break;
            case 6:
                int sem = getIntInput("Enter new Semester (1-8): ");
                studentService.updateSemester(studentId, sem);
                break;
            case 7:
                if (student.isActive()) {
                    studentService.deactivateStudent(studentId);
                } else {
                    studentService.activateStudent(studentId);
                }
                break;
            case 8:
                System.out.println("Update cancelled.");
                break;
            default:
                System.out. println("\n⚠ Invalid choice.");
        }
    }

    private static void deleteStudent() {
        System.out.println("\n╔═══════════════════════════════════════════╗");
        System.out.println("║           DELETE STUDENT                  ║");
        System.out.println("╚═══════════════════════════════════════════╝");

        System.out.print("Enter Student ID:  ");
        String studentId = scanner.nextLine();

        Student student = studentService.getStudentById(studentId);
        if (student == null) return;

        System.out.println(student);
        System.out.print("\n⚠ Are you sure you want to delete this student? (yes/no): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
            studentService. deleteStudent(studentId);
        } else {
            System.out. println("Deletion cancelled.");
        }
    }

    private static void addStudentMarks() {
        System.out.println("\n╔═══════════════════════════════════════════╗");
        System.out.println("║           ADD/UPDATE MARKS                ║");
        System.out.println("╚═══════════════════════════════════════════╝");

        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();

        Student student = studentService.getStudentById(studentId);
        if (student == null) return;

        System.out.println("\nStudent:  " + student.getName());
        System.out.println("Course: " + student.getCourse());
        System.out.println("Semester: " + student.getSemester());

        String[] subjects = Constants.getSubjectsForCourse(student.getCourse());

        System.out.println("\nEnter marks for each subject (0-100):");
        System.out.println("────────────────────────────────────────────");

        for (String subject : subjects) {
            double marks = getDoubleInput(subject + ": ");
            if (InputValidator.isValidMarks(marks)) {
                studentService.addMarks(studentId, subject, marks);
            } else {
                System.out. println("  ⚠ Invalid marks.  Skipping " + subject);
            }
        }

        System.out.println("\n✓ Marks entry completed!");
        studentService.displayMarksheet(studentId);
    }

    private static void viewMarksheet() {
        System.out. print("\nEnter Student ID: ");
        String studentId = scanner. nextLine();
        studentService.displayMarksheet(studentId);
    }

    private static void searchStudents() {
        System.out.println("\n╔═══════════════════════════════════════════╗");
        System.out.println("║           SEARCH STUDENTS                 ║");
        System.out.println("╚═══════════════════════════════════════════╝");

        System.out.print("Enter search keyword (name/ID/email/course): ");
        String keyword = scanner.nextLine();

        List<Student> results = studentService.searchStudents(keyword);

        if (results.isEmpty()) {
            System.out.println("\n📋 No students found matching '" + keyword + "'");
            return;
        }

        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════════════╗");
        System.out. println("║                         SEARCH RESULTS                                               ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("| Student ID | Name                 | Course          | Sem  | Grade  | Status   |");
        System.out. println("|------------|----------------------|-----------------|------|--------|----------|");

        for (Student student : results) {
            System.out.println(student. toShortString());
        }

        System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println("  Found " + results.size() + " student(s)");
    }

    private static void analyticsMenu() {
        while (true) {
            System. out.println("\n┌───────────────────────────────────────────┐");
            System.out.println("│         ANALYTICS & REPORTS               │");
            System.out. println("├───────────────────────────────────────────┤");
            System.out.println("│  1. 📊 Overall Statistics                 │");
            System. out.println("│  2. 📚 Course-wise Report                 │");
            System. out.println("│  3. 🏆 Top Performers                     │");
            System. out.println("│  4. ❌ Failed Students Report             │");
            System.out. println("│  5. 📅 Semester-wise Distribution         │");
            System.out. println("│  6. 🔙 Back to Main Menu                  │");
            System.out. println("└───────────────────────────────────────────┘");

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    analyticsService.generateOverallStatistics();
                    break;
                case 2:
                    analyticsService.generateCourseWiseReport();
                    break;
                case 3:
                    int count = getIntInput("How many top performers to display? ");
                    analyticsService.generateTopPerformersReport(count);
                    break;
                case 4:
                    analyticsService.generateFailedStudentsReport();
                    break;
                case 5:
                    analyticsService.generateSemesterWiseReport();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("\n⚠ Invalid choice.");
            }
        }
    }

    private static int getIntInput(String prompt) {
        System.out. print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static double getDoubleInput(String prompt) {
        System.out.print(prompt);
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
