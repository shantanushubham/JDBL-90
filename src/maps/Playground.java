package maps;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Playground {

    // [pair1, pair2, pair3 ...]
    // I want to store the info of roll no vs student name
    // [{1, Rahul}, {2, Shantanu}, {3, Ankita} ...]
    public static void main(String[] args) {
        // HashMap, LinkedHashMap, TreeMap
        // Key and Value
//        Map<Integer, String> map = new HashMap<>();
//        map.put(2, "Rahul");
//        map.put(1, "Shantanu");
//        map.put(3, "Ankita");
//        map.put(3, "Satyam");
//        map.put(null, "Test");
//        map.put(null, "Test1");
//        System.out.println(map);
//        System.out.println(map.get(3));

        // Value = Value stored inside array
        // Key = the index of the array

        //String[] arr = []["Shantanu"]["Rahul"]["Satyam"]
        //                0     1         2         3

//        Map<String, Integer> map = new HashMap<>();
        // capacity = 16
        // array of linked lists and each box is called a Bucket
        // So we can say that the bucket size, n = 16
        // Node<String, Integer>[] arr = [][][][{115, Sachin, 60, null}][][][{118, Vishal, 20}->{118, Vaibhav, 45}][][][] [] [] [] [] [] []
        //                                0 1 2              3          4  5          6                             7 8 9 10 11 12 13 14 15
        // formula for index: index = hashCode(key) & (n - 1)


//        map.put("apple", 3);
//        map.put("banana", 5);
//        map.put("orange", 2);
//
//        System.out.println(map.get("banana"));

        Map<Key, Integer> map = new HashMap<>();
        Key k1 = new Key("vishal");
        Key k2 = new Key("sachin");
        Key k3 = new Key("vaibhav");
        Key k4 = new Key("sachin");
        System.out.println(k2.equals(k4));
        map.put(k1, 20);
        map.put(k2, 30);
        map.put(k3, 40);
        System.out.println(map);
        map.put(k4, 60);
        System.out.println(map);

    }
}

class Node<K, V> {
    int hash; // Hyderabad converted into an int
    K key; // Hyderabad
    V Value; // Rahul
    Node<K, V> next; // This is a LL which is used to make sure
    // we don't make huge arrays
}

// Created By Shantanu
class Key {

    public String key;

    public Key(String key) {
        this.key = key;
    }

    @Override
    public int hashCode() {
        return this.key.charAt(0);
    }

    @Override
    public String toString() {
        return this.key;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Key key1 = (Key) o;
        return Objects.equals(key, key1.key);
    }
}