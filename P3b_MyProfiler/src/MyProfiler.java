
// Used as the data structure to test our hash table against Tree Map
import java.util.Random;
import java.util.TreeMap;

public class MyProfiler<K extends Comparable<K>, V> {

	HashTableADT<K, V> hashtable;
	TreeMap<K, V> treemap;
	

	public MyProfiler() {
	    treemap = new TreeMap<K, V>();
	    hashtable = new HashTable<K, V>();
	}

	public void insert(K key, V value) throws IllegalNullKeyException {
	    treemap.put(key, value);
	    hashtable.insert(key, value);
	}

	public void retrieve(K key) throws IllegalNullKeyException, KeyNotFoundException {
	    V valueHT = hashtable.get(key);
	    V valueTM = treemap.get(key);
	}

	public static void main(String[] args) {
		try {
			int numElements = Integer.parseInt(args[0]);
			
			MyProfiler<Integer, Integer> profile = new MyProfiler<Integer, Integer>();

			Random rand = new Random();
			int digit = rand.nextInt((numElements + 1));
			int lowerRange = rand.nextInt((numElements/2) + 1);
			int upperRange = rand.nextInt((numElements - ((numElements/2) + 1)) + 1) + ((numElements/2) + 1);
			
			//insert random values
			//if there's a double both just replace no error
            for(int i=0; i < numElements; ++i) {
                profile.insert(rand.nextInt(), i);
            }
            System.out.println("Random Inserts Done");
			
			//reset profile for another round of inserts then a lookup;
			profile = null;
            profile = new MyProfiler<Integer, Integer>();
			
			//insert 0->#
			for(int i=0; i < numElements; ++i) {
			    profile.insert(i, i);
			}
			System.out.println("In Order Inserts Done");
			//lookup in order
			for(int i=0; i < numElements; ++i) {
                profile.retrieve(i);
            }
			System.out.println("In Order LookUps Done");
			//lookup single digit in range
			profile.retrieve(digit);
			System.out.println("Digit LookUp Done");
			//lookup random range of values in range
			for(int i=lowerRange; i < upperRange; ++i) {
                profile.retrieve(i);
            }
			System.out.println("Random Range LookUps Done");
			//lookup random number of values
			//works because I know these values have to be in the structures
			//divide by 100 because big number of inserts will be done in tests
			for(int i=0; i < numElements/100; ++i) {
                profile.retrieve(rand.nextInt(numElements + 1));
            } 
			System.out.println("Random LookUps Done");
			
			String msg = String.format("Inserted and retreived %d (key,value) pairs", numElements);
			System.out.println(msg);
		} catch (Exception e) {
			System.out.println("Usage: java MyProfiler <number_of_elements>");
			System.exit(1);
		}
	}
}
