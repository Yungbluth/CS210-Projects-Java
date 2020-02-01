package main;

import java.util.ArrayList;
import java.util.List;

public class Student {
	public String firstName;
	public String lastName;
	public int idNumber;
	public ArrayList<Class> enrolled;
	public Transcript transcript;
	
	public Student(String first, String last, int id) {
		this.firstName = first;
		this.lastName = last;
		this.idNumber = id;
		this.enrolled = new ArrayList<>();
		this.transcript = new Transcript();
	}
	
	
	public String toString() {
		return this.firstName + this.lastName;
	}
}
