package generics;

import java.util.ArrayList;
import java.util.HashMap;

class Pair<K, V> {

    private K first;
    private V second;

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    public K getKey() {
        return this.first;
    }

    public void setKey(K first) {
        this.first = first;
    }

    public V getValue() {
        return second;
    }

    public void setValue(V second) {
        this.second = second;
    }
}

class Demo {

    public static void main(String[] args) {
        Pair<Integer, String> p1 = new Pair<>(40, "Shantanu");
        Pair<String, String> p2 = new Pair<>("Bengaluru", "Karnataka");
        Pair<String, Integer> p3 = new Pair<>("Pune", 411001);

        System.out.println(p1.getKey());
        System.out.println(p1.getValue());

        System.out.println(p2.getKey());
        System.out.println(p2.getValue());

        System.out.println(p3.getKey());
        System.out.println(p3.getValue());

        ArrayList<Integer> list = new ArrayList<>();
        list.add(10);

        HashMap<String, Integer> map = new HashMap<>();
        map.put("Shantanu", 400);

        System.out.println(list);
        System.out.println(map);

        test("Hello");
    }

    public static <T> void test(T t1) {
        System.out.println(t1);
    }
}

// String, Integer
// Char, Integer

// 40 -> Shantanu
// 41 -> Nikhil
// 42 -> Divit
// 43 -> Hriday