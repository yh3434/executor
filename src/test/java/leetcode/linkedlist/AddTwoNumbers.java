package leetcode.linkedlist;

public class AddTwoNumbers {

    /**
     * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 输出：7 -> 0 -> 8
     * 原因：342 + 465 = 807
     */

    public static void main(String[] args) {
        ListNode l1 = new ListNode(9);
        l1.next = new ListNode(9);

        ListNode l2 = new ListNode(1);

        System.out.println(addTwoNumbers(l2, l1));
        System.out.println(addTwoNumbers2(l2, l1));

        ListNode l3 = new ListNode(0);
        l3.next = new ListNode(2);

        ListNode l4 = new ListNode(1);
        System.out.println(addTwoNumbers(l3, l4));
        System.out.println(addTwoNumbers2(l3, l4));

        ListNode l5 = new ListNode(2);
        l5.next = new ListNode(4);
        l5.next.next = new ListNode(3);

        ListNode l6 = new ListNode(5);
        l6.next = new ListNode(6);
        l6.next.next = new ListNode(4);

        //342+65 = 807  ==> result: 708

        System.out.println(addTwoNumbers(l5, l6));
        System.out.println(addTwoNumbers2(l5, l6));

    }


    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyNode = new ListNode(0);
        ListNode curr = dummyNode;
        int carry = 0;
        while (l1 != null || l2 != null) {
            int x = (l1 != null) ? l1.val : 0;
            int y = (l2 != null) ? l2.val : 0;
            int sum = x + y + carry;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            l1 = (l1 != null) ? l1.next : null;
            l2 = (l2 != null) ? l2.next : null;
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummyNode.next;
    }


    public static ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;
        int carry = 0;
        while (p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null) p = p.next;
            if (q != null) q = q.next;
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummyHead.next;
    }
}
