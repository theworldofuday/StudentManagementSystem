package services;

import models.Student;
import utils.*;
import java.util.*;

public class AnalyticsService {

    public void generateOverallStatistics() {
        List<Student> students = DataStorage.getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("\n📊 No data available for statistics.");
            return;
        }

        int totalStudents = students.size();
        int activeStudents = 0;
        int passedStudents = 0;
        int failedStudents = 0;
        int studentsWithMarks = 0;
        double totalAverage = 0;

        Map<String, Integer> courseCount = new HashMap<>();
        Map<String, Integer> gradeCount = new HashMap<>();

        for (Student student : students) {
            if (student.isActive()) activeStudents++;
            
            // Count by course
            String course = student.getCourse();
            courseCount.put(course, courseCount.getOrDefault(course, 0) + 1);

            // Count by grade and pass/fail
            if (! student.getSubjects().isEmpty()) {
                studentsWithMarks++;
                totalAverage += student.getAverageMarks();
                
                String grade = student.getGrade();
                gradeCount.put(grade, gradeCount.getOrDefault(grade, 0) + 1);
                
                if (student.isPassed()) passedStudents++;
                else failedStudents++;
            }
        }

        double avgMarks = studentsWithMarks > 0 ? totalAverage / studentsWithMarks : 0;
        double passPercentage = studentsWithMarks > 0 ? (passedStudents * 100.0 / studentsWithMarks) : 0;

        System.out.println("\n╔═══════════════════════════════════════════════════════════════════╗");
        System.out. println("║                    OVERALL STATISTICS                             ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");
        System.out. println("  📊 STUDENT COUNT");
        System.out.println("     Total Students      : " + totalStudents);
        System.out.println("     Active Students     : " + activeStudents);
        System.out.println("     Inactive Students   : " + (totalStudents - activeStudents));
        System.out.println("  ────────────────────────────────────────────────────────────────");
        System.out. println("  📈 ACADEMIC PERFORMANCE");
        System.out.println("     Students with Marks :  " + studentsWithMarks);
        System.out.println("     Passed Students     : " + passedStudents);
        System.out.println("     Failed Students     : " + failedStudents);
        System.out.printf("     Average Marks       : %. 2f%%\n", avgMarks);
        System.out. printf("     Pass Percentage     : %.2f%%\n", passPercentage);
        System.out. println("  ────────────────────────────────────────────────────────────────");
        System.out.println("  🎓 COURSE-WISE DISTRIBUTION");
        
        for (Map.Entry<String, Integer> entry : courseCount.entrySet()) {
            System.out.printf("     %-20s : %d students\n", entry.getKey(), entry.getValue());
        }
        
        System.out.println("  ────────────────────────────────────────────────────────────────");
        System.out.println("  📝 GRADE DISTRIBUTION");
        
        String[] grades = {"A+", "A", "B+", "B", "C", "D", "F"};
        for (String grade : grades) {
            int count = gradeCount.getOrDefault(grade, 0);
            if (count > 0) {
                System.out.printf("     Grade %-3s : %d students\n", grade, count);
            }
        }
        
