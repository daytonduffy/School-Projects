//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: MasterMind - BP1
// Files: (list of source files)
// Semester: Spring 2019
//
// Author: Dayton Duffy
// Email: drduffy@wisc.edu
// CS Login: dduffy
// Lecturer's Name: Jim Williams
// Lab Section: 315
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, roommates
// strangers, etc do.
//
// Persons: (identify each person and describe their help in detail)
// Online Sources: (identify each URL and describe its assistance in detail)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * The MasterMind class contains several methods used in order to assemble the game MasterMind. Each
 * method has a specific purpose and in the end from main the game will be played.
 * 
 * @author Dayton Duffy
 */
public class MasterMind {
    /**
     * Prompts the user for a value by displaying prompt. Note: This method should not add a new
     * line to the output of prompt.
     *
     * After prompting the user, the method will consume an entire line of input while reading an
     * int. Leading whitespace is ignored. If the value read is between min and max (inclusive),
     * that value is returned. Otherwise, output "Expected value between 0 and 10." where 0 and 10
     * are the values in the min and max parameters, respectively. Invalid input may be non-integer
     * in which case the same error message is displayed and the user is prompted again.
     * 
     * Note: This is a general purpose method to prompt for, read and validate an int within the min
     * and max. This method should be tested for any min and max.
     *
     * @param input  The Scanner instance to read from System.in.
     * @param prompt Output to the user.
     * @param min    The minimum acceptable int value (inclusive).
     * @param min    The maximum acceptable int value (inclusive).
     * @return Returns the value read from the user.
     */
    // method to prompt the player for an input within bounds
    public static int promptInt(Scanner input, String prompt, int min, int max) {
        int userValue = -1; // used for the return value
        boolean valid = false;

        do { // do loop so it reads at least once
            System.out.print(prompt);

            if (input.hasNextInt()) { // weeds out bad inputs
                userValue = input.nextInt();
                input.nextLine();
                valid = true;
                if (userValue < min || userValue > max) {// input between accepted range
                    System.out.println("Expected value between " + min + " and " + max + ".");
                    valid = false;
                }

            } else {// if there isn't an int to read from input
                input.next();
                System.out.println("Expected value between " + min + " and " + max + ".");
                valid = false;
            }

        } while (!valid); // continues prompting until a valid input

        return userValue; // returns value within the bounds
    }

    /**
    * Returns the index within arr of the first occurrence of the specified character.
    * If arr is null or 0 length then return -1. For all arrays, don't assume a length
    * but use the array .length attribute.
    * @param arr  The array to look through.
    * @param ch   The character to look for.
    * @return The index within the array of the first occurrence of the specified
    *     character or -1 if the character is not found in the array.
    */
    // method for returning the index of a matching character in an array
    public static int indexOf(char[] arr, char ch) {
        int i; // for the 'for' loop
        int chIndex = -1; // used as the value to return to the main

        if (arr == null || arr.length < 1) { // roots out bad inputs
            chIndex = -1;
        } else {
            for (i = 0; i < arr.length; ++i) { // returns the first instance of ch
                if (arr[i] == ch) {
                    chIndex = i;
                    break;
                }
            }
        }
        return chIndex; // returns the index of ch or -1 if failed
    }

    /**
     * Generates the hidden code to be guessed by the user. The hidden code
     * returned is an array of characters with length numPositions.
     * The characters in the array are randomly chosen, in order starting at index 0,
     * from the symbols array.
     *    rand.nextInt( symbols.length)
     * is used to determine the index in the symbols array of each character
     * in the code. For all arrays, don't assume a length but use the array .length attribute.
     * 
     * Example: 
     * if numPositions is 3 and symbols is the array {'A','B','C'}
     * the returned array will have a length of 3 and may contain any selection of 
     * the available symbols such as {'B','C','B'} or {'C','A','B'}.
     * 
     * @param rand A random number generator.
     * @param numPositions  The number of symbols in the code.
     * @param symbols  The symbols to choose from.
     * @return An array of length numPositions of randomly chosen symbols.
     */
    // method used for generating the games hidden code the player tries to guess
    public static char[] generateHiddenCode(Random rand, int numPositions, char[] symbols) {
        char[] hiddenCode = new char[numPositions];// array to return and store the hidden code
        int i;// used for the "for" loop

        for (i = 0; i < hiddenCode.length; ++i) {// fills the hidden code array with random symbols
                                                 // from the set
            hiddenCode[i] = symbols[rand.nextInt(symbols.length)];
        }
        if (Config.DEBUG) {
            System.out.println("DEBUG hiddenCode: " + Arrays.toString(hiddenCode));
        }

        return hiddenCode;// returns the generated hidden code
    }

