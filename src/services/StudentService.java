package services;

import models.Student;
import utils.*;
import java.time.LocalDate;
import java. util.*;

public class StudentService {

    public Student addStudent(String name, String email, String phone, LocalDate dob,
                              String gender, String address, String course) {
        
        // Validate inputs
        if (! InputValidator.isValidName(name)) {
            System.out.println("\n⚠ Invalid name.  Use only letters and spaces (2-50 characters).");
            return null;
        }
        
        if (!InputValidator.isValidEmail(email)) {
            System.out.println("\n⚠ Invalid email format.");
            return null;
        }
        
        if (DataStorage.emailExists(email)) {
            System.out.println("\n⚠ Email already registered.");
            return null;
        }
        
        if (!InputValidator.isValidPhone(phone)) {
            System.out.println("\n⚠ Invalid phone number.  Enter 10-digit mobile number.");
            return null;
        }

        String studentId = DataStorage.generateStudentId();
        Student student = new Student(studentId, name, email, phone, dob, gender, address, course);
        DataStorage.addStudent(student);

        return student;
    }

    public Student getStudentById(String studentId) {
        Student student = DataStorage.getStudent(studentId);
        if (student == null) {
            System.out.println("\n⚠ Student not found with ID: " + studentId);
        }
        return student;
    }

    public List<Student> getAllStudents() {
        return DataStorage.getAllStudents();
    }

    public List<Student> searchStudents(String keyword) {
        return DataStorage.searchStudents(keyword);
    }

    public boolean updateStudentDetails(String studentId, String field, String value) {
        Student student = DataStorage.getStudent(studentId);
        if (student == null) {
            System. out.println("\n⚠ Student not found.");
            return false;
        }

        switch (field.toLowerCase()) {
            case "name": 
                if (!InputValidator.isValidName(value)) {
                    System. out.println("\n⚠ Invalid name format.");
                    return false;
                }
                student.setName(value);
                break;
            case "email":
                if (!InputValidator.isValidEmail(value)) {
                    System. out.println("\n⚠ Invalid email format.");
                    return false;
                }
                student.setEmail(value);
                break;
            case "phone":
                if (!InputValidator. isValidPhone(value)) {
                    System.out.println("\n⚠ Invalid phone format.");
                    return false;
                }
                student.setPhone(value);
                break;
            case "address":
                student.setAddress(value);
                break;
            case "course":
                student. setCourse(value);
                break;
            default:
                System.out.println("\n⚠ Invalid field.");
                return false;
        }

        DataStorage.updateStudent(student);
        System.out. println("\n✓ Student details updated successfully.");
        return true;
    }

    public boolean updateSemester(String studentId, int semester) {
        Student student = DataStorage.getStudent(studentId);
        if (student == null) {
            System.out. println("\n⚠ Student not found.");
            return false;
        }

        if (! InputValidator.isValidSemester(semester)) {
            System. out.println("\n⚠ Invalid semester. Must be between 1 and 8.");
            return false;
        }

        student.setSemester(semester);
        student.clearMarks(); // Clear marks when semester changes
        DataStorage.updateStudent(student);
        System.out.println("\n✓ Semester updated to " + semester);
        return true;
    }

    public boolean addMarks(String studentId, String subject, double marks) {
        Student student = DataStorage.getStudent(studentId);
        if (student == null) {
            System.out.println("\n⚠ Student not found.");
            return false;
        }

        if (!InputValidator.isValidMarks(marks)) {
            System.out.println("\n⚠ Invalid marks. Must be between 0 and 100.");
            return false;
        }

        student.addSubjectMarks(subject, marks);
        DataStorage.updateStudent(student);
        System.out.println("\n✓ Marks added for " + subject + ": " + marks);
        return true;
    }

    public void displayMarksheet(String studentId) {
        Student student = DataStorage.getStudent(studentId);
        if (student == null) {
            System.out.println("\n⚠ Student not found.");
            return;
        }

        Map<String, Double> subjects = student.getSubjects();
        
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════╗");
        System.out. println("║                         MARKSHEET                                 ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");
        System.out.println("  Student ID   : " + student.getStudentId());
        System.out. println("  Student Name : " + student.getName());
        System.out.println("  Course       : " + student.getCourse());
        System.out.println("  Semester     : " + student.getSemester());
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");
        
        if (subjects.isEmpty()) {
            System.out.println("  No marks recorded yet.");
        } else {
            System.out.println("  Subject                          Marks      Status");
            System.out.println("  ─────────────────────────────────────────────────────────────");
            
            for (Map.Entry<String, Double> entry : subjects.entrySet()) {
                String status = entry.getValue() >= 40 ? "PASS" : "FAIL";
                System.out.printf("  %-30s    %-6.1f     %s\n", 
                    entry.getKey(), entry.getValue(), status);
            }
            
            System.out.println("  ─────────────────────────────────────────────────────────────");
            System.out.printf("  Total Marks    : %.1f / %d\n", student.getTotalMarks(), subjects.size() * 100);
            System.out.printf("  Average Marks  : %.2f%%\n", student.getAverageMarks());
            System.out.printf("  Grade          : %s\n", student.getGrade());
            System.out.printf("  Result         : %s\n", student. isPassed() ? "✓ PASSED" : "✗ FAILED");
        }
        
        System.out.println("╚═══════════════════════════════════════════════════════════════════╝");
    }

    public boolean deleteStudent(String studentId) {
        if (DataStorage.deleteStudent(studentId)) {
            System.out.println("\n✓ Student deleted successfully.");
            return true;
        }
        System.out.println("\n⚠ Student not found.");
        return false;
    }

    public boolean deactivateStudent(String studentId) {
        Student student = DataStorage.getStudent(studentId);
        if (student == null) {
            System.out.println("\n⚠ Student not found.");
            return false;
        }
        student.setActive(false);
        DataStorage.updateStudent(student);
        System.out.println("\n✓ Student deactivated successfully.");
        return true;
    }

    public boolean activateStudent(String studentId) {
        Student student = DataStorage.getStudent(studentId);
        if (student == null) {
            System.out.println("\n⚠ Student not found.");
            return false;
        }
        student.setActive(true);
        DataStorage.updateStudent(student);
        System.out.println("\n✓ Student activated successfully.");
        return true;
    }

    public void displayAllStudents() {
        List<Student> students = DataStorage. getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("\n📋 No students registered yet.");
            return;
        }

        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════════════╗");
        System.out. println("║                              ALL STUDENTS                                            ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("| Student ID | Name                 | Course          | Sem  | Grade  | Status   |");
        System.out. println("|------------|----------------------|-----------------|------|--------|----------|");
        
        for (Student student :  students) {
            System.out.println(student.toShortString());
        }
        
        System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println("  Total Students:  " + students.size());
    }

    public void displayStudentsByCourse(String course) {
        List<Student> students = DataStorage.getStudentsByCourse(course);
        
        if (students.isEmpty()) {
            System.out.println("\n📋 No students found in " + course);
            return;
        }

        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    STUDENTS IN " + course. toUpperCase());
        System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("| Student ID | Name                 | Course          | Sem  | Grade  | Status   |");
        System.out.println("|------------|----------------------|-----------------|------|--------|----------|");
        
        for (Student student :  students) {
            System.out.println(student.toShortString());
        }
        
        System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════╝");
        System.out. println("  Total Students in " + course + ": " + students.size());
    }
}
