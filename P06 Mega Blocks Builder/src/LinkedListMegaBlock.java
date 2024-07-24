import java.awt.Color;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class LinkedListMegaBlock {

    private LinkedMegaBlock head; // head of this list
    private LinkedMegaBlock tail; // tail of this list
    private int size; // size of this list (total number of megablocks stored in this list)
    private int redCount; // number of RED megablocks stored in this list
    private int yellowCount; // number of YELLOW megablocks stored in this list
    private int blueCount; // number of BLUE megablocks stored in this list
    
    
    public LinkedListMegaBlock() {
        redCount = 0;
        yellowCount = 0;
        blueCount = 0;
        size = 0;
        head = null;
        tail = null;
    }
    
    public boolean isEmpty() {
        if(size == 0) {
            return true;
        }else {
            return false;
        }
    }
    
    public int size() {
        return size;
    }
    
    public void clear() {
        redCount = 0;
        yellowCount = 0;
        blueCount = 0;
        size = 0;
        head = null;
        tail = null;
    }
    
    public void addBlue(MegaBlock blueBlock) {
        MegaBlock quick = new MegaBlock(Color.BLUE, '1');
        if(blueBlock == null) {
            throw new IllegalArgumentException("Block Null");
        }else if(!blueBlock.equals(quick)) {
            throw new IllegalArgumentException("NOT BLUE");
        }

        LinkedMegaBlock newBlue = new LinkedMegaBlock(blueBlock, null);
        
        if(head == null && tail == null) {//if empty set head and tail to new blue
            head = new LinkedMegaBlock(blueBlock, tail);
            tail = head;
        }else {//otherwise set the current tail block's next to the new block and then change tail
            tail.setNext(newBlue);
            tail = newBlue;
        }
        
        
        blueCount = blueCount + 1;
        size = size + 1;
    }
    
    public void addred(MegaBlock redBlock) {
        MegaBlock quick = new MegaBlock(Color.RED, '1'); 
        if(redBlock == null) {
            throw new IllegalArgumentException("Block Null");
        }else if(!redBlock.equals(quick)) {
            throw new IllegalArgumentException("NOT RED");
        }
        
        
        
        if(head == null && tail == null) {//if empty set head and tail to new red
            head = new LinkedMegaBlock(redBlock, tail);
            tail = head;
        }else {//otherwise set the head to the new block that points to the old head
            LinkedMegaBlock newRed = new LinkedMegaBlock(redBlock, head);
            head = newRed;
        }

        redCount = redCount + 1;
        size = size + 1;
    }
    
    public void addYellow(int index, MegaBlock yellowBlock) {
        if(yellowBlock.equals(null)) {
            throw new IllegalArgumentException("Block is null");
        }
        if((index < redCount || index > size - blueCount)) {
            throw new IndexOutOfBoundsException("Index out of Bounds: " + index);
        }
        
        if(head == null && tail == null && index == 0) {//if empty set head and tail to new yellow
            head = new LinkedMegaBlock(yellowBlock, tail);
            tail = new LinkedMegaBlock(yellowBlock);
        }else {
            if(index==1 && size == 2) {
                LinkedMegaBlock God = new LinkedMegaBlock(yellowBlock, tail);
                head.setNext(God);
                return;
            }
            ArrayList<LinkedMegaBlock> hope = new ArrayList<LinkedMegaBlock>();
            LinkedMegaBlock temp = new LinkedMegaBlock(head.getBlock(),head.getNext());
            for(int i=0; i<size-1; ++i) {
                hope.add(temp);
                temp.setBlock(temp.getNext().getBlock());
                temp.setNext(temp.getNext().getNext());
            }
            LinkedMegaBlock God = new LinkedMegaBlock(yellowBlock,hope.get(index));
            LinkedMegaBlock please = hope.get(index-1);
            please.setNext(God);
            
            head = hope.get(0);
            tail = hope.get(size-1);
        }
        
        yellowCount = yellowCount + 1;
        size = size + 1;
    }


    public MegaBlock get(int index) {
        if((index < 0 || index >= size())) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        if(index == 0) {
            return head.getBlock();
        }else if(index == size -1) {
            System.out.println(tail.toString());
            return tail.getBlock();
        }
        
        LinkedMegaBlock block = new LinkedMegaBlock(head.getBlock(), head.getNext());
        for(int i = 0; i < index; ++i) {//iterates until block holds the same information as the index we want
            block.setBlock(block.getNext().getBlock());
            block.setNext(block.getNext().getNext());

        }
        return block.getBlock();//return the megablock from the reference
    }
    
    public MegaBlock set(int index, MegaBlock block) {
        if(block == null) {
            throw new IllegalArgumentException("Block null");
        }else if((index < 0 || index >= size())) {
            throw new IndexOutOfBoundsException("Index out of Bounds: " + index);
        }
        
        
        LinkedMegaBlock hold = new LinkedMegaBlock(head.getBlock(), head.getNext());
        for(int i = 0; i < index; ++i) {//iterates until block holds the same information as the index we want
            hold.setBlock(hold.getNext().getBlock());
            hold.setNext(hold.getNext().getNext());
        }
        
        if(!hold.getBlock().getColor().equals(block.getColor())) {
            throw new IllegalArgumentException("Wrong color for given index");
        }
        
        
        LinkedMegaBlock newBlock = new LinkedMegaBlock(block, hold.getNext()); 
    
        LinkedMegaBlock ref = head;
        for(int i = 0; i < index-1; ++i) {
            ref = ref.getNext();
        }
        ref.setNext(newBlock);
    
        return hold.getBlock();
    }
    
    public MegaBlock removeRed() {
        
        if(head.getBlock().getColor().equals(Color.RED)) {
            size = size - 1;
            redCount = redCount - 1;
            
            if(head.getNext() == null) {
                MegaBlock red = head.getBlock();
                head.setBlock(null);
                head.setNext(null);
                return red;
            }
            
            MegaBlock red = head.getBlock();
            head.setBlock(head.getNext().getBlock());
            head.setNext(head.getNext().getNext());
            return red;
            
            
        }else {
            throw new NoSuchElementException("No red in list");
        }
        
    }
    
    public MegaBlock removeBlue() {
        
        if(tail.getBlock().getColor().equals(Color.BLUE)) {
            
            MegaBlock blue = tail.getBlock();
            
            LinkedMegaBlock hold = new LinkedMegaBlock(head.getBlock(), head.getNext());
            for(int i = 0; i < size-2; ++i) {//iterates until block holds the same information as the index we want
                hold.setBlock(hold.getNext().getBlock());
                hold.setNext(hold.getNext().getNext());
            }
            hold.setNext(null);
            tail.setBlock(hold.getBlock());
            
            
            
            size = size - 1;
            blueCount = blueCount -1;
            return blue;
        }else {
            throw new NoSuchElementException("No blue in list");
        }
    }
    
    
    public MegaBlock removeYellow(int index) {
        int count =0;
        if( (index < redCount || index >= size - blueCount)) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        
        LinkedMegaBlock temp = head;
        MegaBlock hold = get(index);
        LinkedMegaBlock link = new LinkedMegaBlock(hold);
        if(index == 0) {
            head = head.getNext();
            link.setNext(null);
        }else if(index == size-1) {
            while(temp.getNext().getNext() != null) {
                temp = temp.getNext();
            }
            temp.setNext(null);
            tail = temp;
        }else {
            while(index-1> count) {
                temp = temp.getNext();
                count++;
            }
            temp.setNext(temp.getNext().getNext());
            temp = temp.getNext();
            temp.setNext(null);
        }
        
        return hold;
    }
    
    /**
     * Returns the various color counts
     */
    public int getRedCount() {
        return redCount;
    }
    public int getBlueCount() {
        return blueCount;
    }
    public int getYellowCount() {
        return yellowCount;
    }
    
    
    /**
     * Returns a String representation of the contents of this list
     */
    public java.lang.String toString(){
        if(size == 0) {
            return "";
        }else {
            LinkedMegaBlock hold = new LinkedMegaBlock(head.getBlock(), head.getNext());
            String list = "";
            for(int i=0; i < size; ++i) {
                list = list + hold.toString();
                
                hold.setBlock(hold.getNext().getBlock());
                hold.setNext(hold.getNext().getNext());
            }
            return list;
        }
    }
}
