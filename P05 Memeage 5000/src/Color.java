
public class Color extends FourBytes{

    //kind of explains itself
    public Color(int argb) {
         super(argb);
    }
    
    //Probably a better way to do this
    //I turn all the ints into binary strings, join them in the right order, and turn the string back into an int 
    public Color(int alpha, int red, int green, int blue) {
        super(Integer.parseInt(Integer.toBinaryString(alpha) + Integer.toBinaryString(red) + Integer.toBinaryString(green) + Integer.toBinaryString(blue), 2));
    }
    
    //get the argb from the method Im making
    public Color(Color other) {
        super(other.getARGB());
    }
    
    //each of these 5 methods uses the super class' methods to return the right value
    public int getARGB() {
        return getInt();
    }
    public int getAlpha() {
        return getBits(8, 24);
    }
    public int getRed() {
        return getBits(8, 16);
    }
    public int getGreen() {
        return getBits(8, 8);
    }
    public int getBlue() {
        return getBits(8, 0);
    }
    
    //set all the bits to the new value
    public void setARGB(int argb) {
        setBits(32, 0, argb);
    }
    //next four methods set their respective 8 bits of the argb 
    public void setAlpha(int alpha) {
        setBits(8, 24, alpha);
    }
    public void setRed(int red) {
        setBits(8, 16, red);
    }
    public void setGreen(int green) {
        setBits(8, 8, green);
    }
    public void setBlue(int blue) {
        setBits(8, 0, blue);
    }
}
