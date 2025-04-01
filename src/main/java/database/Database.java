package database;

import java.util.ArrayList;
import java.util.List;

import model.Student;

public class Database {
	
	private static List<Student> students = new ArrayList<>();
	
	public static void add(Student student) {
		Database.students.add(student);
	}
	
	public static void remove(Integer registration) {
		Database.students.removeIf(student -> student.getRegistration().equals(registration));
	}
	
	
	
	

}
