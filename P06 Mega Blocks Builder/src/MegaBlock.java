import java.awt.Color;

/**
 * This class models a Mega Block
 *
 */
public class MegaBlock {
    private final Color COLOR; // color of this Mega Block
    private char label; // label of this Mega Block

  /**
   * Creates a new MegaBlock object with a specific color index and a specific label
   * 
   * @param color color of this MegaBlock object
   * @param label of this MegaBlock object
   */
  public MegaBlock(Color color, char label) {
    this.COLOR = color;
    this.label = label;
  }

  

/**
   * Returns the label of this MegaBlock object
   * 
   * @return the label
   */
  public char getLabel() {
    return label;
  }

  /**
   * Sets the label of this MegaBlock object to a specific one
   * 
   * @param label the label to set
   */
  public void setLabel(char label) {
    this.label = label;
  }

  /**
   * Returns the color of this MegaBlock object
   * 
   * @return the color of this Megablock object
   */
  public Color getColor() {
    return COLOR;
  }


  /**
   * Returns a String representation of this megaBlock object
   * 
   * @return a String representation of this megaBlock object
   */
  @Override
  public String toString() {
      String color = "";
    if(COLOR.toString().equals("java.awt.Color[r=255,g=0,b=0]")) {
        color = "RED";
    }else if(COLOR.toString().equals("java.awt.Color[r=0,g=0,b=255]")) {
        color = "BLUE";
    }else if(COLOR.toString().contentEquals("java.awt.Color[r=255,g=255,b=0]")) {
        color = "YELLOW";
    }
      
      
    return color + " " + this.label;
  }

  /**
   * Returns true if this megaBlock is equal to otherBlock, false otherwise
   * 
   * @param otherBlock object to which this megaBlock will be compared
   * @return true if otherBlock refers to an instance of MegaBlock and the color of this megablock
   *         equals the color of the provided otherBlock.
   */
  @Override
  public boolean equals(Object otherBlock) {
    return (otherBlock instanceof MegaBlock && this.COLOR.equals(((MegaBlock) otherBlock).COLOR));
  }
}
