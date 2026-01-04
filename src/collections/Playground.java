package collections;

import java.util.*;
import java.util.LinkedList;

public class Playground {

    public static void main(String[] args) {
//        ListNode listNode = new ListNode(1,
//                new ListNode(2, new ListNode(3, null)));
//
//        // 1->2->3->X
//        ListNode temp = listNode;
//        while (temp != null) {
//            System.out.println(temp.val);
//            temp = temp.next;
//        }
//
//        // Option 1
//        List<Integer> list = new LinkedList<>();
//        list.add(1);
//        list.add(2);
//        list.add(3);
//
//        for (int i: list) {
//            System.out.println(i);
//        }
//
//        // Option 2
//        Iterator<Integer> iterator = list.iterator();
//        while (iterator.hasNext()) {
//            System.out.println(iterator.next());
//        }

        // Let's say you have a fruit basket with many kinds of fruits.
        // There are more than 1 unit for each fruit.
        // Find the number of different kinds of fruits you have.

        List<String> fruitBasket = Arrays.asList("Banana", "Kiwi", "Apple", "Banana", "Mango", "Mango", "Kiwi");
        Set<String> uniqueFruits = new HashSet<>(fruitBasket);
        System.out.println(uniqueFruits.size());
//        int uniqueCount = 0;
//        for (String fruit: fruitBasket) {
//            if (!uniqueFruits.contains(fruit)) {
//                uniqueCount++;
//            }
//            uniqueFruits.add(fruit);
//        }
//        System.out.println(uniqueCount);
    }
}
