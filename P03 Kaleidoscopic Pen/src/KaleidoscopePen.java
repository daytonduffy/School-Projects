import java.util.Arrays;
import processing.core.PApplet;


public class KaleidoscopePen {
    private TrianglePen[] pens = new TrianglePen[5];
    private int numberOfTrianglePens;
    
    public KaleidoscopePen(PApplet drawTo, int numberOfTrianglePens) {
        this.numberOfTrianglePens = numberOfTrianglePens;
        pens = new TrianglePen[numberOfTrianglePens];
        boolean show = true;
        for(int i = 0; i < pens.length; ++i) {
            int count = 0;
            pens[i] = new TrianglePen(drawTo, show);
            show = false;
        }
    }
    /**
    * Rotates a position around the center of an 800x600 screen by the specified
    * amount, and then returns an array containing the resulting position.
    * @param x position of the point to be rotated (0 to 800 pixels)
    * @param y position of the point to be rotated (0 to 600 pixels)
    * @param angle amount of rotation to apply (angle in radians units: 0 to 2*PI)
    * @return the rotated position array: x @ index 0, y @ index 1
    */
    private static int[] rotate(int x, int y, double angle) {
     x -= 400; // translate center of screen to origin (0,0)
     y -= 300;
     int[] rotatedPosition = new int[] { // rotate around origin
     (int)(x * Math.cos(angle) - y * Math.sin(angle)),
     (int)(x * Math.sin(angle) + y * Math.cos(angle)) };
     rotatedPosition[0] += 400; // return to center of screen
     rotatedPosition[1] += 300;
     return rotatedPosition;
    }
    
    public void update(int mouseX, int mouseY, boolean mousePressed, char keyPressed) {
        double degree = (2*Math.PI)/numberOfTrianglePens;

        for(int i=0; i < pens.length; ++i) {
            int xPos = rotate(mouseX, mouseY, (i * degree))[0];
            int yPos = rotate(mouseX, mouseY, (i * degree))[1];
            pens[i].update(xPos, yPos, mousePressed, keyPressed);
        }
        
    }
    
}