        System. out.println("╚═══════════════════════════════════════════════════════════════════╝");
    }

    public void generateCourseWiseReport() {
        List<Student> students = DataStorage.getAllStudents();
        
        if (students.isEmpty()) {
            System. out.println("\n📊 No data available.");
            return;
        }

        Map<String, List<Student>> courseStudents = new HashMap<>();
        
        for (Student student : students) {
            String course = student. getCourse();
            courseStudents.computeIfAbsent(course, k -> new ArrayList<>()).add(student);
        }

        System.out. println("\n╔═══════════════════════════════════════════════════════════════════╗");
        System.out. println("║                   COURSE-WISE REPORT                              ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");

        for (Map.Entry<String, List<Student>> entry : courseStudents.entrySet()) {
            String course = entry.getKey();
            List<Student> courseList = entry.getValue();
            
            int total = courseList.size();
            int passed = 0;
            int withMarks = 0;
            double avgMarks = 0;

            for (Student s : courseList) {
                if (! s.getSubjects().isEmpty()) {
                    withMarks++;
                    avgMarks += s.getAverageMarks();
                    if (s.isPassed()) passed++;
                }
            }

            avgMarks = withMarks > 0 ? avgMarks / withMarks : 0;
            double passPercent = withMarks > 0 ? (passed * 100.0 / withMarks) : 0;

            System.out.println("\n  📚 " + course. toUpperCase());
            System.out.println("     Total Students    : " + total);
            System.out.println("     With Marks        : " + withMarks);
            System.out.println("     Passed            : " + passed);
            System.out.printf("     Average Marks     : %.2f%%\n", avgMarks);
            System. out.printf("     Pass Percentage   : %.2f%%\n", passPercent);
        }
        
        System.out.println("\n╚═══════════════════════════════════════════════════════════════════╝");
    }

    public void generateTopPerformersReport(int count) {
        List<Student> students = DataStorage.getAllStudents();
        
        List<Student> studentsWithMarks = new ArrayList<>();
        for (Student student : students) {
            if (! student.getSubjects().isEmpty()) {
                studentsWithMarks.add(student);
            }
        }

        if (studentsWithMarks.isEmpty()) {
            System.out.println("\n📊 No students with marks found.");
            return;
        }

        // Sort by average marks descending
        studentsWithMarks.sort((s1, s2) -> Double.compare(s2.getAverageMarks(), s1.getAverageMarks()));

        int displayCount = Math.min(count, studentsWithMarks.size());

        System.out.println("\n╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    TOP " + displayCount + " PERFORMERS                                ║");
        System.out. println("╠═══════════════════════════════════════════════════════════════════╣");
        System.out.println("| Rank | Student ID | Name                 | Course          | Avg %  |");
        System.out.println("|------|------------|----------------------|-----------------|--------|");

        for (int i = 0; i < displayCount; i++) {
            Student s = studentsWithMarks.get(i);
            System.out.printf("| %-4d | %-10s | %-20s | %-15s | %5.2f%% |\n",
                    (i + 1), s.getStudentId(), 
                    s.getName().length() > 20 ? s.getName().substring(0, 17) + "..." : s.getName(),
                    s.getCourse().length() > 15 ? s.getCourse().substring(0, 12) + "..." : s.getCourse(),
                    s.getAverageMarks());
        }
        
        System.out.println("╚═══════════════════════════════════════════════════════════════════╝");
    }

    public void generateFailedStudentsReport() {
        List<Student> students = DataStorage. getAllStudents();
        
        List<Student> failedStudents = new ArrayList<>();
        for (Student student : students) {
            if (! student.getSubjects().isEmpty() && !student.isPassed()) {
                failedStudents.add(student);
            }
        }

        if (failedStudents.isEmpty()) {
            System.out.println("\n✓ No failed students found.  Great performance!");
            return;
        }

        System.out.println("\n╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    FAILED STUDENTS REPORT                         ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");
        System.out. println("| Student ID | Name                 | Course          | Failed Subjects |");
        System.out. println("|------------|----------------------|-----------------|-----------------|");

        for (Student s : failedStudents) {
            System.out.printf("| %-10s | %-20s | %-15s | %-15d |\n",
                    s.getStudentId(),
                    s.getName().length() > 20 ? s.getName().substring(0, 17) + "..." : s.getName(),
                    s.getCourse().length() > 15 ? s.getCourse().substring(0, 12) + "..." : s.getCourse(),
                    s.getFailedSubjectsCount());
        }
        
        System.out.println("╚═══════════════════════════════════════════════════════════════════╝");
        System.out.println("  Total Failed Students: " + failedStudents.size());
    }

    public void generateSemesterWiseReport() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                   SEMESTER-WISE DISTRIBUTION                      ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");
        System.out. println("| Semester | Students | With Marks | Passed | Pass %  |");
        System.out.println("|----------|----------|------------|--------|---------|");

        for (int sem = 1; sem <= 8; sem++) {
            List<Student> semStudents = DataStorage.getStudentsBySemester(sem);
            
            if (! semStudents.isEmpty()) {
                int total = semStudents. size();
                int withMarks = 0;
                int passed = 0;

                for (Student s : semStudents) {
                    if (! s.getSubjects().isEmpty()) {
                        withMarks++;
                        if (s.isPassed()) passed++;
                    }
                }

                double passPercent = withMarks > 0 ? (passed * 100.0 / withMarks) : 0;

                System.out.printf("| %-8d | %-8d | %-10d | %-6d | %6.2f%% |\n",
                        sem, total, withMarks, passed, passPercent);
            }
        }
        
        System.out.println("╚═══════════════════════════════════════════════════════════════════╝");
    }
}
