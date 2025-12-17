package utils;

public class Constants {
    public static final String DATA_DIRECTORY = "data/";
    public static final String STUDENTS_FILE = DATA_DIRECTORY + "students.dat";
    
    public static final double MAX_MARKS = 100.0;
    public static final double PASS_MARKS = 40.0;
    
    public static final String[] AVAILABLE_COURSES = {
        "Computer Science",
        "Information Technology",
        "Electronics",
        "Mechanical",
        "Civil",
        "Electrical"
    };
    
    public static final String[] CS_SUBJECTS = {
        "Mathematics", "Programming", "Data Structures", 
        "Database", "Networking", "Operating Systems"
    };
    
    public static final String[] IT_SUBJECTS = {
        "Mathematics", "Web Development", "Software Engineering",
        "Database", "Cloud Computing", "Cyber Security"
    };
    
    public static final String[] DEFAULT_SUBJECTS = {
        "Mathematics", "Physics", "Chemistry",
        "English", "Technical Drawing", "Workshop"
    };

    public static String[] getSubjectsForCourse(String course) {
        if (course. equalsIgnoreCase("Computer Science")) {
            return CS_SUBJECTS;
        } else if (course.equalsIgnoreCase("Information Technology")) {
            return IT_SUBJECTS;
        }
        return DEFAULT_SUBJECTS;
    }
}
