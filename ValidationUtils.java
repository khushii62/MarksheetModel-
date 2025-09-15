package com.thrivesup.marksheet;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ValidationUtils {

    public static String validateName(Scanner sc) {
        String name;
        while (true) {
            System.out.print("Enter Your Name : ");
            name = sc.nextLine().trim().replaceAll("\\s+", " ");

            String error = (name.length() < 2 || name.length() > 30)
                    ? "Invalid Name! Name must be 2–30 characters long.\n"
                    : (!name.matches("^[A-Za-z ]+$")
                        ? "Invalid Name! Name must contain only alphabets.\n"
                        : "");

            if (error.isEmpty()) break;
            System.err.print(error);
        }
        return name;
    }

    public static int validateMarks(Scanner sc, String subject) {
        int marks;
        while (true) {
            try {
                System.out.print("Enter Your " + subject + " Marks (0-100 or A for Absent): ");
                String input = sc.next();

                if (input.equalsIgnoreCase("A")) {
                    return -1; 
                }

                marks = Integer.parseInt(input);
                if (marks >= 0 && marks <= 100) return marks;
                else System.err.println("Invalid Marks! Enter 0-100 or A.\n");

            } catch (NumberFormatException e) {
                System.err.println("Invalid Input! Enter only numbers (0-100) or A.\n");
            }
        }
    }

 // Gender
    public static char validateGender(Scanner sc) {
        char gender;
        while (true) {
            System.out.print("Enter Your Gender (M/F): ");
            gender = sc.next().toUpperCase().charAt(0);  // first character
            if (gender == 'M' || gender == 'F') {
                return gender;
            } else {
                System.err.println("Invalid Gender! Please enter M or F.");
            }
        }
    }


    // DOB
    public static LocalDate validateDOB(Scanner sc) {
        LocalDate dob;
        while (true) {
            System.out.print("Enter Your DOB (yyyy-mm-dd): ");
            String dobStr = sc.next();
            try {
                dob = LocalDate.parse(dobStr);
                LocalDate today = LocalDate.now();

                if (dob.isAfter(today)) {
                    System.err.println("DOB cannot be in the future.\n");
                    continue;
                }

                int age = Period.between(dob, today).getYears();
                if (age < 14) {
                    System.err.println("Minimum age 14 years.\n");
                    continue;
                } else if (age > 80) {
                    System.err.println("Maximum age 80 years.\n");
                    continue;
                }

                return dob;
            } catch (Exception e) {
                System.err.println("Invalid format! Use yyyy-mm-dd.\n");
            }
        }
    }

    //Mobile
    public static long validateMobile(Scanner sc) {
        String mobStr;
        while (true) {
            System.out.print("Enter Your Mobile Number : ");
            mobStr = sc.next();

            if (mobStr.matches("^[6-9]\\d{9}$")) {
                return Long.parseLong(mobStr);
            } else {
                System.err.println("Invalid Mobile Number!\n");
            }
        }
    }

    //Email
    public static String validateEmail(Scanner sc) {
        String email;
        while (true) {
            System.out.print("Enter Your Email ID : ");
            email = sc.next().trim();

                    
                    if (!Pattern.matches("^[A-Za-z0-9._%+-]+@(gmail\\.com|yahoo\\.com|outlook\\.com|hotmail\\.com|rediffmail\\.com)$", email)) {
                        System.err.println("Invalid Email format. Only Gmail, Yahoo, Outlook, Hotmail, Rediffmail allowed.\n");
                        continue;
            }
            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM Student_info WHERE EmailID=?")) {
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    if (rs.getInt(1) > 0) {
                        System.err.println("Email already exists! Try another.\n");
                        continue;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return email;
        }
    }
}
