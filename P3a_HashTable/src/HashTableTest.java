import static org.junit.jupiter.api.Assertions.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



/** TODO: add class header comments here*/
public class HashTableTest{
    
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }
    
    /** 
     * Tests that a HashTable returns an integer code
     * indicating which collision resolution strategy 
     * is used.
     * REFER TO HashTableADT for valid collision scheme codes.
     */
    @Test
    public void test000_collision_scheme() {
        HashTableADT<Integer, String> htIntegerKey = new HashTable<Integer,String>();
        int scheme = htIntegerKey.getCollisionResolution();
        if (scheme < 1 || scheme > 9) 
            fail("collision resolution must be indicated with 1-9");
    }
        
    /** IMPLEMENTED AS EXAMPLE FOR YOU
     * Tests that insert(null,null) throws IllegalNullKeyException
     */
    @Test
    public void test001_IllegalNullKey() {
        try {
            HashTableADT<Integer, String> htIntegerKey = new HashTable<Integer,String>();
            htIntegerKey.insert(null, null);
            fail("should not be able to insert null key");
        } 
        catch (IllegalNullKeyException e) { /* expected */ } 
        catch (Exception e) {
            fail("insert null key should not throw exception "+e.getClass().getName());
        }
    }
    
    /** 
     * Tests that get(null) throws IllegalNullKeyException
     */
    @Test
    public void test002_IllegalNullKey() {
        try {
            HashTableADT<Integer, String> htIntegerKey = new HashTable<Integer,String>();
            htIntegerKey.get(null);
            fail("should not be able to insert null key");
        } 
        catch (IllegalNullKeyException e) { /* expected */ } 
        catch (Exception e) {
            fail("get null key should not throw exception "+e.getClass().getName());
        }
    }
    
    /** 
     * Tests that remove(null) throws IllegalNullKeyException
     */
    @Test
    public void test003_IllegalNullKey() {
        try {
            HashTableADT<Integer, String> htIntegerKey = new HashTable<Integer,String>();
            htIntegerKey.remove(null);
            fail("should not be able to insert null key");
        } 
        catch (IllegalNullKeyException e) { /* expected */ } 
        catch (Exception e) {
            fail("remove null key should not throw exception "+e.getClass().getName());
        }
    }
    
    /** 
     * Tests that getLoadFactor works correctly
     */
    @Test
    public void test004_getLoadFactor() {
        try {
            HashTableADT<Integer, String> ht = new HashTable<Integer,String>(10, .75);
            ht.insert(10, "Hope");
            ht.insert(20, "Hope");
            ht.insert(30, "Hope");
            if(ht.getLoadFactor() != (3/10)) {
                fail("Load Factor is incorrect");
            }
            
        }catch (Exception e) {
            fail("no exception should be thrown");
        }
    }
    
    /** 
     * Tests that getLoadFactorThreshold works correctly
     */
    @Test
    public void test005_getLoadFactorThreshold() {
        try {
            HashTableADT<Integer, String> ht = new HashTable<Integer,String>(10, .75);
            if(ht.getLoadFactorThreshold() != .75) {
                fail("Load Factor incorrect");
            }
            ht.insert(10, "Hope");
            ht.insert(20, "Hope");
            ht.insert(30, "Hope");
            if(ht.getLoadFactorThreshold() != .75) {
                fail("Load Factor Threshold is incorrect");
            }
            
        }catch (Exception e) {
            fail("no exception should be thrown");        
        }
    }
    
    /** 
     * Tests that getCapacity works correctly
     */
    @Test
    public void test006_getCapacity() {
        try {
            HashTableADT<Integer, String> ht = new HashTable<Integer,String>(10, .75);
            if(ht.getCapacity() != 10) {
                fail("Load Factor incorrect");
            }
            ht.insert(10, "Hope");
            ht.insert(20, "Hope");
            ht.insert(30, "Hope");
            if(ht.getCapacity() != 10) {
                fail("Capacity is incorrect");
            }
            
        }catch (Exception e) {
            fail("no exception should be thrown");        
        }
    }
    
    /** 
     * Tests that get throws exception on not found
     */
    @Test
    public void test007_getNotFound() {
        try {
            HashTableADT<Integer, String> ht = new HashTable<Integer,String>();
            ht.insert(10, "Hope");
            ht.insert(20, "Hope");
            ht.insert(30, "Hope");
            
            ht.get(40);
            fail("Get should throw an exception here.");
        }catch (KeyNotFoundException e) {    
            //expected
        }catch (Exception e) {
            fail("no exception should be thrown");    
        }
    }
    
    /** 
     * Tests that get returns the correct value
     */
    @Test
    public void test008_getFound() {
        try {
            HashTableADT<Integer, String> ht = new HashTable<Integer,String>();
            ht.insert(10, "Hope1");
            ht.insert(20, "Hope2");
            ht.insert(30, "Hope3");
            ht.insert(4, "Hope4");
            ht.insert(5, "Hope5");
            ht.insert(6, "Hope6");
            ht.insert(7, "Hope7");
            ht.insert(8, "Hope8");
            ht.insert(9, "Hope0");
            
            if(!ht.get(10).equals("Hope1")) {
                fail("Get is not returning correct value-1.");
            }
            if(!ht.get(20).equals("Hope2")) {
                fail("Get is returning incorrectly-2.");
            }
            if(!ht.get(30).equals("Hope3")) {
                fail("Get is returning incorrectly-3.");
            }
            if(!ht.get(4).equals("Hope4")) {
                fail("Get is not returning correct value-4.");
            }
            if(!ht.get(5).equals("Hope5")) {
                fail("Get is returning incorrectly-5.");
            }
            if(!ht.get(6).equals("Hope6")) {
                fail("Get is returning incorrectly-6.");
            }
            if(!ht.get(7).equals("Hope7")) {
                fail("Get is not returning correct value-7.");
            }
            if(!ht.get(8).equals("Hope8")) {
                fail("Get is returning incorrectly-8.");
            }
            if(!ht.get(9).equals("Hope0")) {
                fail("Get is returning incorrectly-9.");
            }
            
        }catch (Exception e) {
            fail("no exception should be thrown");      
        }
    }
    
    
    /** 
     * Tests that insert can take several values and has right size
     */
    @Test
    public void test009_insertSeveral() {
        try {
            HashTableADT<Integer, String> ht = new HashTable<Integer,String>();
            ht.insert(1, "Hope");
            ht.insert(2, "Hope");
            ht.insert(3, "Hope");
            ht.insert(4, "Hope");
            ht.insert(5, "Hope");
            ht.insert(6, "Hope");
            ht.insert(7, "Hope");
            ht.insert(8, "Hope");
            ht.insert(0, "Hope");
            
            for(int i=0; i < ht.numKeys(); ++i) {
                ht.get(i);//if throws not found something is wrong
            }
            
            if(ht.numKeys() != 9) {
                fail("size is being returned wrong");
            }
        }catch (Exception e) {
            fail("no exception should be thrown");     
        }
    }


    /** 
     * Tests that get throws exception on not found
     */
    @Test
    public void test010_insertDuplicate() {
        try {
            HashTableADT<Integer, String> ht = new HashTable<Integer,String>();
            ht.insert(1, "Hope1");
            ht.insert(2, "Hope2");
            ht.insert(3, "Hope3");
            ht.insert(4, "Hope4");
            ht.insert(5, "Hope5");
            ht.insert(6, "Hope6");
            ht.insert(4, "Hope7");
            ht.insert(4, "Hope8");
            ht.insert(4, "Hope0");
            
            if(ht.numKeys() != 6) {
                fail("size is being returned wrong");
            }
            if(!ht.get(4).equals("Hope0")) {
                fail("Duplicate didn't get updated value.");
            }
            
        }catch (Exception e) {
            fail("no exception should be thrown");
        }
    }

    /** 
     * Tests that structure will resize and still work correctly
     */
    @Test
    public void test011_resize() {
        try {
            HashTableADT<Integer, String> ht = new HashTable<Integer,String>(10, .5);
            ht.insert(1, "Hope1");
            ht.insert(2, "Hope2");
            ht.insert(3, "Hope3");

            ht.insert(4, "Hope4");
            ht.insert(5, "Hope5");
            ht.insert(6, "Hope6");
            ht.insert(7, "Hope7");
            ht.insert(8, "Hope8");
            ht.insert(9, "Hope0");

            ht.insert(10, "1Hope1");
            ht.insert(20, "2Hope2");
            ht.insert(35, "3Hope3");
            ht.insert(41, "4Hope4");
            ht.insert(54, "5Hope5");
            ht.insert(68, "6Hope6");
            ht.insert(42, "7Hope7");
            ht.insert(43, "8Hope8");
            ht.insert(44, "0Hope0");

            if(ht.numKeys() != 18) {
                fail("size is being returned wrong");
            }

            if(!ht.get(4).equals("Hope4")) {
                fail("Get is returning incorrectly.");
            }

            if(!ht.get(44).equals("0Hope0")) {
                fail("Get is returning incorrectly.");
            }

        }catch (Exception e) {
            fail("no exception should be thrown");
        }
    }
    
    
    /** 
     * Tests that remove returns and acts correctly
     */
    @Test
    public void test012_removeWorks() {
        try {
            HashTableADT<Integer, String> ht = new HashTable<Integer,String>();
            ht.insert(1, "Hope1");
            ht.insert(2, "Hope2");
            ht.insert(3, "Hope3");
            ht.insert(4, "Hope4");
            ht.insert(5, "Hope5");
            ht.insert(6, "Hope6");
            ht.insert(7, "Hope7");
            ht.insert(8, "Hope8");
            ht.insert(9, "Hope0");
            
            if(!ht.remove(5)) {
                fail("Should return true on correct remove.");
            }
            
            if(ht.numKeys() != 8) {
                fail("size is being returned wrong after remove");
            }
            
            ht.get(5);
            fail("Should not find key in structure.");
        }catch(KeyNotFoundException e) {
            //expected
        }catch (Exception e) {
            fail("no exception should be thrown");
        }
    }

    /** 
     * Tests that remove returns false when not found
     */
    @Test
    public void test013_removeNotFound() {
        try {
            HashTableADT<Integer, String> ht = new HashTable<Integer,String>();
            ht.insert(1, "Hope1");
            ht.insert(2, "Hope2");
            ht.insert(3, "Hope3");
            ht.insert(4, "Hope4");
            ht.insert(5, "Hope5");
            ht.insert(6, "Hope6");
            ht.insert(7, "Hope7");
            ht.insert(8, "Hope8");
            ht.insert(9, "Hope0");
            
            if(ht.remove(10)) {
                fail("Method should return false if not found");
            }
            if(ht.numKeys() != 9) {
                fail("Size should not change");
            }
            
        }catch (Exception e) {
            fail("no exception should be thrown");
        }
    }
    
    /** 
     * Tests that remove can go down to 0
     */
    @Test
    public void test014_removeMany() {
        try {
            HashTableADT<Integer, String> ht = new HashTable<Integer,String>();
            ht.insert(1, "Hope1");
            ht.insert(2, "Hope2");
            ht.insert(3, "Hope3");
            ht.insert(4, "Hope4");
            ht.insert(5, "Hope5");
            ht.insert(6, "Hope6");
            ht.insert(7, "Hope7");
            ht.insert(8, "Hope8");
            ht.insert(9, "Hope0");
            
            if(!ht.remove(2)) {
                fail("Method should return true");
            }
            if(!ht.remove(3)) {
                fail("Method should return true");
            }
            if(!ht.remove(4)) {
                fail("Method should return true");
            }
            if(!ht.remove(5)) {
                fail("Method should return true");
            }
            if(!ht.remove(6)) {
                fail("Method should return true");
            }
            if(!ht.remove(7)) {
                fail("Method should return true");
            }
            if(!ht.remove(8)) {
                fail("Method should return true");
            }
            if(!ht.remove(9)) {
                fail("Method should return true");
            }
            if(!ht.remove(1)) {
                fail("Method should return true");
            }
            
            
            
            if(ht.numKeys() != 0) {
                fail("Size should be 0");
            }
            
        }catch (Exception e) {
            fail("no exception should be thrown");
        }
    }
    
    /** 
     * Tests that all methods are working well together
     */
    @Test
    public void test015_inputsinputsinputs() {
        try {
            HashTableADT<Integer, String> ht = new HashTable<Integer,String>();
            ht.insert(1, "Hope1");
            ht.insert(2, "Hope2");
            ht.insert(3, "Hope3");
            ht.insert(4, "Hope4");
            ht.remove(2);
            
            ht.insert(5, "Hope5");
            ht.insert(6, "Hope6");
            ht.insert(7, "Hope7");
            ht.insert(8, "Hope8");
            ht.insert(9, "Hope0");
            ht.remove(6);
            ht.remove(9);
            
            ht.insert(10, "1Hope1");
            ht.insert(20, "2Hope2");
            ht.insert(35, "3Hope3");
            ht.insert(41, "4Hope4");
            ht.remove(35);
            
            
            ht.insert(54, "5Hope5");
            ht.insert(68, "6Hope6");
            ht.insert(42, "7Hope7");
            ht.remove(3);
            ht.insert(54, "55");
            ht.insert(68, "66");
            ht.insert(42, "77");
            
            ht.insert(43, "8Hope8");
            ht.insert(44, "0Hope0");
            ht.remove(44);
            
            if(ht.numKeys() != 12) {
                fail("Size should not change");
            }
            
        }catch (Exception e) {
            fail("no exception should be thrown");
        }
    }
    
    /** 
     * Tests that remove returns false when not found
     */
    @Test
    public void test016_negativeKey() {
        try {
            HashTableADT<Integer, String> ht = new HashTable<Integer,String>();
            ht.insert(1, "Hope1");
            ht.insert(2, "Hope2");
            ht.insert(-3, "Hope3");
            ht.insert(-4, "Hope4");
            ht.insert(5, "Hope5");
            ht.insert(6, "Hope6");
            ht.insert(-7, "Hope7");
            ht.insert(8, "Hope8");
            ht.insert(9, "Hope0");
            
            
        }catch (Exception e) {
            fail("no exception should be thrown");
        }
    }
}
