package utils;

import models.Student;
import java.io.*;
import java.util.*;

public class DataStorage {
    
    private static Map<String, Student> students = new HashMap<>();
    private static int studentCounter = 1;

    static {
        createDataDirectory();
        loadData();
    }

    private static void createDataDirectory() {
        File directory = new File(Constants.DATA_DIRECTORY);
        if (!directory.exists()) {
            directory. mkdirs();
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadData() {
        try {
            File file = new File(Constants.STUDENTS_FILE);
            if (file.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                students = (Map<String, Student>) ois.readObject();
                ois. close();
                
                for (String id : students.keySet()) {
                    try {
                        int num = Integer.parseInt(id.substring(3));
                        if (num >= studentCounter) {
                            studentCounter = num + 1;
                        }
                    } catch (NumberFormatException e) {
                        // Ignore parsing errors
                    }
                }
            }
        } catch (Exception e) {
            System. out.println("Note: Starting with fresh data.");
            students = new HashMap<>();
        }
    }

    public static void saveData() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(Constants. STUDENTS_FILE));
            oos.writeObject(students);
            oos.close();
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public static String generateStudentId() {
        return "STU" + String.format("%04d", studentCounter++);
    }

    public static void addStudent(Student student) {
        students.put(student.getStudentId(), student);
        saveData();
    }

    public static Student getStudent(String studentId) {
        return students.get(studentId. toUpperCase());
    }

    public static Student getStudentByEmail(String email) {
        for (Student student : students.values()) {
            if (student. getEmail().equalsIgnoreCase(email)) {
                return student;
            }
        }
        return null;
    }

    public static List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }

    public static List<Student> getActiveStudents() {
        List<Student> activeStudents = new ArrayList<>();
        for (Student student : students.values()) {
            if (student.isActive()) {
                activeStudents.add(student);
            }
        }
        return activeStudents;
    }

    public static List<Student> getStudentsByCourse(String course) {
        List<Student> result = new ArrayList<>();
        for (Student student : students.values()) {
            if (student.getCourse().equalsIgnoreCase(course)) {
                result.add(student);
            }
        }
        return result;
    }

    public static List<Student> getStudentsBySemester(int semester) {
        List<Student> result = new ArrayList<>();
        for (Student student : students.values()) {
            if (student.getSemester() == semester) {
                result.add(student);
            }
        }
        return result;
    }

    public static List<Student> searchStudents(String keyword) {
        List<Student> result = new ArrayList<>();
        String lowerKeyword = keyword. toLowerCase();
        for (Student student : students.values()) {
            if (student.getName().toLowerCase().contains(lowerKeyword) ||
                student.getStudentId().toLowerCase().contains(lowerKeyword) ||
                student.getEmail().toLowerCase().contains(lowerKeyword) ||
                student.getCourse().toLowerCase().contains(lowerKeyword)) {
                result.add(student);
            }
        }
        return result;
    }

    public static void updateStudent(Student student) {
        students.put(student.getStudentId(), student);
        saveData();
    }

    public static boolean deleteStudent(String studentId) {
        if (students.containsKey(studentId. toUpperCase())) {
            students.remove(studentId.toUpperCase());
            saveData();
            return true;
        }
        return false;
    }

    public static int getTotalStudentCount() {
        return students. size();
    }

    public static int getActiveStudentCount() {
        int count = 0;
        for (Student student : students.values()) {
            if (student.isActive()) count++;
        }
        return count;
    }

    public static boolean studentExists(String studentId) {
        return students.containsKey(studentId.toUpperCase());
    }

    public static boolean emailExists(String email) {
        return getStudentByEmail(email) != null;
    }
}
