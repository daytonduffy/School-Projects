
/**
 * This class stores four bytes of data (within a primitive int), and provides
 * both accessor and mutator methods for consecutive bits within this data.
 * 
 * @author dahl
 */
public class FourBytes {
  private int value; // stores four bytes or 32 bits of data
  
  /**
   * Create a new FourBytes object with the specified initial value.
   * @param value contains initial four bytes (32 bits) of data
   */
  public FourBytes(int value) {
    this.value = value;
  }  
  /**
   * Create a new FourBytes object with the specified initial value.
   * @param value lease significant initial two bytes (16 bits) of data
   */
  public FourBytes(char value) {
    this((int)value);
  }
  
  /**
   * Changes a sequence of consecutive bits within this FourBytes object
   * to reflect the specified value.  Consider for example the a call of
   * setBits(3,4,13) on the following FourBytes object initialized to 0:
   * 
   * bitstring                : 00000000 00000000 00000000 00000000  
   * bitstring.setBits(3,8,13): 00000000 00000000 00000101 00000000
   *                                     changed bits: ^^^ <- offset of 8 bits
   * 13 in binary is 1101, but only 3 right-most/least-significant bits are set
   * If you'd like to test this, the resulting bit string: 101 00000000 = 1280
   * 
   * @param numberOfBits is the total number of bits to mutate
   * @param offset distance left from the right-most/least-significant bit
   *   an offset of 0, changes only the numberOfBits right-most bits
   * @param value to store in the specified portion of this FourBytes object
   *   note that only the numberOfBits least significant bits are used
   */
  public void setBits(int numberOfBits, int offset, int value) {
    int mask = (1 << numberOfBits)-1;  // create mask with appropriate number of 1s
    if(numberOfBits >= 32) mask = -1;  // fill entire mask with 1s
    mask <<= offset; // shift mask left by offset amount
    value <<= offset; // shift value being stored left by offset amount
    this.value = (this.value & ~mask) | (value & mask); // store value at specified position
  }

  /**
   * Retrieves the int value represented by a sequence of consecutive bits
   * within this FourBytes object.  Consider for example the a call of
   * getBits(4,10) on the following FourBytesObject:
   * 
   * bitstring: 00000000 00000000 00110100 00000000  
   *                     read bits: ^^^^ <- offset of 10 bits
   * returns 13 corresponding to binary sequence 1101
   * If you'd like to test this, the initial bit string: 11010000000000 = 13312
   * 
   * @param numberOfBits is the total number of bits to access
   * @param offset distance left from th right-most/least significant bit
   *   an offset of 0, reads only the numberOfBits right-most bits
   * @return int representation of the specified bits within this FourBytes object
   *   note that any extra higher significant bits in this int are initialized to zero
   */
  public int getBits(int numberOfBits, int offset) {
    int value = this.value >> offset; // shift contents of value to right end of bit string
    int mask = (1 << numberOfBits)-1; // create a mask with appropriate number of 1s
    if(numberOfBits >= 32) mask = -1; // fill entire mask with 1s
    return value & mask; // extract leftmost numberOfBits from shifted value
  }  
  
  /**
   * Changes the value of this this FourByte object.
   * @param value is the new data that this object is being changed to store
   */
  public void setInt(int value) {
    this.value = value;
  }
  
  /**
   * Retrieve the int value represented by this FourByte object.
   * @return that integer value
   */
  public int getInt() {
    return this.value;
  }
  
  /**
   * Retrieve the char value represented by this FourByte object.
   * (note that chars are only 16 bit, and the right-most/least-significant bits
   * are used for this.
   * @return that character value
   */
  public char getChar() {
    return (char)this.value;
  }
  
  /**
   *  Retrieve a string representation showing the both int and binary representations
   *  of this FourByte object.
   */
  public String toString() {
    return value + " = " + Integer.toBinaryString(this.value); 
  }
  
}