    /**
    * Checks whether the code is the correct length and only contains valid symbols.
    * Uses the indexOf method you wrote to check whether each character in the input is in the 
    * symbols array.  If code or symbols are null then false is returned.
    * For all arrays, don't assume a length but use the array .length attribute.
    *   
    * @param numPositions  The required number of symbols in the code.
    * @param symbols  The allowed symbols in the code.
    * @param code  The code that is being checked.
    * @return true if the code is the correct length and has only valid symbols otherwise
    * returns false.
    */
    // method used for testing if the user's input guess is a valid guess with current conditions
    public static boolean isValidCode(int numPositions, char[] symbols, char[] code) {
        int i;// used for the "for" loop
        boolean valid = true;// boolean value to return

        if (numPositions != code.length) {// tests length accuracy
            valid = false;// returns false if the length isn't equal
        } else { // otherwise makes sure symbols are all allowed
            for (i = 0; i < numPositions; ++i) {
                if (!(indexOf(symbols, code[i]) >= 0)) {
                    valid = false;// returns false if there is an invalid symbol
                }
            }
        }

        return valid;// returns true if valid false if not
    }

    /**
     * Prompts the user for a string value by displaying prompt.
     * Note: This method should not add a new line to the output of prompt. 
     *
     * After prompting the user, the method will read an entire line of input and remove
     * leading and trailing whitespace. If the line equals the single character '?'
     * then return null. If the line is a valid code (determine with isValidCode) return
     * the code, otherwise print "Invalid code." and prompt again.
     *
     * @param input The Scanner instance to read from System.in
     * @param prompt The user prompt.
     * @param numPositions The number of code positions.
     * @param symbols The valid symbols.
     * @return Returns null or a valid code.
     */
    // method used to get the user's guess
    public static char[] promptForGuess(Scanner input, String prompt, int numPositions,
        char[] symbols) {

        char[] userGuess = new char[numPositions];// array to be returned later

        int i;// used for the "for" loop
        String userGu; // used to hold the initial user input before placing in an array
        boolean valid = false;// boolean for keeping the loop going

        do {// do loop to keep prompting for guesses until one is valid
            System.out.print(prompt);

            userGu = input.nextLine();
            userGu = userGu.trim();
            if (userGu.equals("")) {
                System.out.println("Invalid code.");
                continue;
            }
            if (userGu.length() > 0) {

                char[] ugNonValid = new char[userGu.length()];// a place to hold the array prevents
                                                              // accidental return

                if (userGu.equals("?")) {// specific case
                    return null;
                }
                
                for (i = 0; i < userGu.length(); ++i) {// fills the place holder array
                    ugNonValid[i] = userGu.charAt(i);
                }

                if (isValidCode(numPositions, symbols, ugNonValid)) {// either takes the valid input
                                                                     // or sets loop going again
                    userGuess = ugNonValid;
                    valid = true;
                } else {
                    System.out.println("Invalid code.");
                }
            }
            
        } while (valid == false);// if the input is invalid the loop will continue

        if (Config.DEBUG) {
            System.out.println("DEBUG userGuess: " + Arrays.toString(userGuess));
        }

        return userGuess;// returns only a valid user guess
    }

