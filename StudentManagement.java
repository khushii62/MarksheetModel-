package com.thrivesup.marksheet;

import java.util.InputMismatchException;
import java.time.LocalDate;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.LinkedHashSet;
import java.util.List;
public class StudentManagement {
	
    static Scanner sc = new Scanner(System.in);
    static MarksheetModelInterface model = new MarksheetModel();

    public static void start() {
        int choice;
        while (true) {
        	System.out.println("╔════════════════════════════════════╗");
        	System.out.println("║    Marksheet Management Menu       ║");
        	System.out.println("╠════════════════════════════════════╣");
        	System.out.println("║ 1.  Add Student                    ║");
        	System.out.println("║ 2.  Remove Student by Roll No.     ║");
        	System.out.println("║ 3.  Remove Student by Email ID     ║");
        	System.out.println("║ 4.  Update                         ║");
        	System.out.println("║ 5.  Update All                     ║");
        	System.out.println("║ 6.  Delete All                     ║");
        	System.out.println("║ 7.  Delete                         ║");
        	System.out.println("║ 8.  Get                            ║");
        	System.out.println("║ 9.  Get All                        ║");
        	System.out.println("║ 10. Get Merit List                 ║");
        	System.out.println("║ 11. Number of Students             ║");
        	System.out.println("║ 12. getFailedStudentList           ║");
        	System.out.println("║ 13. getAbsentees                   ║");
        	System.out.println("║ 14. getTopper                      ║");
        	System.out.println("║ 15. getLowestMarkesStudent         ║");
        	System.out.println("║ 16. getATKTStudents                ║");
        	System.out.println("║ 17. getCutoff                      ║");
        	System.out.println("║ 18. getNumberOfBoysPass            ║");
        	System.out.println("║ 19. getNumberOfGirlsPass           ║");
        	System.out.println("║ 20. getGradeOfStudent              ║");
        	System.out.println("║ 21. getTotalNumberOfGirls          ║");
        	System.out.println("║ 22. getTotalNumberOfBoys           ║");
        	System.out.println("║ 23. getAverageResultOfGirls        ║");
        	System.out.println("║ 24. getAverageResultOfBoys         ║");
        	System.out.println("║ 25. Exit                           ║");
        	System.out.println("╚════════════════════════════════════╝");
        	
        	while (true) {
        	    try {
        	        System.out.print("Enter your choice: ");
        	        choice = sc.nextInt();
        	        sc.nextLine(); 
        	        break; 
        	    } catch (InputMismatchException e) {
        	        System.err.println("❌ Invalid choice! Please enter only a number (1-25).\n");
        	        sc.nextLine(); 
        	    }
        	}

            switch (choice) {
                case 1:
                    addStudent();       
                    break;
                case 2:
                    deleteByRollNo();
                    break;
                case 3:
                    deleteByEmailId();
                	break;
                case 4:
                	updateStudent();
                	break;
                case 5:
                	updateAllStudent();
                	break;
                case 6:
                	deleteAll();
                   	break;
                case 7:
                    delete();
                    break;
                case 8:
                	getStudentByRollNo();
                	break;
                case 9:
                	showAllStudents();
                	break;
                case 10:
                    showMeritList();
                    break;
                case 11:
                	showNumberOfStudents();
                	break;
                case 12:
                	showFailedStudents();
                	break;
                case 13:
                	showAbsentees();
                	break;
                case 14:
                	showTopper();
                	break;
                case 15:
                	showLowestMarksStudent();
                	break;
                case 16:
                    showATKTStudents();
                	break;
                case 17:
                	showCutoff();
                	break;
                case 18:
                	showBoysPassCount();
                	break;
                case 19:
                	showGirlsPassCount();
                	break;
                case 20:
                    showStudentGrade(); // only call the method
                    break;
                case 21:
                	showTotalNumberOfGirls();
                	break;
                case 22:
                	showTotalNumberOfBoys();
                	break;
                case 23:
                	showAverageResultOfGirls();
                	break;
                case 24:
                	showAverageResultOfBoys();
                	break;
                case 25:
                    System.out.println("Exiting... Thank you!");
                    return;
                default:
                    System.err.println("Invalid choice, try again.");
            }
        }
    }
	private static String generateRollNo() {
        String prefix = "22ENGCAI";
        int nextId = 1001; 

        try {
            java.sql.Connection conn = DBUtil.getConnection(); 
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery("SELECT MAX(rollNo) FROM Student_info");

            if (rs.next() && rs.getString(1) != null) {
                String lastRoll = rs.getString(1);   
                String lastNum = lastRoll.substring(prefix.length()); 
                nextId = Integer.parseInt(lastNum) + 1;  
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return prefix + nextId;
    }
	public static void addStudent() {
	    Scanner sc = new Scanner(System.in);
	    MarksheetDAO dao = new MarksheetDAO();  

	    Marksheet m = new Marksheet();

	    m.setRollNo(generateRollNo()); 
	    m.setName(ValidationUtils.validateName(sc));
	    m.setPhysics(ValidationUtils.validateMarks(sc, "Physics"));
	    m.setChemistry(ValidationUtils.validateMarks(sc, "Chemistry"));
	    m.setMath(ValidationUtils.validateMarks(sc, "Math"));
	    m.setGender(ValidationUtils.validateGender(sc));
	    m.setDob(java.sql.Date.valueOf(ValidationUtils.validateDOB(sc)));
	    m.setMob(ValidationUtils.validateMobile(sc));
	    m.setEmailID(ValidationUtils.validateEmail(sc));

	    boolean status = dao.add(m);
	    if (status) {
	        System.out.println("Student added successfully!");
	    } else {
	        System.out.println("Failed to add student.");
	    }
	}

    public static boolean deleteByRollNo() {
        boolean deleted = false;
        Scanner sc = new Scanner(System.in);
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBUtil.getConnection();

            while (true) {
                System.out.print("Enter Roll No to delete: ");
                String rollNo = sc.nextLine().trim();

                String checkQuery = "SELECT * FROM Student_info WHERE rollNo = ?";
                ps = con.prepareStatement(checkQuery);
                ps.setString(1, rollNo);
                rs = ps.executeQuery();

                if (!rs.next()) {
                    System.err.println("❌ Roll No " + rollNo + " does not exist! Please try again.");
                    continue; 
                }

                System.out.print("Are you sure you want to delete Roll No " + rollNo + "? (yes/no): ");
                String choice = sc.nextLine().trim().toLowerCase();

                if (choice.equals("yes")) {
                    String deleteQuery = "DELETE FROM Student_info WHERE rollNo = ?";
                    ps = con.prepareStatement(deleteQuery);
                    ps.setString(1, rollNo);

                    int rows = ps.executeUpdate();
                    if (rows > 0) {
                        deleted = true;
                        System.out.println("Student deleted successfully!");
                    }
                } else {
                    System.out.println("Delete cancelled.");
                }
                break; 
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deleted;
    }


    public static boolean deleteByEmailId() {
        boolean deleted = false;
        Scanner sc = new Scanner(System.in);
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBUtil.getConnection();

            while (true) {
                System.out.print("Enter Email ID to delete: ");
                String email = sc.nextLine().trim();

              
                String checkQuery = "SELECT * FROM Student_info WHERE EmailID = ?";
                ps = con.prepareStatement(checkQuery);
                ps.setString(1, email);
                rs = ps.executeQuery();

                if (!rs.next()) {
                    System.err.println("❌ Email " + email + " does not exist! Please try again.");
                    continue; 
                }

             
                System.out.print("Are you sure you want to delete student with Email " + email + "? (yes/no): ");
                String choice = sc.nextLine().trim().toLowerCase();

                if (choice.equals("yes")) {
                    // Delete query
                    String deleteQuery = "DELETE FROM Student_info WHERE EmailID = ?";
                    ps = con.prepareStatement(deleteQuery);
                    ps.setString(1, email);

                    int rows = ps.executeUpdate();
                    if (rows > 0) {
                        deleted = true;
                        System.out.println("Student deleted successfully!");
                    }
                } else {
                    System.out.println("Delete cancelled.");
                }
                break; 
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deleted;
    }

 //UPDATE

    public static void updateStudent() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Enter Roll No of student to update: ");
            String rollNo = sc.nextLine().trim();

            try (Connection conn = DBUtil.getConnection()) {
                String checkSql = "SELECT COUNT(*) FROM Student_info WHERE rollNo = ?";
                try (PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
                    checkPs.setString(1, rollNo);
                    ResultSet rs = checkPs.executeQuery();
                    rs.next();
                    if (rs.getInt(1) == 0) {
                        System.err.println("Roll No not found! Please try again.\n");
                        continue; 
                    }
                }

                System.out.println("\nSelect field to update:");
                System.out.println("1. Name");
                System.out.println("2. Email");
                System.out.println("3. DOB");
                System.out.println("4. Gender");
                System.out.println("5. Physics Marks");
                System.out.println("6. Chemistry Marks");
                System.out.println("7. Math Marks");
                System.out.println("8. Mobile Number");

                int choice;
                while (true) {
                    try {
                        System.out.print("Enter your choice: ");
                        choice = sc.nextInt();
                        sc.nextLine();
                        if (choice >= 1 && choice <= 8) break;
                        else System.err.println("Invalid choice! Enter between 1-8.");
                    } catch (InputMismatchException e) {
                        System.err.println("Invalid input! Please enter a number.");
                        sc.nextLine();
                    }
                }

                String sql = null;
                PreparedStatement ps = null;

                switch (choice) {
                    case 1: 
                        String name = ValidationUtils.validateName(sc);
                        sql = "UPDATE Student_info SET name=? WHERE rollNo=?";
                        ps = conn.prepareStatement(sql);
                        ps.setString(1, name);
                        ps.setString(2, rollNo);
                        break;

                    case 2: 
                        String email = ValidationUtils.validateEmail(sc); // Assume method checks duplicate
                        sql = "UPDATE Student_info SET EmailID=? WHERE rollNo=?";
                        ps = conn.prepareStatement(sql);
                        ps.setString(1, email);
                        ps.setString(2, rollNo);
                        break;

                    case 3: 
                        LocalDate dobLocal = ValidationUtils.validateDOB(sc);
                        
                        java.sql.Date dob = java.sql.Date.valueOf(dobLocal);

                        sql = "UPDATE Student_info SET dob=? WHERE rollNo=?";
                        ps = conn.prepareStatement(sql);
                        ps.setDate(1, dob);
                        ps.setString(2, rollNo);
                        break;


                    case 4: 
                        String gender = ValidationUtils.validateGender(sc);
                        sql = "UPDATE Student_info SET gender=? WHERE rollNo=?";
                        ps = conn.prepareStatement(sql);
                        ps.setString(1, gender);
                        ps.setString(2, rollNo);
                        break;

                    case 5: //phy
                    case 6: // Chemistry
                    case 7: // Math
                        String subject = (choice == 5) ? "physics" : (choice == 6) ? "chemistry" : "math";
                        int marks = ValidationUtils.validateMarks(sc, subject);
                        sql = "UPDATE Student_info SET " + subject + "=? WHERE rollNo=?";
                        ps = conn.prepareStatement(sql);
                        ps.setInt(1, marks);
                        ps.setString(2, rollNo);
                        break;

                    case 8: 
                        long mob = ValidationUtils.validateMobile(sc);
                        sql = "UPDATE Student_info SET mob=? WHERE rollNo=?";
                        ps = conn.prepareStatement(sql);
                        ps.setLong(1, mob);
                        ps.setString(2, rollNo);
                        break;

                    default:
                        System.out.println("Invalid choice!");
                        return;
                }

                int count = ps.executeUpdate();
                if (count > 0) System.out.println("Student record updated successfully!");
                ps.close();
                break; 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void updateAllStudent() {
        Scanner sc = new Scanner(System.in);
        MarksheetModel model = new MarksheetModel();

        System.out.print("Enter Roll No of the student to update: ");
        String rollNo = sc.nextLine().trim();

        Marksheet m = new Marksheet();
        m.setRollNo(rollNo);

        
        m.setName(ValidationUtils.validateName(sc));

        
        m.setEmailID(ValidationUtils.validateEmail(sc)); // create overloaded method with rollNo to check uniqueness

       
        LocalDate dobLocal = ValidationUtils.validateDOB(sc);
        m.setDob(java.sql.Date.valueOf(dobLocal));
        m.setGender(ValidationUtils.validateGender(sc));
        m.setPhysics(ValidationUtils.validateMarks(sc, "Physics"));
        m.setChemistry(ValidationUtils.validateMarks(sc, "Chemistry"));
        m.setMath(ValidationUtils.validateMarks(sc, "Math"));
        m.setMob(ValidationUtils.validateMobile(sc));
        boolean updated = model.updateAll(m);
        if (updated) System.out.println("Student updated successfully!");
        else System.err.println("Failed to update student.");
    }

    public static boolean deleteAll() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Are you sure you want to delete ALL students? (yes/no): ");
        String choice = sc.nextLine().trim().toLowerCase();

        if (!choice.equals("yes")) {
            System.out.println("Delete cancelled.");
            return false;
        }

        boolean deleted = false;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBUtil.getConnection();
            String query = "DELETE FROM Student_info";
            ps = con.prepareStatement(query);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                deleted = true;
                System.out.println("All students deleted successfully!");
            } else {
                System.out.println("No student records found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return deleted;
    }
    public static boolean delete() {
    	        boolean deleted = false;
    	        Scanner sc = new Scanner(System.in);
    	        Connection con = null;
    	        PreparedStatement ps = null;
    	        ResultSet rs = null;

    	        try {
    	            con = DBUtil.getConnection();
    	            while (true) {
    	                System.out.print("Enter Roll No to delete: ");
    	              String  rollNo = sc.nextLine().trim();

    	               
    	                String checkQuery = "SELECT * FROM Student_info WHERE rollNo = ?";
    	                ps = con.prepareStatement(checkQuery);
    	                ps.setString(1, rollNo);
    	                rs = ps.executeQuery();

    	                if (!rs.next()) {
    	                    System.err.println("❌ Roll No " + rollNo + " does not exist! Please try again.");
    	                    continue; 
    	                }

    	           
    	                System.out.print("Are you sure you want to delete Roll No " + rollNo + "? (yes/no): ");
    	                String choice = sc.nextLine().trim().toLowerCase();

    	                if (choice.equals("yes")) {
    	                  
    	                    String deleteQuery = "DELETE FROM Student_info WHERE rollNo = ?";
    	                    ps = con.prepareStatement(deleteQuery);
    	                    ps.setString(1, rollNo);

    	                    int rows = ps.executeUpdate();
    	                    if (rows > 0) {
    	                        deleted = true;
    	                        System.out.println("Student deleted successfully!");
    	                    }
    	                } else {
    	                    System.out.println("Delete cancelled.");
    	                }
    	                break; 
    	            }

    	        } catch (Exception e) {
    	            e.printStackTrace();
    	        } finally {
    	            try {
    	                if (rs != null) rs.close();
    	                if (ps != null) ps.close();
    	                if (con != null) con.close();
    	            } catch (Exception e) {
    	                e.printStackTrace();
    	            }
    	        }
    	        return deleted;
    	    }
    
    public static void getStudentByRollNo() {
        while (true) {
            System.out.print("Enter Roll No to Search: ");
            String rollNo = sc.nextLine().trim();

            MarksheetDAO dao = new MarksheetDAO();
            ArrayList<Marksheet> list = dao.get(rollNo);

            if (list.isEmpty()) {
                System.err.println("No record found for Roll No: " + rollNo + "\n");
                System.out.println("Please try again...\n");
            } else {
                System.out.println("Student Record Found:");
                for (Marksheet m : list) {
                    System.out.println("Roll No : " + m.getRollNo());
                    System.out.println("Name    : " + m.getName());
                    System.out.println("Email   : " + m.getEmailID());
                    System.out.println("DOB     : " + m.getDob());
                    System.out.println("Gender  : " + m.getGender());
                    System.out.println("Physics : " + m.getPhysics());
                    System.out.println("Chem    : " + m.getChemistry());
                    System.out.println("Math    : " + m.getMath());
                    System.out.println("Mobile  : " + m.getMob());
                    System.out.println("-------------------------------");
                }
                break; 
            }
        }
    }

        
    public static void showAllStudents() {
        MarksheetDAO dao = new MarksheetDAO();
        ArrayList<Marksheet> list = dao.getAll();

        if (list.isEmpty()) {
            System.err.println("No student records found in database!");
        } else {
            System.out.println("==========================================================================================================================");
            System.out.printf("%-12s %-20s %-25s %-12s %-8s %-8s %-8s %-8s %-15s%n",
                    "Roll No", "Name", "Email", "DOB", "Gender", "Physics", "Chem", "Math", "Mobile");
            System.out.println("==========================================================================================================================");

            for (Marksheet m : list) {
                System.out.printf("%-10s %-20s %-25s %-12s %-8s %-8d %-8d %-8d %-15s%n",
                        m.getRollNo(),
                        m.getName(),
                        m.getEmailID(),
                        m.getDob(),
                        m.getGender(),
                        m.getPhysics(),
                        m.getChemistry(),
                        m.getMath(),
                        m.getMob());
            }

            System.out.println("==========================================================================================================================");
        }
    }

        public static void showMeritList() {
            try {
                MarksheetModel model = new MarksheetModel();
                LinkedList<Marksheet> meritList = model.getMeritList();

                System.out.println("\n===== Top 10 Students (Merit List) =====");
                System.out.printf("%-12s %-20s %-25s %-12s %-8s %-8s %-8s %-15s\n",
                        "RollNo", "Name", "EmailID", "DOB", "Physics", "Chemistry", "Math", "Mobile");

                for (Marksheet m : meritList) {
                    System.out.printf("%-10s %-20s %-25s %-12s %-8d %-8d %-8d %-12d\n",
                            m.getRollNo(),
                            m.getName(),
                            m.getEmailID(),
                            m.getDob(),
                            m.getPhysics(),
                            m.getChemistry(),
                            m.getMath(),
                            m.getMob());
                }
                System.out.println("======================================================================================================================");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public static void showNumberOfStudents() {
            try {
                MarksheetModel model = new MarksheetModel();
                int total = model.numberOfStudents();
                System.out.println("\n Total Number of Students: " + total);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public static void showFailedStudents() {
            try {
                MarksheetModel model = new MarksheetModel();
                LinkedHashSet<Marksheet> failedList = model.getFailedStudentList();

                if (failedList.isEmpty()) {
                    System.out.println("\n No failed students. Everyone passed!");
                } else {
                    System.out.println("\n===== Failed Students List =====");
                    System.out.printf("%-10s %-15s %-25s %-12s %-8s %-8s %-8s %-12s\n",
                            "RollNo", "Name", "EmailID", "DOB", "Physics", "Chemistry", "Math", "Mobile");

                    for (Marksheet m : failedList) {
                        System.out.printf("%-10s %-20s %-25s %-12s %-8d %-8d %-8d %-12d\n",
                                m.getRollNo(),
                                m.getName(),
                                m.getEmailID(),
                                m.getDob(),
                                m.getPhysics(),
                                m.getChemistry(),
                                m.getMath(),
                                m.getMob());
                    }
                    System.out.println("=================================");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public static void showAbsentees() {
            try {
                MarksheetModel model = new MarksheetModel();
                ArrayList<Marksheet> absentees = model.getAbsentees();

                if (absentees.isEmpty()) {
                    System.out.println("\n No absentees found!");
                } else {
                    System.out.println("\n===== Absentees List =====");
                    System.out.printf("%-10s %-20s %-25s %-12s %-8s %-8s %-8s %-12s\n",
                            "RollNo", "Name", "EmailID", "DOB", "Physics", "Chemistry", "Math", "Mobile");

                    for (Marksheet m : absentees) {
                        System.out.printf("%-10s %-15s %-25s %-12s %-8d %-8d %-8d %-12d\n",
                                m.getRollNo(),
                                m.getName(),
                                m.getEmailID(),
                                m.getDob(),
                                m.getPhysics(),
                                m.getChemistry(),
                                m.getMath(),
                                m.getMob());
                    }
                    System.out.println("==================================");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Topper student
        public static void showTopper() {
            try {
                MarksheetModel model = new MarksheetModel();
                ArrayList<Marksheet> topper = model.getTopper();

                if (topper.isEmpty()) {
                    System.out.println("\n No topper found!");
                } else {
                    System.out.println("\n===== Topper Student =====");
                    for (Marksheet m : topper) {
                        int total = m.getPhysics() + m.getChemistry() + m.getMath();
                        System.out.printf("RollNo: %s\nName: %s\nEmail: %s\nDOB: %s\nGender: %s\nPhysics: %d\nChemistry: %d\nMath: %d\nTotal: %d\nMobile: %d\n",
                                m.getRollNo(),
                                m.getName(),
                                m.getEmailID(),
                                m.getDob(),
                                m.getGender(),
                                m.getPhysics(),
                                m.getChemistry(),
                                m.getMath(),
                                total,
                                m.getMob());
                    }
                    System.out.println("===========================");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
     // Lowest scorer
        public static void showLowestMarksStudent() {
            try {
                MarksheetModel model = new MarksheetModel();
                String[][] data = model.getLowestMarkesStudent();

                if (data == null) {
                    System.out.println("\n  No data found!");
                } else {
                    System.out.println("\n===== Lowest Marks Student =====");
                    System.out.printf("%-10s %-20s %-25s %-12s %-8s %-8s %-8s %-8s %-12s\n",
                            "RollNo", "Name", "EmailID", "DOB", "Gender",
                            "Physics", "Chemistry", "Math", "Mobile");

                    System.out.printf("%-10s %-15s %-25s %-12s %-8s %-8s %-8s %-8s %-12s\n",
                            data[0][0], data[0][1], data[0][2], data[0][3], data[0][4],
                            data[0][5], data[0][6], data[0][7], data[0][8]);
                    System.out.println("=================================");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // ATKT students
        public static void showATKTStudents() {
            try {
                MarksheetModel model = new MarksheetModel();
                List<Marksheet> atktList = model.getATKTStudents();

                if (atktList.isEmpty()) {
                    System.out.println("\n No ATKT students!");
                } else {
                    System.out.println("\n===== ATKT Students =====");
                    System.out.printf("%-10s %-15s %-25s %-12s %-8s %-8s %-8s %-12s\n",
                            "RollNo", "Name", "EmailID", "DOB", "Physics", "Chemistry", "Math", "Mobile");

                    for (Marksheet m : atktList) {
                        System.out.printf("%-10s %-20s %-25s %-12s %-8d %-8d %-8d %-12d\n",
                                m.getRollNo(),
                                m.getName(),
                                m.getEmailID(),
                                m.getDob(),
                                m.getPhysics(),
                                m.getChemistry(),
                                m.getMath(),
                                m.getMob());
                    }
                    System.out.println("=================================");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public static void showCutoff() {
            try {
                MarksheetModel model = new MarksheetModel();
                double cutoff = model.getCutoff();
               
                System.out.printf("\n Cutoff (Average Marks of Class): %.2f\n", cutoff);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Number of Boys Passed
        public static void showBoysPassCount() {
            try {
                MarksheetModel model = new MarksheetModel();
                int count = model.getNumberOfBoysPass();
                System.out.println("\n Total Boys Passed: " + count);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Number of Girls Passed
        public static void showGirlsPassCount() {
            try {
                MarksheetModel model = new MarksheetModel();
                int count = model.getNumberOfGirlsPass();
                System.out.println("\n Total Girls Passed: " + count);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
     // Show Grade of Student
        public static void showStudentGrade() {
            Scanner sc = new Scanner(System.in);
            MarksheetModel model = new MarksheetModel();
            String rollNo;

            while (true) {
                System.out.print("Enter Roll No of student to see grade: ");
                rollNo = sc.nextLine().trim();

                try {
                    // Check if rollNo exists
                    if (model.get(rollNo).isEmpty()) { // assuming get() returns a List
                        System.err.println("Roll No does not exist in database! Please try again.\n");
                    } else {
                        // Valid roll number found, display grade
                        char grade = model.getGradeOfStudent(rollNo);
                        System.out.println("\nGrade of Student (Roll No: " + rollNo + ") = " + grade);
                        break; // exit loop after successful display
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

     // Show Total Number of Girls
        public static void showTotalNumberOfGirls() {
            try {
                MarksheetModel model = new MarksheetModel();
                int count = model.getTotalNumberOfGirls();
                System.out.println("\n Total Number of Girls: " + count);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
     // Show Total Number of Boys
        public static void showTotalNumberOfBoys() {
            try {
                MarksheetModel model = new MarksheetModel();
                int count = model.getTotalNumberOfBoys();
                System.out.println("\n Total Number of Boys: " + count);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
     // Show Average Result of Girls
        public static void showAverageResultOfGirls() {
            try {
                MarksheetModel model = new MarksheetModel();
                double avg = model.getAverageResultOfGirls();
                System.out.printf("\n Average Result of Girls: %.2f%%\n", avg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
     // Show Average Result of Boys
        public static void showAverageResultOfBoys() {
            try {
                MarksheetModel model = new MarksheetModel();
                double avg = model.getAverageResultOfBoys();
                System.out.printf("\n Average Result of Boys: %.2f%%\n", avg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

}


