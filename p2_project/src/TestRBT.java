import static org.junit.Assert.fail;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;

// TODO: Add tests to test if a Red-Black tree 
// extension of bst is correct.  Mostly check node color and position

//@SuppressWarnings("rawtypes")
public class TestRBT  {

    protected RBT<Integer,String> rbt;

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception {
         rbt = new RBT<Integer,String>();
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterEach
    void tearDown() throws Exception {
    }

    /** 
     * CASE 123 Insert three values in sorted order and then check 
     * the root, left, and right keys to see if RBT rebalancing 
     * occurred.
     * 
     */
    @Test
    void testRBT_001_insert_sorted_order_simple() {
        try {
            rbt.insert(10, "10");
            Assert.assertTrue(rbt.rootIsBlack());
            
            rbt.insert(20, "20");
            Assert.assertTrue(rbt.getKeyOfRightChildOf(10).equals(20)) ;
            Assert.assertEquals(rbt.colorOf(20),RBT.RED);
            
            rbt.insert(30, "30");  // SHOULD CAUSE REBALANCING
            Assert.assertTrue(rbt.getKeyOfRightChildOf(20).equals(30));
            
            // IF rebalancing is working,
            // the tree should have 20 at the root
            // and 10 as its left child and 30 as its right child
            Assert.assertEquals(rbt.getKeyAtRoot(), Integer.valueOf(20));
            Assert.assertEquals(rbt.getKeyOfLeftChildOf(20), Integer.valueOf(10));
            Assert.assertEquals(rbt.getKeyOfRightChildOf(20), Integer.valueOf(30));

            rbt.print();
            
        } catch (Exception e) {
            //e.printStackTrace();
            fail( "Unexpected exception: "+e.getMessage() );
        }
    }

    /** 
     * CASE 321 Insert three values in reverse sorted order and then check 
     * the root, left, and right keys to see if rebalancing 
     * occurred in the other direction.
     */
    @Test
    void testRBT_002_insert_reversed_sorted_order_simple() {
        try {
            rbt.insert(30, "30");
            rbt.insert(20, "20");
            rbt.insert(10, "10");
            
            if(rbt.getKeyAtRoot() != 20) {
                fail("Rebalence done wrong");
            }
            if(rbt.getKeyOfLeftChildOf(20) != 10) {
                fail("Rebalence done wrong");
            }
            if(rbt.getKeyOfRightChildOf(20) != 30) {
                fail("Rebalence done wrong");
            }
            
        }catch(Exception e) {    
            fail("unexpected Exception: " + e.getMessage());
        }
    }

    /** 
     * CASE 132 Insert three values so that rebalancing requires new key 
     * to be the "new" root of the rebalanced tree.
     * 
     * Then check the root, left, and right keys to see if rebalancing 
     * occurred correctly.
     */
    @Test
    void testRBT_003_insert_smallest_largest_middle_order_simple() {
        try {
            rbt.insert(10, "10");
            rbt.insert(30, "30");
            rbt.insert(20, "20");
            
            if(rbt.getKeyAtRoot() != 20) {
                fail("Rebalence done wrong");
            }
            if(rbt.getKeyOfLeftChildOf(20) != 10) {
                fail("Rebalence done wrong");
            }
            if(rbt.getKeyOfRightChildOf(20) != 30) {
                fail("Rebalence done wrong");
            }
            
        }catch(Exception e) {    
            fail("unexpected Exception: " + e.getMessage());
        }
    }

    /** 
     * CASE 312 Insert three values so that rebalancing requires new key 
     * to be the "new" root of the rebalanced tree.
     * 
     * Then check the root, left, and right keys to see if rebalancing 
     * occurred correctly.
     */
    @Test
    void testRBT_004_insert_largest_smallest_middle_order_simple() {
        try {
            rbt.insert(30, "30");
            rbt.insert(10, "10");
            rbt.insert(20, "20");
            
            if(rbt.getKeyAtRoot() != 20) {
                fail("Rebalence done wrong");
            }
            if(rbt.getKeyOfLeftChildOf(20) != 10) {
                fail("Rebalence done wrong");
            }
            if(rbt.getKeyOfRightChildOf(20) != 30) {
                fail("Rebalence done wrong");
            }
            
        }catch(Exception e) {    
            fail("unexpected Exception: " + e.getMessage());
        }
    }
    
    
    // TODO: Add your own tests
    
    // Add tests to make sure that rebalancing occurs even if the 
    // tree is larger.   Does it maintain it's balance?
    // Does the height of the tree reflect it's actual height
    // Use the traversal orders to check.
    
    // Can you insert many and still "get" them back out?
    
    // Does delete work?  Does the tree maintain balance when a key is deleted?
    // If delete is not implemented, does calling it throw an UnsupportedOperationException

    @Test
    void testrbt_005_key_at_root_returns_null() {
        try {
            if(rbt.getKeyAtRoot() != null) {
                fail("Method didn't return null on empty tree");
            }
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }    
    
    @Test
    void testrbt_006_key_at_left_child_null_key() {
        try {
            rbt.insert(20, "20");
            rbt.insert(10, "10");
            rbt.insert(30, "30");
            
            rbt.getKeyOfLeftChildOf(null);
            
            fail("Method should throw exception here");
        }catch(KeyNotFoundException e) {
           fail("Method should throw an IllegalNullKeyException not a KeyNotFoundException");
        }catch(IllegalNullKeyException e) {
            //Yay!
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    @Test
    void testrbt_007_key_at_left_child_not_found() {
        try {
            rbt.insert(20, "20");
            rbt.insert(10, "10");
            rbt.insert(30, "30");
            
            rbt.getKeyOfLeftChildOf(40);
            
            fail("Method should throw exception here");
        }catch(KeyNotFoundException e) {
           //yay
        }catch(IllegalNullKeyException e) {
            fail("Method should throw a KeyNotFoundException not an IllegalNullKeyException");
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    @Test
    void testrbt_008_key_at_right_child_not_found() {
        try {
            rbt.insert(20, "20");
            rbt.insert(10, "10");
            rbt.insert(30, "30");
            
            rbt.getKeyOfRightChildOf(40);
            
            fail("Method should throw exception here");
        }catch(KeyNotFoundException e) {
           //yay
        }catch(IllegalNullKeyException e) {
            fail("Method should throw a KeyNotFoundException not an IllegalNullKeyException");
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    void testrbt_009_key_at_right_child_null_key() {
        try {
            rbt.insert(20, "20");
            rbt.insert(10, "10");
            rbt.insert(30, "30");
            
            rbt.getKeyOfRightChildOf(null);
            
            fail("Method should throw exception here");
        }catch(KeyNotFoundException e) {
           fail("Method should throw an IllegalNullKeyException not a KeyNotFoundException");
        }catch(IllegalNullKeyException e) {
            //Yay!
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    
    void testrbt_010_get_height_returns_correct() {
        try {
            rbt.insert(20, "20");
            rbt.insert(10, "10");
            rbt.insert(30, "30");
            
            
            int height = rbt.getHeight();
            if(height != 2) {
                fail("height should be 2 is: " + height);
            }
            
            rbt.insert(40, "20");
            rbt.insert(50, "10");
            rbt.insert(60, "30");
            height = rbt.getHeight();
            if(height != 5) {
                fail("height should be 5 is: " + height);
            }
            
            rbt.insert(9, "20");
            rbt.insert(8, "10");
            rbt.insert(7, "30");
            height = rbt.getHeight();
            if(height != 5) {
                fail("height should be 5 is: " + height);
            }
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    
    @Test
    void testrbt_011_numKeys_correct() {
        try {
            rbt.insert(40, "20");
            rbt.insert(20, "10");
            rbt.insert(60, "30");
            rbt.insert(10, "20");
            rbt.insert(30, "10");
            rbt.insert(50, "30");
            rbt.insert(70, "20");
            rbt.insert(45, "10");
            rbt.insert(5, "30");
            
            if(rbt.numKeys() != 9) {
                fail("numKeys should be 9, is: " + rbt.numKeys());
            }
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    @Test
    void testrbt_012_contains_returns_true() {
        try {
            rbt.insert(40, "20");
            rbt.insert(20, "10");
            rbt.insert(60, "30");
            rbt.insert(10, "20");
            rbt.insert(30, "10");
            rbt.insert(50, "30");
            rbt.insert(70, "20");
            rbt.insert(45, "10");
            rbt.insert(5, "30");
            
            if(!rbt.contains(20)) {
                fail("Tree should contain this node.");
            }
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    @Test
    void testrbt_013_contains_returns_false() {
        try {
            rbt.insert(40, "20");
            rbt.insert(20, "10");
            rbt.insert(60, "30");
            rbt.insert(10, "20");
            rbt.insert(30, "10");
            rbt.insert(50, "30");
            rbt.insert(70, "20");
            rbt.insert(45, "10");
            rbt.insert(5, "30");
            
            if(rbt.contains(null)) {
                fail("Tree shouldn't return a value.");
            }else {
                fail("Tree shouldn't return a value");
            }
        }catch(IllegalNullKeyException e) {    
            //yay
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    @Test
    void testrbt_014_contains_returns_true() {
        try {
            rbt.insert(40, "20");
            rbt.insert(20, "10");
            rbt.insert(60, "30");
            rbt.insert(10, "20");
            rbt.insert(30, "10");
            rbt.insert(50, "30");
            rbt.insert(70, "20");
            rbt.insert(45, "10");
            rbt.insert(5, "30");
            
            if(!rbt.contains(20)) {
                fail("Tree should contain this node.");
            }
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    @Test
    void testrbt_015_get_returns_correct() {
        try {
            rbt.insert(40, "40");
            rbt.insert(20, "40");
            rbt.insert(60, "40");
            rbt.insert(10, "40");
            rbt.insert(30, "30");
            rbt.insert(50, "40");
            rbt.insert(70, "40");
            rbt.insert(45, "40");
            rbt.insert(5, "40");
            
            if(!rbt.get(30).equals("30")) {
                fail("Method should return 30, returns: " + rbt.get(30));
            }
            if(rbt.numKeys() != 9) {
                fail("numKeys should be 9, is: " + rbt.numKeys());
            }
        }catch(IllegalNullKeyException e) {
            fail("Method shouldn't throw exception here");
        }catch(KeyNotFoundException e) {
            fail("Method shouldn't throw exception here");
            
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    
    @Test
    void testrbt_016_get_null_key() {
        try {
            rbt.insert(40, "40");
            rbt.insert(20, "40");
            rbt.insert(60, "40");
            rbt.insert(10, "40");
            rbt.insert(30, "30");
            rbt.insert(50, "40");
            rbt.insert(70, "40");
            rbt.insert(45, "40");
            rbt.insert(5, "40");
            
            rbt.get(null);
            
            fail("Method should return 30, returns: " + rbt.get(30));
            
        }catch(IllegalNullKeyException e) {
            //yay
        }catch(KeyNotFoundException e) {
            fail("Method should throw an IllegalNullKeyException here");
            
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    @Test
    void testrbt_017_get_not_found() {
        try {
            rbt.insert(40, "40");
            rbt.insert(20, "40");
            rbt.insert(60, "40");
            rbt.insert(10, "40");
            rbt.insert(30, "30");
            rbt.insert(50, "40");
            rbt.insert(70, "40");
            rbt.insert(45, "40");
            rbt.insert(5, "40");
            
            rbt.get(300);
            fail("Method should throw exception");
            
        }catch(IllegalNullKeyException e) {
            fail("Method should throw a KeyNotFoundException");
        }catch(KeyNotFoundException e) {
            //yay
            
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    

    @Test
    void testrbt_018_insert_null_key() {
        try {
            rbt.insert(20, "20");
            rbt.insert(10, "10");
            rbt.insert(30, "30");
            
            rbt.insert(null, "whatever");
            
            fail("Method should throw exception here");

        }catch(IllegalNullKeyException e) {
            //yay
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    @Test
    void testrbt_019_insert_dup_key() {
        try {
            rbt.insert(20, "20");
            rbt.insert(10, "10");
            rbt.insert(30, "30");
            
            rbt.insert(10, "20");
            
            fail("Method should throw exception here");
    
        }catch(DuplicateKeyException e) {
            //yay
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    @Test
    void testRBT_020_insert_lots() {
        try {
            rbt.insert(17, "17");
            rbt.insert(05, "05");
            rbt.insert(51, "51");
            rbt.insert(10, "10");
            rbt.insert(69, "69");
            rbt.insert(92, "92");
            rbt.insert(12, "12");
            rbt.insert(32, "32");
            rbt.insert(35, "35");
            rbt.insert(07, "07");
            rbt.insert(22, "22");
            rbt.insert(02, "02");
            rbt.insert(39, "39");
            rbt.insert(90, "90");
            rbt.insert(56, "56");
            
            List<Integer> inOrder = rbt.getInOrderTraversal();
            
            if(inOrder.get(0) != 2) {
                fail("Insert not working correctly");
            }
            if(inOrder.get(14) != 92) {
                fail("Insert not working correctly");
            }
            if(inOrder.get(5) != 17) {
                fail("Insert not working correctly");
            }
            if(inOrder.get(8) != 35) {
                fail("Insert not working correctly");
            }
            if(inOrder.get(3) != 10) {
                fail("Insert not working correctly");
            }
            
            
        }catch(Exception e) {    
            fail("unexpected Exception: " + e.getMessage());
        }
    }
    
} // copyright Deb Deppeler, all rights reserved, DO NOT SHARE
