

public class TestLL {

    public static Node evenThenOdd(Node head) {
        if (head == null || head.next == null) return head;

        Node odd = head;
        Node even = head.next;
        Node evenHead = head.next;

        while (even.next != null && even.next.next != null) {
            odd.next = odd.next.next;
            even.next = even.next.next;

            odd = odd.next;
            even = even.next;
        }

        even.next = head; // connect even list before odd
        odd.next= null;
        return evenHead;
    }

    public static void printList(Node head) {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.data + " -> ");
            temp = temp.next;
        }
        System.out.println("null");
    }

    public static void main(String[] args) {
        // Build: 1 -> 2 -> 3 -> 4 -> 5 -> 6
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.next = new Node(6);

        System.out.print("Original: ");
        printList(head);

        head = evenThenOdd(head);

        System.out.print("Modified: ");
        printList(head);
    }
}
class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}









public class Main {

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    // Custom method (your sorting logic)
    public static Node sort012(Node head) {
        if (head == null || head.next == null) return head;

        Node zeroHead = new Node(-1), zeroTail = zeroHead;
        Node oneHead  = new Node(-1), oneTail  = oneHead;
        Node twoHead  = new Node(-1), twoTail  = twoHead;

        Node curr = head;

        while (curr != null) {
            Node nextNode = curr.next;
            curr.next = null;

            if (curr.data == 0) {
                zeroTail.next = curr;
                zeroTail = curr;
            } else if (curr.data == 1) {
                oneTail.next = curr;
                oneTail = curr;
            } else {
                twoTail.next = curr;
                twoTail = curr;
            }

            curr = nextNode;
        }

        if (oneHead.next != null) {
            zeroTail.next = oneHead.next;
        } else {
            zeroTail.next = twoHead.next;
        }

        oneTail.next = twoHead.next;

        if (zeroHead.next != null) return zeroHead.next;
        if (oneHead.next != null) return oneHead.next;
        return twoHead.next;
    }

    // Helper: create list
    public static Node convertArrayToList(int[] arr) {
        if (arr.length == 0) return null;

        Node head = new Node(arr[0]);
        Node temp = head;

        int i = 1;
        while (i < arr.length) {
            temp.next = new Node(arr[i]);
            temp = temp.next;
            i++;
        }
        return head;
    }

    // Helper: print list
    public static void printList(Node head) {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.data + " -> ");
            temp = temp.next;
        }
        System.out.println("null");
    }

    public static void main(String[] args) {
        int[] arr = {1, 0, 2, 0, 1};

        Node head = convertArrayToList(arr);

        System.out.print("Original: ");
        printList(head);

        head = sort012(head);

        System.out.print("Sorted: ");
        printList(head);
    }
}



public static Node sort012(Node head) {
    if (head == null || head.next == null) return head;

    int count0 = 0, count1 = 0, count2 = 0;

    Node temp = head;

    // Count values
    while (temp != null) {
        if (temp.data == 0) count0++;
        else if (temp.data == 1) count1++;
        else count2++;
        temp = temp.next;
    }

    // Overwrite values
    temp = head;

    while (temp != null) {
        if (count0 > 0) {
            temp.data = 0;
            count0--;
        } else if (count1 > 0) {
            temp.data = 1;
            count1--;
        } else {
            temp.data = 2;
            count2--;
        }
        temp = temp.next;
    }

    return head;
}
