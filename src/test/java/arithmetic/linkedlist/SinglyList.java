package arithmetic.linkedlist;

import java.util.StringJoiner;

public class SinglyList {


    public static void main(String[] args) {
        Node n_4 = new Node(4, null);
        Node n_3 = new Node(3, n_4);
        Node n_2 = new Node(2, n_3);
        Node n_1 = new Node(1, n_2);
        System.out.println(n_1.toString());
        Node reverse = n_1.reverse();
        System.out.println(reverse.toString());
        reverse = reverse.recursionReverse(reverse);
        System.out.println(reverse);
    }

}

class Node {
    int v;
    Node next;

    public Node(int v, Node next) {
        this.v = v;
        this.next = next;
    }

    public Node reverse() {
        Node prev = null;
        Node node = this;
        while (node != null) {
            final Node next = node.next;
            node.next = prev;
            prev = node;
            node = next;
        }
        return prev;
    }

    public Node recursionReverse(Node node) {
        if (node == null || node.next == null) {
            return node;
        }
        final Node next = node.next;
        final Node newHead = recursionReverse(next);
        next.next = node;
        node.next = null;
        return newHead;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Node.class.getSimpleName() + "[", "]")
                .add("v=" + v)
                .add("next=" + next)
                .toString();
    }
}