import java.util.LinkedList;



// I am using a linked list bucket collision resolution. When a collision
// is detected simply add key to the linkedlist at that index.
//
//
// explain your hashing algorithm here: 
// My hashing algorithm uses java's hashcode function 
// because I don't know what type K will be
// then I mod the hashcode by the current table size
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {
	
    //Inner node class used to hold key value pairs
    //contains only basic getter functions
    //along with basic constructor
    private class Node{
	    private K key;
	    private V value;
	    
	    private Node(K newKey, V newValue){
            key = newKey;
            value = newValue;
        }
	    
	    private V getValue() {
            return value;
        }
        
        private K getKey() {
            return key;
        }
        
	}
    
    
    private int tableSize;
	private double loadFactorThresh;
	private int numKeys;
	private LinkedList<Node>[] table;
	
	//default no arg constructor
	@SuppressWarnings("unchecked")
    public HashTable() {
	    numKeys = 0;//no keys inside
        loadFactorThresh = .75;//basic threshold should be .75
        tableSize = 47;//with none given make array size 101
        table = new LinkedList[tableSize];//initialize array
        //for(int i=0; i < tableSize; ++i) {
        //    table[i] = new LinkedList<Node>();
        //}
	}
	
	// constructor that takes initial capacity and load factor threshold
    // threshold is the load factor that causes a resize and rehash
	@SuppressWarnings("unchecked")
    public HashTable(int initialCapacity, double loadFactorThreshold) {
	    numKeys = 0;//empty structure
	    loadFactorThresh = loadFactorThreshold;//set given threshold
	    tableSize = initialCapacity;//set given initial capacity for table size
	    table = new LinkedList[tableSize];//initialize array to given size
	    //for(int i=0; i < tableSize; ++i) {
        //    table[i] = new LinkedList<Node>();
        //}
	}
	
	// Add the key,value pair to the data structure and increase the number of keys.
    // If key is null, throw IllegalNullKeyException;
    // If key is already in data structure, replace value with new value
    public void insert(K key, V value) throws IllegalNullKeyException {
        if(key == null) {
            throw new IllegalNullKeyException("Key is null.");
        }
        //get hashindex and create new node
        int index = hashIndex(key);
        if(index < 0) {
            index = index * -1;
        }
        Node node = new Node(key, value);
        
        
        if(contains(key)) {//If in structure update value
            LinkedList<Node> temp = table[index];
            int llDex = -1;
            for(int i=0; i < temp.size(); ++i) {
                Node tNode = temp.get(i);
                int comp = key.compareTo(tNode.getKey());
                if(comp == 0) {    
                    llDex = i;
                    break;
                }
            }
            if(llDex == -1) {
                throw new IllegalNullKeyException("Something went real wrong buddy.");
            }
            //setting new node over keeps key and changes value
            temp.set(llDex, node);
            table[index] = temp;
        }else {//If not in structure add new node to list at calculated index
            LinkedList<Node> temp = new LinkedList<Node>();
            if(table[index] == null) {
                table[index] = new LinkedList<Node>();
            }
            temp = table[index];
            temp.add(node);
            table[index] = temp;
            numKeys = numKeys + 1;
        }

        //if load factor has gotten to big resize the structure
        if(getLoadFactor() >= getLoadFactorThreshold()) {
            resize();

        }
        
    }
    
    // If key is found,
    // remove the key,value pair from the data structure
    // decrease number of keys.
    // return true
    // If key is null, throw IllegalNullKeyException
    // If key is not found, return false
    public boolean remove(K key) throws IllegalNullKeyException {
        if(key == null) {
            throw new IllegalNullKeyException("Key is null");
        }
        if(contains(key)) {//if key is in structure
            int index = hashIndex(key);
            if(index < 0) {
                index = index * -1;
            }
            LinkedList<Node> temp = table[index];
            int llDex = -1;
            for(int i=0; i < temp.size(); ++i) {//find key in linked list
                Node tNode = temp.get(i);
                int comp = key.compareTo(tNode.getKey());
                if(comp == 0) {    
                    llDex = i;
                    break;
                }
            }
            if(llDex == -1) {
                throw new IllegalNullKeyException("Something went real wrong buddy.");
            }
            //remove key and set new linked list in table
            temp.remove(llDex);
            table[index] = temp;
            numKeys = numKeys - 1;
            return true;
        }else {//If key isn't in structure
            return false;//return false
        }
    }

    // Returns the value associated with the specified key
    // Does not remove key or decrease number of keys
    //
    // If key is null, throw IllegalNullKeyException
    // If key is not found, throw KeyNotFoundException().
    public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
        if(key == null) {
            throw new IllegalNullKeyException("Key is null.");
        }
        if(contains(key)) {//if key is in structure
            int index = hashIndex(key);//get hashindex
            if(index < 0) {
                index = index * -1;
            }
            LinkedList<Node> temp = new LinkedList<Node>();
            temp = table[index];
            int llDex = -1;
            for(int i=0; i < temp.size(); ++i) {//find where in linked list it is
                Node tNode = temp.get(i);
                int comp = key.compareTo(tNode.getKey());
                if(comp == 0) {    
                    llDex = i;
                    break;
                }
            }
            if(llDex == -1) {
                throw new IllegalNullKeyException("Something went real wrong buddy.");
            }
            //return value
            return table[index].get(llDex).getValue();
        }else {
            throw new KeyNotFoundException("Key not found in structure.");
        }
    }

    //Method returns current number of keys in the hash table
    public int numKeys() {
        return numKeys;
    }

    //Method returns the load factor threshold, where the hashtable needs to resize
    public double getLoadFactorThreshold() {
        return loadFactorThresh;
    }

    //Method returns the current load factor based off of number of elements and table size
    public double getLoadFactor() {
        return numKeys/tableSize;
    }

    //Method returns the current capacity of the HashTable
    public int getCapacity() {
        return tableSize;
    }

    //5 - CHAINED BUCKET: array of linked nodes
    public int getCollisionResolution() {
        return 5;
    }
		
    //method used to find hashindex of a key
    private int hashIndex(K key) {
        int hashCode = key.hashCode();//java's hash code function
        return hashCode%tableSize;//in class method
    }
    
    //method used to check if a key is in the structure
    private boolean contains(K key) {
        int index = hashIndex(key);
        if(index < 0) {
            index = index * -1;
        }
        LinkedList<Node> temp = table[index];//only check given index
        if(temp != null) {
            for(int i=0; i < temp.size(); ++i) {//look through linked list
                Node tNode = temp.get(i);
                int comp = key.compareTo(tNode.getKey());
                if(comp == 0) {//if keys are equal
                    return true;
                }
            }
        }
        
        return false;
    }
    
    
    //Method used when load factor crosses the threshold and the table needs to be resized
    //doubles the size of the table and reinserts all the values
    @SuppressWarnings("unchecked")
    private void resize() throws IllegalNullKeyException {
        LinkedList<Node> temp = new LinkedList<Node>();
        for(int i=0; i < tableSize; ++i) {
            if(table[i] != null) {
                for(int j=0; j < table[i].size(); ++j) {
                    temp.add(table[i].get(j));//Put all nodes into a new list to hold
                }
            }    
        }

        table = null;//clear table
        table = new LinkedList[(tableSize * 2) + 1];//make new table
        tableSize = (tableSize * 2) + 1;//set new size
        numKeys = 0;//set keys back to zero, they get added later
        //for(int i=0; i < tableSize; ++i) {
        //    table[i] = new LinkedList<Node>();
        //}
        for(int i=0; i < temp.size(); ++i) {//insert each key into the new table
            insert(temp.get(i).getKey(), temp.get(i).getValue());
        }

    }
}
