package schooljava;

public class LL {
    Node head;
    Node tail;
    public LL(Node h, Node t){
        head = h;
        tail = t;
        head.next = tail;
    }
    public void add(Node newN) {
        if (head!=null){
            tail.next = newN;
        } else {
            head = newN;
        }
    }



    public static void main(String[] args){
        Node tail = new Node(2,null);
        Node head = new Node(1,null);
        LL list = new LL(head, tail);

    }
}
