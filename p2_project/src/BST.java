import java.util.LinkedList;
import java.util.List;

// DO IMPLEMENT A BINARY SEARCH TREE IN THIS CLASS

/**
 * Defines the operations required of student's BST class.
 *
 * NOTE: There are many methods in this interface 
 * that are required solely to support gray-box testing 
 * of the internal tree structure.  They must be implemented
 * as described to pass all grading tests.
 * 
 * @author Deb Deppeler (deppeler@cs.wisc.edu)
 * @param <K> A Comparable type to be used as a key to an associated value.  
 * @param <V> A value associated with the given key.
 */
public class BST<K extends Comparable<K>, V> implements STADT<K,V> {
    
    private class Node{
        K key; // the key that each node is assigned
        V value;// the value assigned to a key
        Node leftChild;// the left child of the node
        Node rightChild;// the right child of the node
         
        
        //Create a new node with a new Key Value pair
        //sets the children to null
        private Node(K newKey, V newValue){
            key = newKey;
            value = newValue;
            leftChild = null;
            rightChild = null;
        }
        
        private void setRightChild(Node node) {
            rightChild = node;
        }
        
        private void setLeftChild(Node node) {
            leftChild = node;
        }
        
        private V getValue() {
            return value;
        }
        
        private K getKey() {
            return key;
        }
        
        private Node getRightChild() {
            return rightChild;
        }
        
        private Node getLeftChild() {
            return leftChild;
        }
        
    }
    
    private Node root = null;
    private int numKeys;
 
    
    /**
     * Returns the key that is in the root node of this ST.
     * If root is null, returns null.
     * @return key found at root node, or null
     */
    public K getKeyAtRoot() {
        if(root == null) {
            return null;
        }
        return root.getKey();
    }
    
    
    /**
     * Tries to find a node with a key that matches the specified key.
     * If a matching node is found, it returns the returns the key that is in the left child.
     * If the left child of the found node is null, returns null.
     * 
     * @param key A key to search for
     * @return The key that is in the left child of the found key
     * 
     * @throws IllegalNullKeyException if key argument is null
     * @throws KeyNotFoundException if key is not found in this BST
     */
    public K getKeyOfLeftChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
        if(key == null) {//key can't be null
            throw new IllegalNullKeyException("Key is null.");
        }
        //find node and return the child
        Node parent = findNode(key, root);
        if(parent == null) {
            throw new KeyNotFoundException("Key not found in tree");
        }
        
