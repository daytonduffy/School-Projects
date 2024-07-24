import java.util.EmptyStackException;

public class DrawingStack implements StackADT<DrawingChange>, Iterable<DrawingChange>{
    private LinkedNode<DrawingChange> top; //head of list, top of stack
    private int size; //size of the stack
    
    //Constructor that creates an empty drawing stack with no parameter
    public DrawingStack() {
        size = 0;
        top = null;
    }
    //Constructor that sets current size to one
    //as well as creates the top of the list with the data provided
    public DrawingStack(DrawingChange data) {
        size = 1;
        LinkedNode<DrawingChange> dataNode = new LinkedNode<DrawingChange>(data);
        top = dataNode;
    }
    
    //Returns iterator object
    public DrawingStackIterator iterator() {
        return new DrawingStackIterator(top);
    }

    /**
     * Add an element to this stack
     * 
     * @param element an element to be added
     * @throws java.util.IllegalArgumentException with a descriptive error message if the input
     *         element is null
     */
    public void push(DrawingChange element) {
        if(element == null ) {
            throw new IllegalArgumentException("Element is null");
        }
        LinkedNode<DrawingChange> elementNode = new LinkedNode<DrawingChange>(element);
        elementNode.setNext(top);;
        top = elementNode;
        size = size+1;
    }

    /**
     * Remove the element on the top of this stack and return it
     * 
     * @return the element removed from the top of the stack
     * @throws java.util.EmptyStackException without error message if the stack is empty
     */
    public DrawingChange pop() {
        if(size == 0) {
            throw new EmptyStackException();
        }
        LinkedNode<DrawingChange> oldTop = new LinkedNode<DrawingChange>(top.getData());
        top = top.getNext();
        size = size-1;
       
        return oldTop.getData();
    }

    /**
     * Get the element on the top of this stack
     * 
     * @return the element on the stack top
     * @throws java.util.EmptyStackException without error message if the stack is empty
     */
    public DrawingChange peek() {
        if(size == 0) {
            throw new EmptyStackException();
        }
        return top.getData();
    }

    /**
     * Check whether this stack is empty or not
     * 
     * @return true if this stack contains no elements, otherwise false
     */
    public boolean isEmpty() {
        if(size==0) {
            return true;
        }
        return false;
    }

    /**
     * Get the number of elements in this stack
     * 
     * @return the size of the stack
     */
    public int size() {
        return size;
    }



}
