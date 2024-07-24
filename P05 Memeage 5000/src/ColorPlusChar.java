
public class ColorPlusChar extends Color{

    public ColorPlusChar(Color color, char character) {
        super(color);
        hideChar(character);
    }
    
    public ColorPlusChar(Color color) {
        super(color);
    }
    
 // stores 8-bit character within the least significant bits of color components
    public void hideChar(char character) {
        int ascii = (int) character;
        String asciiS = Integer.toBinaryString(ascii);
        int bits = asciiS.length();
        
        
        int alpha;
        int red;
        int green;
        int blue;
        if(bits == 8) {
            alpha = Integer.parseInt(asciiS.substring(0, 2),2);
            red = Integer.parseInt(asciiS.substring(2, 4),2);
            green = Integer.parseInt(asciiS.substring(4, 6),2);
            blue = Integer.parseInt(asciiS.substring(6, 8),2);
        }else if(bits == 7) {
            alpha = Integer.parseInt(asciiS.substring(0,1),2);
            red = Integer.parseInt(asciiS.substring(1, 3),2);
            green = Integer.parseInt(asciiS.substring(3, 5),2);
            blue = Integer.parseInt(asciiS.substring(5, 7),2);
        }else if(bits == 6) {
            alpha = 0;
            red = Integer.parseInt(asciiS.substring(0, 2),2);
            green = Integer.parseInt(asciiS.substring(2, 4),2);
            blue = Integer.parseInt(asciiS.substring(4, 6),2);
            
        }else if(bits == 5) {
            alpha = 0;
            red = Integer.parseInt(asciiS.substring(0,1),2);
            green = Integer.parseInt(asciiS.substring(1, 3),2);
            blue = Integer.parseInt(asciiS.substring(3, 5),2);
        }else if(bits == 4) {
            alpha = 0;
            red = 0;
            green = Integer.parseInt(asciiS.substring(0, 2),2);
            blue = Integer.parseInt(asciiS.substring(2, 4),2);
        }else if(bits == 3) {
            alpha = 0;
            red = 0;
            green = Integer.parseInt(asciiS.substring(0,1),2);
            blue = Integer.parseInt(asciiS.substring(1, 3),2);
        }else if(bits == 2) {
            alpha = 0;
            red = 0;
            green = 0;
            blue = Integer.parseInt(asciiS.substring(0, 2),2);
        }else {
            alpha = 0;
            red = 0;
            green = 0;
            blue = Integer.parseInt(asciiS.substring(0,1),2);
        }
        
        
        
        setBits(2, 24, alpha);
        setBits(2, 16, red);
        setBits(2, 8, green);
        setBits(2, 0, blue);
    }
    
    // retrieves 8-bit character from the least significant bits of color components
    public char revealChar() {
        int alpha = getBits(2, 24);
        int red = getBits(2, 16);
        int green = getBits(2, 8);
        int blue = getBits(2, 0);
        
        String alphaS = Integer.toBinaryString(alpha);
        String redS = Integer.toBinaryString(red);
        String greenS = Integer.toBinaryString(green);
        String blueS = Integer.toBinaryString(blue);
        
        
        
        if(alpha == 0) {
            alphaS = "00";
        }else if(alpha == 1) {
            alphaS = "01";
        }
        if(red == 0) {
            redS = "00";
        }else if(red == 1) {
            redS = "01";
        }
        if(green == 0) {
            greenS = "00";
        }else if(green == 1) {
            greenS = "01";
        }
        if(blue == 0) {
            blueS = "00";
        }else if(blue == 1) {
            blueS = "01";
        }
        
        String ascii = alphaS + redS + greenS + blueS;
        int asciiInt = Integer.parseInt(ascii, 2);
        char character = (char) asciiInt;

        return character;
    }
    
   
}
