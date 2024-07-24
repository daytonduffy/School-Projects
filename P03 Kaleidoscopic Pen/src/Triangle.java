import processing.core.PApplet;

public class Triangle {
    private Point point1;// used for constructor
    private Point point2;// used for constructor
    private Point point3;// used for constructor
    private int color;// used in constructor, index
    private static final int[] COLORS = new int[] { // int packed w/8 bits of ARGB
     // WHITE, RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, VIOLET
      -1, -766643, -752563, -723891, -11668348, -11696908, -8106508, -766476 };
    
    
    
    public Triangle(Point point1, Point point2, Point point3, int color) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        this.color = color;
    }
    
    public void setColor(int color) {        
        this.color = color;
        
    }
    
    public void draw(PApplet drawTo) {
     drawTo.triangle(point1.getX(), point1.getY(), point2.getX(), point2.getY(), point3.getX(), point3.getY());   
     drawTo.fill(COLORS[color]);
    }
    
    public boolean isOver(int x, int y) {
        boolean isOver = isPointInTriangle(x, y, point1.getX(), point1.getY(), point2.getX(), point2.getY(), point3.getX(), point3.getY());

        return isOver;
    }
    private static boolean isPointInTriangle(int px, int py,
        int t1x, int t1y, int t2x, int t2y, int t3x, int t3y) {
        px -= t1x; // don't worry about this arithmetic
        py -= t1y;
        t2x -= t1x;
        t2y -= t1y;
        t3x -= t1x;
        t3y -= t1y;
        double dotp2 = px*t2x+py*t2y;
        double dotp3 = px*t3x+py*t3y;
        double dot22 = t2x*t2x+t2y*t2y;
        double dot23 = t2x*t3x+t2y*t3y;
        double dot33 = t3x*t3x+t3y*t3y;
        double invDen = 1 / (dot33 * dot22 - dot23 * dot23);
        double a = (dot22 * dotp3 - dot23 * dotp2) * invDen;
        double b = (dot33 * dotp2 - dot23 * dotp3) * invDen;
        return (a >= 0) && (b >= 0) && (a + b < 1);
       }
    
}
