import processing.core.PApplet;
import java.util.ArrayList;

public class TrianglePen {
    private boolean mouseWasPressed; // mousePressed from previous update() call
    private char keyWasPressed; // keyPressed from previous update() call
    private PApplet processing; //used in constructor
    private boolean showPoints; //used in constructor
    private ArrayList<Point> Points = new ArrayList<Point>();
    private ArrayList<Triangle> Triangles = new ArrayList<Triangle>();
    private int count = 0;
    private Point curPoint = null;
    private boolean over = false;
    private boolean drag = false;
    
    public TrianglePen(PApplet processing, boolean showPoints){
        this.processing = processing;
        this.showPoints = showPoints;
        
    }
    public void update(int mouseX, int mouseY, boolean mousePressed, char keyPressed){
     // process mouse-based user input
        if(mousePressed != mouseWasPressed) {
         if(mousePressed) handleMousePress(mouseX, mouseY);
         else handleMouseRelease(mouseX, mouseY);
        }
        if(mousePressed) handleMouseDrag(mouseX, mouseY);
        mouseWasPressed = mousePressed;
        // process keyboard-based user input
        if(keyPressed != keyWasPressed) handleKeyPress(mouseX, mouseY, keyPressed);
        keyWasPressed = keyPressed;
        // draw everything in its current state
        draw();
        
    }
    
    private void handleMousePress(int mouseX, int mouseY) {
        
        for(int i=0; i < Points.size(); ++i) {
            if(Points.get(i).isOver(mouseX, mouseY)) {
                over = true;
                curPoint = Points.get(i);
                break;
            }
        }
        if(over) {
            drag = true;
            over = false;
        }else {
            Points.add(new Point(mouseX, mouseY));
            count = count + 1;
            if(count %3 == 0 && count != 0) {
                Triangles.add(new Triangle(Points.get(count - 3),Points.get(count - 2), Points.get(count - 1), 0));
                
            }
        }
    }
    
    private void handleMouseRelease(int mouseX, int mouseY) {
        drag = false;
        curPoint = null;
    }
    
    private void handleMouseDrag(int mouseX, int mouseY) {
        
        if(drag) {
            int dex = (Points.indexOf(curPoint));
            Points.get(dex).setPosition(mouseX, mouseY);
        }
    }
    
    private void handleKeyPress(int mouseX, int mouseY, char keyPressed) {
        boolean pressed = false;
        
        
        
        if(keyPressed == '0') {
            pressed = true;
        }else if(keyPressed == '1') {
            pressed = true;
        }else if(keyPressed == '2') {
            pressed = true;
        }else if(keyPressed == '3') {
            pressed = true;
        }else if(keyPressed == '4') {
            pressed = true;
        }else if(keyPressed == '5') {
            pressed = true;
        }else if(keyPressed == '6') {
            pressed = true;
        }else if(keyPressed == '7') {
            pressed = true;
        }

        if(pressed) {
            for(int i=0; i < Triangles.size(); ++i) {
                if(Triangles.get(i).isOver(mouseX, mouseY)) {
                    if(i == 0) {
                        Triangles.get(Triangles.size() - 1).setColor(keyPressed - '0');
                    }else {
                        Triangles.get(i - 1).setColor(keyPressed - '0');
                    }    
                }
            }
        }pressed = false;
    }
    
    private void draw() {
        
        if(showPoints == true) {
            for(int i=0; i < Points.size(); ++i) {
                Points.get(i).draw(processing);
            }
        }
        for(int i=0; i < Triangles.size(); ++i) {
            Triangles.get(i).draw(processing);
        }
    }
}