    /**
     * Returns the sum of "black hits" and "white hits" between the hiddenCode 
     * and guess.  A "black hit" indicates a matching symbol in the same position in the
     * hiddenCode and guess.  A "white hit" indicates a matching symbol but different
     * position in the hiddenCode and guess that is not already accounted for with other 
     * hits.
     * 
     * Algorithm to determine the total number of hits:
     * 
     * Count the number of each symbol in the hiddenCode, and separately count the
     * number of each symbol in the guess. For each symbol, determine the minimum of the
     * count of that symbol in the hiddenCode and the count of that symbol found in the guess.  
     * The total number of hits, black and white, is the sum of these minimums for 
     * all the symbols.
     * 
     * Algorithm based on Donald Knuth, 1976, The Computer As Master Mind, 
     *      J. Recreational Mathematics, Vol. 9(1)
     *      
     * Suggestion: To do the count, create an array of int the length of the number of symbols.
     * For each symbol use the indexOf method you wrote to determine the index in the array to increment
     * the symbols count.
     *  
     * @param hiddenCode The code hidden from the user.
     * @param guess  The user's guess of the code.
     * @param symbols  The possible symbols in the hiddenCode and guess.
     * @return The total number of hits.
     */
    // method used to count the total number of hits, used later for the specific hits
    public static int countAllHits(char[] hiddenCode, char[] guess, char[] symbols) {
        int[] countGuess = new int[symbols.length];// array for counting the player guess
        int[] countHidden = new int[symbols.length];// array for counting the hidden code
        int i; // for the 'for' loop
        int count = 0; // to be returned

        for (i = 0; i < hiddenCode.length; ++i) {// counts symbols in the hidden code

            countHidden[indexOf(symbols, hiddenCode[i])] =
                countHidden[indexOf(symbols, hiddenCode[i])] + 1;
        }

        for (i = 0; i < guess.length; ++i) {// counts symbols in guess

            countGuess[indexOf(symbols, guess[i])] = countGuess[indexOf(symbols, guess[i])] + 1;
        }

        for (i = 0; i < symbols.length; ++i) {// returns the minimum count of each symbol
            count = count + Math.min(countGuess[i], countHidden[i]);
        }

        return count; // returns the total number of hits
    }

    /**
     * Returns the number of each kind of hit the guess has with the code. 
     * The results are an array of length Config.HITS_ARRAY_LENGTH. 
     * The count of the number of symbols in the guess that correspond in position 
     * and symbol with the hidden code are recorded in the Config.BLACK_HITS_INDEX 
     * position within the result array. The number of symbols that match between
     * the guess and the hidden code but are in different positions and not otherwise
     * counted are recorded in the Config.WHITE_HITS_INDEX within the result array.
     * 
     * Algorithm:
     * Count the number of black hits - the number of positions in the guess and hidden code
     * that have the same symbol.
     * Count the total number of hits using countAllHits and subtract to find the number
     * of white hits. White hits are symbols that match between guess and hiddenCode that
     * are not in the same position and not already accounted for with other hits.
     * 
     * @param hiddenCode  The code the user is trying to guess.
     * @param guess  The user's guess.
     * @param symbols  The possible symbols in the hiddenCode and guess.
     * @return The array containing the number of "black hits" and "white hits".
     */
    // method used to determine the number of black hits and white hits
    public static int[] determineHits(char[] hiddenCode, char[] guess, char[] symbols) {
        int[] results = new int[Config.HITS_ARRAY_LENGTH];// array to be returned
        int i; // used for the 'for' loop
        results[0] = Config.BLACK_HITS_INDEX;// set up as requested
        results[1] = Config.WHITE_HITS_INDEX;// set up as requested

        for (i = 0; i < guess.length; ++i) {// determines the number of black hits
            if (hiddenCode[i] == guess[i]) {
                results[0] = results[0] + 1;// increment the number of black hits when they match
            }
        }

        results[1] = countAllHits(hiddenCode, guess, symbols) - results[0];// total hits - black
                                                                           // hits = white hits

        if (Config.DEBUG) {
            System.out.println("DEBUG results: " + Arrays.toString(results));
        }

        return results; // return the resulting array of black and white hits
    }

