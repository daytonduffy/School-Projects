import java.io.File;
import java.io.IOException;

public class Memeage extends Image{
    
    //uses the inherited class constructor
    public Memeage(File file) throws IOException{
        super(file);
    }
    
    //uses the inherited constructor
    //uses the setMeme class while creating the new object
    public Memeage(File file, String meme) throws IOException, IllegalArgumentException{
        super(file);
        setMeme(meme);
    }
    
    public void setMeme(String meme) throws IllegalArgumentException{
        int width = getWidth();
        int height = getHeight();
        char[] characters = new char[meme.length()];
        characters = meme.toCharArray();
        int x = 0;
        int y = height - 1;
        
        if(meme.length() >= (width*height)) {
            throw new IllegalArgumentException("String to long for current image");
        }
        for(int i = 0; i < characters.length; ++i) {
            int ascii = (int) characters[i];
            if(ascii > 127) {
                throw new IllegalArgumentException("Invalid character within String");
            }
        }
        
        for(int i=0; i < meme.length(); ++i) {
            ColorPlusChar color = new ColorPlusChar(getColor(x,y), characters[i]);
            setColor(x,y, color);
            
            if(x  ==  width-1) {
                x = 0;
                y = y-1;
            }else {
                x = x+1;
            }
        }
        
        //will have updated x, y when it breaks from the loop
        ColorPlusChar color2 = new ColorPlusChar(getColor(x,y), '\0');
        setColor(x, y, color2);//sets final null character color
        
      
    }
    
    public String getMeme() throws IllegalStateException{
        int width = getWidth();
        int height = getHeight();
        int x = 0;
        int y = height - 1;
        int pixels = width * height;
        int count = 0;
        char temp = '\0';
        String meme = new String();
        meme = "";
        
        
        for(int i=0; i < pixels; ++i) {
            
            ColorPlusChar color = new ColorPlusChar(getColor(x,y));
            temp = color.revealChar();
            int ascii = (int) temp;
            
            
            if(ascii > 127) {
                throw new IllegalArgumentException("Invalid character within String");
            }
            
            if(temp == '\0') {
                break;
            }else {
                
                count = count + 1;
                meme = meme + temp;
            }
            
            if(count == pixels) {
                throw new IllegalArgumentException("No end of String found");
            }
            
            if(x  ==  width-1) {
                x = 0;
                y = y-1;
            }else {
                x = x+1;
            }
        }
        
        
        
        
        return meme;
    }
}
