

public class CompareDS {
    private static DataStructureADT<String, String> ds;

    public static void main(String[] args) {
        long startTime;
        long endTime;
        long myTime;
        long taTime;


        System.out.println("CompareDS Compares work time for: DS_My and DS_Brian");
        System.out.println(
            "Description: CompareDS will run all tests on both programs for various sized inputs");
        System.out.println("----------------------------------------------------");        

        
        //100 values
        startTime = System.nanoTime();
        runMy(100);
        endTime = System.nanoTime();
        myTime = endTime - startTime;
        
        startTime = System.nanoTime();
        runTA(100);
        endTime = System.nanoTime();
        taTime = endTime - startTime;
        
        System.out.println("DS_My is doing work for 100 values");
        System.out.println("It took " + myTime + "ns to process 100 values");
        System.out.println("DS_Brian is doing work for 100 values");
        System.out.println("It took " + taTime + "ns to process 100 values");
        System.out.println("----------------------------------------------------"); 
        
        
        //1,000 values
        startTime = System.nanoTime();
        runMy(1000);
        endTime = System.nanoTime();
        myTime = endTime - startTime;
        
        startTime = System.nanoTime();
        runTA(1000);
        endTime = System.nanoTime();
        taTime = endTime - startTime;
        
        System.out.println("DS_My is doing work for 1000 values");
        System.out.println("It took " + myTime + "ns to process 1000 values");
        System.out.println("DS_Brian is doing work for 1000 values");
        System.out.println("It took " + taTime + "ns to process 1000 values");
        System.out.println("----------------------------------------------------");
        
        
        //10,000 values
        startTime = System.nanoTime();
        runMy(10000);
        endTime = System.nanoTime();
        myTime = endTime - startTime;
        
        startTime = System.nanoTime();
        runTA(10000);
        endTime = System.nanoTime();
        taTime = endTime - startTime;
        
        System.out.println("DS_My is doing work for 10000 values");
        System.out.println("It took " + myTime + "ns to process 10000 values");
        System.out.println("DS_Brian is doing work for 10000 values");
        System.out.println("It took " + taTime + "ns to process 10000 values");
        System.out.println("----------------------------------------------------");
        
        
        //100,000 values
        startTime = System.nanoTime();
        runMy(100000);
        endTime = System.nanoTime();
        myTime = endTime - startTime;
        
        startTime = System.nanoTime();
        runTA(100000);
        endTime = System.nanoTime();
        taTime = endTime - startTime;
        
        System.out.println("DS_My is doing work for 100000 values");
        System.out.println("It took " + myTime + "ns to process 100000 values");
        System.out.println("DS_Brian is doing work for 100000 values");
        System.out.println("It took " + taTime + "ns to process 100000 values");
        System.out.println("----------------------------------------------------");
        
        
        //1,000,000 values COMPUTER COULDN'T RUN 1,000,000 AS LIMIT
        //startTime = System.nanoTime();
        //runMy(1000000);
        //endTime = System.nanoTime();
        //myTime = endTime - startTime;
        
        //startTime = System.nanoTime();
        //runTA(1000000);
        //endTime = System.nanoTime();
        //taTime = endTime - startTime;
        
        //System.out.println("DS_My is doing work for 1000000 values");
        //System.out.println("It took " + myTime + "ns to process 1000000 values");
        //System.out.println("DS_Brian is doing work for 1000000 values");
        //System.out.println("It took " + taTime + "ns to process 1000000 values");
        //System.out.println("----------------------------------------------------");

    }


    private static boolean runMy(int limit) {
        try {
            ds = new DS_My();
            for(int i=0; i < limit; ++i) {//add a bunch
                ds.insert(Integer.toString(i), "My Constant");
            }
            for(int i=0; i < limit; ++i) {//see if you can find them all
                if(!ds.contains(Integer.toString(i))) {
                    System.out.println("My-Lost data while inserting, key=" + i);
                    return false;
                }
            }
            for(int i=0; i < limit; ++i) {//remove them all
                if(!ds.remove(Integer.toString(i))) {
                    System.out.println("My-Failed to find key to remove key=" + i);
                    return false;
                }
            }
            if(ds.size() != 0) {
                System.out.println("My-Size should be zero after operation, size=" + ds.size());
                return false;
            }
            return true;
            
        
        }catch(NullPointerException e) {
            System.out.println("My-Program should never throw NullPointerException");
            return false;
        }catch(IllegalArgumentException e) {
            System.out.println("My-Program should not throw IllegalArgumentException in this case");
            return false;
        }catch(RuntimeException e){
            System.out.println("My-Program should not throw a RuntimeException in this case");
            return false;
        }    
    }

    @SuppressWarnings("unchecked")
    private static boolean runTA(int limit) {
        try {
            ds = new DS_Brian();
            for(int i=0; i < limit; ++i) {//add a bunch
                ds.insert(Integer.toString(i), "TA Constant");
            }
            for(int i=0; i < limit; ++i) {//see if you can find them all
                if(!ds.contains(Integer.toString(i))) {
                    System.out.println("TA-Lost data while inserting, key=" + i);
                    return false;
                }
            }
            for(int i=0; i < limit; ++i) {//remove them all
                if(!ds.remove(Integer.toString(i))) {
                    System.out.println("TA-Failed to find key to remove key=" + i);
                    return false;
                }
            }
            if(ds.size() != 0) {
                System.out.println("TA-Size should be zero after operation, size=" + ds.size());
                return false;
            }
            return true;
            
        
        }catch(NullPointerException e) {
            System.out.println("TA-Program should never throw NullPointerException");
            return false;
        }catch(IllegalArgumentException e) {
            System.out.println("TA-Program should not throw IllegalArgumentException in this case");
            return false;
        }catch(RuntimeException e){
            System.out.println("TA-Program should not throw a RuntimeException in this case");
            return false;
        }    
    }
}
