///////////////////////////////////////////////////////////////////////////////
// Config.java Spring 2019
//
// This file contains constants used within your MasterMind program.
// This file will not be handed in, because testing Config.java files will
// be used to test your program. Your code must reference these constant
// values by the names defined below and not the values themselves.
//
///////////////////////////////////////////////////////////////////////////////

public class Config {

    /**
     * A debugging technique is to add statements like the following at key places in your program:
     * if (Config.DEBUG) { System.out.println("DEBUG: value=" + value); } Then you can turn on or
     * off all these statements simply by changing the value of DEBUG here.
     */
    static final boolean DEBUG = false;

    /**
     * The acceptable set of symbols and length for a code. Your code should
     * use CODE_SYMBOLS and CODE_POSITIONS for the symbols and number of positions.
     * 
     * With 6 symbols and 4 positions there are 6*6*6*6 == 6^4 == 1,296 possible
     * codes. With 8 symbols and 6 positions there are 8*8*8*8*8*8 == 8^6 == 262,144
     * possible codes. This is referred to as exponential growth. A problem that
     * seems just a little bit bigger actually may be much more difficult to solve.
     */
    public static final char[] CODE_SYMBOLS = new char[] {'1', '2', '3', '4', '5', '6'};
    public static final int CODE_POSITIONS = 4;

    /**
     * The maximum number of guesses for a user to win.
     */
    public static final int MAX_GUESSES = 10;

    /**
     *  The length of the hits array
     */
    public static final int HITS_ARRAY_LENGTH = 2;

    /**
     * A "black hit" indicates a matching symbol in the same position in the
     * hidden code and guess.
     */
    public static final int BLACK_HITS_INDEX = 0;

    /**
     * A "white hit" indicates a matching symbol but different position in the 
     * hidden code and guess that is not already accounted for with other 
     * hits.
     */
    public static final int WHITE_HITS_INDEX = 1;

    /**
     * The symbol that indicates a black hit.
     */
    public static final char BLACK_HITS_SYMBOL = 'B';

    /**
     * The symbol that indicates a white hit.
     */
    public static final char WHITE_HITS_SYMBOL = 'W';
}
