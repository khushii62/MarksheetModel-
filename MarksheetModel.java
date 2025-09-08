package com.thrivesup.marksheet;

import java.sql.ResultSet;
	import java.util.ArrayList;
	import java.util.List;
	import java.util.*;
	import java.sql.SQLException;
	import java.sql.Connection;
	import java.sql.PreparedStatement;

public class MarksheetModel implements MarksheetModelInterface {

	        @Override
	        public boolean add(Marksheet m) {
	            boolean isInserted = false;
	            try (Connection con = DBUtil.getConnection()) {
	                String sql = "INSERT INTO Student_info (rollNo, name, physics, chemistry, math, gender, dob, mob, emailID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	                PreparedStatement ps = con.prepareStatement(sql);

	                ps.setString(1, m.getRollNo());
	                ps.setString(2, m.getName());
	                ps.setInt(3, m.getPhysics());
	                ps.setInt(4, m.getChemistry());
	                ps.setInt(5, m.getMath());
	                ps.setString(6, m.getGender());
	                ps.setDate(7, m.getDob());
	                ps.setLong(8, m.getMob());
	                ps.setString(9, m.getEmailID());

	                int rows = ps.executeUpdate();
	                if (rows > 0) {
	                    isInserted = true;
	                    System.out.println("Student added successfully!");
	                }
	            }
	            catch(SQLException sqle){
	               sqle.printStackTrace();
	            }
	            return isInserted;
	        }

