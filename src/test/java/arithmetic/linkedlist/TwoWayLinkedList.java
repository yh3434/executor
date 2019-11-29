package arithmetic.linkedlist;

import java.util.StringJoiner;

public class TwoWayLinkedList {
}


class TNode {
    private int v;
    private TNode prev;
    private TNode next;

    public TNode(int v, TNode prev, TNode next) {
        this.v = v;
        this.prev = prev;
        this.next = next;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TNode.class.getSimpleName() + "[", "]")
                .add("v=" + v)
                .add("prev=" + prev)
                .add("next=" + next)
                .toString();
    }

    public TNode reverse(TNode head) {
        TNode curr = null;
        while (head != null) {
            curr = head;
            head = curr.next;
            curr.next = curr.prev;
            curr.prev = head;
        }
        return curr;
    }

}