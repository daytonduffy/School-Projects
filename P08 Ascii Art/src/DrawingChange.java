
public class DrawingChange {
    public final int row; // row (y-coordinate) for this DrawingChange
    public final int col; // col (x-coordinate) for this DrawingChange
    public final char prevChar; // previous character in the (row,col) position
    public final char newChar; // new character in the (row,col) position
    
    public DrawingChange(int row, int col, char prevChar, char newChar) {
        this.row = row;
        this.col = col;
        this.prevChar = prevChar;
        this.newChar = newChar;
    }
}
