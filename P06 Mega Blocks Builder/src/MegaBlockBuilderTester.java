import java.awt.Color;

public class MegaBlockBuilderTester {
    
    public static void main(String[] args) {
        LinkedListMegaBlock test = new LinkedListMegaBlock();
        MegaBlock tester = null;
        
        
        boolean test1 = testMegaBlockEquals();
        if(test1) {
            System.out.println("Hero-1");
        }else {
            System.out.println("Zero-1");
        }
        
        boolean test2 = testMegaBlockToString();
        if(test2) {
            System.out.println("Hero-2");
        }else {
            System.out.println("Zero-2");
        }
        
        boolean test3 = testLinkedMegaBlock();
        if(test3) {
            System.out.println("Hero-3");
        }else {
            System.out.println("Zero-3");
        }
        
        boolean test4 = testLinkedMegaBlockListAddRed();
        if(test4) {
            System.out.println("Hero-4");
        }else {
            System.out.println("Zero-4");
        }
        
        boolean test5 = testLinkedListMegaBlockRemoveBlue();
        if(test4) {
            System.out.println("Hero-5");
        }else {
            System.out.println("Zero-5");
        }
        
        boolean testBlue = testBlueBlock();
        if(testBlue) {
            System.out.println("Hero-Blue");
        }else {
            System.out.println("Zero-Blue");
        }
        
        boolean testRed = testRedBlock();
        if(testRed) {
            System.out.println("Hero-Red");
        }else {
            System.out.println("Zero-Red");
        }
        
        boolean testYellow = testYellowBlock();
        if(testYellow) {
            System.out.println("Hero-Yellow");
        }else {
            System.out.println("Zero-Yellow");
        }
        
        
            

    }
    
    /**
     * Method test the functionality of the equals method 
     * Returns false if any tests fail
     */
    public static boolean testMegaBlockEquals() {
        
        MegaBlock test1 = new MegaBlock(Color.RED, 'r');
        MegaBlock test2 = new MegaBlock(Color.RED, 'r');
        MegaBlock test3 = new MegaBlock(Color.RED, 'f');
        MegaBlock test4 = new MegaBlock(Color.BLUE, 'r');
        
        
        if(!test1.equals(test2)) {
           return false; 
        }
        if(!test1.equals(test3)) {
            return false; 
        }
        if(test1.equals(test4)) {
            return false; 
        }
        
        return true;
    }
    
    /**
     * Method test the functionality of the toString method 
     * Returns false if any tests fail
     */
    public static boolean testMegaBlockToString() {

        MegaBlock test1 = new MegaBlock(Color.RED, 'r');
        MegaBlock test2 = new MegaBlock(Color.BLUE, 'f');
        MegaBlock test3 = new MegaBlock(Color.YELLOW, 'f');

        
        String string1 = test1.toString();
        String string2 = test2.toString();
        
        if(!string1.equals("RED r")) {
            return false;
        }
        if(!string2.equals("BLUE f")) {
            return false;
        }
        
        return true;
    }

    /**
     * Method test the functionality of the LinkedMegaBlock method 
     * Returns false if any tests fail, tries to test all the methods more or less
     */
    public static boolean testLinkedMegaBlock() {
        LinkedMegaBlock test1 = new LinkedMegaBlock(new MegaBlock(Color.BLUE, '1'));
        LinkedMegaBlock test2 = new LinkedMegaBlock(new MegaBlock(Color.RED, '2'), test1);
        LinkedMegaBlock test3 = new LinkedMegaBlock(new MegaBlock(Color.YELLOW, '3'));
        MegaBlock block1 = new MegaBlock(Color.BLUE, '1');
        test1.setNext(test3);
        
        if(!test1.getBlock().equals(block1)) {
            return false;
        }
        if(!test1.getNext().equals(test3)) {
            return false;
        }
        
        
        return true;
    }

