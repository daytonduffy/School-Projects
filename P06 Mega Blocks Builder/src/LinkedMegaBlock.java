
public class LinkedMegaBlock {
    private MegaBlock block; // data field of this linkedMegaBlock
    private LinkedMegaBlock next; // link to the next linkedMegaBlock
    
    /**
     * Method creates a new non linked block
     */
    public LinkedMegaBlock(MegaBlock block) {
        this.block = block;
    }
    
    /**
     * Method creates a new linked megablock
     */
    public LinkedMegaBlock(MegaBlock block, LinkedMegaBlock next) {
        this.block = block;
        this.next = next;
        
        
    }
    
    /**
     * Method returns the megablock
     */
    public MegaBlock getBlock() {
        return block;
    }
    
    /**
     * Method sets a new block
     */
    public void setBlock(MegaBlock block) {
        this.block = block;
    }
    
    /**
     * Method returns the next block
     */
    public LinkedMegaBlock getNext() {
        return next;
    }
    
    /**
     * Method sets a new pointer
     */
    public void setNext(LinkedMegaBlock next) {
        this.next = next;
    }
    
    /**
     * Method returns a string representation of the linked megablock
     * 
     */
    public String toString(){
        String link;
        
        if(next == null) {
            link = block.toString() + " -> END ";
        }else {
            link = block.toString() + " -> ";
        }
        
        
        return link;
    }
}
