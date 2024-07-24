
public class Canvas {
    private final int width; // width of the canvas
    private final int height; // height of the canvas
    private char[][] drawingArray; // 2D character array to store the drawing
    private final DrawingStack undoStack; // store previous changes for undo
    private final DrawingStack redoStack; // store undone changes for redo

    // Constructor creates a new canvas which is initially blank (use the default value
    // for char type or you can use spaces)
    public Canvas(int width, int height) {
        if (width < 0) {
            throw new IllegalArgumentException("Invalid width: " + width);
        }
        if (height < 0) {
            throw new IllegalArgumentException("Invalid height: " + height);
        }
        this.width = width;
        this.height = height;
        undoStack = new DrawingStack();
        redoStack = new DrawingStack();
        drawingArray = new char[height][width];
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                drawingArray[i][j] = ' ';
            }
        }
    }

    // Draw a character at the given position drawingArray[row][col]
    public void draw(int row, int col, char c) {
        if (row > height) {
            throw new IllegalArgumentException("Invalid row: " + row);
        }
        if (col > width) {
            throw new IllegalArgumentException("Invalid collum: " + col);
        }
        
        DrawingChange change = new DrawingChange(row, col, drawingArray[row][col], c);
        drawingArray[row][col] = c;//set new char
        
        undoStack.push(change);
        while(!redoStack.isEmpty()) {//clear the redoStack
            redoStack.pop();
        }
        
    }
    
    // Undo the most recent drawing change.
    public boolean undo() {
        if(undoStack.isEmpty()) {
            return false;
        }
        
        DrawingChange change = undoStack.pop();//takes off stack and copies
        redoStack.push(change);
        
        //take the data of the drawing change
        int row = change.row;
        int col = change.col;
        char prev = change.prevChar;
        //reset the position
        drawingArray[row][col] = prev;
        
        return true;
    }
    
    // Redo the most recent undone drawing change.
    public boolean redo() {
        if(redoStack.isEmpty()) {
            return false;
        }
        
        DrawingChange change = redoStack.pop();//takes off stack and copies
        undoStack.push(change);
        
        //take the data of the drawing change
        int row = change.row;
        int col = change.col;
        char back = change.newChar;
        //reset the position
        drawingArray[row][col] = back;
        
        return true;
    }
    
    // Return a printable string version of the Canvas.
    public String toString() {
        String line = System.lineSeparator();
        String canvas = new String();
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                canvas = canvas + drawingArray[i][j];
            }
            canvas = canvas + line;
        }
        return canvas;
    }
    
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public char[][] getDrawingArray(){
        return drawingArray;
    }
    public DrawingStack getUndoStack() {
        return undoStack;
    }
    public DrawingStack getRedoStack() {
        return redoStack;
    }

    //Print the string representation of the Canvas
    public void printDrawing() {
        System.out.print(toString());
    }
    
    //Prints the whole undoStack
    public void printHistory() {
        System.out.println("Sorry man I got lazy :/");
    }
}
