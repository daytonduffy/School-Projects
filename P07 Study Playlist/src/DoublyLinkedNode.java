
public class DoublyLinkedNode <T>{
    private DoublyLinkedNode<T> previous;
    private DoublyLinkedNode<T> next;
    private T data;
    
    //Method constructs new Doubly Linked Node 
    //Sets the next and previous nodes as well as the given data
    public DoublyLinkedNode(DoublyLinkedNode<T> previous, T data, 
        DoublyLinkedNode<T> next) {
        if(data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        
        this.previous = previous;
        this.next = next;
        this.data = data;
        
    }
    
    //Method constructs new Doubly Linked Node
    //Sets the data for the node, sets the pointers to null
    public DoublyLinkedNode(T data) {
        if(data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        this.data = data;
        previous = null;
        next = null;
    }
    
    //These Methods return the elements of the Node
    public T getData() {
        return data;
    }
    public DoublyLinkedNode<T> getPrevious(){
        return previous;
    }
    public DoublyLinkedNode<T> getNext(){
        return next;
    }
    
    //These Methods set the pointers for the next and previous nodes
    public void setNext(DoublyLinkedNode<T> next) {
        this.next = next;
    }
    public void setPrevious(DoublyLinkedNode<T> previous) {
        this.previous = previous;
    }
}
