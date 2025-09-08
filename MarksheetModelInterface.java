package com.thrivesup.marksheet;
import java.util.*;
public interface MarksheetModelInterface {

	    public boolean add(Marksheet m);
	    public boolean deleteByRollNo(String rollNo);
	    public boolean deleteByEmailId(String email);
	    public boolean update(String rollNo);
	    public boolean updateAll(Marksheet m);
	    public boolean deleteAll();
	    public boolean delete(String rollNo);
	    public ArrayList<Marksheet> get(String rollNo);
	    public ArrayList<Marksheet> getAll();
	    public LinkedList<Marksheet> getMeritList();
	    public int numberOfStudents();
	    public LinkedHashSet<Marksheet> getFailedStudentList();
	    public ArrayList<Marksheet> getAbsentees();
	    public ArrayList<Marksheet> getTopper();
	    public String[][] getLowestMarkesStudent();
	    public List<Marksheet> getATKTStudents();
	    public double getCutoff();
	    public int getNumberOfBoysPass();
	    public int getNumberOfGirlsPass();
	    public char getGradeOfStudent(String rollNo);
	    public int getTotalNumberOfGirls();
	    public int getTotalNumberOfBoys();
	    public double getAverageResultOfGirls();
	    public double getAverageResultOfBoys();
	}

	


