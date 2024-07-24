import processing.core.PApplet;

public class Point {
    private int xPos; //used in constructor
    private int yPos; //used in constructor
    private final int POINT_DIAMETER = 8;
    
    public Point(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getX() {
        return xPos;
    }
    public int getY() {
        return yPos;
    }
    public void draw(PApplet drawTo) {
        drawTo.circle(xPos, yPos, POINT_DIAMETER);
    }
    public void setPosition(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }
    
    
    public boolean isOver(int x, int y) { // returns true when the position x, y is within the circle drawn to visualize this point, otherwise returns false
        boolean isOver = false;
        int radius = POINT_DIAMETER/2;
        
        double xTerm = Math.pow(xPos - x, 2);
        double yTerm = Math.pow(yPos - y, 2);
        
        double distance = Math.sqrt(((xTerm)) + ((yTerm)));        
        
        if(distance <= radius) {
            isOver = true;
        }
        return isOver;
    }
}