import java.util.NoSuchElementException;
import java.util.Scanner;

public class ProcessScheduler {
    private int currentTime; // stores the current time after the last run
    private int numProcessesRun; // stores the number of processes run so far
    private WaitingProcessQueue queue; // this processing unit's queue

    
    public void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        boolean quit = false;
        String userIn = new String();
        
        System.out.println("========== Welcome to the SJF Process Scheduler App ==========");
        while(!quit) {
            System.out.println();
            System.out.println("Enter command:");
            System.out.println("[schedule <burstTime>] or [s <burstTime>]");
            System.out.println("[run] or [r]");
            System.out.println("[quit] or [q]\n");
        
            userIn = scnr.next();
            
            
            if(userIn.equals("schedule") || userIn.equals("s")) {
                try {
                    if(scnr.hasNextInt()) {
                        CustomProcess newSched = new CustomProcess(Integer.parseInt(scnr.next()));
                        scheduleProcess(newSched);
                        System.out.println("Process ID " + newSched.getProcessId() + 
                            " scheduled. Burst Time = " + newSched.getBurstTime());
                    }else {
                        System.out.println("WARNING: burst time MUST be an integer!");
                    }
                }catch(IllegalArgumentException e) {
                    System.out.println(e);
                }
            }else if(userIn.equals("run") || userIn.equals("r")) {
                System.out.println(run());
            }else if(userIn.equals("quit") || userIn.equals("q")) {
                System.out.println(numProcessesRun + " processes run in " + currentTime + " units of time!\n" +
                    "Thank you for using our scheduler!\n" + "Goodbye!\n");
                quit = true;
            }else {
                System.out.println("WARNING: Please enter a valid command!");
            }
            
        } 
        scnr.close();
    }
    
    
    
    /**
     * Creates a new instance of the Process Scheduler
     * Initializes queue to a new instance of WaitingProcessQueue
     * with initial time and number of processes zero
     */
    public ProcessScheduler() {
        queue = new WaitingProcessQueue();
        numProcessesRun = 0;
        currentTime = 0;
    }
    
    /**
     * This method inserts the given process in the WaitingProcessQueue queue.
     * 
     * @param process the CustomProcess to add to the queue
     */
    public void scheduleProcess(CustomProcess process) {
        queue.insert(process);
    }

    
    /**
     * This method inserts the given process in the WaitingProcessQueue queue.
     * 
     * @throws NoSuchElementException
     */
    public String run() throws NoSuchElementException{
        String log = new String();
        if(queue.size() == 1) {
            log = "Starting " + queue.size() + " process\n\n";
        }else {
            log = "Starting " + queue.size() + " processes\n\n";
        }
        
        while(!queue.isEmpty()) {
            
            int processID = queue.peekBest().getProcessId();
            
            log = log + "Time " + currentTime + " : Process ID " + processID + 
                " Starting.\n";
            
            currentTime = currentTime + queue.peekBest().getBurstTime();
            
            log = log + "Time " + currentTime + " : Process ID " + processID + 
                " Completed.\n";
            
            queue.removeBest();
            numProcessesRun = numProcessesRun +1;
            
        }
        
        log = log + "\nTime " + currentTime + ": All scheduled processes completed.\n";
        
        return log;
    }
}
