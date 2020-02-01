package edu.spr19cs210.section07;
/*
 * MyGeneric is a generic type.
 * 
 */

public class MyGeneric<T> {
	
	T t;
	
	public MyGeneric(T t) {
		this.t = t;
	}
	
	public String toString() {
		return "" + this.t;
	}
	
	public int hashCode() {
		return this.t.hashCode();
	}
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if(other == null || other.getClass() != this.getClass()) {
			return false;
		}
		MyGeneric otherGen = (MyGeneric) other;
		return (this.t.equals(otherGen.t));
	}
	
}
