import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class AsciiArtTester {
    public static void main(String[] args) {
        boolean test1 = runAsciiArtTestSuite();
        if(test1) {
            System.out.println("Hero");
        }else {
            System.out.println("Zero");
        }
        
        
    }
    
    //method which tests the accuracy of DrawingStack's push, pop, and peek methods
    public static boolean testStackPushPeek() {
        try {
            DrawingStack stack1 = new DrawingStack();
            if(!stack1.isEmpty()) {
                return false;
            }
            DrawingChange image1 = new DrawingChange(0,0,'a','b');
            stack1.push(image1);
            DrawingChange hold = stack1.peek();
            if(!hold.equals(image1)) {
                return false;
            }
            
            DrawingChange image2 = new DrawingChange(0,0,'a','b');
            DrawingChange image3 = new DrawingChange(1,1,'c','d');
            DrawingChange image4 = new DrawingChange(2,2,'e','f');
            DrawingStack stack2 = new DrawingStack(image2);
            stack2.push(image3);
            stack2.push(image4);
            DrawingChange hold2 = stack2.peek();
            if(!hold2.equals(image4)) {
                return false;
            }
            
            
            DrawingChange holder = stack2.pop();
            if(!holder.equals(image4)) {
                return false;
            }
            stack2.pop();
            stack2.pop();
            if(stack2.size() != 0) {
                return false;
            }
            
            
            return true;
        }catch(NoSuchMethodError e){
            return false;
        }
    }
    
    
    public static boolean runAsciiArtTestSuite() { 
        try {
            boolean test1 = testStackPushPeek();
            if(!test1) {
                System.out.println("testStackPushPeek failed");
                return false;
            }
            boolean test2 = testCanvas();
            if(!test2) {
                System.out.println("testCanvas failed");
                return false;
            }
            boolean test3 = testIterate();
            if(!test3) {
                System.out.println("testIterate failed");
                return false;
            }
            
            
            return true;
        }catch(NoSuchMethodError e){
            return false;
        }catch(IllegalArgumentException e) {
            return false;
        }catch(EmptyStackException e) {
            return false;
        }
   }
    
    
    public static boolean testCanvas() {
        Canvas canvas = new Canvas(4,4);
        canvas.draw(0, 0, '1');
        canvas.draw(0, 3, '2');
        canvas.draw(0, 2, '3');
        canvas.draw(0, 3, '4');
        canvas.draw(1, 0, '5');
        
        String please = canvas.toString();
        char[][] array = canvas.getDrawingArray();
        if(array[0][0] != '1') {
            return false;
        }
        
        return true;
    }
    
    public static boolean testIterate() {
        try {
            DrawingChange image1 = new DrawingChange(0,0,'a','b');
            DrawingChange image2 = new DrawingChange(1,1,'c','d');
            DrawingChange image3 = new DrawingChange(2,2,'e','f');
            
            DrawingStack stack = new DrawingStack();
            stack.push(image1);
            stack.push(image2);
            stack.push(image3);
            
            Iterator<DrawingChange> iter = stack.iterator();
            System.out.println(iter.hasNext());
            if(iter.next().newChar != 'f') {
                return false;
            }
            if(iter.hasNext() != true) {
                return false;
            }
            return true;
        
        }catch(NoSuchMethodError e){
            return false;
        }catch(IllegalArgumentException e) {
            return false;
        }catch(EmptyStackException e) {
            return false;
        }catch(NoSuchElementException e) {
            return false;
        }
    }
    
}
