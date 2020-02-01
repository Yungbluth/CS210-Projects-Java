package main;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UAccess {
	public static void main(String [] args) {
		
		// Collections of students and classes to keep track of data
		Map<String, Student> students = new HashMap<>();
		Map<String, Class> classes = new HashMap<>();
		
		// creation and storing of cs courses
		Class cs110 = 
					new Class("CSC", "Intro to Computer Programming 1", 4, 110, "Ben Dicken");
		Class cs120 = 
					new Class("CSC", "Intro to Computer Programming 2", 4, 120, "Janalee O'bagy");
		Class cs210 = 
					new Class("CSC", "Software Development", 4, 210, "Tyler Conklin");
		
		classes.put("CSC " + cs110.getNumber(), cs110);
		classes.put("CSC " + cs120.getNumber(), cs120);
		classes.put("CSC " + cs210.getNumber(), cs210);
		
		// initialize the number of students in the system
		int idCounter = students.keySet().size();
		
		// create scanner and get initial input
		Scanner input = new Scanner(System.in);
		
		System.out.print("Hello, welcome to UAccess! What is your name(first last)? ");
		
		String name = input.nextLine();
		
		// init initial student
		Student current;
		
		// if student exists, use the one stored, if not, create a new student and
		// store
		if (students.containsKey(name)) {
			current = students.get(name);
		} else {
			String [] firstLast = name.split("\\s+");
			System.out.println(firstLast[1]);
			current = new Student(firstLast[0], firstLast[1], idCounter);
			idCounter++;
			
			students.put(name, current);
		}
		
		// init decision
		String decision = "";
		while (!decision.equals("exit")) {
			
			// output of decision options
			System.out.println("Input Key");
			System.out.println("'courses': List Courses");
			System.out.println("'transcript': show transcript");
			System.out.println("'add': add a course");
			System.out.println("'drop': drop a course");
			System.out.println("'exit': exit program");
			System.out.print("\nWhat would you like to do next? ");
			
			// get next decision
			decision = input.nextLine().toLowerCase();
			
			// switch statement to select which option was selected
			switch (decision) {
				case "courses":
					// output courses
					for (String each : classes.keySet()) {
						System.out.println(classes.get(each).toString());
					}
					break;
				case "transcript":
					System.out.println(current.transcript.toString());
					break;
				case "add":
					// add classes if it exists in the system
					System.out.print("Which class? ");
					String val = input.nextLine();
					System.out.println(val);
					if (classes.containsKey(val)) {
						current.enrolled.add(classes.get(val));
						System.out.println("Successful");
					} else {
						System.out.println("Course not available");
					}
					break;
				case "drop":
					// remove class from student's courses
					System.out.print("Which class? ");
					String value = input.nextLine();
					
					for (int i = 0; i < current.enrolled.size(); i++) {
						if (current.enrolled.get(i).equals(value)) {
							current.enrolled.remove(i);
						}
					}
					break;
				case "exit":
					break;
				default:
					System.out.println("Unrecognized Command.");
					break;
			}
		}
		
		System.out.println("Logged out of UAuth Successfully");
	}
}
