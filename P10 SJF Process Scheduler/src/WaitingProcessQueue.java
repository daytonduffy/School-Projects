import java.util.NoSuchElementException;

public class WaitingProcessQueue implements WaitingQueueADT<CustomProcess> {
    private static final int INITIAL_CAPACITY = 20; // the initial capacity of this
                                                    // waiting process queue
    private CustomProcess[] data; // min heap-array storing the CustomProcesses
                                  // inserted in this WaitingProcessQueue.
                                  // data is an oversize array
    private int size; // number of CustomProcesses stored in this WaitingProcessQueue

    /**
     * Creates a new instance of WaitingProcessQueue 
     * with initial capacity and size zero
     * 
     */
    public WaitingProcessQueue() {
        data = new CustomProcess[INITIAL_CAPACITY];
        for(int i=0; i< INITIAL_CAPACITY; ++i) {
            data[i] = null;
        }
        size = 0;
    }
    
    
    /**
     * Recursively percolates a new CustomProcess to its correct spot in the array
     * 
     * @param index of the CustomProcess getting percolated up
     */
    private void minHeapPercolateUp(int index) { 
        while(index > 0) {//once at the root no more to look at  
             int parentIndex = (index-1)/2;
             
             //Gives us the value that tells me if the child is greater than, less than, equal
             int comparison = data[index].compareTo(data[parentIndex]);
             
             if(comparison < 0) {//Child Node < Parent Node
                 //holds the two values to swap
                 CustomProcess origParent = data[parentIndex];
                 CustomProcess origChild = data[index];
                 
                 //replace the values in the array
                 data[parentIndex] = origChild;
                 data[index] = origParent;
                 
                 //recursive call with the new parent to be compared up the tree
                 minHeapPercolateUp(parentIndex);
                 
             }else {//Child Node = Parent Node or Child Node > Parent Node
                 //In either of these cases the relationship between the parent and child is correct
                 return;
             }
        }
        return;
     }
    
    /**
     * Tests if the index is a leaf node for use in percolate down
     * 
     * @param index of the CustomProcess getting tested
     * @return true if index is a leaf node and false otherwise
     */
    private boolean isLeaf(int index) {
        
        if (index >= (size / 2) && index <= size) { 
            return true; 
        } 
        return false;
    }
    
    /**
     * Recursively percolates a CustomProcess down to the correct spot in the heap array
     * 
     * @param index of the CustomProcess getting percolated down
     */
    private void minHeapPercolateDown(int index) { 

        //If the node is not a leaf node
        if(!isLeaf(index)) {

                int rightIndex = (index * 2) + 2;
                int leftIndex = (index * 2) + 1;
                
            if(rightIndex < size) {
                
                int rightComp = data[index].compareTo(data[rightIndex]);
                int leftComp = data[index].compareTo(data[leftIndex]);
                int compareKids = data[leftIndex].compareTo(data[rightIndex]);
                
                //if either child is less than the parent
                if(rightComp > 0 || leftComp > 0) {
                    
                    //if left child is less it takes priority
                    if(compareKids < 0) {
                        CustomProcess origChild = data[leftIndex];
                        CustomProcess origParent = data[index];
                        
                        data[index] = origChild;
                        data[leftIndex] = origParent;
                        
                        minHeapPercolateDown(leftIndex);
                    //otherwise right child gets swapped    
                    }else {
                        CustomProcess origChild = data[rightIndex];
                        CustomProcess origParent = data[index];
                        
                        data[index] = origChild;
                        data[rightIndex] = origParent;
                        
                        minHeapPercolateDown(rightIndex);
                    }
    
                }
            }else {//case where there is only one child
                int leftComp = data[index].compareTo(data[leftIndex]);
                if(leftComp > 0) {
                    CustomProcess origChild = data[leftIndex];
                    CustomProcess origParent = data[index];
                    
                    data[index] = origChild;
                    data[leftIndex] = origParent;
                    
                    minHeapPercolateDown(leftIndex);
                }
            }
        }
        
    }
    
    
    /**
     * Doubles the size of the data array while keeping its contents 
     * (hopefully)
     * 
     * @return the new larger copy of the array
     * 
     */
    private CustomProcess[] doubleArray() {
        int newSize = data.length * 2;
        CustomProcess[] newArray = new CustomProcess[newSize];
        
        //copy all the data from orig array to the new larger one
        for(int i=0; i < data.length; ++i) {
            newArray[i] = data[i];
        }//leaves the rest as null correctly
        
        //return the new array
        return newArray;
    }
    
    
    /**
     * inserts a newObject in this waiting queue.
     * All we need to do is insert the object into the end 
     *  then use percolate up to put it in the right position

     * 
     * @param newObject to insert in this waiting queue
     */
    public void insert(CustomProcess newObject) {
        if(size >= data.length) {
            //do some stuff(double size and copy) maybe in diff method for ease
            data = doubleArray();
        }
        
        //add the new object to the first empty space in the array
        int current = size;
        data[current] = newObject;
        
        //use helper method to recursively make sure newObject is placed in the right spot
        minHeapPercolateUp(current);
        size = size + 1;
    }
    
    
    /**
     * removes and returns the element with the highest priority.(top)
     * 
     * @return the removed element
     * @throws java.util.NoSuchElementException with a descriptive error message if this waiting queue
     *                                          is empty
     */
    public CustomProcess removeBest() {
        if(size == 0) {
            throw new NoSuchElementException("Queue Empty");
        }
        CustomProcess oldRoot = new CustomProcess(data[0].getBurstTime());
        data[0] = data[size-1];
        minHeapPercolateDown(0);//zero because always zero for root
        
        data[size-1] = null;//DONT KNOW BUT NEEDS TO LEAVE
        
        size = size -1;
        return oldRoot;
    }
    
    
    /**
     * returns without removing the element with the highest priority.
     * 
     * @return the element with the highest priority
     * @throws java.util.NoSuchElementException with a descriptive error message if this waiting queue
     *                                          is empty
     */
    public CustomProcess peekBest() {
        if(isEmpty()) {
            throw new NoSuchElementException("Array is empty, nothing to peek at!");
        }
        
        //highest priority item is always the root
        //root is always at index zero
        return data[0];
    }
    
    /**
     * returns size of priority queue
     * 
     * @return the size of priority queue
     */
    public int size() {
        return size;
    }
    
    /**
     * checks whether this waiting queue is empty or not.
     * 
     * @return true if this waiting queue is empty, false otherwise
     */
    public boolean isEmpty() {
        if(size == 0) {
            return true;
        }else {
            return false;
        }
    }
    
    
    public String toString() {
        if(size == 0) {
            return " ";
        }
        String rep = "";
        for(int i=0; i < size; ++i) {
            rep = rep + "BurstTime: " + data[i].getBurstTime() + ", Process-ID: " + data[i].getProcessId() + ";\n";
        }
        return rep;
    }
    
}
