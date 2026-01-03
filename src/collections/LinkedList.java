package collections;

import java.util.List;

public class LinkedList {

    public static void main(String[] args) {
        ListNode head =
                new ListNode(1,
                        new ListNode(2,
                                new ListNode(3, null)));
        // 1->2->3->4->5->X
        addToLL(head, 4);
        addToLL(head, 5);
        head.printList();


        int[] arr = new int[]{1, 2, 3};
//        System.out.println(arr[0]);
//        System.out.println(arr[1]);
//        arr[3] = 4; // Not allowed
    }

    public static void addToLL(ListNode head, int el) {
        ListNode newNode = new ListNode(el, null);
        if (head == null) {
            head = newNode;
            return;
        }
        ListNode temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = newNode;
    }
}

class ListNode {

    int val;
    ListNode next;

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public void printList() {
        ListNode temp = this;
        while (temp.next != null) {
            System.out.println(temp.val);
            temp = temp.next;
        }
        System.out.println(temp.val);
    }

}

/*
array of int -> 4 bytes 4 * 3 = 12 bytes
Array of size 3:

   [1][2][3][4]
       arr
Create new array of size 4: brr -> [1][2][3][4] // 16 bytes
// arr = brr
 */


// [C1 | 1][C2 | 2][C3 | 3]->X || 1->2->3->X
/* ListNode c1 = new ListNode(1, null) // 1->2->X

    1 (int) -> 4 bytes || address: Delhi
    next (pointer) -> Bangalore (8 bytes)

    ListNode c2 = new ListNode(2, null)     || address: Bangalore
    c1.next = c2;

    ListNode c3 = new ListNode(3, null)     || address: Hyderabad
    c2.next = c3;
 */