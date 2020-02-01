import java.util.ArrayList;
import java.util.HashSet;

public class MyHashMap<K, V> {
    /**
     * AUTHOR: Matthew Yungbluth
     * File: MyHashMap.java
     * ASSIGNMENT: Programming Assignment 7 - HashTableImpl
     * COURSE: CSc 210; Section C; Spring 2019
     * PURPOSE: This file creates and manipulates a custom implementation of a
     * HashMap for use in PA7.java
     */

    int size;
    int maxSize;
    ArrayList<MyHashMap<K, V>> map;
    private K key;
    private V value;

    private MyHashMap<K, V> next;

    public MyHashMap() {
        this.map = new ArrayList<MyHashMap<K, V>>();
        this.key = null;
        this.value = null;
        this.size = 0;
        this.next = null;
        this.maxSize = 8;
        for (int i = 0; i <= 7; i++) {
            this.map.add(null);
        }
    }

    // Purpose: Returns a hashcode for a key
    public int hash(K key) {
        int hashCode = key.hashCode();
        int index = hashCode % this.maxSize;
        return Math.abs(index);
    }
    
    // Purpose: Returns the current size of the Hashmap
    public int getSize() {
        return this.size;
    }

    // Purpose: Returns the current key of the Hashmap
    public K getKey() {
        return this.key;
    }

    // Purpose: Sets the current key of the Hashmap
    public void setKey(K key) {
        this.key = key;
    }

    // Purpose: Returns the current value of the Hashmap
    public V getValue() {
        return this.value;
    }

    // Purpose: Returns the value associated with a key in the hashmap
    public V getValue(K key) {
        int index = hash(key);
        MyHashMap<K, V> curIndex = this.map.get(index);
        while (curIndex != null) {
            if (curIndex.getKey().equals(key)) {
                return curIndex.value;
            }
            curIndex = curIndex.getNext();
        }
        return null;
    }

    // Purpose: Sets the current value of the hashmap
    public void setValue(V value) {
        this.value = value;
    }

    // Purpose: Returns the next node in the LinkedList on the hash index
    public MyHashMap<K, V> getNext() {
        return next;
    }

    // Purpose: Sets the next node in the LinkedList
    public void setNext(MyHashMap<K, V> next) {
        this.next = next;
    }

    // Purpose: Inserts a key/value pair into the hashmap
    public MyHashMap<K, V> put(K key, V value) {
        int index = hash(key);
        MyHashMap<K, V> curIndex = this.map.get(index);
        boolean hasFound = false;
        while (curIndex != null) {
            if (curIndex.getKey().equals(key)) {
                curIndex.setValue(value);
                hasFound = true;
            }
            curIndex = curIndex.getNext();
        }
        // if its the first time inserting this key in the hashmap
        if (!hasFound) {
            MyHashMap<K, V> thisHash = new MyHashMap<K, V>();
            thisHash.setKey(key);
            thisHash.setValue(value);
            curIndex = thisHash;
            thisHash.setNext(this.map.get(index));
            this.map.set(index, thisHash);
            this.size += 1;
        }
        return curIndex;
    }

    // Purpose: Returns if the hashmap contains a key
    public boolean containsKey(K key) {
        boolean hasKey = false;
        int index = hash(key);
        MyHashMap<K, V> curIndex = this.map.get(index);
        while (curIndex != null) {
            if (curIndex.getKey().equals(key)) {
                hasKey = true;
            }
            curIndex = curIndex.getNext();
        }
        return hasKey;
    }

    // Purpose: Returns a set of all the keys in the hashmap
    public HashSet<K> keySet() {
        HashSet<K> mySet = new HashSet<>();
        for (int i = 0; i < this.maxSize; i++) {
            MyHashMap<K, V> curIndex = this.map.get(i);
            while (curIndex != null) {
                mySet.add(curIndex.getKey());
                curIndex = curIndex.getNext();
            }
        }
        return mySet;
    }

    // Purpose: Returns if the hashmap is empty
    public boolean isEmpty() {
        return this.size == 0;
    }

    // Purpose: Prints the entire hashmap in a table
    public void printTable() {
        int totalConflicts = 0;
        for (int i = 0; i < this.maxSize; i++) {
            // Finds all conflicts
            int curConflicts = -1;
            MyHashMap<K, V> curIndex = this.map.get(i);
            while (curIndex != null) {
                curIndex = curIndex.getNext();
                curConflicts += 1;
            }
            if (curConflicts == -1) {
                curConflicts = 0;
            }
            totalConflicts += curConflicts;
            MyHashMap<K, V> curNode = this.map.get(i);
            System.out.print(
                    "Index " + i + ": (" + curConflicts + " conflicts), [");
            while (curNode != null) {
                // Prints all nodes
                System.out.print(curNode.getKey() + ", ");
                curNode = curNode.getNext();
                }
                System.out.println("]");
        }
        System.out.println("Total # of conflicts: " + totalConflicts);
    }
}
