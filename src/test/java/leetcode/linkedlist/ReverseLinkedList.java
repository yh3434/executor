package leetcode.linkedlist;

public class ReverseLinkedList {
    public static void main(String[] args) {
        ListNode l6 = new ListNode(5);
        l6.next = new ListNode(6);
        l6.next.next = new ListNode(4);
        ListNode reverse = reverse(l6);
        System.out.println(reverse);
        reverse = reverse2(reverse, null);
        System.out.println(reverse);
        System.out.println(reverse3(reverse));
    }

    public static ListNode reverse(ListNode node) {
        ListNode prev = null;
        ListNode now = node;
        while (now != null) {
            ListNode next = now.next;
            now.next = prev;
            prev = now;
            now = next;
        }
        return prev;
    }

    public static ListNode reverse2(ListNode node, ListNode prev) {
        if (node.next == null) {
            node.next = prev;
            return node;
        } else {
            ListNode re = reverse2(node.next, node);
            node.next = prev;
            return re;
        }
    }

    public static ListNode reverse3(ListNode node) {
        if (node.next == null) return node;
        ListNode next = node.next;
        node.next = null;
        ListNode re = reverse3(next);
        next.next = node;
        return re;
    }
}
