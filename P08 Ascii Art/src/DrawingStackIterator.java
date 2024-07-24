import java.util.Iterator;
import java.util.NoSuchElementException;

public class DrawingStackIterator implements Iterator<DrawingChange>{
   private LinkedNode <DrawingChange> next; //the next drawing on the stack
   
   //Constructor that takes in the top of the stack to iterate through
   public DrawingStackIterator(LinkedNode<DrawingChange> top) {
       next = top;
   }
   
   //returns true if there's another drawing in the stack
   public boolean hasNext() {
       if(next == null) {
           return false;
       }
       return true;
   }
   
   //Returns the next drawing in the stack and edits the pointer
   public DrawingChange next() {
       if(hasNext()) {
           DrawingChange temp = next.getData();
           next = next.getNext();
           return temp;
       }else {
           throw new NoSuchElementException("No more Drawings in Stack");
       }
       
   }
}