    public static boolean testBlueBlock() {
        try {
            LinkedListMegaBlock blue = new LinkedListMegaBlock();
            
            //blocks for testing
            MegaBlock blueBlock1 = new MegaBlock(Color.BLUE, '1');
            MegaBlock blueBlock2 = new MegaBlock(Color.BLUE, '2');
            MegaBlock blueBlock3 = new MegaBlock(Color.BLUE, '3');
            MegaBlock blueBlock4 = new MegaBlock(Color.BLUE, '4');
            MegaBlock yellowBlock = new MegaBlock(Color.YELLOW, 'Y');
            MegaBlock redBlock = new MegaBlock(Color.RED, 'R');
            
            
            //fill four blocks (we know we can add forever without problem)
            //no shown tests but I did test the bad colors it does throw the error
            blue.addBlue(blueBlock1);
            blue.addBlue(blueBlock2);
            blue.addBlue(blueBlock3);
            blue.addBlue(blueBlock4);

            
            if(!blue.get(0).toString().equals(blueBlock1.toString())) {
                return false;
            }
            if(!blue.get(1).toString().equals(blueBlock2.toString())) {
                return false;
            }
            if(!blue.get(2).toString().equals(blueBlock3.toString())) {
                return false;
            }
            if(!blue.get(3).toString().equals(blueBlock4.toString())) {
                return false;
            }
        
            
            
            MegaBlock holder1 = blue.removeBlue();
            if(!holder1.toString().equals(blueBlock4.toString())) {
                return false;
            }
            if(!(blue.size() == 3)){
                return false;
            }
            
            
            MegaBlock holder2 = blue.removeBlue();
            if(!holder2.toString().equals(blueBlock3.toString())) {
                return false;
            }
            if(!(blue.size() == 2)){
                return false;
            }
            
            blue.removeBlue();
            blue.removeBlue();
            if(!(blue.size() == 0 && blue.isEmpty())) {
                return false;
            }
            
            LinkedListMegaBlock last = new LinkedListMegaBlock();
            last.addBlue(blueBlock1);
            last.addBlue(blueBlock2);
            last.addBlue(blueBlock3);
            last.addBlue(blueBlock4);
            last.removeBlue();
            last.removeBlue();
            last.addBlue(blueBlock1);
            last.removeBlue();
            last.addBlue(blueBlock3);
            
            if(!last.get(2).toString().equals(blueBlock3.toString())) {
                return false;
            }
            
            
        }catch(IllegalArgumentException e) {
            System.out.println(e);
        }
        
      
        return true;
    }
    
    public static boolean testRedBlock(){
        LinkedListMegaBlock red = new LinkedListMegaBlock();
        MegaBlock redBlock1 = new MegaBlock(Color.RED, '1');
        MegaBlock redBlock2 = new MegaBlock(Color.RED, '2');
        MegaBlock redBlock3 = new MegaBlock(Color.RED, '3');
        MegaBlock redBlock4 = new MegaBlock(Color.RED, '4');
        
        
        red.addred(redBlock4);
        red.addred(redBlock3);
        red.addred(redBlock2);
        red.addred(redBlock1);
        
        if(!red.get(0).toString().equals(redBlock1.toString())) {
            return false;
        }
        if(!red.get(1).toString().equals(redBlock2.toString())) {   
            return false;
        }
        if(!red.get(2).toString().equals(redBlock3.toString())) {            
            return false;
        }
        if(!red.get(3).toString().equals(redBlock4.toString())) {            
            return false;
        }
        
        
        MegaBlock hold1 = red.removeRed();
        if(!(hold1.toString().equals(redBlock1.toString()))) {
            return false;
        }
        if(red.size() != 3) {
            return false;
        }
        MegaBlock hold2 = red.removeRed();
        if(!(hold2.toString().equals(redBlock2.toString()))) {
            return false;
        }
        if(red.size() != 2) {
            return false;
        }
        
        red.removeRed();
        red.removeRed();
        
        if(red.size() != 0 || !red.isEmpty()) {
            return false;
        }
        
        
        LinkedListMegaBlock last = new LinkedListMegaBlock();
        last.addred(redBlock4);
        last.addred(redBlock3);
        last.addred(redBlock2);
        last.addred(redBlock1);
        last.addred(redBlock2);
        last.addred(redBlock3);
        last.removeRed();
        last.removeRed();
        last.removeRed();
        last.addred(redBlock3);
        last.addred(redBlock4);
        last.removeRed();
        
        if(!last.get(0).toString().equals(redBlock3.toString())) {
            return false;
        }
        if(!last.get(1).toString().equals(redBlock2.toString())) {
            return false;
        }
        if(!last.get(2).toString().equals(redBlock3.toString())) {
            return false;
        }
        if(!last.get(3).toString().equals(redBlock4.toString())) {
            return false;
        }
        return true;
    }
    