    /**
     * Prints out the game board showing the guesses and the corresponding hits.
     * See output examples. 
     * Game board example:
     *  6) [4, 5, 2, 4] BBBB
     *  5) [4, 4, 2, 5] BBWW
     *  4) [4, 4, 2, 4] BBB
     *  3) [1, 3, 3, 3] 
     *  2) [2, 3, 3, 3] W
     *  1) [1, 1, 2, 2] B
     * 
     * Only rows with non-null guesses are shown. The number on the left is the guess, 
     * so the guesses are shown from last to first.
     * Looking at one line in detail:
     *  5) [4, 4, 2, 5] BBWW
     * 				      ^^  2 white hits, the 2nd 4 and 5 (we don't know which until solved)
     *                  ^^ 2 black hits, the 1st 4 and 2 (we don't know which until solved)
     *     ^^^^^^^^^^^^ the guess output using Arrays.toString()
     *  ^^ the guess number
     * The symbols B and W are the characters from Config.BLACK_HIT_SYMBOL and
     * Config.WHITE_HIT_SYMBOL. All the black hits will be shown before the white hits.
     * The length of all arrays should be determined using the array .length attribute, not
     * assumed from a constant.
     * 
     * @param guesses  The array of guesses. Each row is a guess or null (meaning no guess
     * yet).
     * @param hits  The array of hits. Each row is the hits from determineHits for 
     * the corresponding guess in the parallel guesses array, or null.
     */
    // method used for printing out the status of the game so far.
    public static void printBoard(char[][] guesses, int[][] hits) {
        int i; // used for the main for loop
        int j; // used for 'for' loops
        char[] testFill = new char[guesses[0].length];

        for (i = guesses.length; i > 0; --i) {
            if (Arrays.equals(guesses[i - 1], testFill)) {

            } else {
                System.out.print(" " + i + ") ");// prints "#) "
                System.out.print(Arrays.toString(guesses[i - 1]) + " "); // prints: "[?, ?, ?, ?] "

                for (j = 0; j < hits[i - 1][Config.BLACK_HITS_INDEX]; ++j) {
                    System.out.print(Config.BLACK_HITS_SYMBOL); // prints B's

                }
                for (j = 0; j < hits[i - 1][Config.WHITE_HITS_INDEX]; ++j) {
                    System.out.print(Config.WHITE_HITS_SYMBOL); // prints W's

                }
                System.out.println();// prints a new line for the next iteration of the for loop
            }
        }
    }

    /**
     * The MasterMind main method that contains the welcome and the main game
     * loop. Carefully examine example output to help answer questions on prompts and
     * how this program should work. 
     * 
     * Algorithm:
     * Use appropriate constants from Config. For example, to create an array use Config.MAX_GUESSES,
     *     but once an array exists don't use the constants but use the array .length attribute.
     * Determine seed or not (call promptInt with Integer.MIN_VALUE, Integer.MAX_VALUE)
     * Display welcome message.
     * Generate the hidden code (call generateHiddenCode)
     * Create 2D arrays for guesses and corresponding hits. Initially every row is null
     *     until guesses are made and hits are determined for a guess.
     */
    /**     
    *     
    * (milestone 3) enumerate all the possibilities (call enumeratePossibilities)
    * Loop
    *     Prompt for guess (call promptForGuess)
    *     (milestone 3) If guess is null then call computerGuess
    *     Determine how many black and white hits (call determineHits)
    *     Output the board (call printBoard)
    *     (milestone 3) Output number of remaining possibilities
    * End loop when won or lost  
    * Output won or lost message.
    * 
    * @param args  unused
    */
    // this is the main method where the main game loop is
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        Random rand = new Random(promptInt(scnr, "Enter seed (0 for unrepeatable): ",
            Integer.MIN_VALUE, Integer.MAX_VALUE));// gets seed from player

        char[][] guesses = new char[Config.MAX_GUESSES][Config.CODE_POSITIONS];// 2D arary for
                                                                               // guesses
        int[][] hits = new int[Config.MAX_GUESSES][Config.HITS_ARRAY_LENGTH];// 2D array for hits

        char[] symbols = Config.CODE_SYMBOLS; // simplification
        char[] hiddenCode = new char[Config.CODE_POSITIONS]; // array to hold the hidden code after
                                                             // generation
        char[] testCase = new char[Config.CODE_POSITIONS];
        int i; // used for the 'for' loop
        int numGuesses = 0; // used to count guesses
        int numHints = 0; // counts number of hints
        int remainingPossibilities = 1; // used to count codes left


        char[][] possibilities = enumeratePossibilities(Config.CODE_POSITIONS, symbols);

        System.out.println("Welcome to Master Mind!");// welcome

        hiddenCode = generateHiddenCode(rand, hiddenCode.length, symbols);// generates hidden code

        System.out.println("I have a " + Config.CODE_POSITIONS + " symbol code using "
            + Arrays.toString(symbols) + ".");
        System.out.println("Can you guess my code within " + Config.MAX_GUESSES + " guesses?");// gives
                                                                                               // symbols

