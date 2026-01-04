package stream;

import java.util.*;

public class Playground {

    // Streams

    /*
    They are linked directly with lambdas and collections and maps.
    They are used to perform an operation on a collection or a map.
    They convert the collection/map into an intermediate state (called stream)
    where operations can be made, and then allows us to make
    operations and then close the stream to get the final output we
    need
     */

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        // I want to filter all the odd elements from the list and keep only even elements
        // Output: [2,4,6,8,10]
//        List<Integer> result = new ArrayList<>();
//        for (int i: list) {
//            if (i % 2 == 0) {
//                result.add(i);
//            }
//        }
//        System.out.println(result);

        List<Integer> result = list.stream()
                .filter(el -> el % 2 == 0)
                .map(el -> el * el)
                .toList();
        // filter()
        // map()
        // reduce()
        System.out.println(result);

        // Task
        // Imagine you have a map: {Banana: 3, Apple: 4, Mango: 1}
        // and a List that has: [Banana, Apple, Mango, Guava, Strawberry]
        // Use .map() on the list to get a list of
        // string: ["Banana:3", "Apple:4", "Mango:1", "Guava:0", Strawberry:0"]
        // and print it

        Map<String, Integer> map = new HashMap<>();
        map.put("Banana", 3);
        map.put("Apple", 4);
        map.put("Mango", 1);

        List<String> fruits = Arrays.asList("Banana", "Apple", "Mango", "Guava", "Strawberry");
        List<String> result1 = fruits.stream()
                .map(el -> el + ":" + map.getOrDefault(el, 0))
//                .map(el -> String.format("%s:%d", el, map.getOrDefault(el, 0)))
                .toList();
        System.out.println(result1);

    }
}
