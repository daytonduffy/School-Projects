

public class CompareDS {
    
    public static void main(String[] args) {
        long startTime;
        long endTime;
        long bstTime;
        long rbtTime;


        System.out.println("CompareDS Compares work time for: BST and RBT");
        System.out.println(
            "Description: CompareDS will run all tests on both programs for various sized inputs");
        System.out.println("----------------------------------------------------"); 
        
        
        //100 values
        startTime = System.nanoTime();
        runBST(100);
        endTime = System.nanoTime();
        bstTime = endTime - startTime;
        
        startTime = System.nanoTime();
        runRBT(100);
        endTime = System.nanoTime();
        rbtTime = endTime - startTime;
        
        System.out.println("BST is doing work for 100 values");
        System.out.println("It took " + bstTime + "ns to process 100 values");
        System.out.println("RBT is doing work for 100 values");
        System.out.println("It took " + rbtTime + "ns to process 100 values");
        System.out.println("----------------------------------------------------"); 
        
        
        //1000 values
        startTime = System.nanoTime();
        runBST(1000);
        endTime = System.nanoTime();
        bstTime = endTime - startTime;
        
        startTime = System.nanoTime();
        runRBT(1000);
        endTime = System.nanoTime();
        rbtTime = endTime - startTime;
        
        System.out.println("BST is doing work for 1000 values");
        System.out.println("It took " + bstTime + "ns to process 1000 values");
        System.out.println("RBT is doing work for 1000 values");
        System.out.println("It took " + rbtTime + "ns to process 1000 values");
        System.out.println("----------------------------------------------------"); 
        
        
        
        //10000 values
        startTime = System.nanoTime();
        runBST(10000);
        endTime = System.nanoTime();
        bstTime = endTime - startTime;
        
        startTime = System.nanoTime();
        runRBT(10000);
        endTime = System.nanoTime();
        rbtTime = endTime - startTime;
        
        System.out.println("BST is doing work for 10000 values");
        System.out.println("It took " + bstTime + "ns to process 10000 values");
        System.out.println("RBT is doing work for 10000 values");
        System.out.println("It took " + rbtTime + "ns to process 10000 values");
        System.out.println("----------------------------------------------------"); 
    }
    
    //tests worst case BST
    private static void runBST(int limit) {
        try {
            BST<Integer, String> bst = new BST<Integer, String>();

            for(int i=0; i < limit; ++i) {//add a bunch
                bst.insert(i, "NOTTESTED");
            }
            
            for(int i=0; i < limit; ++i) {//see if you can find them all
                if(!bst.contains(i)) {
                    System.out.println("BST lost data while inserting, key=" + i);
                    return;
                }
            }
            
            for(int i=0; i < limit; ++i) {//use get just because
                if(!bst.get(i).equals("NOTTESTED")) {
                    System.out.println("BST couldn't get, key=" + i);
                    return;
                }
            }

            if(bst.numKeys() != limit) {
                System.out.println("numKeys should be limit after operation, numKeys=" + bst.numKeys());
                return;
            }
            return;
            
        
        }catch(Exception e) {
            System.out.println("No exceptions should be thrown");
            return;
        }
    }
    
    //should be better than worst case BST thanks to rebalancing
    private static void runRBT(int limit) {
        try {
            RBT<Integer, String> rbt = new RBT<Integer, String>();

            for(int i=0; i < limit; ++i) {//add a bunch
                rbt.insert(i, "NOTTESTED");
            }
            
            for(int i=0; i < limit; ++i) {//see if you can find them all
                if(!rbt.contains(i)) {
                    System.out.println("rbt lost data while inserting, key=" + i);
                    return;
                }
            }
            
            for(int i=0; i < limit; ++i) {//use get just because
                if(!rbt.get(i).equals("NOTTESTED")) {
                    System.out.println("rbt couldn't get, key=" + i);
                    return;
                }
            }

            if(rbt.numKeys() != limit) {
                System.out.println("numKeys should be limit after operation, numKeys=" + rbt.numKeys());
                return;
            }
            return;
            
        
        }catch(Exception e) {
            System.out.println("No exceptions should be thrown");
            return;
        }
    }
    
}
