package utils;

import java.util.regex.Pattern;

public class InputValidator {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );
    
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^[6-9]\\d{9}$"
    );
    
    private static final Pattern NAME_PATTERN = Pattern.compile(
        "^[A-Za-z\\s]{2,50}$"
    );

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN. matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean isValidName(String name) {
        return name != null && NAME_PATTERN.matcher(name).matches();
    }

    public static boolean isValidMarks(double marks) {
        return marks >= 0 && marks <= Constants.MAX_MARKS;
    }

    public static boolean isValidSemester(int semester) {
        return semester >= 1 && semester <= 8;
    }

    public static boolean isValidDate(int day, int month, int year) {
        if (year < 1980 || year > 2010) return false;
        if (month < 1 || month > 12) return false;
        if (day < 1 || day > 31) return false;
        
        if (month == 2) {
            boolean isLeap = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
            return day <= (isLeap ? 29 :  28);
        }
        
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return day <= 30;
        }
        
        return true;
    }
}
