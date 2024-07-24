
/**
 *  ADT: Array of Pairs
 *  Dayton Duffy 
 *  drduffy@wisc.edu
 *  Lecture 2
 *     This Program is my implementation of the DataStructureADT. 
 *  The program uses an inner class, "Pair," to store the key and value 
 *  together. And collectively stores all those pair in an array that can grow 
 *  larger when the program demands it. 
 */
/**
 *     DS_My my implementation of the given ADT primarily using an Array structure.
 *  This class only implements the DataStructureADT and nothing more advanced.
 */

public class DS_My implements DataStructureADT< String, String > {
    
    /**
     *  Inner class used to pair the key and value in a single object
     */
    private class Pair {
        private final String key;//data structure's key
        private final String value;//data structure's value
        
        //Constructs the pair object and initializes fields
        private Pair(String key, String value) {
            this.key = key;
            this.value = value;
        }
        
        //returns key string
        private String getKey() {
            return key;
        }
        
        //returns value string
        private String getValue() {
            return value;
        }
    }
    
    
    
    private int size;//number of elements in the array
    private int arraySize;//actual size of array
    private Pair[] pairs;//array of pairs, main data structure
    
    /**
     *  constructs empty data structure with 
     *  size=0 and an opening arraySize of 10
     */
    public DS_My() {
        size = 0;
        arraySize = 10;
        pairs = new Pair[10];
    }

    /**
     *  inserts a new pair of key and value into a the data structure
     *  @param String key the key for the pair
     *  @param String value the value for the pair
     *  @throws IllegalArgumentException when key=null
     *  @throws RuntimeException when duplicate key
     */
    @Override
    public void insert(String key, String value) {
        if(key == null) {//checks for null key
            throw new IllegalArgumentException("null key");
        }
        for(int i=0; i < arraySize; ++i) {//checks for duplicate keys
            if(pairs[i] == null) {

            }else if(pairs[i].getKey().equals(key)) {
                throw new RuntimeException("duplicate key");
            }
        }
        
        
        if(size == arraySize) {//create larger array for more pairs
            growArray();
        }
        
        //Insert key into the next available space
        for(int i=0; i < arraySize; ++i) {
            if(pairs[i] == null) {
               Pair newPair = new Pair(key, value);
               pairs[i] = newPair; 
               size = size + 1;
               return; 
            }
        }
    }
    
    /**
     *  Increases the array size when called
     */
    private void growArray() {
        //increase array size by 10
        Pair[] newArray = new Pair[arraySize + 10];
        for(int i=0; i < arraySize; ++i) {//copy existing array
            newArray[i] = pairs[i];
        }
        //set new array
        pairs = newArray;
        arraySize = arraySize + 10;
    }
    
    /**
     *  removes the pair that matches the given key
     *  @param String key the key to remove
     *  @throws IllegalArgumentException when key=null
     *  @return returns true when pair is removed false otherwise
     */
    @Override
    public boolean remove(String key) {
        if(key == null) {
            throw new IllegalArgumentException("null key");
        }
        //look through data structure for key
        for(int i=0; i < arraySize; ++i) {
            if(pairs[i] == null) {
                
            }else if(pairs[i].getKey().equals(key)) {
                pairs[i] = null;
                size = size - 1;
                return true;
            }
        }
        return false;
    }

    /**
     *  Returns the value that is paired with the given key
     *  @param String key the key to find
     *  @throws IllegalArgumentException when key=null
     *  @return value paired with key and null when not found
     */
    @Override
    public String get(String key) {
        if(key == null) {
            throw new IllegalArgumentException("null key");
        }
        //look through data structure for key
        for(int i=0; i < arraySize; ++i) {
            if(pairs[i] == null) {

            }else if(pairs[i].getKey().equals(key)) {
                return pairs[i].getValue();
            }
        }
        return null;
    }

    /**
     *  checks whether the given key exists within the data structure
     *  @param String key the key to look for
     *  @return true when key is found, false otherwise
     */
    @Override
    public boolean contains(String key) {
        if(key == null) {
            return false;
        }
        //look through data structure for key
        for(int i=0; i < arraySize; ++i) {
            if(pairs[i] == null) {

            }else if(pairs[i].getKey().equals(key)) {
                return true;
            }
        }
        
        return false;
    }

    /**
     *  getter method for the number of elements in the data structure
     *  @return the number of elements in the data structure
     */
    @Override
    public int size() {
        return size;
    }


                                                        

}                            
    
