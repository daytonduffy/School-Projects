import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class CamperBST {
    public CampTreeNode root; // root of the BST
    private int size;
    private LinkedList<Camper> traversedLList;//LinkedList to maintain current traversal


    // Creates an empty BST
    public CamperBST() {
        root = null;
        size = 0;
    }

    // returns the current size of the CamperBST
    public int size() {
        return size;
    }

    // returns true if the tree is empty, false otherwise
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }

    }


    // starts tree insertion by calling insertHelp() on the root and
    // assigning root to be the subtree returned by that method
    public void insert(Camper newCamper) {
        root = insertHelp(root, newCamper);
    }

    /** Recursive helper method to insert.
    * @param current, The "root" of the subtree we are inserting into,
    * ie the node we are currently at.
    * @param newCamper, the camper to be inserted into the tree
    * @return the root of the modified subtree we inserted into
    */
    private CampTreeNode insertHelp(CampTreeNode current, Camper newCamper) {
        if (current == null) {
            CampTreeNode newCamperNode = new CampTreeNode();
            newCamperNode.setData(newCamper);// Node useing newCamper Data
            current = newCamperNode;
            size = size + 1;
            return current;
        }

        int difference = newCamper.compareTo(current.getData());

        if (difference == 0) {
            return current;
        } else if (difference > 0) {
            current.setRightNode(insertHelp(current.getRightNode(), newCamper));
            return current;
        } else if (difference < 0) {
            current.setLeftNode(insertHelp(current.getLeftNode(), newCamper));
            return current;
        }
        return current;
    }


    // Prints the contents of this tree in alphabetical order
    // based on the string "lastName, firstName"
    public void print() {
        printHelp(root);
    }

    private void printHelp(CampTreeNode current) {
        if (current == null) {
            return;
        }
        printHelp(current.getLeftNode());
        System.out.println(current.getData());
        printHelp(current.getRightNode());
    }

    /** Deletes a Camper into the binary search tree if it exists.
    * @param key, the camper to be deleted from the tree
    * @throws NoSuchElementException if it is thrown by deleteHelp
    */
    public void delete(Camper key) throws NoSuchElementException {
        root = deleteHelp(root, key);
    }

    /** Recursive helper method to delete.
    * @param current, The "root" of the subtree we are deleting from,
    * ie the node we are currently at.
    * @param key, the camper to be deleted from the tree
    * @return the root of the modified subtree we deleted from
    * @throws NoSuchElementException if the camper is not in the tree
    */
    private CampTreeNode deleteHelp(CampTreeNode current, Camper key) {
        if(current == null) {
            throw new NoSuchElementException("That camper is not enrolled.");
        }
        
        int difference = key.compareTo(current.getData());

        if(difference == 0) {//matching key

            int kids = 2;//Number of kids the node has
            if(current.getLeftNode() == null) {

                kids = kids-1;
            }
            if(current.getRightNode() == null) {
               // System.out.println("here right");

                kids = kids-1;
            }
                        
            if(kids == 0) {//Removes single leaf
                size = size -1;
                return null;
            }else if(kids == 1) {//removes branch with a single kid
                size = size-1;
                if(current.getRightNode() != null) {
                    return current.getRightNode();
                }else if(current.getLeftNode() != null) {
                    return current.getLeftNode();
                }
            }else if(kids == 2) {
                CampTreeNode hold = new CampTreeNode();//exact copy of current
                Camper camper = new Camper(current.getData().getFirstName(), current.getData().getLastName(), current.getData().getAge());
                hold.setData(camper);
                hold.setLeftNode(new CampTreeNode());
                hold.getLeftNode().setData(current.getLeftNode().getData());
                hold.getLeftNode().setLeftNode(current.getLeftNode().getLeftNode());
                hold.getLeftNode().setRightNode(current.getLeftNode().getRightNode());
                hold.setRightNode(new CampTreeNode());
                hold.getRightNode().setData(current.getRightNode().getData());
                hold.getRightNode().setLeftNode(current.getRightNode().getLeftNode());
                hold.getRightNode().setRightNode(current.getRightNode().getRightNode());
                //Start down Right path
                
                hold = hold.getRightNode();
                
                while(hold.getLeftNode() != null) {//Find leftmost child
                    hold = hold.getLeftNode();//hold = leftmost child
                }

                //size not changed because it's handled here
                current = deleteHelp(current, hold.getData());
                hold.setLeftNode(current.getLeftNode());
                hold.setRightNode(current.getRightNode());
                
                //return hold the new root of this subtree
                return hold;
            }

        }else if(difference > 0) {//right tree
            current.setRightNode(deleteHelp(current.getRightNode(), key));
            return current;
        }else if(difference < 0) {//left tree
            current.setLeftNode(deleteHelp(current.getLeftNode(), key));
            return current;
        }
        return current;//can't be reached unless difference just doesn't exist
    }


    
    //returns an iterator of camper in the correct order as designated
    public Iterator<Camper> traverse(String order){
    //first time traversing need to initialize LinkedList
        if(traversedLList==null){
            traversedLList = new LinkedList<Camper>();
        }
        else{
            //clear the list to start over for a new traversal
            traversedLList.clear();
        }
        traverseHelp(root, order);
        return traversedLList.listIterator();
    }
    
    /** Recursive helper method to traverse. Will take the current CampTreeNode's data
    *and add it to traversedLList based on the given order. Then continue to recurse on
    *the correct subtree.
    * @param current, the root of the current subtree we are traversing
    * @param order, the type of traversal to perform
    */
    //IN THIS WE NEED TO STORE THINGS IN THE LLIST IN THE RIGHT ORDER FOR GIVEN
    private void traverseHelp (CampTreeNode current, String order) {
        if(current != null) {
            if(order.equals("INORDER")) {
                
                traverseHelp(current.getLeftNode(), order);
                
                traversedLList.add(current.getData());
                
                traverseHelp(current.getRightNode(), order);
            
            }else if(order.equals("PREORDER")) {
                
                traversedLList.add(current.getData());
                
                traverseHelp(current.getLeftNode(), order);
                
                traverseHelp(current.getRightNode(), order);
                    
                
            }else if(order.equals("POSTORDER")) {
                
                traverseHelp(current.getLeftNode(), order);
                
                traverseHelp(current.getRightNode(), order);
                
                traversedLList.add(current.getData());
                
            }else {
                System.out.println("Something went wrong bud");
            }
        }
    }
    
    
  
}