	        @Override
	        public boolean deleteByRollNo(String rollNo) {
	            boolean status = false;
	            try {
	                Connection conn = DBUtil.getConnection();
	                PreparedStatement ps = conn.prepareStatement("DELETE FROM Student_info WHERE rollNo=?");
	                ps.setString(1, rollNo);
	                int count = ps.executeUpdate();
	                if (count > 0) {
	                    status = true;
	                }
	                ps.close();
	                conn.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return status;
	        }

	        @Override
	        public boolean deleteByEmailId(String email) {
	            // delete by email
	        	  boolean status = false;
		            try {
		                Connection conn = DBUtil.getConnection();
		                PreparedStatement ps = conn.prepareStatement("DELETE FROM Student_info WHERE EmailID=?");  
		                ps.setString(1, email); 
		                int count = ps.executeUpdate();
		                if (count > 0) {
		                    status = true;
		                }
		                ps.close();
		                conn.close();
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		            return status;
		        }

	        @Override
	        public boolean update(String rollNo) {
	            boolean status = false;
	            try {
	                Connection conn = DBUtil.getConnection();

	                String sql = "UPDATE Student_info SET name = ?, email = ? WHERE rollNo = ?";
	                PreparedStatement ps = conn.prepareStatement(sql);

	              
	                ps.setString(1, "Updated Name");  
	                ps.setString(2, "updatedemail@example.com");  
	                ps.setString(3, rollNo);

	                int count = ps.executeUpdate();
	                if (count > 0) {
	                    status = true;
	                }

	                ps.close();
	                conn.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return status;
	        }

	        @Override
	        public boolean updateAll(Marksheet m) {
	            boolean status = false;
	            try {
	                Connection conn = DBUtil.getConnection();
	                String sql = "UPDATE Student_info SET name=?, emailID=?, dob=?, gender=?, physics=?, chemistry=?, math=?, mob=? WHERE rollNo=?";
	                
	                PreparedStatement ps = conn.prepareStatement(sql);
	                ps.setString(1, m.getName());
	                ps.setString(2, m.getEmailID());
	                ps.setDate(3, m.getDob());
	                ps.setString(4, m.getGender());
	                ps.setInt(5, m.getPhysics());
	                ps.setInt(6, m.getChemistry());
	                ps.setInt(7, m.getMath());
	                ps.setLong(8, m.getMob());
	                ps.setString(9, m.getRollNo());

	                int count = ps.executeUpdate();
	                if (count > 0) {
	                    status = true;
	                }

	                ps.close();
	                conn.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return status;
	        }

	        @Override
	        public boolean deleteAll() {
		            boolean status = false;
		            try {
		                Connection conn = DBUtil.getConnection();
		                PreparedStatement ps = conn.prepareStatement("DELETE FROM Student_info");
		               
		                int count = ps.executeUpdate();
		                if (count > 0) {
		                    status = true;
		                }
		                ps.close();
		                conn.close();
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		            return status;
		        }

	        @Override
	        public boolean delete(String rollNo) {
	        	 boolean status = false;
		            try {
		                Connection conn = DBUtil.getConnection();
		                PreparedStatement ps = conn.prepareStatement("DELETE FROM Student_info WHERE rollNo=?");
		                ps.setString(1, rollNo);
		                int count = ps.executeUpdate();
		                if (count > 0) {
		                    status = true;
		                }
		                ps.close();
		                conn.close();
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		            return status;
		        }
	       
	        
	        @Override
	        public ArrayList<Marksheet> get(String rollNo) {
	            ArrayList<Marksheet> list = new ArrayList<>();
	            try {
	                Connection conn = DBUtil.getConnection();
	                String sql = "SELECT * FROM Student_info WHERE rollNo=?";
	                PreparedStatement ps = conn.prepareStatement(sql);
	                ps.setString(1, rollNo);
	                ResultSet rs = ps.executeQuery();

	                while (rs.next()) {
	                    Marksheet m = new Marksheet();
	                    m.setRollNo(rs.getString("rollNo"));
	                    m.setName(rs.getString("name"));
	                    m.setEmailID(rs.getString("emailID"));
	                    m.setDob(rs.getDate("dob"));
	                    m.setGender(rs.getString("gender"));
	                    m.setPhysics(rs.getInt("physics"));
	                    m.setChemistry(rs.getInt("chemistry"));
	                    m.setMath(rs.getInt("math"));
	                    m.setMob(rs.getLong("mob"));
	                    list.add(m);
	                }
	                rs.close();
	                ps.close();
	                conn.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return list;
	        }

	        @Override
	        public ArrayList<Marksheet> getAll() {
	            ArrayList<Marksheet> list = new ArrayList<>();
	            try {
	                Connection conn = DBUtil.getConnection();
	                String sql = "SELECT * FROM student_info";
	                PreparedStatement ps = conn.prepareStatement(sql);
	                ResultSet rs = ps.executeQuery();

	                while (rs.next()) {
	                    Marksheet m = new Marksheet();
	                    m.setRollNo(rs.getString("rollNo"));
	                    m.setName(rs.getString("name"));
	                    m.setEmailID(rs.getString("emailID"));
	                    m.setDob(rs.getDate("dob"));
	                    m.setGender(rs.getString("gender"));
	                    m.setPhysics(rs.getInt("physics"));
	                    m.setChemistry(rs.getInt("chemistry"));
	                    m.setMath(rs.getInt("math"));
	                    m.setMob(rs.getLong("mob"));
	                    list.add(m);
	                }
	                rs.close();
	                ps.close();
	                conn.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return list;
	        }

	        @Override
	        public LinkedList<Marksheet> getMeritList() {
	            LinkedList<Marksheet> meritList = new LinkedList<>();
	            try {
	                Connection conn = DBUtil.getConnection();
	                
	                String sql = "SELECT *, (physics + chemistry + math) AS total FROM Student_info ORDER BY total DESC LIMIT 10";
	                PreparedStatement ps = conn.prepareStatement(sql);
	                ResultSet rs = ps.executeQuery();

	                while (rs.next()) {
	                    Marksheet m = new Marksheet();
	                    m.setRollNo(rs.getString("rollNo"));
	                    m.setName(rs.getString("name"));
	                    m.setEmailID(rs.getString("emailID"));
	                    m.setDob(rs.getDate("dob"));
	                    m.setGender(rs.getString("gender"));
	                    m.setPhysics(rs.getInt("physics"));
	                    m.setChemistry(rs.getInt("chemistry"));
	                    m.setMath(rs.getInt("math"));
	                    m.setMob(rs.getLong("mob"));
	                    meritList.add(m);
	                }
	                rs.close();
	                ps.close();
	                conn.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return meritList;
	        }

	        @Override
	        public int numberOfStudents() {
	            int count = 0;
	            try {
	                Connection conn = DBUtil.getConnection();
	                PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM Student_info");
	                ResultSet rs = ps.executeQuery();
	                if (rs.next()) {
	                    count = rs.getInt(1);
	                }
	                rs.close();
	                ps.close();
	                conn.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return count;
	        }
	        
	        @Override
	        public LinkedHashSet<Marksheet> getFailedStudentList() {
	            LinkedHashSet<Marksheet> failedStudents = new LinkedHashSet<>();
	            try {
	                Connection conn = DBUtil.getConnection();
	               
	                PreparedStatement ps = conn.prepareStatement(
	                    "SELECT * FROM Student_info WHERE physics < 33 OR chemistry < 33 OR math < 33"
	                );
	                ResultSet rs = ps.executeQuery();
	                while (rs.next()) {
	                    Marksheet m = new Marksheet();
	                    m.setRollNo(rs.getString("rollNo"));
	                    m.setName(rs.getString("name"));
	                    m.setEmailID(rs.getString("emailID"));
	                    m.setDob(rs.getDate("dob"));
	                    m.setGender(rs.getString("gender"));
	                    m.setPhysics(rs.getInt("physics"));
	                    m.setChemistry(rs.getInt("chemistry"));
	                    m.setMath(rs.getInt("math"));
	                    m.setMob(rs.getLong("mob"));
	                    failedStudents.add(m);
	                }
	                rs.close();
	                ps.close();
	                conn.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return failedStudents;
	        }
        
	        @Override
	        public ArrayList<Marksheet> getAbsentees() {
	            ArrayList<Marksheet> absentees = new ArrayList<>();

	            String sql = "SELECT * FROM Student_info WHERE physics = -1 OR chemistry = -1 OR math = -1";

	            try (Connection conn = DBUtil.getConnection();
	                 PreparedStatement ps = conn.prepareStatement(sql);
	                 ResultSet rs = ps.executeQuery()) {

	                while (rs.next()) {
	                    Marksheet m = new Marksheet();
	                    m.setRollNo(rs.getString("rollNo"));
	                    m.setName(rs.getString("name"));
	                    m.setEmailID(rs.getString("emailID"));
	                    m.setDob(rs.getDate("dob"));
	                    m.setGender(rs.getString("gender"));
	                    m.setPhysics(rs.getInt("physics"));
	                    m.setChemistry(rs.getInt("chemistry"));
	                    m.setMath(rs.getInt("math"));
	                    m.setMob(rs.getLong("mob"));

	                    absentees.add(m);
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return absentees;
	        }


	        @Override
	        public ArrayList<Marksheet> getTopper() {
	            ArrayList<Marksheet> list = new ArrayList<>();
	            try {
	                Connection conn = DBUtil.getConnection();
	                String sql = "SELECT *, (physics + chemistry + math) AS total "
	                           + "FROM Student_info ORDER BY total DESC LIMIT 1";
	                PreparedStatement ps = conn.prepareStatement(sql);
	                ResultSet rs = ps.executeQuery();

	                while (rs.next()) {
	                    Marksheet m = new Marksheet();
	                    m.setRollNo(rs.getString("rollNo"));
	                    m.setName(rs.getString("name"));
	                    m.setEmailID(rs.getString("emailID"));
	                    m.setDob(rs.getDate("dob"));
	                    m.setGender(rs.getString("gender"));
	                    m.setPhysics(rs.getInt("physics"));
	                    m.setChemistry(rs.getInt("chemistry"));
	                    m.setMath(rs.getInt("math"));
	                    m.setMob(rs.getLong("mob"));
	                    list.add(m);
	                }

	                rs.close();
	                ps.close();
	                conn.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return list;
	        }


	        @Override
	        public String[][] getLowestMarkesStudent() {
	            String[][] data = null;
	            try {
	                Connection conn = DBUtil.getConnection();
	                String sql = "SELECT *, (physics + chemistry + math) AS total "
	                           + "FROM Student_info ORDER BY total ASC LIMIT 1";
	                PreparedStatement ps = conn.prepareStatement(sql);
	                ResultSet rs = ps.executeQuery();

	                if (rs.next()) {
	                    data = new String[1][9]; // 9 columns
	                    data[0][0] = rs.getString("rollNo");
	                    data[0][1] = rs.getString("name");
	                    data[0][2] = rs.getString("emailID");
	                    data[0][3] = rs.getDate("dob").toString();
	                    data[0][4] = rs.getString("gender");
	                    data[0][5] = String.valueOf(rs.getInt("physics"));
	                    data[0][6] = String.valueOf(rs.getInt("chemistry"));
	                    data[0][7] = String.valueOf(rs.getInt("math"));
	                    data[0][8] = rs.getString("mob");
	                }

	                rs.close();
	                ps.close();
	                conn.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return data;
	        }

	        @Override
	        public List<Marksheet> getATKTStudents() {
	            List<Marksheet> list = new ArrayList<>();
	            try {
	                Connection conn = DBUtil.getConnection();
	                String sql = "SELECT * FROM Student_info WHERE " +
	                             "((physics < 33 AND chemistry >= 33 AND math >= 33) " +
	                             "OR (chemistry < 33 AND physics >= 33 AND math >= 33) " +
	                             "OR (math < 33 AND physics >= 33 AND chemistry >= 33))";
	                PreparedStatement ps = conn.prepareStatement(sql);
	                ResultSet rs = ps.executeQuery();

	                while (rs.next()) {
	                    Marksheet m = new Marksheet();
	                    m.setRollNo(rs.getString("rollNo"));
	                    m.setName(rs.getString("name"));
	                    m.setEmailID(rs.getString("emailID"));
	                    m.setDob(rs.getDate("dob"));
	                    m.setGender(rs.getString("gender"));
	                    m.setPhysics(rs.getInt("physics"));
	                    m.setChemistry(rs.getInt("chemistry"));
	                    m.setMath(rs.getInt("math"));
	                    m.setMob(rs.getLong("mob"));
	                    list.add(m);
	                }

	                rs.close();
	                ps.close();
	                conn.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return list;
	        }
	        
	        @Override
	        public double getCutoff() {
	            double cutoff = 0.0;
	            try {
	                Connection conn = DBUtil.getConnection();
	                String sql = "SELECT AVG((physics + chemistry + math)/3.0) as avgMarks " +
	                        "FROM Student_info " +
	                        "WHERE physics >= 0 AND chemistry >= 0 AND math >= 0";
	                PreparedStatement ps = conn.prepareStatement(sql);
	                ResultSet rs = ps.executeQuery();
	                if (rs.next()) {
	                    cutoff = rs.getDouble("avgMarks");
	                    if (rs.wasNull()) {
	                        cutoff = 0.0; 
	                    }
	                }
	                conn.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return cutoff;
	        }

	        @Override
	        public int getNumberOfBoysPass() {
	            int count = 0;
	            try {
	                Connection conn = DBUtil.getConnection();
	                String sql = "SELECT COUNT(*) as cnt FROM Student_info " +
	                             "WHERE gender='M' AND physics>=33 AND chemistry>=33 AND math>=33";
	                PreparedStatement ps = conn.prepareStatement(sql);
	                ResultSet rs = ps.executeQuery();
	                if (rs.next()) {
	                    count = rs.getInt("cnt");
	                }
	                conn.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return count;
	        }

	        @Override
	        public int getNumberOfGirlsPass() {
	            int count = 0;
	            try {
	            	
	                Connection conn = DBUtil.getConnection();
	                String sql = "SELECT COUNT(*) as cnt FROM Student_info " +
	                             "WHERE gender='F' AND physics>=33 AND chemistry>=33 AND math>=33";
	                PreparedStatement ps = conn.prepareStatement(sql);
	                ResultSet rs = ps.executeQuery();
	                if (rs.next()) {
	                    count = rs.getInt("cnt");
	                }
	                conn.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return count;
	        }

	        @Override
	        public char getGradeOfStudent(String rollNo) {
	            char grade = 'F';
	            try (Connection conn = DBUtil.getConnection()) {
	                String sql = "SELECT physics, chemistry, math FROM Student_info WHERE rollNo=?";
	                PreparedStatement ps = conn.prepareStatement(sql);
	                ps.setString(1, rollNo);
	                ResultSet rs = ps.executeQuery();

	                if (rs.next()) {
	                    int phy = rs.getInt("physics");
	                    int chem = rs.getInt("chemistry");
	                    int math = rs.getInt("math");

	                    int total = phy + chem + math;
	                    double avg = total / 3.0;

	                    if (avg >= 85) grade = 'A';
	                    else if (avg >= 70) grade = 'B';
	                    else if (avg >= 50) grade = 'C';
	                    else if (avg >= 40) grade = 'D';
	                    else grade = 'F';
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return grade;
	        }

	        @Override
	        public int getTotalNumberOfGirls() {
	            int count = 0;
	            try (Connection conn = DBUtil.getConnection()) {
	                String sql = "SELECT COUNT(*) FROM Student_info WHERE gender='F'";
	                PreparedStatement ps = conn.prepareStatement(sql);
	                ResultSet rs = ps.executeQuery();
	                if (rs.next()) {
	                    count = rs.getInt(1);
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return count;
	        }

	        @Override
	        public int getTotalNumberOfBoys() {
	            int count = 0;
	            try (Connection conn = DBUtil.getConnection()) {
	                String sql = "SELECT COUNT(*) FROM Student_info WHERE gender='M'";
	                PreparedStatement ps = conn.prepareStatement(sql);
	                ResultSet rs = ps.executeQuery();
	                if (rs.next()) {
	                    count = rs.getInt(1);
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return count;
	        }

	        @Override
	        public double getAverageResultOfGirls() {
	            double avg = 0.0;
	            try {
	                Connection conn = DBUtil.getConnection();
	                String sql = "SELECT AVG((physics + chemistry + math)/3.0) as avgMarks FROM Student_info WHERE gender='F'";
	                PreparedStatement ps = conn.prepareStatement(sql);
	                ResultSet rs = ps.executeQuery();
	                if (rs.next()) {
	                    avg = rs.getDouble("avgMarks");
	                }
	                conn.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return avg;
	        }

	        @Override
	        public double getAverageResultOfBoys() {
	            double avg = 0.0;
	            try {
	                Connection conn = DBUtil.getConnection();
	                String sql = "SELECT AVG((physics + chemistry + math)/3.0) as avgMarks FROM Student_info WHERE gender='M'";
	                PreparedStatement ps = conn.prepareStatement(sql);
	                ResultSet rs = ps.executeQuery();
	                if (rs.next()) {
	                    avg = rs.getDouble("avgMarks");
	                }
	                conn.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return avg;
	        }

	    }

	   	