        return parent.getLeftChild().getKey();
    }
    
    
    /**
     * Tries to find a node with a key that matches the specified key.
     * If a matching node is found, it returns the returns the key that is in the right child.
     * If the right child of the found node is null, returns null.
     * 
     * @param key A key to search for
     * @return The key that is in the right child of the found key
     * 
     * @throws IllegalNullKeyException if key is null
     * @throws KeyNotFoundException if key is not found in this BST
     */
    public K getKeyOfRightChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
        if(key == null) {//key can't be null
            throw new IllegalNullKeyException("Key is null.");
        }
        //find node and return child key
        Node parent = findNode(key, root);
        if(parent == null) {
            throw new KeyNotFoundException("Key not found in tree");
        }
        
        return parent.getRightChild().getKey();
    }
    

    /**
     * Returns the height of this BST.
     * H is defined as the number of levels in the tree.
     * 
     * If root is null, return 0
     * If root is a leaf, return 1
     * Else return 1 + max( height(root.left), height(root.right) )
     * 
     * Examples:
     * A BST with no keys, has a height of zero (0).
     * A BST with one key, has a height of one (1).
     * A BST with two keys, has a height of two (2).
     * A BST with three keys, can be balanced with a height of two(2)
     *                        or it may be linear with a height of three (3)
     * ... and so on for tree with other heights
     * 
     * @return the number of levels that contain keys in this BINARY SEARCH TREE
     */
    public int getHeight() {
        //use the helper and the given rules to return the height of the tree
        if(root == null) {
            return 0;
        }else if(isLeaf(root)) {
            return 1;
        }else {
            return 1 + max(height(root.getLeftChild()), height(root.getRightChild()));
        }
    }
    
    
    /**
     * Returns the keys of the data structure in sorted order.
     * In the case of binary search trees, the visit order is: L V R
     * 
     * If the SearchTree is empty, an empty list is returned.
     * 
     * @return List of Keys in-order
     */
    public List<K> getInOrderTraversal() {
        //use helper
        List<K> inOrder = new LinkedList<K>();
        traverseHelp(root, "INORDER", inOrder);
        return inOrder;
    }
    
    /**
     * Returns the keys of the data structure in pre-order traversal order.
     * In the case of binary search trees, the order is: V L R
     * 
     * If the SearchTree is empty, an empty list is returned.
     * 
     * @return List of Keys in pre-order
     */
    public List<K> getPreOrderTraversal() {
        //use helper
        List<K> preOrder = new LinkedList<K>();
        traverseHelp(root, "PREORDER", preOrder);
        return preOrder;
    }

    /**
     * Returns the keys of the data structure in post-order traversal order.
     * In the case of binary search trees, the order is: L R V 
     * 
     * If the SearchTree is empty, an empty list is returned.
     * 
     * @return List of Keys in post-order
     */
    public List<K> getPostOrderTraversal() {
        //use helper
        List<K> postOrder = new LinkedList<K>();
        traverseHelp(root, "POSTORDER", postOrder);
        return postOrder;
    }

    /**
     * Returns the keys of the data structure in level-order traversal order.
     * 
     * The root is first in the list, then the keys found in the next level down,
     * and so on. 
     * 
     * If the SearchTree is empty, an empty list is returned.
     * 
     * @return List of Keys in level-order
     */
    public List<K> getLevelOrderTraversal() {
        //use helper
        List<K> levelOrder = new LinkedList<K>();
        traverseHelp(root, "LEVELORDER", levelOrder);
        return levelOrder;
    }
    
    
    /** 
     * Add the key,value pair to the data structure and increase the number of keys.
     * If key is null, throw IllegalNullKeyException;
     * If key is already in data structure, throw DuplicateKeyException(); 
     * Do not increase the num of keys in the structure, if key,value pair is not added.
     */
    public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
        if(key == null) {//key can't be null
            throw new IllegalNullKeyException("Key is null.");
        }else if(contains(key)) {//can't have doubles
            throw new DuplicateKeyException("Key already in structure.");
        }
        //special case for the root node being empty
        if(root == null) {
            root = new Node(key, value);
            numKeys = numKeys + 1;
            return;
        }
        //otherwise call the helper
        root = insertHelp(key, value, root);
        
        return;
    }
    
    /** 
     * If key is found, remove the key,value pair from the data structure 
     * and decrease num keys, and return true.
     * If key is not found, do not decrease the number of keys in the data structure, return false.
     * If key is null, throw IllegalNullKeyException
     */
    public boolean remove(K key) throws IllegalNullKeyException {
        if(key == null) {//key can't be null
            throw new IllegalNullKeyException("Key is null.");
        }
        if(!contains(key)) {//key must be in structure
            return false;
        }
        //use helper
        root = removeHelp(key, root);
      
        return true;
    }
  

    /**
     * Returns the value associated with the specified key.
     *
     * Does not remove key or decrease number of keys
     * If key is null, throw IllegalNullKeyException 
     * If key is not found, throw KeyNotFoundException().
     */
    public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
        if(key == null) {//key can't be null
            throw new IllegalNullKeyException("Key is null.");
        }
        //find the node and return it's value
        Node node = findNode(key, root);
        if(node == null) {
            throw new KeyNotFoundException("Key not found in tree.");
        }else {
            return node.getValue();
        }
    }

    /** 
     * Returns true if the key is in the data structure
     * If key is null, throw IllegalNullKeyException 
     * Returns false if key is not null and is not present 
     */
    public boolean contains(K key) throws IllegalNullKeyException { 
        if(key == null) {//key can't be null
            throw new IllegalNullKeyException("Key is null.");
        }
        //if node is in structure node won't be null
        Node node = findNode(key, root);
        if(node == null) {
            return false;
        }else {
            return true;
        }
    }

    /**
     *  Returns the number of key,value pairs in the data structure
     */
    public int numKeys() {
        return numKeys;
    }
    
    
    /**
     * Print the tree. 
     *
     * For our testing purposes of your print method: 
     * all keys that we insert in the tree will have 
     * a string length of exactly 2 characters.
     * example: numbers 10-99, or strings aa - zz, or AA to ZZ
     *
     * This makes it easier for you to not worry about spacing issues.
     *
     * You can display a binary search in any of a variety of ways, 
     * but we must see a tree that we can identify left and right children 
     * of each node
     *
     * For example: 
     
           30
           /\
          /  \
         20  40
         /   /\
        /   /  \
       10  35  50 

       Look from bottom to top. Inorder traversal of above tree (10,20,30,35,40,50)
       
       Or, you can display a tree of this kind.

       |       |-------50
       |-------40
       |       |-------35
       30
       |-------20
       |       |-------10
       
       Or, you can come up with your own orientation pattern, like this.

       10                 
               20
                       30
       35                
               40
       50                  

       The connecting lines are not required if we can interpret your tree.

     */
    public void print() {
        if(root == null) {//if there's no nodes there's nothing to print
            System.out.println("No tree currently");
            return;
        }
        //get the in order traversal list
        List<K> inOrder = new LinkedList<K>();
        inOrder = getInOrderTraversal();

        //iterate once for each key
        for(int i=0; i < numKeys; ++i) {
            Node curr = findNode(inOrder.get(i), root);
            int h = height(curr);
            for(int j=1; j < h; ++j) {//before the key print the spaces needed for the design
                System.out.print("       ");
            }
            System.out.println(inOrder.get(i));//print th ekey
                
        }
        //print the buffer
        System.out.println();
        System.out.println();
    }
    
    
    
    /**
     *  Method used to find and return a node matching the given key in the BST
     *  
     *  @param key, the key to look for
     *  @param curr, the node to check
     *  @return null if key isn't found, otherwise the node that matches the given key
     */
    private Node findNode(K key, Node curr) {
        if(curr == null) {//if we hit a null node the key isn;t in the tree
            return null;
        }
        int compare = key.compareTo(curr.getKey());//get the comparison
        
        
        if(compare > 0) {//look right
            curr = findNode(key, curr.getRightChild());
            return curr;
        }else if(compare < 0) {//look left
            curr = findNode(key, curr.getLeftChild());
            return curr;
        }else {//the node has been found
            return curr;
        }

    }
    
    /**
     *  Method that tests weather a given node is a leaf node or not
     *  
     *  @param curr, the node to test 
     *  @return true if leaf, false otherwise
     */
    private boolean isLeaf(Node curr) {
        if(curr.getLeftChild() == null && curr.getRightChild() == null) {//if both children are null it is a leaf
            return true;
        }else {
            return false;
        }
    }
    
    /**
     *  Recursive method that finds the height of a given node
     *  
     *  @param curr, the node to find the height of
     *  @return the height of the given node
     */
    private int height(Node curr) {
        if(curr == null) {//if there is no data the height is 0
            return 0;
        }else if(isLeaf(curr)) {// a leaf node has a height of one
            return 1;
        }else {//any other node's height is 1 + the height of its longest tree
            return 1 + max(height(curr.getLeftChild()), height(curr.getRightChild()));
        }
    }
    
    /**
     *  Method to make height simpler code, finds the max of two given numbers
     *  
     *  @param first, first number to look at
     *  @param second, second number to look at
     *  @return returns the greater of the two numbers
     */
    private int max(int first, int second) {
        if(first > second) {//if first is greater return it
            return first;
        }else {//else return second
            return second;
        }
    }
    
    /**
     *  Recursive helper method for inserting a new key into the tree
     *  
     *  @param key, the key to add to the tree
     *  @param value, the value associated with the key
     *  @param curr, the node currently being looked at
     *  @return the modified root
     */
    private Node insertHelp(K key, V value, Node curr) throws DuplicateKeyException{
        if (curr == null) {//if the null node is found add the new node
            Node newNode = new Node(key, value);
            curr = newNode;
            numKeys = numKeys + 1;
            return curr;
        }

        //find the comparision between the keys
        int difference = key.compareTo(curr.getKey());

        if (difference == 0) {//if the difference is zero something has gone wrong
            throw new DuplicateKeyException("Key already in structure.");
        } else if (difference > 0) {//if greater than search right subtree
            curr.setRightChild(insertHelp(key, value, curr.getRightChild()));
            return curr;
        } else if (difference < 0) {//otherwise search left subtree
            curr.setLeftChild(insertHelp(key, value, curr.getLeftChild()));
            return curr;
        }
        return curr;//shouldn't get here, I just like using else if instead of else
    }
    
    /**
     *  Recursive helper method for removing a node from the tree
     *  
     *  @param key, the key to remove
     *  @param curr, the node currently being worked on
     *  @return the modified root 
     */
    private Node removeHelp(K key, Node curr) {
        
        //find the comparison between the keys
        int difference = key.compareTo(curr.getKey());
        
        if(difference > 0) {//if greater continue right
            curr.setRightChild(removeHelp(key, curr.getRightChild()));
            return curr;
        }else if(difference < 0) {//if less continue left
            curr.setLeftChild(removeHelp(key, curr.getLeftChild()));
            return curr;
        }else {//Found the node to remove
           if(isLeaf(curr)) {//no kids just remove leaf
               numKeys = numKeys - 1;
               return null;
           }else if(curr.getLeftChild() == null || curr.getRightChild() == null) {//one kid, return the kid
               if(curr.getLeftChild() == null) {
                   numKeys = numKeys - 1;
                   return curr.getRightChild();
               }else if(curr.getRightChild() == null) {
                   numKeys = numKeys - 1;
                   return curr.getLeftChild();
               }
           }else {//has two kids, restructure needed, right then left
               Node helper = curr.getRightChild();//start down the correct path
               while(helper.getLeftChild() != null) {
                   helper = helper.getLeftChild();
               }
               
               curr = removeHelp(helper.getKey(), curr);//removes the leaf node from the tree
               helper.setLeftChild(curr.getLeftChild());
               helper.setRightChild(curr.getRightChild());
               return helper;
               
           }
            
        }
        return null;//shouldn't ever get here...
    }
    
    /**
     *  Helper method for the level order traversal, goes level by level adding 
     *  keys to the list
     *   
     *  @param curr, the node being looked at
     *  @param level, the level currently being worked on
     *  @param list, list to add keys to 
     */
    private void levelHelp(Node curr, int level, List<K> list) {
        if(curr == null) {//return when a null node is hit
            return;
        }else if(level == 1) {//if the right level add to the structure
            list.add(curr.getKey());
        }else {//go left then right recursively 
            levelHelp(curr.getLeftChild(), level - 1, list);
            levelHelp(curr.getRightChild(), level - 1, list);
        }
    }
    
    /**
     *  Recursive method for the four traversals
     *  
     *  @param curr, the current node being looked at
     *  @param type, the type of traversal
     *  @param list, the list to add them to 
     *  
     */
    private void traverseHelp(Node curr, String type, List<K> list) {
        if(curr != null) {//stop when you reach a null node
            if(type.equals("INORDER")) {//Left, right, root
                
                traverseHelp(curr.getLeftChild(), type, list);
                
                list.add(curr.getKey());
                
                traverseHelp(curr.getRightChild(), type, list);
            
            }else if(type.equals("PREORDER")) {//rot, left, right
                
                list.add(curr.getKey());
                
                traverseHelp(curr.getLeftChild(), type, list);
                
                traverseHelp(curr.getRightChild(), type, list);
                    
                
            }else if(type.equals("POSTORDER")) {//left, right, root
                
                traverseHelp(curr.getLeftChild(), type, list);
                
                traverseHelp(curr.getRightChild(), type, list);
                
                list.add(curr.getKey());
            }else if(type.equals("LEVELORDER")) {//level by level
                int height = getHeight();
                
                //use helper method to add each level right
                for(int i=1; i <= height; ++i) {
                    levelHelp(root, i, list);
                }
            }else {
                System.out.println("Something went wrong bud");
            }
        }
    }
} // copyrighted material, students do not have permission to post on public sites




//  deppeler@cs.wisc.edu
