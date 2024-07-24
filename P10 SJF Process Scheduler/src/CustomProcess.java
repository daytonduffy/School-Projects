
public class CustomProcess implements Comparable<CustomProcess>{
    private static int nextProcessId = 1; // stores the id to be assigned to the next process to be created
    private final int PROCESS_ID; // unique identifier for this process
    private int burstTime; // time required by this process for CPU execution


    /**
     * Creates a new instance of CustomProcess with the given burstTime.
     * 
     * @param burstTime The given burst time
     * @throws java.util.IllegalArgumentException with a descriptive error message if burst time isn't positive
     *                                     
     */
    public CustomProcess(int burstTime) { 
        if(burstTime <= 0) {
            throw new IllegalArgumentException("Invalid burstTime: " + burstTime);
        }
        this.burstTime = burstTime;
        PROCESS_ID = nextProcessId;
        nextProcessId = nextProcessId + 1;
    }

    /**
    * Returns a String representation of this CustomProcess
    * @return a string representation of this CustomProcess
    */
    public String toString() {
    return "p" + this.PROCESS_ID + "(" + this.burstTime + ")";
    }

    /**
     * Returns a value to compare two CustomProcess objects
     * @param other the second object to compare
     * @return an int representation of this comparison
     */
    public int compareTo(CustomProcess other) {
        int burstTime1 = burstTime; //p1
        int burstTime2 = other.getBurstTime();//p2
        
        //Special case for when burstTimes are equal
        if(burstTime1 == burstTime2) {
            if(PROCESS_ID < other.getProcessId()) {//p1 smaller
                return -1;
            }else if(PROCESS_ID > other.getProcessId()) {//p1 larger
                return 1;
            }else {//Truly equal 
                return 0;
            }
        }
        
        return burstTime1 - burstTime2;
        //returns a positive value if p1>p2 and a negative value otherwise
    }
    
    /**
     * Returns the unique process ID
     * @return the unique process ID
     */
    public int getProcessId() {
        return PROCESS_ID;
    }
    /**
     * Returns burstTime
     * @return burstTime
     */
    public int getBurstTime() {
        return burstTime;
    }
}