        for (i = 0; i < Config.MAX_GUESSES; ++i) {// main for loop of the game
            testCase =
                promptForGuess(scnr, "Enter guess (? for help): ", hiddenCode.length, symbols);// for
                                                                                               // '?'
                                                                                               // exception

            if (testCase == null) { // testing for ? exception
                guesses[i] = computerGuess(possibilities); // stores computer guess if "?"
                numHints = numHints + 1;
            } else {
                guesses[i] = testCase; // stores the guess if it works
            }

            numGuesses = numGuesses + 1;// increments the guess counter

            hits[i] = determineHits(hiddenCode, guesses[i], symbols); // determines hits

            printBoard(guesses, hits);// always print the full board

            remainingPossibilities =
                updatePossibilities(guesses[i], hits[i], possibilities, symbols);

            if (Arrays.equals(guesses[i], hiddenCode)) {// win condition
                System.out.println("remaining possibilities: 1");
                System.out.println("Congratulations! You guessed code with only " + numGuesses
                    + " guesses and " + numHints + " hints!");
                break;
            } else {
                System.out.println("remaining possibilities: " + remainingPossibilities);
            }
        }

    }

    /**
     * Determine the next code in sequence given the ordered symbols and
     * a code. See MasterMindTests.testNextCode() method for various test cases.
     * Most significant positions are the left most, just like for a number 
     * such as 1234, where 1 is the most significant digit.
     * 
     * Consider how you would add 1 to 199. Work out on paper.  Now try with
     * the symbols A, B, C in that order. If you added B to BC what would
     * the result be? CA?
     * 
     * Algorithm:
     * Loop from least significant position to the most significant
     *     Find the index of the symbol
     *     if least significant position
     *         if last symbol then 
     *             set to first symbol and carry
     *         else set next symbol
     *     else 
     *         if carry and last symbol
     *             set to first symbol and keep carry set
     *         else if carry and not last symbol
     *             set next symbol, clear carry
     *         else 
     *             no carry, so keep symbol
     *         end if
     *     end if
     * End loop
     * If carry then prepend an additional symbol. Since, in decimal, leading 0's 
     * are assumed then we assume the same for any symbols. So, we would prepend
     * the 2nd symbol, in decimal a 1.   
     *     
     * @param code   A code with the symbols.
     * @param symbols  The symbols to use for the code.
     * @return  The next code in the sequence based on the order of the symbols.
     */
    // method used for generating the next code sequentially
    public static char[] nextCode(char[] code, char[] symbols) {
        char[] nextCode = new char[code.length]; // array to be returned
        int i; // used in for loop
        int index; // used to track indexOf
        boolean carry = false; // used to track if a symbol needs to carry or not
        boolean lastSymbol = false; // used to simplify reading the code

        for (i = code.length - 1; i >= 0; --i) {// loops from last symbol to the first in the code
            index = indexOf(symbols, code[i]);// Find the index of the symbol
            if (index == (symbols.length - 1)) {// for easy reading
                lastSymbol = true; // lets me use boolean instead of expression
            }

            if (i == (code.length - 1)) {// if least significant position
                if (lastSymbol) {// if last symbol then
                    nextCode[i] = symbols[0];// set to first symbol
                    carry = true; // and carry
                } else {
                    nextCode[i] = symbols[index + 1]; // else set next symbol
                }
            } else {
                if (carry && lastSymbol) {// if carry and last symbol
                    nextCode[i] = symbols[0];// set to first symbol and keep carry set
                } else if (carry && !lastSymbol) {// else if carry and not last symbol
                    nextCode[i] = symbols[index + 1];// set next symbol
                    carry = false;// clear carry
                } else {
                    nextCode[i] = symbols[index];// no carry, so keep symbol
                }
            }
            lastSymbol = false; // resets for next iteration
        }

        if (carry) {
            char[] tempArray = new char[nextCode.length + 1]; //
            tempArray[0] = symbols[1];
            for (i = 0; i < nextCode.length; ++i) {
                tempArray[i + 1] = nextCode[i];
            }
            nextCode = tempArray;
        }



        return nextCode; // returns the next code in sequence
    }

    /**
     * List all the possibilities (permutations) of codes for the specified number of 
     * positions and the provided codes.
     * 
     * Algorithm:
     * Create an array the length being the number of possibilities (permutations). 
     *     For example, 3 symbols in each of 4 positions means there are 3*3*3*3 or 3^4 
     *     which equals 81 permutations.
     * Determine the "first" code (all positions having the same first symbol).
     * For each permutation call nextCode to generate the next code from the current.
     * 
     * If numPositions is less than or equal to 0 or symbols is 0 length or null
     * then return null.
     * 
     * @param numPositions The number of positions in a code.
     * @param symbols The possible symbols used in a code.
     * @return An array of all the possible codes that can be generated from the 
     * symbols for the numPositions.
     */
    // method used to generate every possible code with given symbols and positions
    public static char[][] enumeratePossibilities(int numPositions, char[] symbols) {
        if (symbols == null) {// if symbols is empty skip the method
            return null;
        }
        if ((numPositions <= 0) || (symbols.length == 0)) {// if symbols or numPositions are wrong
                                                           // skip
            return null;
        }
        int i;// used in for loop
        int numSymbols = symbols.length;// used for simplicity
        int permutations = 1;// counts the number of permutations
        for (i = 0; i < numPositions; ++i) {// counts permutations
            permutations = permutations * numSymbols;
        }

        char[][] allCodes = new char[permutations][numPositions];// 2D array to hold all
                                                                 // possibilities

        for (i = 0; i < numPositions; ++i) {// sets first code
            allCodes[0][i] = symbols[0];
        }

        for (i = 1; i < permutations; ++i) {// uses next code to fill the rest of the 2D array
            allCodes[i] = nextCode(allCodes[i - 1], symbols);
        }


        return allCodes; // 2D array to be returned of all possibilities
    }

    /**
     * Updates the remaining possibilities array and returns the number
     * of possibilities.
     * The hiddenCodeHits parameter contains the black and white hits when the guess is compared 
     * to the code. The possibilities parameter contains all the possible remaining candidates
     * for the hidden code. Determine the hits for each non-null guess when compared to each 
     * possibility and only keep the possibilities that match the result parameter hits.
     * Remove a possibility from the array of possibilities by setting it to null.
     * 
     * @param guess  The current guess
     * @param hiddenCodeHits  The hits when guess is compared to hiddenCode.
     * @param possibilities The remaining codes that contain the hidden code.
     * @param symbols The potential symbols in the codes.
     * @return The number of remaining possibilities.
     */
    // method used to update the 2D array with the still active possibilities and returns the number
    public static int updatePossibilities(char[] guess, int[] hiddenCodeHits,
        char[][] possibilities, char[] symbols) {
        int numPosibilities = 0;// used to count the active possibilities
        int i; // used for the for loop

        for (i = 0; i < possibilities.length; ++i) {
            if (possibilities[i] == null) {// skips space if null already
                continue;
            }
            if (!Arrays.equals(hiddenCodeHits, determineHits(possibilities[i], guess, symbols))
                || Arrays.equals(possibilities[i], guess)) {// removes non matching hit
                                                            // possibilities or current guess
                possibilities[i] = null;
            }

            if (possibilities[i] != null) {// increments count if the space isn't null
                numPosibilities = numPosibilities + 1;
            }
        }

        return numPosibilities; // return the number of still possible solutions
    }

    /**
     * Select the first remaining code (lowest index) from possibilities.
     * If no codes are left then return null.
     * 
     * @param possibilities The array of remaining possible codes.
     * @return A code from the array.
     */
    // method used to return a guess "made" by the computer
    public static char[] computerGuess(char[][] possibilities) {
        int i;
        char[] compGuess = new char[Config.CODE_POSITIONS];
        char[] testTime = new char[Config.CODE_POSITIONS];// used to make special case easy

        for (i = 0; i < possibilities.length; ++i) {// will look through every possibility
            if (possibilities[i] != null) {// looks for first instance of a non null guess
                compGuess = possibilities[i]; // sets first possibility to compGuess
                break; // breaks from loop
            }
        }
        if (Arrays.equals(compGuess, testTime)) {// if nothing has been done to the array
            return null;
        }
        return compGuess; // guess to be returned
    }
}
