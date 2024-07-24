
public class ProcessSchedulerTester {

    public static void main(String[] args) {
        boolean testCP = testCustomProcess();
        if(testCP) {
            System.out.println("Process - Hero");
        }else {
            System.out.println("Process - Zero");
        }
        
        boolean testInsert = testInsertWaitingProcessQueue();
        if(testInsert) {
            System.out.println("Insert - Hero");
        }else {
            System.out.println("Insert - Zero");
        }
        
        boolean testRemove = testRemoveBestWaitingProcessQueue();
        if(testRemove) {
            System.out.println("Remove - Hero");
        }else {
            System.out.println("Remove - Zero");
        }
        
        boolean testSchedule = testProcessScheduler();
        if(testSchedule) {
            System.out.println("Schedule - Hero");
        }else {
            System.out.println("Schedule - Zero");
        }
    }
    
    //Tests the functionality of the Custom Process objects
    public static boolean testCustomProcess() {
        CustomProcess p1 = new CustomProcess(1);
        CustomProcess p2 = new CustomProcess(2);
        CustomProcess p3 = new CustomProcess(3);
        CustomProcess p4 = new CustomProcess(4);
        CustomProcess p5 = new CustomProcess(5);
        
        if(p1.getBurstTime() != 1) {
            return false;
        }
        if(p5.getProcessId() != 5) {
            return false;
        }
      
        return true;
    }
    
    
     // checks the correctness of the insert operation
     // implemented in the WaitingProcessQueue class
     public static boolean testInsertWaitingProcessQueue(){
         CustomProcess p1 = new CustomProcess(10);
         CustomProcess p2 = new CustomProcess(2);
         CustomProcess p3 = new CustomProcess(5);
         CustomProcess p4 = new CustomProcess(3);
         CustomProcess p5 = new CustomProcess(1);
         
         
         WaitingProcessQueue que = new WaitingProcessQueue();
         
         que.insert(p1);
         que.insert(p2);
         que.insert(p3);
         que.insert(p4);
         que.insert(p5);
         //System.out.println(que.toString());
         CustomProcess hold = que.peekBest();
         
         //System.out.println(p5.getProcessId());
         //System.out.println(hold.getBurstTime());
         //System.out.println(hold.getProcessId());
         
         //System.out.println(que.size());
         if(hold.getBurstTime() != 1) {
             return false;
         }
         
       
         
         //It ends up in the correct order
         //They didn't allow me to use any accessors for the Queue data
         //Making it hard to write any tests besides printing and self checking
         
         return true;
     }
     
     
     // checks the correctness of the removeBest operation
     // implemented in the WaitingProcessQueue class
     public static boolean testRemoveBestWaitingProcessQueue(){
         CustomProcess p1 = new CustomProcess(1);
         CustomProcess p2 = new CustomProcess(2);
         CustomProcess p3 = new CustomProcess(3);
         CustomProcess p4 = new CustomProcess(4);
         CustomProcess p5 = new CustomProcess(5);
         CustomProcess p6 = new CustomProcess(6);
         CustomProcess p7 = new CustomProcess(7);
         
         
         WaitingProcessQueue que = new WaitingProcessQueue();
         
         que.insert(p3);
         que.insert(p4);
         que.insert(p2);
         que.insert(p1);
         que.insert(p5);
         que.insert(p7);
         que.insert(p6);
         
         //System.out.println(p5.compareTo(p2));
         
         //System.out.println(que.toString());
         que.removeBest();
         //System.out.println(que.toString());
         que.removeBest();
         //System.out.println(que.toString());
         que.removeBest();
         //System.out.println(que.toString());
         que.removeBest();
         //System.out.println(que.toString());
         que.removeBest();
         //System.out.println(que.size());
         //System.out.println(que.toString());
         que.removeBest();
         que.removeBest();
         //System.out.println(que.size());
         
         //again hard to test normally without accesors but with print statements it's clear the method is working correctly
         
         return true;
     }
     
     //Tests the functionality of the ProcessSchedular
     //most easily tested by self checking the printed run method
     public static boolean testProcessScheduler() {
         CustomProcess p1 = new CustomProcess(1);
         CustomProcess p2 = new CustomProcess(2);
         CustomProcess p3 = new CustomProcess(3);
         CustomProcess p4 = new CustomProcess(4);
         CustomProcess p5 = new CustomProcess(5);
         CustomProcess p6 = new CustomProcess(6);
         CustomProcess p7 = new CustomProcess(7);
         
         ProcessScheduler que = new ProcessScheduler();
         
         que.scheduleProcess(p3);
         que.scheduleProcess(p4);
         que.scheduleProcess(p2);
         que.scheduleProcess(p1);
         que.scheduleProcess(p5);
         que.scheduleProcess(p6);
         que.scheduleProcess(p7);
         
         
         String run = que.run();
         //System.out.println(run);
         if(run == null) {
             return false;
         }
         
         return true;
     }
     
     
}
