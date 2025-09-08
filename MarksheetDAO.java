package com.thrivesup.marksheet;

import java.sql.*;
import java.util.ArrayList;

public class MarksheetDAO {
	public boolean add(Marksheet m) {
	    try {
	        Connection conn = DBUtil.getConnection();
	        String sql = "INSERT INTO Student_info (rollNo, name, physics, chemistry, math, gender, dob, mob, emailID) " +
	                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        PreparedStatement ps = conn.prepareStatement(sql);
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
	        ps.close();
	        conn.close();

	        return rows > 0; 
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

    // Get student by roll no.
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
    
    public ArrayList<Marksheet> getAll() {
        ArrayList<Marksheet> list = new ArrayList<>();
        try {
            Connection conn = DBUtil.getConnection();
            String sql = "SELECT * FROM Student_info";
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
}
