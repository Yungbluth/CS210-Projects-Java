import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class PA7Test {
    /**
     * AUTHOR: Matthew Yungbluth
     * File: PA7Test.java
     * ASSIGNMENT: Programming Assignment 7 - HashTableImpl
     * COURSE: CSc 210; Section C; Spring 2019
     * PURPOSE: This file tests the custom HashMap from MyHashMap.java
     */
    
    // Purpose: Tests the put method in MyHashMap.java
    @Test
    public void PutTest() {
        MyHashMap<String, Integer> myHash = new MyHashMap<String, Integer>();
        assertTrue(myHash.getSize() == 0);
        myHash.put("hi", 1);
        assertTrue(myHash.getSize() == 1);
        myHash.put("hi", 2);
        assertTrue(myHash.getSize() == 1);
        myHash.put("bye", 1);
        assertTrue(myHash.getSize() == 2);
    }

    // Purpose: Tests the getValue and getKey methods in MyHashMap.java
    @Test
    public void GetTest() {
        MyHashMap<String, Integer> myHash = new MyHashMap<String, Integer>();
        MyHashMap<String, Integer> thisTest = myHash.put("hi", 3);
        int test = myHash.getValue("hi");
        assertTrue(thisTest.getKey().equals("hi"));
        assertTrue(test == 3);
    }

    // Purpose: Tests the containsKey method in MyHashMap.java
    @Test
    public void ContainsKeyTest() {
        MyHashMap<String, Integer> myHash = new MyHashMap<String, Integer>();
        myHash.put("hi", 1);
        assertTrue(myHash.containsKey("hi"));
    }

    // Purpose: Tests the keySet method in MyHashMap.java
    @Test
    public void KeySetTest() {
        MyHashMap<String, Integer> myHash = new MyHashMap<>();
        myHash.put("hi", 1);
        HashSet<String> mySet = new HashSet<>();
        mySet.add("hi");
        myHash.put("bye", 1);
        mySet.add("bye");
        assertTrue(myHash.keySet().equals(mySet));
    }

}
