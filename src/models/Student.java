package models;

import java.io. Serializable;
import java. time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String studentId;
    private String name;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String gender;
    private String address;
    private String course;
    private int semester;
    private Map<String, Double> subjects;
    private LocalDate enrollmentDate;
    private boolean isActive;

    public Student(String studentId, String name, String email, String phone, 
                   LocalDate dateOfBirth, String gender, String address, String course) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.course = course;
        this.semester = 1;
        this.subjects = new HashMap<>();
        this.enrollmentDate = LocalDate.now();
        this.isActive = true;
    }

    // Getters
    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getGender() { return gender; }
    public String getAddress() { return address; }
    public String getCourse() { return course; }
    public int getSemester() { return semester; }
    public Map<String, Double> getSubjects() { return subjects; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public boolean isActive() { return isActive; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }
    public void setCourse(String course) { this.course = course; }
    public void setSemester(int semester) { this.semester = semester; }
    public void setActive(boolean active) { this.isActive = active; }

    public void addSubjectMarks(String subject, double marks) {
        subjects.put(subject, marks);
    }

    public void clearMarks() {
        subjects.clear();
    }

    public double getAverageMarks() {
        if (subjects.isEmpty()) return 0;
        double total = 0;
        for (double marks : subjects.values()) {
            total += marks;
        }
        return total / subjects.size();
    }

    public double getTotalMarks() {
        double total = 0;
        for (double marks : subjects.values()) {
            total += marks;
        }
        return total;
    }

    public String getGrade() {
        double avg = getAverageMarks();
        if (avg >= 90) return "A+";
        if (avg >= 80) return "A";
        if (avg >= 70) return "B+";
        if (avg >= 60) return "B";
        if (avg >= 50) return "C";
        if (avg >= 40) return "D";
        return "F";
    }

    public boolean isPassed() {
        if (subjects.isEmpty()) return false;
        for (double marks : subjects.values()) {
            if (marks < 40) return false;
        }
        return true;
    }

    public int getFailedSubjectsCount() {
        int count = 0;
        for (double marks : subjects.values()) {
            if (marks < 40) count++;
        }
        return count;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter. ofPattern("dd-MM-yyyy");
        StringBuilder sb = new StringBuilder();
        
        sb.append("\n╔══════════════════════════════════════════════════════════╗\n");
        sb.append("║                   STUDENT DETAILS                        ║\n");
        sb.append("╠══════════════════════════════════════════════════════════╣\n");
        sb.append(String. format("  Student ID     : %s\n", studentId));
        sb.append(String.format("  Name           : %s\n", name));
        sb.append(String.format("  Email          : %s\n", email));
        sb.append(String.format("  Phone          : %s\n", phone));
        sb.append(String.format("  Date of Birth  :  %s\n", dateOfBirth.format(formatter)));
        sb.append(String.format("  Gender         : %s\n", gender));
        sb.append(String.format("  Address        : %s\n", address));
        sb.append(String. format("  Course         : %s\n", course));
        sb.append(String.format("  Semester       : %d\n", semester));
        sb.append(String.format("  Enrollment Date:  %s\n", enrollmentDate.format(formatter)));
        sb.append(String.format("  Status         : %s\n", isActive ? "Active" : "Inactive"));
        sb.append("╚══════════════════════════════════════════════════════════╝");
        
        return sb.toString();
    }

    public String toShortString() {
        return String. format("| %-10s | %-20s | %-15s | %-4d | %-6s | %-8s |",
                studentId, 
                name. length() > 20 ? name.substring(0, 17) + "..." : name, 
                course. length() > 15 ? course.substring(0, 12) + "..." : course, 
                semester, 
                getGrade(), 
                isActive ?  "Active" : "Inactive");
    }
}
