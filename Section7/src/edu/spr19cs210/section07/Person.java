package edu.spr19cs210.section07;
/* 
 * This example shows how a user defined object is written
 * to be used in a HashSet or HashMap.
 * Equals and hashCode must be overridden in order to retrieve the object. 
 * 
 */

public class Person {
	
	int id;
	String firstName;
	String lastName;
	
	public Person(int id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public int hashCode() {
		return this.id;
	}
	
	public boolean equals(Object other) {
		if(this == other) {
			return true;
		}
		if(other == null || other.getClass() != this.getClass()) {
			return false;
		}
		Person otherPerson = (Person) other;
		return(this.firstName.equals(otherPerson.firstName) && this.lastName.equals(otherPerson.lastName)&&  this.id == otherPerson.id);
	}
	
	public String toString() {
		return this.firstName + " " + this.lastName + " (id=" + this.id + ")";
	}
	
}