    public static boolean testYellowBlock(){
        LinkedListMegaBlock yellow = new LinkedListMegaBlock();
        MegaBlock yellowBlock1 = new MegaBlock(Color.YELLOW, '1');
        MegaBlock yellowBlock2 = new MegaBlock(Color.YELLOW, '2');
        MegaBlock yellowBlock3 = new MegaBlock(Color.YELLOW, '3');
        MegaBlock yellowBlock4 = new MegaBlock(Color.YELLOW, '4');
        MegaBlock yellowBlock5 = new MegaBlock(Color.YELLOW, '5');
        MegaBlock yellowBlock6 = new MegaBlock(Color.YELLOW, '6');
        
        yellow.addYellow(0, yellowBlock1);
        yellow.addYellow(1, yellowBlock2);
        yellow.addYellow(2, yellowBlock3);
        yellow.addYellow(1, yellowBlock4);
        yellow.addYellow(2, yellowBlock5);
        
        
               
        //passing this section means both the add and get methods are working 
        if(!yellow.get(0).toString().equals(yellowBlock1.toString())) {
            return false;
        }
        if(!yellow.get(1).toString().equals(yellowBlock4.toString())) {
            return false;
        }
        if(!yellow.get(2).toString().equals(yellowBlock5.toString())) {
            return false;
        }
        if(!yellow.get(3).toString().equals(yellowBlock2.toString())) {
            return false;
        }
        if(!yellow.get(4).toString().equals(yellowBlock3.toString())) {
            System.out.println(yellow.get(4).toString());
            System.out.println(yellowBlock3.toString() + " " + yellow.size());
            return false;
        }
        
        MegaBlock thing = yellow.removeYellow(4);

        
        if(!yellow.get(4).toString().equals(yellowBlock6.toString())) {
            return false;
        }
        if(!thing.toString().equals(yellowBlock3.toString())) {
            return false;
        }


        LinkedListMegaBlock last = new LinkedListMegaBlock();
        last.addYellow(0, yellowBlock1);
        last.addYellow(0, yellowBlock2);
        last.addYellow(1, yellowBlock3);
        last.addYellow(0, yellowBlock4);
        last.addYellow(2, yellowBlock5);
        last.removeYellow(2);
        last.removeYellow(0);
        last.addYellow(1, yellowBlock2);
        last.removeYellow(2);
        
        
        
        if(!last.get(0).toString().equals(yellowBlock4.toString())) {
            return false;
        }
        if(!last.get(1).toString().equals(yellowBlock2.toString())) {
            return false;
        }
        if(!last.get(2).toString().equals(yellowBlock3.toString())) {
            return false;
        }
        
        return true;
    }
    
    // checks for the correctness of the LinkedListMegaBlock.addRed() method
    public static boolean testLinkedMegaBlockListAddRed() {
        LinkedListMegaBlock red = new LinkedListMegaBlock();
        MegaBlock redBlock1 = new MegaBlock(Color.RED, '1');
        MegaBlock redBlock2 = new MegaBlock(Color.RED, '2');
        MegaBlock redBlock3 = new MegaBlock(Color.RED, '3');
        MegaBlock redBlock4 = new MegaBlock(Color.RED, '4');
        
        
        red.addred(redBlock4);
        red.addred(redBlock3);
        red.addred(redBlock2);
        red.addred(redBlock1);
        
        if(!red.get(0).toString().equals(redBlock1.toString())) {
            return false;
        }
        if(!red.get(1).toString().equals(redBlock2.toString())) {   
            return false;
        }
        if(!red.get(2).toString().equals(redBlock3.toString())) {            
            return false;
        }
        if(!red.get(3).toString().equals(redBlock4.toString())) {            
            return false;
        }
        
        return true;
    }
    // checks for the correctness of the LinkedListMegaBlock.removeBlue() method
    public static boolean testLinkedListMegaBlockRemoveBlue() {
        LinkedListMegaBlock blue = new LinkedListMegaBlock();
        
        //blocks for testing
        MegaBlock blueBlock1 = new MegaBlock(Color.BLUE, '1');
        MegaBlock blueBlock2 = new MegaBlock(Color.BLUE, '2');
        MegaBlock blueBlock3 = new MegaBlock(Color.BLUE, '3');
        MegaBlock blueBlock4 = new MegaBlock(Color.BLUE, '4');
        blue.addBlue(blueBlock1);
        blue.addBlue(blueBlock2);
        blue.addBlue(blueBlock3);
        blue.addBlue(blueBlock4);
        MegaBlock holder2 = blue.removeBlue();
        if(!holder2.toString().equals(blueBlock3.toString())) {
            return false;
        }
        if(!(blue.size() == 2)){
            return false;
        }
        
        blue.removeBlue();
        blue.removeBlue();
        if(!(blue.size() == 0 && blue.isEmpty())) {
            return false;
        }
        
        return true;
    }

    
    
}
