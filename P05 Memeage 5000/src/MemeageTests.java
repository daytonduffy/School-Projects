import java.io.File;
import java.io.IOException;

public class MemeageTests {

    public static void main(String args[]) {
        boolean test1 = testFourBytesGetBits();
        if(test1) {
            System.out.println("HERO-1");
        }else {
            System.out.println("ZERO-1");
        }

        boolean test2 = testFourBytesSetBits();
        if(test2) {
            System.out.println("HERO-2");
        }else {
            System.out.println("ZERO-2");
        }

        boolean test3 = testColor();
        if(test3) {
            System.out.println("HERO-3");
        }else {
            System.out.println("ZERO-3");
        }

        boolean test4 = testImage();
        if(test4) {
            System.out.println("HERO-4");
        }else {
            System.out.println("ZERO-4");
        }
        
        boolean test5 = testColorPlusChar();
        if(test5) {
            System.out.println("HERO-5");
        }else {
            System.out.println("ZERO-5");
        }
        
        boolean test6 = testMemeage();
        if(test6) {
            System.out.println("HERO-6");
        }else {
            System.out.println("ZERO-6");
        }
    }
    
    //this method tests the getBits method
    public static boolean testFourBytesGetBits() {
        FourBytes test1 = new FourBytes(0);
        FourBytes test2 = new FourBytes(16);
        FourBytes test3 = new FourBytes(255);
        FourBytes test4 = new FourBytes(13312);
        
        int compare1 = test1.getBits(11, 4);
        int compare2 = test2.getBits(5, 0);
        int compare3 = test3.getBits(4, 0);
        int compare4 = test4.getBits(4, 10);
        
        if(compare1 != 0) {
            return false;
        }
        if(compare2 != 16) {
            return false;
        }
        if(compare3 != 15) {
            return false;
        }
        if(compare4 != 13) {
            return false;
        }
        
        return true;
    }
    
    //this method tests the setBits method
    public static boolean testFourBytesSetBits() {
        FourBytes test1 = new FourBytes(0);
        FourBytes test2 = new FourBytes(1280);
        
        test1.setBits(5, 0, 16);
        test2.setBits(3, 8, 13);

        if(test1.getBits(5, 0) != 16) {
            return false;
        }
        if(test2.getBits(3, 8) != 5) {
            return false;
        }
        
        return true;
    }
    
    public static boolean testColor() {
        Color test1 = new Color(255);
        Color test2 = new Color(128,16,16,128);
        
        int argb1 = test1.getARGB();
        if(argb1 != 255) {
            return false;
        }
        test1.setARGB(16);
        if(test1.getARGB() != 16) {
            return false;
        }
        
        int green = test2.getGreen();
        if(green != 16) {
            return false;
        }
        test2.setBlue(255);
        if(test2.getBlue() != 255) {
            return false;
        }
        
        return true;
    }
    
    public static boolean testImage() {
        File file = new File("testImage.png");
        try {
            Image test = new Image(file);
            
            if(test.getHeight() != 3) {
                return false;
            }
            if(test.getWidth() != 3) {
                return false;
            }
            
            Color colorTest = new Color(test.getColor(1, 1));
            
            if(colorTest.getGreen() != 255) {
                return false;
            }
            if(colorTest.getBlue() != 255) {
                return false;
            }
            if(colorTest.getRed() != 0) {
                return false;
            }
            
        } catch (IOException e) {
           System.out.println("IOException thrown");
        }

        return true;
    }
    
    public static boolean testColorPlusChar() {
        Color testColor = new Color(255, 16, 16, 255);
        ColorPlusChar test = new ColorPlusChar(testColor);
        
        test.hideChar('a');
        char revealed = test.revealChar();
        if(revealed != 'a') {
            return false;
        }
        
        return true;
    }
    
    public static boolean testMemeage() {
        File file = new File("image01.png");
        try {
            Memeage test = new Memeage(file);
            String meme = " please work ";
            
            test.setMeme(meme);
            
            String hope = test.getMeme();
            
            if(!meme.equals(hope)) {
                return false;
            }
            
        } catch (IOException e) {
            System.out.println(e);
        }
        
        
        
        return true;
    }
}
