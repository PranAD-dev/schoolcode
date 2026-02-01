package schooljava;

public class Node {
    int val;
    Node next;
    public Node(int val, Node next){
        this.val = val;
        this.next = next;
    }
    public void tuff (String name){
        System.out.println("Hi "+ name);
    }
}
