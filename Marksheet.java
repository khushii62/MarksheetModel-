package com.thrivesup.marksheet;

import java.sql.Date;

public class Marksheet {
	    private String rollNo;
	    private String name;
	    private int physics;
	    private int chemistry;
	    private int math;
	    private char gender;
	    private Date dob;
	    private long mob;
	    private String emailID;
	    
	    
	public void setRollNo(String roll){
		 this.rollNo = roll;
	}
	public void setName(String name){
		 this.name = name;
	}
	public void setMath(int math){
		 this.math = math;
	}
	public void setPhysics(int phy){
		 this.physics = phy;
	}
	public void setChemistry(int che){
		 this.chemistry = che;
	}
	public void setGender(char gender){
		 this.gender = gender;
	}
	public void setDob(Date dob){
		 this.dob = dob;
	}
	public void setMob(long mob){
		 this.mob = mob;
	}
	public void setEmailID(String email){
		 this.emailID = email;
	}
	public String getRollNo(){
		 return rollNo;
	}
	public String getName(){
		 return name;
	}
	public int getMath(){
		 return math;
	}
	public int getPhysics(){
		 return physics;
	}
	public int getChemistry(){
		 return chemistry;
	}
	public char getGender(){
		 return gender;
	}
	public Date getDob(){
		 return dob;
	}
	public long getMob(){
		 return mob;
	}
	public String getEmailID(){
		 return emailID;
	}
	}



