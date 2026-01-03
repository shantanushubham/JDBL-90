package collections;

import java.util.*;
import java.util.LinkedList;

public class Test {

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 4, 5}; // [1,2,3,4,5]
        // [[[1,2,3]],[[4,5,6]],[[7,8,9]]] n = 2
        // 1->2->3>4->5->X
        // [1][2][3][4][5]<-->
        //<-[1][2][3][4][5]<-
        //

        // Collections are directly linked with Linear data structures
        /*
        1. Arrays | ArrayList
        2. LinkedList | LinkedList
        3. Stack
        4. Queue | Deque, ArrayDeque, LinkedList
        5. P-Queue | PriorityQueue
        6. Set | HashSet, LinkedHashSet, TreeSet
        ... and others
         */

        // 1->2->3->4->5->6

//        List<Integer> list1 = new ArrayList<>(3); // this creates an array of size 5 internally
//        // arr = [45][50][55][60][65][70]
//        // newCapacity = oldCapacity + (oldCapacity >> 1); (~1.5 times grow)
//        list1.add(45);
//        list1.add(50);
//        list1.add(55);
//        list1.add(60);
//        list1.add(65);
//        list1.add(70);
//
//        List<String> list2 = new LinkedList<>();
//        list2.add("Banana");
//        list2.add("Apple");
////        list2.getLast();
//
//        printList(list1);
//        printList(list2);
//
//        Stack<String> stack = new Stack<>();
//        stack.push("Banana");
//        stack.push("Mango");
//        stack.set(0, "Pineapple");
//        System.out.println(stack);
//        stack.pop();
//        System.out.println(stack);

        // [a0, X, 0]
        // [a0, a0, X]
        // [X, a0, a0]
        // DFS BFS
//        Queue<String> queue = new LinkedList<>();
//        queue.offer("Shantanu");
//        queue.offer("Hitesh");
//        queue.offer("Hari");
//
//        System.out.println(queue.poll());
//        System.out.println(queue);

        Set<String> set1 = new LinkedHashSet<>();
        set1.add("Banana");
        set1.add("Apple");
        set1.add("Guava");
        set1.add("Apple");
        System.out.println(set1);

        // HashSet & LinkedHashSet use Maps internally.
        // We will study their implementation when we study Maps.

    }

    public static <T> void printList(List<T> list) {
        for (T t: list) {
            System.out.println(t);
        }
    }
}