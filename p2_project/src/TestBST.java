import static org.junit.Assert.fail;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//@SuppressWarnings("rawtypes")
public class TestBST {

    protected STADT<Integer,String> bst;

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception {
         bst = new BST<Integer,String>();
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterEach
    void tearDown() throws Exception {
    }

    /** 
     * CASE 123 Insert three values in sorted order and then check 
     * the root, left, and right keys to see if insert worked 
     * correctly.
     */
    @Test
    void testBST_001_insert_sorted_order_simple() {
        try {
            bst.insert(10, "10");
            if (!bst.getKeyAtRoot().equals(10))
                fail("insert at root does not work");
            
            bst.insert(20, "20");
            if (!bst.getKeyOfRightChildOf(10).equals(20)) 
                fail("insert to right child of root does not work");
            
            bst.insert(30, "30");
            if (!bst.getKeyAtRoot().equals(10)) 
                fail("inserting 30 changed root");

            if (!bst.getKeyOfRightChildOf(20).equals(30)) 
                fail("inserting 30 as right child of 20");

            // IF rebalancing is working,
            // the tree should have 20 at the root
            // and 10 as its left child and 30 as its right child

            Assert.assertEquals(bst.getKeyAtRoot(), Integer.valueOf(10));
            Assert.assertEquals(bst.getKeyOfRightChildOf(10), Integer.valueOf(20));
            Assert.assertEquals(bst.getKeyOfRightChildOf(20), Integer.valueOf(30));

            //bst.print();
            
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception: "+e.getMessage() );
        }
    }

    /** 
     * CASE 321 Insert three values in reverse sorted order and then check 
     * the root, left, and right keys to see if insert 
     * worked in the other direction.
     */
    @Test
    void testBST_002_insert_reversed_sorted_order_simple() {
        try {
            bst.insert(30, "30");
            if (!bst.getKeyAtRoot().equals(30))
                fail("insert at root does not work");
            
            bst.insert(20, "20");
            if (!bst.getKeyOfLeftChildOf(30).equals(20)) 
                fail("insert to left child of root does not work");
            
            bst.insert(10, "10");
            if (!bst.getKeyAtRoot().equals(30)) 
                fail("inserting 10 changed root");

            if (!bst.getKeyOfLeftChildOf(20).equals(10)) 
                fail("inserting 10 as left child of 20");

            // IF rebalancing is working,
            // the tree should have 20 at the root
            // and 10 as its left child and 30 as its right child

            Assert.assertEquals(bst.getKeyAtRoot(), Integer.valueOf(30));
            Assert.assertEquals(bst.getKeyOfLeftChildOf(30), Integer.valueOf(20));
            Assert.assertEquals(bst.getKeyOfLeftChildOf(20), Integer.valueOf(10));

            //bst.print();
            
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception: "+e.getMessage() );
        }
    }

    /** 
     * CASE 132 Insert three values so that rebalancing requires new key 
     * to be the "new" root of the rebalanced tree.
     * 
     * Then check the root, left, and right keys to see if insert 
     * occurred correctly.
     */
    @Test
    void testBST_003_insert_smallest_largest_middle_order_simple() {
        try {
            bst.insert(10, "10");
            if (!bst.getKeyAtRoot().equals(10))
                fail("insert at root does not work");
            
            bst.insert(30, "30");
            if (!bst.getKeyOfRightChildOf(10).equals(30)) 
                fail("insert to right child of root does not work");
            Assert.assertEquals(bst.getKeyOfRightChildOf(10),Integer.valueOf(30));
            
            bst.insert(20, "20");
            if (!bst.getKeyAtRoot().equals(10)) 
                fail("inserting 20 changed root");

            if (!bst.getKeyOfLeftChildOf(30).equals(20)) 
                fail("inserting 20 as left child of 30");

            // IF rebalancing is working,
            // the tree should have 20 at the root
            // and 10 as its left child and 30 as its right child

            Assert.assertEquals(bst.getKeyAtRoot(), Integer.valueOf(10));
            Assert.assertEquals(bst.getKeyOfRightChildOf(10), Integer.valueOf(30));
            Assert.assertEquals(bst.getKeyOfLeftChildOf(30), Integer.valueOf(20));

            //bst.print();
            
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception: "+e.getMessage() );
        }
    }

    /** 
     * CASE 312 Insert three values so that rebalancing requires new key 
     * to be the "new" root of the rebalanced tree.
     * 
     * Then check the root, left, and right keys to see if insert 
     * occurred correctly.
     */
    @Test
    void testBST_004_insert_largest_smallest_middle_order_simple() {
        try {
            bst.insert(30, "30");
            if (!bst.getKeyAtRoot().equals(30))
                fail("insert at root does not work");
            
            bst.insert(10, "10");
            if (!bst.getKeyOfLeftChildOf(30).equals(10)) 
                fail("insert to left child of root does not work");
            
            bst.insert(20, "20");
            if (!bst.getKeyAtRoot().equals(30)) 
                fail("inserting 10 changed root");

            if (!bst.getKeyOfRightChildOf(10).equals(20)) 
                fail("inserting 20 as right child of 10");

            // the tree should have 30 at the root
            // and 10 as its left child and 20 as 10's right child

            Assert.assertEquals(bst.getKeyAtRoot(), Integer.valueOf(30));
            Assert.assertEquals(bst.getKeyOfLeftChildOf(30), Integer.valueOf(10));
            Assert.assertEquals(bst.getKeyOfRightChildOf(10), Integer.valueOf(20));

            //bst.print();
            
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception: "+e.getMessage() );
        }
    }
    
    
    // TODO: Add your own tests
    
    // Add tests to make sure that bst grows as expected.
    // Does it maintain it's balance?
    // Does the height of the tree reflect it's actual height
    // Use the traversal orders to check.
    
    // Can you insert many and still "get" them back out?
    
    // Does delete work? 
    // If delete is not implemented, does calling it throw an UnsupportedOperationException
    
    @Test
    void testBST_005_key_at_root_returns_null() {
        try {
            if(bst.getKeyAtRoot() != null) {
                fail("Method didn't return null on empty tree");
            }
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }    
    
    @Test
    void testBST_006_key_at_left_child_null_key() {
        try {
            bst.insert(20, "20");
            bst.insert(10, "10");
            bst.insert(30, "30");
            
            bst.getKeyOfLeftChildOf(null);
            
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
    void testBST_007_key_at_left_child_not_found() {
        try {
            bst.insert(20, "20");
            bst.insert(10, "10");
            bst.insert(30, "30");
            
            bst.getKeyOfLeftChildOf(40);
            
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
    void testBST_008_key_at_right_child_not_found() {
        try {
            bst.insert(20, "20");
            bst.insert(10, "10");
            bst.insert(30, "30");
            
            bst.getKeyOfRightChildOf(40);
            
            fail("Method should throw exception here");
        }catch(KeyNotFoundException e) {
           //yay
        }catch(IllegalNullKeyException e) {
            fail("Method should throw a KeyNotFoundException not an IllegalNullKeyException");
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    void testBST_009_key_at_right_child_null_key() {
        try {
            bst.insert(20, "20");
            bst.insert(10, "10");
            bst.insert(30, "30");
            
            bst.getKeyOfRightChildOf(null);
            
            fail("Method should throw exception here");
        }catch(KeyNotFoundException e) {
           fail("Method should throw an IllegalNullKeyException not a KeyNotFoundException");
        }catch(IllegalNullKeyException e) {
            //Yay!
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    
    void testBST_010_get_height_returns_correct() {
        try {
            bst.insert(20, "20");
            bst.insert(10, "10");
            bst.insert(30, "30");
            
            
            int height = bst.getHeight();
            if(height != 2) {
                fail("height should be 2 is: " + height);
            }
            
            bst.insert(40, "20");
            bst.insert(50, "10");
            bst.insert(60, "30");
            height = bst.getHeight();
            if(height != 5) {
                fail("height should be 5 is: " + height);
            }
            
            bst.insert(9, "20");
            bst.insert(8, "10");
            bst.insert(7, "30");
            height = bst.getHeight();
            if(height != 5) {
                fail("height should be 5 is: " + height);
            }
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    @Test
    void testBST_011_traversal_inOrder() {
        try {
            bst.insert(40, "20");
            bst.insert(20, "10");
            bst.insert(60, "30");
            bst.insert(10, "20");
            bst.insert(30, "10");
            bst.insert(50, "30");
            bst.insert(70, "20");
            bst.insert(45, "10");
            bst.insert(5, "30");
            
            List<Integer> inOrder = bst.getInOrderTraversal();
            if(inOrder.size() != 9) {
                fail("Size should be 9 is: " + inOrder.size());
            }
            
            if(inOrder.get(0) != 5) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(1) != 10) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(2) != 20) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(3) != 30) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(4) != 40) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(5) != 45) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(6) != 50) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(7) != 60) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(8) != 70) {
                fail("Traersal not working correctly");
            }
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    @Test
    void testBST_012_traversal_preOrder() {
        try {
            bst.insert(40, "20");
            bst.insert(20, "10");
            bst.insert(60, "30");
            bst.insert(10, "20");
            bst.insert(30, "10");
            bst.insert(50, "30");
            bst.insert(70, "20");
            bst.insert(45, "10");
            bst.insert(5, "30");
            
            List<Integer> preOrder = bst.getPreOrderTraversal();
            if(preOrder.size() != 9) {
                fail("Size should be 9 is: " + preOrder.size());
            }
            
            if(preOrder.get(0) != 40) {
                fail("Traersal not working correctly");
            }
            if(preOrder.get(1) != 20) {
                fail("Traersal not working correctly");
            }
            if(preOrder.get(2) != 10) {
                fail("Traersal not working correctly");
            }
            if(preOrder.get(3) != 5) {
                fail("Traersal not working correctly");
            }
            if(preOrder.get(4) != 30) {
                fail("Traersal not working correctly");
            }
            if(preOrder.get(5) != 60) {
                fail("Traersal not working correctly");
            }
            if(preOrder.get(6) != 50) {
                fail("Traersal not working correctly");
            }
            if(preOrder.get(7) != 45) {
                fail("Traersal not working correctly");
            }
            if(preOrder.get(8) != 70) {
                fail("Traersal not working correctly");
            }
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    @Test
    void testBST_013_traversal_postOrder() {
        try {
            bst.insert(40, "20");
            bst.insert(20, "10");
            bst.insert(60, "30");
            bst.insert(10, "20");
            bst.insert(30, "10");
            bst.insert(50, "30");
            bst.insert(70, "20");
            bst.insert(45, "10");
            bst.insert(5, "30");
            
            List<Integer> postOrder = bst.getPostOrderTraversal();
            if(postOrder.size() != 9) {
                fail("Size should be 9 is: " + postOrder.size());
            }
            
            if(postOrder.get(0) != 5) {
                fail("Traersal not working correctly");
            }
            if(postOrder.get(1) != 10) {
                fail("Traersal not working correctly");
            }
            if(postOrder.get(2) != 30) {
                fail("Traersal not working correctly");
            }
            if(postOrder.get(3) != 20) {
                fail("Traersal not working correctly");
            }
            if(postOrder.get(4) != 45) {
                fail("Traersal not working correctly");
            }
            if(postOrder.get(5) != 50) {
                fail("Traersal not working correctly");
            }
            if(postOrder.get(6) != 70) {
                fail("Traersal not working correctly");
            }
            if(postOrder.get(7) != 60) {
                fail("Traersal not working correctly");
            }
            if(postOrder.get(8) != 40) {
                fail("Traersal not working correctly");
            }
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    @Test
    void testBST_014_traversal_levelOrder() {
        try {
            bst.insert(40, "20");
            bst.insert(20, "10");
            bst.insert(60, "30");
            bst.insert(10, "20");
            bst.insert(30, "10");
            bst.insert(50, "30");
            bst.insert(70, "20");
            bst.insert(45, "10");
            bst.insert(5, "30");
            
            List<Integer> levelOrder = bst.getLevelOrderTraversal();
            if(levelOrder.size() != 9) {
                fail("Size should be 9 is: " + levelOrder.size());
            }
            
            if(levelOrder.get(0) != 40) {
                fail("Traersal not working correctly");
            }
            if(levelOrder.get(1) != 20) {
                fail("Traersal not working correctly");
            }
            if(levelOrder.get(2) != 60) {
                fail("Traersal not working correctly");
            }
            if(levelOrder.get(3) != 10) {
                fail("Traersal not working correctly");
            }
            if(levelOrder.get(4) != 30) {
                fail("Traersal not working correctly");
            }
            if(levelOrder.get(5) != 50) {
                fail("Traersal not working correctly");
            }
            if(levelOrder.get(6) != 70) {
                fail("Traersal not working correctly");
            }
            if(levelOrder.get(7) != 5) {
                fail("Traersal not working correctly");
            }
            if(levelOrder.get(8) != 45) {
                fail("Traersal not working correctly");
            }
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    
    @Test
    void testBST_015_numKeys_correct() {
        try {
            bst.insert(40, "20");
            bst.insert(20, "10");
            bst.insert(60, "30");
            bst.insert(10, "20");
            bst.insert(30, "10");
            bst.insert(50, "30");
            bst.insert(70, "20");
            bst.insert(45, "10");
            bst.insert(5, "30");
            
            if(bst.numKeys() != 9) {
                fail("numKeys should be 9, is: " + bst.numKeys());
            }
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    @Test
    void testBST_016_contains_returns_true() {
        try {
            bst.insert(40, "20");
            bst.insert(20, "10");
            bst.insert(60, "30");
            bst.insert(10, "20");
            bst.insert(30, "10");
            bst.insert(50, "30");
            bst.insert(70, "20");
            bst.insert(45, "10");
            bst.insert(5, "30");
            
            if(!bst.contains(20)) {
                fail("Tree should contain this node.");
            }
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    @Test
    void testBST_017_contains_returns_false() {
        try {
            bst.insert(40, "20");
            bst.insert(20, "10");
            bst.insert(60, "30");
            bst.insert(10, "20");
            bst.insert(30, "10");
            bst.insert(50, "30");
            bst.insert(70, "20");
            bst.insert(45, "10");
            bst.insert(5, "30");
            
            if(bst.contains(null)) {
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
    void testBST_018_contains_returns_true() {
        try {
            bst.insert(40, "20");
            bst.insert(20, "10");
            bst.insert(60, "30");
            bst.insert(10, "20");
            bst.insert(30, "10");
            bst.insert(50, "30");
            bst.insert(70, "20");
            bst.insert(45, "10");
            bst.insert(5, "30");
            
            if(!bst.contains(20)) {
                fail("Tree should contain this node.");
            }
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    @Test
    void testBST_019_get_returns_correct() {
        try {
            bst.insert(40, "40");
            bst.insert(20, "40");
            bst.insert(60, "40");
            bst.insert(10, "40");
            bst.insert(30, "30");
            bst.insert(50, "40");
            bst.insert(70, "40");
            bst.insert(45, "40");
            bst.insert(5, "40");
            
            if(!bst.get(30).equals("30")) {
                fail("Method should return 30, returns: " + bst.get(30));
            }
            if(bst.numKeys() != 9) {
                fail("numKeys should be 9, is: " + bst.numKeys());
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
    void testBST_020_get_null_key() {
        try {
            bst.insert(40, "40");
            bst.insert(20, "40");
            bst.insert(60, "40");
            bst.insert(10, "40");
            bst.insert(30, "30");
            bst.insert(50, "40");
            bst.insert(70, "40");
            bst.insert(45, "40");
            bst.insert(5, "40");
            
            bst.get(null);
            
            fail("Method should return 30, returns: " + bst.get(30));
            
        }catch(IllegalNullKeyException e) {
            //yay
        }catch(KeyNotFoundException e) {
            fail("Method should throw an IllegalNullKeyException here");
            
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    @Test
    void testBST_021_get_not_found() {
        try {
            bst.insert(40, "40");
            bst.insert(20, "40");
            bst.insert(60, "40");
            bst.insert(10, "40");
            bst.insert(30, "30");
            bst.insert(50, "40");
            bst.insert(70, "40");
            bst.insert(45, "40");
            bst.insert(5, "40");
            
            bst.get(300);
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
    void testBST_022_remove_null_key() {
        try {
            bst.insert(40, "40");
            bst.insert(20, "40");
            bst.insert(60, "40");
            bst.insert(10, "40");
            bst.insert(30, "30");
            bst.insert(50, "40");
            bst.insert(70, "40");
            bst.insert(45, "40");
            bst.insert(5, "40");
            
            if(bst.remove(null)) {
                fail("Method should throw an exception");
            }else {
                fail("Method should throw an exception");
            }
        }catch(IllegalNullKeyException e) {
            //yay
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    @Test
    void testBST_023_remove_key_not_found() {
        try {
            bst.insert(40, "40");
            bst.insert(20, "40");
            bst.insert(60, "40");
            bst.insert(10, "40");
            bst.insert(30, "30");
            bst.insert(50, "40");
            bst.insert(70, "40");
            bst.insert(45, "40");
            bst.insert(5, "40");
            
            if(bst.remove(300)) {
                fail("Method should return false");
            }
            if(bst.numKeys() != 9) {
                fail("numKeys should be 9, is: " + bst.numKeys());
            }
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    @Test
    void testBST_024_remove_leaf() {
        try {
            bst.insert(40, "40");
            bst.insert(20, "40");
            bst.insert(60, "40");
            bst.insert(10, "40");
            bst.insert(30, "30");
            bst.insert(50, "40");
            bst.insert(70, "40");
            bst.insert(45, "40");
            bst.insert(5, "40");
            
            if(!bst.remove(5)) {
                fail("Method should return true");
            }
            if(bst.numKeys() != 8) {
                fail("numKeys should be 8, is: " + bst.numKeys());
            }
            List<Integer> inOrder = bst.getInOrderTraversal();
            
            if(inOrder.get(0) != 10) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(1) != 20) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(2) != 30) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(3) != 40) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(4) != 45) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(5) != 50) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(6) != 60) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(7) != 70) {
                fail("Traersal not working correctly");
            }

        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    @Test
    void testBST_025_remove_one_child() {
        try {
            bst.insert(40, "40");
            bst.insert(20, "40");
            bst.insert(60, "40");
            bst.insert(10, "40");
            bst.insert(30, "30");
            bst.insert(50, "40");
            bst.insert(70, "40");
            bst.insert(45, "40");
            bst.insert(5, "40");
            
            if(!bst.remove(10)) {
                fail("Method should return true");
            }
            if(bst.numKeys() != 8) {
                fail("numKeys should be 8, is: " + bst.numKeys());
            }
            List<Integer> inOrder = bst.getInOrderTraversal();

            if(inOrder.get(0) != 5) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(1) != 20) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(2) != 30) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(3) != 40) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(4) != 45) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(5) != 50) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(6) != 60) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(7) != 70) {
                fail("Traersal not working correctly");
            }

        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    @Test
    void testBST_026_remove_two_children() {
        try {
            bst.insert(40, "40");
            bst.insert(20, "40");
            bst.insert(60, "40");
            bst.insert(10, "40");
            bst.insert(30, "30");
            bst.insert(50, "40");
            bst.insert(70, "40");
            bst.insert(45, "40");
            bst.insert(5, "40");
            
            if(!bst.remove(60)) {
                fail("Method should return true");
            }
            if(bst.numKeys() != 8) {
                fail("numKeys should be 8, is: " + bst.numKeys());
            }
            List<Integer> inOrder = bst.getInOrderTraversal();
            
            if(inOrder.get(0) != 5) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(1) != 10) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(2) != 20) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(3) != 30) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(4) != 40) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(5) != 45) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(6) != 50) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(7) != 70) {
                fail("Traersal not working correctly");
            }

        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    @Test
    void testBST_027_remove_root() {
        try {
            bst.insert(40, "40");
            bst.insert(20, "40");
            bst.insert(60, "40");
            bst.insert(10, "40");
            bst.insert(30, "30");
            bst.insert(50, "40");
            bst.insert(70, "40");
            bst.insert(45, "40");
            bst.insert(5, "40");
            
            if(!bst.remove(40)) {
                fail("Method should return true");
            }
            if(bst.numKeys() != 8) {
                fail("numKeys should be 8, is: " + bst.numKeys());
            }
            List<Integer> inOrder = bst.getInOrderTraversal();
            
            if(inOrder.get(0) != 5) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(1) != 10) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(2) != 20) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(3) != 30) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(4) != 45) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(5) != 50) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(6) != 60) {
                fail("Traersal not working correctly");
            }
            if(inOrder.get(7) != 70) {
                fail("Traersal not working correctly");
            }

        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    @Test
    void testBST_028_insert_null_key() {
        try {
            bst.insert(20, "20");
            bst.insert(10, "10");
            bst.insert(30, "30");
            
            bst.insert(null, "whatever");
            
            fail("Method should throw exception here");

        }catch(IllegalNullKeyException e) {
            //yay
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
    
    @Test
    void testBST_029_insert_dup_key() {
        try {
            bst.insert(20, "20");
            bst.insert(10, "10");
            bst.insert(30, "30");
            
            bst.insert(10, "20");
            
            fail("Method should throw exception here");
    
        }catch(DuplicateKeyException e) {
            //yay
        }catch(Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
    }
}
