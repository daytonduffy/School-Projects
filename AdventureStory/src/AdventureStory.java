//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: AdventureStory - BP2
// Files: AdventureStory.java TestAdventureStory.java
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
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;

/**
 * This class contains a methods for playing the AdventureStory class that's been developed.
 * 
 * @author Dayton Duffy
 *
 */
public class AdventureStory {
    enum Parse {
        description, transition, comment, error, start
    };

    /**
     * Prompts the user for a value by displaying prompt. Note: This method should not add a new
     * line to the output of prompt.
     *
     * After prompting the user, the method will consume an entire line of input while reading an
     * int. If the value read is between min and max (inclusive), that value is returned. Otherwise,
     * "Invalid value." terminated by a new line is output and the user is prompted again.
     *
     * @param sc     The Scanner instance to read from System.in.
     * @param prompt The name of the value for which the user is prompted.
     * @param min    The minimum acceptable int value (inclusive).
     * @param max    The maximum acceptable int value (inclusive).
     * @return Returns the value read from the user.
     */
    public static int promptInt(Scanner sc, String prompt, int min, int max) {
        int userValue = -1; // used for the return value
        boolean valid = false;

        do { // do loop so it reads at least once
            System.out.print(prompt);

            if (sc.hasNextInt()) { // weeds out bad inputs
                userValue = sc.nextInt();
                sc.nextLine();
                valid = true;
                if (userValue < min || userValue > max) {// input between accepted range
                    System.out.println("Invalid value.");
                    valid = false;
                }

            } else {// if there isn't an int to read from input
                sc.next();
                System.out.println("Invalid value.");
                valid = false;
            }

        } while (!valid); // continues prompting until a valid input

        return userValue; // returns value within the bounds
    }

    /**
     * Prompts the user for a char value by displaying prompt.
     * Note: This method should not add a new line to the output of prompt. 
     *
     * After prompting the user, the method will read an entire line of input and return the first
     * non-whitespace character converted to lower case.
     *
     * @param sc The Scanner instance to read from System.in
     * @param prompt The user prompt.
     * @return Returns the first non-whitespace character (in lower case) read from the user. If 
     *         there are no non-whitespace characters read, the null character is returned.
     */
    public static char promptChar(Scanner sc, String prompt) {
        String userIn;// string used to hold input
        char userChar = '\0';// value to be returned

        System.out.print(prompt);

        if (sc.hasNext()) {// makes sure there is input, leave '\0' otherwise
            userIn = sc.nextLine();
            userIn = userIn.trim();// cut out white space
            userIn = userIn.toLowerCase();// force to lower case no matter what

            userChar = userIn.charAt(0); // assign first character to return value
        }


        return userChar;// char to be returned
    }

    /**
     * Prompts the user for a string value by displaying prompt.
     * Note: This method should not add a new line to the output of prompt. 
     *
     * After prompting the user, the method will read an entire line of input, removing any leading and 
     * trailing whitespace.
     *
     * @param sc The Scanner instance to read from System.in
     * @param prompt The user prompt.
     * @return Returns the string entered by the user with leading and trailing whitespace removed.
     */
    public static String promptString(Scanner sc, String prompt) {
        String userStr = "";// string used to hold input


        System.out.print(prompt);

        if (sc.hasNext()) {// makes sure there is input
            userStr = sc.nextLine();
            userStr = userStr.trim();// cut out white space
        }

        return userStr;// char to be returned
    }

    /**
     * Saves the current position in the story to a file.
     *
     * The format of the bookmark file is as follows:
     * Line 1: The value of Config.MAGIC_BOOKMARK
     * Line 2: The filename of the story file from storyFile
     * Line 3: The current room id from curRoom
     *
     * Note: use PrintWriter to print to the file.
     *
     * @param storyFile The filename containing the cyoa story.
     * @param curRoom The id of the current room.
     * @param bookmarkFile The filename of the bookmark file.
     * @return false on an IOException, and true otherwise.
     */
    public static boolean saveBookmark(String storyFile, String curRoom, String bookmarkFile) {
        try {
            File file = new File(bookmarkFile);
            PrintWriter printWriter = new PrintWriter(file);

            printWriter.println(Config.MAGIC_BOOKMARK);
            printWriter.println(storyFile);
            printWriter.println(curRoom);
            printWriter.close();

        } catch (IOException e) {
            return false;
        }

        return true;
    }

    /**
     * Loads the story and current location from a file either a story file or a bookmark file. 
     * NOTE: This method is partially implementd in Milestone 2 and then finished in Milestone 3.
     * 
     * The type of the file will be determined by reading the first line of the file. The first
     * line of the file should be trimmed of whitespace.
     *
     * If the first line is Config.MAGIC_STORY, then the file is parsed using the parseStory method.
     * If the first line is Config.MAGIC_BOOKMARK, the the file is parsed using the parseBookmark
     * method.
     * Otherwise, print an error message, terminated by a new line, to System.out, displaying: 
     * "First line: trimmedLineRead does not correspond to known value.", where trimmedLineRead is 
     * the trimmed value of the first line from the file. 
     *
     * If there is an IOException, print an error message, terminated by a new line, to System.out,
     * saying "Error reading file: fName", where fName is the value of the parameter.
     *
     * If there is an error reading the first line, print an error message, terminated by a new 
     * line, to System.out, displaying: "Unable to read first line from file: fName", where fName is
     * the value of the parameter. 
     *
     * This method will be partially implemented in Milestone #2 and completed in Milestone #3 as 
     * described below.
     *
     * Milestone #2: Open the file, handling the IOExceptions as described above. Do not read the
     * the first line: Assume the file is a story file and call the parseStory method.
     *
     * Milestone #3: Complete the implementation of this method by reading the first line from the
     * file and following the rules of the method as described above.
     *
     * @param fName The name of the file to read.
     * @param rooms The ArrayList structure that will contain the room details. A parallel ArrayList
     *              trans.
     * @param trans The ArrayList structure that will contain the transition details. A parallel 
     *              ArrayList to rooms. Since the rooms can have multiple transitions, each room 
     *              will be an ArrayList<String[]> with one String[] per transition with the 
     *              overall structure being an ArrayList of ArrayLists of String[].
     * @param curRoom An array of at least length 1. The current room id will be stored in the cell
     *                at index 0.
     * @return false if there is an IOException or a parsing error. Otherwise, true. 
     */
    public static boolean parseFile(String fName, ArrayList<String[]> rooms,
        ArrayList<ArrayList<String[]>> trans, String[] curRoom) {
        try {
            File f = new File(fName);// open file
            Scanner scnr = new Scanner(f);// open file
            String firstLine = scnr.nextLine().trim();
            
            if (firstLine.equals(Config.MAGIC_BOOKMARK)) {
                parseBookmark(scnr, rooms, trans, curRoom);
            } else if (firstLine.equals(Config.MAGIC_STORY)) {
                parseStory(scnr, rooms, trans, curRoom);// run parseStory
            } else {
                System.out
                    .println("First line: " + firstLine + " does not correspond to known value.");
                scnr.close();
                return false;
            }

        } catch (IOException e) {// handles IOExcpetion
            System.out.println("Error reading file: " + fName);
            return false;// return false if IOException
        } catch (Exception e) {
            System.out.println("Unable to read first line from file: " + fName);
            return false;// return false if Exception
        }

        return true;
    }

    /**
     * Loads the story and the current room from a bookmark file. This method assumes that the first
     * line of the file, containing Config.MAGIC_BOOKMARK, has already been read from the Scanner.
     *
     * The format of a bookmark file is as follows:
     * Line No: Contents
     *       1: Config.MAGIC_BOOKMARK
     *       2: Story filename
     *       3: Current room id
     *
     * As an example, the following contents would load the story Goldilocks.story and set the 
     * current room to id 7.
     *
     * #!BOOKMARK
     * Goldilocks.story
     * 7
     *
     * Your method should not duplicate the code from the parseFile method. It must use the
     * parseFile method to populate the rooms and trans methods based on the contents of the story
     * filename read and trimmed from line 2 of the file. The value of for the cell at index 0 of 
     * curRoom is the trimmed value read on line 3 of the file.
     *
     * @param sc The Scanner object buffering the input file to read.
     * @param rooms The ArrayList structure that will contain the room details. A parallel ArrayList
     *              trans.
     * @param trans The ArrayList structure that will contain the transition details. A parallel 
     *              ArrayList to rooms.
     * @param curRoom An array of at least length 1. The current room id will be stored in the cell
     *                at index 0.
     * @return false if there is a parsing error. Otherwise, true. 
     */
    public static boolean parseBookmark(Scanner sc, ArrayList<String[]> rooms,
        ArrayList<ArrayList<String[]>> trans, String[] curRoom) {

        // assumes first line has been read already
        String fileName = sc.nextLine().trim();// get the story file
        String bookId = sc.nextLine().trim();// store current room ID
        sc.close();
        if (parseFile(fileName, rooms, trans, curRoom)) {// only works if we fill the arrays
                                                         // correctly
            curRoom[0] = bookId;// store current room ID
        } else {
            return false;
        }

        return true;
    }

    /**
     * This method parses a story adventure file.
     *
     * The method will read the contents from the Scanner, line by line, and populate the parallel 
     * ArrayLists rooms and trans. As such the story files have a specific structure. The order of
     * the rooms in the story file correspond to the order in which they will be stored in the 
     * parallel ArrayLists.
     *
     * When reading the file line-by-line, whitespace at the beginning and end of the line should be
     * trimmed. The file format described below assumes that whitespace has been trimmed.
     *
     * Story file format:
     *
     * - Any line (outside of a room's description) that begins with a '#' is considered a comment 
     *   and should be ignored.
     * - Room details begin with a line starting with 'R' followed by the room id, terminated with 
     *   a ':'. Everything  after the first colon is the room title. The substrings of the room id 
     *   and the room title should be trimmed.
     * - The room description begins on the line immediate following the line prefixed with 'R',
     *   containing the room id, and continues until a line of ";;;" is read.
     *   - The room description may be multi-line. Every line after the first one, should be 
     *     prefixed with a newline character ('\n'), and concatenated to the previous description 
     *     lines read for the current room.
     * - The room transitions begin immediately after the line of ";;;", and continue until a line
     *   beginning with 'R' is encountered. There are 3 types of transition lines:
     *   - 1 -- Terminal Transition: A terminal transition is either Config.SUCCESS or 
     *                               Config.FAIL. This room is the end of the story. 
     *                               This value should be stored as a transition with the String at
     *                               index Config.TRAN_DESC set to the value read. The rest of the 
     *                               Strings in the transition String array should be null.
     *                               A room with a terminal transition can only have one transition 
     *                               associated with it. Any additional transitions should result in
     *                               a parse error.
     *   - 2 -- Normal Transition: The line begins with ':' followed by the transition description, 
     *                             followed by " -> " (note the spaces), followed by the room id to 
     *                             transition to. For normal transitions (those without a transition
     *                             weight), set the value at index Config.TRAN_PROB to null.
     *   - 3 -- Weighted Transition: Similar to a normal transition except that there is a 
     *                               probability weight associated with the transition. After the 
     *                               room id (as described in the normal transition) is a '?' 
     *                               followed by the probability weight. 
     *   - You can assume that room ids do not contain a '?'.
     *   - You can assume that Config.SUCCESS and Config.FAIL do not start with a ':'.
     *
     * In the parallel ArrayLists rooms and trans, the internal structures are as follows:
     *
     * The String array structure for each room has a length of Config.ROOM_DET_LEN. The entries in
     * the array are as follows:
     * Index              | Description
     * --------------------------------------------
     * Config.ROOM_ID     | The room id
     * Config.ROOM_TITLE  | The room's title
     * Config.ROOM_DESC   | The room's description
     *
     * The String array structure for each transition. Note that each room can have multiple 
     * transitions, hence, the ArrayList of ArrayLists of String[]. The length of the String[] is
     * Config.TRAN_DET_LEN. The entries in the String[] are as follows:
     * Index               | Description
     * ------------------------------------------------------------------
     * Config.TRAN_DESC    | The transition description
     * Config.TRAN_ROOM_ID | The transition destination (id of the room) 
     * Config.TRAN_PROB    | The probability weight for the transition
     *
     * If you encounter a line that violates the story file format, the method should print out an 
     * error message, terminated by a new line, to System.out displaying: 
     * "Error parsing file on line: lineNo: lineRead", where lineNo is the number of lines read
     * by the parseStory method (i.e. ignoring the magic number if Milestone #3), and lineRead is 
     * the offending trimmed line read from the Scanner.
     *
     * After parsing the file, if rooms or trans have zero size, or they have different sizes, print
     * out an error message, terminated by a new line, to System.out displaying:
     * "Error parsing file: rooms or transitions not properly parsed."
     *
     * After parsing the file, if curRoom is not null, store the reference of the id of the room at 
     * index 0 of the rooms ArrayList into the cell at index 0 of curRoom. 
     *
     * Hint: This method only needs a single loop, reading the file line-by-line.
     * 
     * Hint: To successfully parse the file, you will need to maintain a state of where you are in 
     *       the file. I.e., are you parsing the description, parsing the transitions; is there an 
             error; etc? One suggestion would be to use an enum to enumerate the different states. 
     *
     * @param sc The Scanner object buffering the input file to read.
     * @param rooms The ArrayList structure that will contain the room details.
     * @param trans The ArrayList structure that will contain the transition details.
     * @param curRoom An array of at least length 1. The current room id will be stored in the cell
     *                at index 0.
     * @return false if there is a parsing error. Otherwise, true. 
     */
    public static boolean parseStory(Scanner sc, ArrayList<String[]> rooms,
        ArrayList<ArrayList<String[]>> trans, String[] curRoom) {

        Parse state = Parse.start;
        String currLine = "";
        String roomDesc = "";
        String endPoint;
        ArrayList<String[]> currTranRoom = new ArrayList<String[]>();
        String[] currTran = new String[Config.TRAN_DET_LEN];
        String[] currRoom = new String[Config.ROOM_DET_LEN];
        int i = 0;
        int lineNo = 0;
        int firstChar = 0;
        int count = 0;
        int count2 = 0;
        int indexPoint = 0;
        int indexPoint2 = 0;
        boolean check2 = true;
        boolean error = true;
        

        while (sc.hasNextLine()) {
            
            currLine = sc.nextLine().trim();// take in and trim next line
            
            lineNo = lineNo + 1; // count what line we're on

            if ((currLine.length() <= 0)) {// if a comment no other statements will execute
                if (state == Parse.description)
                    roomDesc = roomDesc + "\n";
                continue;// skip
            } else if ((currLine.equals(";;;")) && (state != Parse.transition)) {// if end of desc
                                                                                 // set it in the
                                                                                 // array
                state = Parse.transition;
                currRoom[Config.ROOM_DESC] = roomDesc;
                roomDesc = "";
                String[] tempRoom = new String[Config.ROOM_DET_LEN];
                tempRoom[Config.ROOM_ID] = currRoom[Config.ROOM_ID];
                tempRoom[Config.ROOM_TITLE] = currRoom[Config.ROOM_TITLE];
                tempRoom[Config.ROOM_DESC] = currRoom[Config.ROOM_DESC];
                rooms.add(tempRoom);

                
                continue; // don't continue in loop with line of ';'

            } else if ((currLine.charAt(firstChar) == 'R') && (state != Parse.description)) {// new
                                                                                             // room
                state = Parse.description;
                check2 = true;


                indexPoint = currLine.indexOf(':');
                indexPoint2 = currLine.indexOf('R');
                  
                currRoom[Config.ROOM_ID] = currLine.substring(indexPoint2 + 1, indexPoint).trim();
                currRoom[Config.ROOM_TITLE] = currLine.substring(indexPoint + 1).trim();

                
                continue;// don't continue with loop
            } else if ((currLine.charAt(firstChar) == '#') && (state != Parse.description)) {// if a
                                                                                             // comment
                                                                                             // nothing
                                                                                             // else
                                                                                             // goes
                continue;// skip
            }


            if (state == Parse.description) {// for each line after the room title will be
                                             // concatenated
                if (check2) {
                    roomDesc = roomDesc + currLine;
                    check2 = false;
                } else {
                    roomDesc = roomDesc + '\n' + currLine;
                }

            } else if (state == Parse.transition) {
                
                if (currLine.charAt(firstChar) == ':') {
                    
                    indexPoint = currLine.indexOf(':') + 2;// gives index of first char of desc
                    
                    if(indexPoint > currLine.indexOf(" -> ")) {
                        indexPoint = 1;
                    }
                    
                    currTran[Config.TRAN_DESC] =
                        currLine.substring(indexPoint, currLine.indexOf(" -> ")).trim();

                    indexPoint = currLine.indexOf(" -> ") + 4;// gives index of room id
                    endPoint = currLine.substring(indexPoint);
                    
                    if (endPoint.indexOf("?") >= 0) {// if the weight is there to be used
                        indexPoint2 = endPoint.indexOf('?');
                        
                        currTran[Config.TRAN_ROOM_ID] =
                            endPoint.substring(0, indexPoint2).trim();

                        currTran[Config.TRAN_PROB] = endPoint.substring(indexPoint2 + 1).trim();
                        
                        
                    } else {
                        currTran[Config.TRAN_ROOM_ID] = currLine.substring(indexPoint).trim();
                        currTran[Config.TRAN_PROB] = null;
                    }

                    String[] tempTran = new String[Config.TRAN_DET_LEN];
                    tempTran[Config.TRAN_DESC] = currTran[Config.TRAN_DESC];
                    tempTran[Config.TRAN_ROOM_ID] = currTran[Config.TRAN_ROOM_ID];
                    tempTran[Config.TRAN_PROB] = currTran[Config.TRAN_PROB];
                    currTranRoom.add(tempTran);

                } else if (currLine.equals(Config.SUCCESS) || currLine.equals(Config.FAIL)) {
                    String[] tempTran2 = new String[Config.TRAN_DET_LEN];
                    tempTran2[Config.TRAN_DESC] = currLine;
                    tempTran2[Config.TRAN_ROOM_ID] = null;
                    tempTran2[Config.TRAN_PROB] = null;
                    currTranRoom.add(tempTran2);

                }

                if (!sc.hasNext(":")) {

                    ArrayList<String[]> tempRoomTrans = new ArrayList<String[]>();
                    for (i = 0; i < currTranRoom.size(); ++i) {
                        tempRoomTrans.add(currTranRoom.get(i));
                    }
                    trans.add(tempRoomTrans);
                }
            } else {
                System.out.println("Error parsing file on line: " + lineNo + ": " + currLine);
                error = false;
            }

        } // exit while loop

        for (i = 1; i < trans.size(); ++i) {
            count2 = trans.get(i - 1).size() + count;
            count = 0;
            for (int j = 0; j < count2; ++j) {
                trans.get(i).remove(0);
                count = count + 1;
            }
        }

        if (rooms.size() < 0 || trans.size() < 0) {
            System.out.println("Error parsing file: rooms or transitions not properly parsed.");
            error = false;
        } else if (rooms.size() != trans.size()) {
            System.out.println("Error parsing file: rooms or transitions not properly parsed.");
            error = false;
        }

        if (!curRoom.equals(null)) {
            currRoom = rooms.get(0);

            curRoom[0] = currRoom[Config.ROOM_ID];

        }
        return error;
    }

    /**
     * Returns the index of the given room id in an ArrayList of rooms. 
     *
     * Each entry in the ArrayList contain a String array, containing the details of a room. The 
     * String array structure, which has a length of Config.ROOM_DET_LEN, and has the following 
     * entries:
     * Index              | Description
     * --------------------------------------------
     * Config.ROOM_ID     | The room id
     * Config.ROOM_TITLE  | The room's title
     * Config.ROOM_DESC   | The room's description
     *
     * @param id The room id to search for.
     * @param rooms The ArrayList of rooms.
     * @return The index of the room with the given id if found in rooms. Otherwise, -1.
     */
    public static int getRoomIndex(String id, ArrayList<String[]> rooms) {
        int roomIndex = -1; // will return -1 if index not found
        int i;// works as the index of the ArrayList
        String[] tempHold = new String[Config.ROOM_DET_LEN];

        for (i = 0; i < rooms.size(); ++i) {// searches each element in the ArrayList
            tempHold = rooms.get(i);// holds current string array


            if (id.equals(tempHold[Config.ROOM_ID])) {// if the id matches the id in this array
                roomIndex = i;// roomIndex = the index in ArrayList
                break;// once it matches don't waste time
            }
        }

        return roomIndex;// return the index
    }

    /**
     * Returns the room String array of the given room id in an ArrayList of rooms. 
     *
     * Remember to avoid code duplication!
     *
     * @param id The room id to search for.
     * @param rooms The ArrayList of rooms.
     * @return The reference to the String array in rooms with the room id of id. Otherwise, null.
     */
    public static String[] getRoomDetails(String id, ArrayList<String[]> rooms) {
        int roomIndex = getRoomIndex(id, rooms);// get the index of this room
        String[] roomDetails = new String[Config.ROOM_DET_LEN];
        if (roomIndex == -1) {// error catch
            return null;

        } else {

            roomDetails = rooms.get(roomIndex);// sets return value to the array
        }
        return roomDetails;// returns the array of the room
    }

    /**
     * Prints out a line of characters to System.out. The line should be terminated by a new line.
     *
     * @param len The number of times to print out c. 
     * @param c The character to print out.
     */
    public static void printLine(int len, char c) {
        int i;

        for (i = 0; i < len; ++i) {// loops len times
            System.out.print(c);// prints char c each time loop is iterated
        }
        System.out.println("");

    }

    /**
     * Prints out a String to System.out, formatting it into lines of length no more than len 
     * characters.
     * 
     * This method will need to print the string out character-by-character, counting the number of
     * characters printed per line. 
     * If the character to output is a newline, print it out and reset your counter.
     * If it reaches the maximum number of characters per line, len, and the next character is:
     *   - whitespace (as defined by the Character.isWhitespace method): print a new line 
     *     character, and move onto the next character.
     *   - NOT a letter or digit (as defined by the Character.isLetterOrDigit method): print out the
     *     character, a new line, and move onto the next character.
     *   - Otherwise:
     *       - If the previous character is whitespace, print a new line then the character.
     *       - Otherwise, print a '-', a new line, and then the character. 
     * Remember to reset the counter when starting a new line. 
     *
     * After printing out the characters in the string, a new line is output.
     *
     * @param len The maximum number of characters to print out.
     * @param val The string to print out.
     */
    public static void printString(int len, String val) {
        int strIndex = 0;
        int chPrinted = 0;
        char currChar;
        char prevChar = 'a';

        do {
            if(val.length() <= 0) {
                break;
            }
            currChar = val.charAt(strIndex);// sets current character to work with in string
            if (strIndex != 0) {// error catch
                prevChar = val.charAt(strIndex - 1); // sets the last character for later use
            }

            if (currChar == '\n') {// If the character to output is a newline
                System.out.print(currChar);// print it
                strIndex++;// next char of the string
                chPrinted = 0;// reset counter
                continue;
            }

            if (chPrinted == len - 1) {// If it reaches the maximum number of characters per line

                if (Character.isWhitespace(currChar)) {// whitespace
                    System.out.print('\n');
                    strIndex++;// next char of the string
                    chPrinted = 0;// reset counter

                } else if (!Character.isLetterOrDigit(currChar)) {// NOT a letter or digit
                    System.out.println(currChar);
                    strIndex++;// next char of the string
                    chPrinted = 0;// reset counter

                } else {
                    if (Character.isWhitespace(prevChar)) {
                        System.out.println("");
                        System.out.print(currChar);
                        strIndex++;// next char of the string
                        chPrinted = 1;// reset counter, printed one on new line
                    } else {
                        System.out.println("-");
                        System.out.print(currChar);
                        strIndex++;// next char of the string
                        chPrinted = 1;// reset counter, printed one on new line
                    }
                }
            } else {
                System.out.print(currChar);
                strIndex++;// next char of the string
                chPrinted++;// one character printed on the line
            }

        } while (strIndex < val.length());
        System.out.println("");
    }

    /**
     * This method prints out the room title and description to System.out. Specifically, it first
     * loads the room details, using the getRoomDetails method. If no room is found, the method
     * should return, avoiding any runtime errors.
     *
     * If the room is found, first a line of Config.LINE_CHAR of length Config.DISPLAY_WIDTH is 
     * output. Followed by the room's title, a new line, and the room's description. Both the title
     * and the description should be printed using the printString method with a maximum length of
     * Config.DISPLAY_WIDTH. Finally, a line of Config.LINE_CHAR of length Config.DISPLAY_WIDTH is 
     * output.
     *
     * @param id Room ID to display
     * @param rooms ArrayList containing the room details.
     */
    public static void displayRoom(String id, ArrayList<String[]> rooms) {
        String[] roomInfo = new String[Config.ROOM_DET_LEN];

        if (getRoomDetails(id, rooms) == null) {// condition catch
            return;
        } else {
            roomInfo = getRoomDetails(id, rooms);// set the room

            printLine(Config.DISPLAY_WIDTH, Config.LINE_CHAR);// print a line
            printString(Config.DISPLAY_WIDTH, roomInfo[Config.ROOM_TITLE]);// print the rooms title
            System.out.println("");// followed by a new line
            printString(Config.DISPLAY_WIDTH, roomInfo[Config.ROOM_DESC]);// print room description
            printLine(Config.DISPLAY_WIDTH, Config.LINE_CHAR);// print a line
        }
        return;
    }

    /**
     * Prints out and returns the transitions for a given room. 
     *
     * If the room ID of id cannot be found, nothing should be output to System.out and null should
     * be returned.
     *
     * If the room is a terminal room, i.e., the transition list is consists of only a single 
     * transition with the value at index Config.TRAN_DESC being either Config.SUCCESS or 
     * Config.FAIL, nothing should be printed out.
     *
     * The transitions should be output in the same order in which they are in the ArrayList, and 
     * only if the transition probability (String at index TRAN_PROB) is null. Each transition 
     * should be output on its own line with the following format:
     * idx) transDesc
     * where idx is the index in the transition ArrayList and transDesc is the String at index 
     * Config.TRAN_DESC in the transition String array.
     *
     * See parseStory method for the details of the transition String array.
     *
     * @param id The room id of the transitions to output and return.
     * @param rooms The ArrayList structure that contains the room details.
     * @param trans The ArrayList structure that contains the transition details.
     * @return null if the id cannot be found in rooms. Otherwise, the reference to the ArrayList of
     *         transitions for the given room.
     */
    public static ArrayList<String[]> displayTransitions(String id, ArrayList<String[]> rooms,
        ArrayList<ArrayList<String[]>> trans) {
        int room;// stores the index in the first Array List
        int i;
        String[] currTran = new String[Config.TRAN_DET_LEN]; // stores the current string array
                                                             // transition
        ArrayList<String[]> transitions = new ArrayList<String[]>();// holds the used array list of
                                                                    // transitions

        if (getRoomIndex(id, rooms) == -1) {// catches if room isn't found
            return null;
        } else {
            room = getRoomIndex(id, rooms); // sets the index in rooms of the current room
            transitions = trans.get(room); // sets the Array List of transitions
        }

        if (transitions.size() == 1) {// if an end room
            if (transitions.get(0)[Config.TRAN_DESC].equals(Config.SUCCESS)
                || transitions.get(0)[Config.TRAN_DESC].equals(Config.FAIL)) {// if an end room
                return transitions;
            }
        } else {
            for (i = 0; i < transitions.size(); ++i) {// will go through each transition
                currTran = transitions.get(i);// sets the transition being looked at

                if (currTran[Config.TRAN_PROB] == null) {// if fits given conditions
                    System.out.print(i + ") ");
                    System.out.println(currTran[Config.TRAN_DESC]);// print
                }
            }
        }

        return transitions;// always returns the reference to the array list of transitions
    }

    /**
     * Returns the next room id, selected randomly based on the transition probability weights.
     *
     * If curTrans is null or the total sum of all the probability weights is 0, then return null. 
     * Use Integer.parseInt to convert the Strings at index Config.TRAN_PROB of the transition
     * String array to integers. If there is a NumberFormatException, return null.
     *
     * It is important to follow the specifications of the random process exactly. Any deviation may
     * result in failed tests. The random transition work as follows:
     *   - Let totalWeight be the sum of the all the transition probability weights in curTrans.
     *   - Draw a random integer between 0 and totalWeight - 1 (inclusive) from rand.
     *   - From the beginning of the ArrayList curTrans, start summing up the transition probability 
     *     weights.
     *   - Return the String at index Config.TRAN_ROOM_ID of the first transition that causes the 
     *     running sum of probability weights to exceed the random integer.   
     *
     * See parseStory method for the details of the transition String array.
     *
     * @param rand The Random class from which to draw random values.
     * @param curTrans The ArrayList structure that contains the transition details.
     * @return The room id that was randomly selected if the sum of probabilities is greater than 0.
     *         Otherwise, return null. Also, return null if there is a NumberFormatException. 
     */
    public static String probTrans(Random rand, ArrayList<String[]> curTrans) {
        String roomId = "";// string to be returned
        Integer totalWeight = 0;
        Integer weight = 0;// two weights used to count probabilities
        int random;
        int i;
        try {

            if (curTrans == null) {// condition catch
                return null;
            }

            for (i = 0; i < curTrans.size(); ++i) {// goes through each transition
                totalWeight = totalWeight + Integer.parseInt(curTrans.get(i)[Config.TRAN_PROB]);// add
                                                                                                // up
                                                                                                // all
                                                                                                // the
                                                                                                // weights
            }
            if (totalWeight == 0) {
                return null;
            }
            random = rand.nextInt(totalWeight);// random integer between 0 and totalWeight - 1
                                               // (inclusive)

            for (i = 0; i < curTrans.size(); ++i) {// goes through each transition
                weight = weight + Integer.parseInt(curTrans.get(i)[Config.TRAN_PROB]);// add up all
                                                                                      // the weights
                if (weight > random) {// running sum of probability weights to exceed the random
                                      // integer
                    roomId = curTrans.get(i)[Config.TRAN_ROOM_ID];// set the current room Id to be
                                                                  // returned
                    break;
                }
            }
        } catch (NumberFormatException e) {
            return null;
        }

        return roomId;
    }

    /**
     * This is the main method for the Story Adventure game. It consists of the main game loop and
     * play again loop with calls to the various supporting methods. This method will evolve over 
     * the 3 milestones.
     * 
     * The Scanner object to read from System.in and the Random object with a seed of Config.SEED 
     * will be created in the main method and used as arguments for the supporting methods as 
     * required.
     *
     * Milestone #1:
     *   - Print out the welcome message: "Welcome to this choose your own adventure system!"
     *   - Begin the play again loop:
     *       - Prompt for a filename using the promptString method with the prompt:
     *         "Please enter the story filename: "
     *       - Prompt for a char using the promptChar method with the prompt:
     *         "Do you want to try again? "
     *   - Repeat until the character returned by promptChar is an 'n'
     *   - Print out "Thank you for playing!", terminated by a newline.
     */
    /** Milestone #2:
    *   - Print out the welcome message: "Welcome to this choose your own adventure system!"
    *   - Begin the play again loop:
    *       - Prompt for a filename using the promptString method with the prompt:
    *         "Please enter the story filename: "
    *       - If the file is successfully parsed using the parseFile method:
    *            - Begin the game loop with the current room ID being that in the 0 index of the 
    *              String array passed into the parseFile method as the 4th parameter
    *                 - Output the room details via the displayRoom method
    *                 - Output the transitions via the displayTransitions method
    *                 - If the current transition is not terminal:
    *                   - Prompt the user for a number between -1 and the number of transitions 
    *                     minus 1, using the promptInt method with a prompt of "Choose: "
    *                   - If the returned value is -1:
    *                      - read a char using promptChar with a prompt of
    *                        "Are you sure you want to quit the adventure? "
    *                      - Set the current room ID to Config.FAIL if that character returned is 'y'
    *                   - Otherwise: Set the current room ID to the room ID at index 
    *                                Config.TRAN_ROOM_ID of the selected transition.
    *                 - Otherwise, the current transition is terminal: Set the current room ID to 
    *                   the terminal state in the transition String array.
    *            - Continue the game loop until the current room ID is Config.SUCCESS or
    *              Config.FAIL
    *            - If the current room ID is Config.FAIL, print out the message (terminated by a 
    *              line): "You failed to complete the adventure. Better luck next time!"
    *            - Otherwise: print out the message (terminated by a line): 
    *              "Congratulations! You successfully completed the adventure!"
    *       - Prompt for a char using the promptChar method with the prompt:
    *         "Do you want to try again? "
    *   - Repeat until the character returned by promptChar is an 'n'
    *   - Print out "Thank you for playing!", terminated by a newline.
    */
    /** Milestone #3:
    *   - Print out the welcome message: "Welcome to this choose your own adventure system!"
    *   - Begin the play again loop:
    *       - Prompt for a filename using the promptString method with the prompt:
    *         "Please enter the story filename: "
    *       - If the file is successfully parsed using the parseFile method:
    *            - Begin the game loop with the current room ID being that in the 0 index of the 
    *              String array passed into the parseFile method as the 4th parameter
    *                 - Output the room details via the displayRoom method
    *                 - Output the transitions via the displayTransitions method
    *                 - If the current transition is not terminal:
    *                   - If the value returned by the probTrans method is null:
    *                     - Prompt the user for a number between -2 and the number of transitions 
    *                       minus 1, using the promptInt method with a prompt of "Choose: "
    *                     - If the returned value is -1:
    *                        - read a char using promptChar with a prompt of
    *                          "Are you sure you want to quit the adventure? "
    *                        - Set the current room ID to Config.FAIL if that character returned is 
    *                          'y'
    *                     - If the returned value is -2:
    *                        - read a String using the promptString method with a prompt of:
    *                          "Bookmarking current location: curRoom. Enter bookmark filename: ", 
    *                          where curRoom is the current room ID.
    *                        - Call the saveBookmark method and output (terminated by a new line):
    *                           - if successful: "Bookmark saved in fSave"
    *                           - if unsuccessful: "Error saving bookmark in fSave"
    *                       where fSave is the String returned by promptString.
    *                     - Otherwise: Set the current room ID to the room id at index 
    *                                  Config.TRAN_ROOM_ID of the selected transition.
    *                   - Otherwise, the value returned by probTrans is not null: make this value
    *                     the current room ID.
    *            - Continue the game loop until the current room ID is Config.SUCCESS or
    *              Config.FAIL.
    *            - If the current room ID is Config.FAIL, print out the message (terminated by a 
    *              line): "You failed to complete the adventure. Better luck next time!"
    *            - Otherwise: print out the message (terminated by a line): 
    *              "Congratulations! You successfully completed the adventure!"
    *       - Prompt for a char using the promptChar method with the prompt:
    *         "Do you want to try again? "
    *   - Repeat until the character returned by promptChar is an 'n'
    *   - Print out "Thank you for playing!", terminated by a newline.
    *
    * @param args Unused
    */
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        Random rand = new Random(Config.SEED);

        String fileName;
        String bookMark;
        String probHold;
        int userTran;
        char repeat = 'y';
        char userChar;
        boolean stay = true;
        ArrayList<String[]> currTrans = new ArrayList<String[]>();
        ArrayList<String[]> rooms = new ArrayList<String[]>();
        ArrayList<ArrayList<String[]>> trans = new ArrayList<ArrayList<String[]>>();
        String[] curRoom = new String[Config.ROOM_DET_LEN];

        System.out.println("Welcome to this choose your own adventure system!");
        do {
            fileName = promptString(scnr, "Please enter the story filename: ");
            
            if (parseFile(fileName, rooms, trans, curRoom)) {
                
                do {
                    displayRoom(curRoom[0], rooms);
                    displayTransitions(curRoom[0], rooms, trans);

                    currTrans = trans.get(getRoomIndex(curRoom[0], rooms));

                    if (currTrans.get(0)[Config.TRAN_DESC].equals(Config.FAIL)) {
                        curRoom[0] = Config.FAIL;
                        stay = false;
                    } else if (currTrans.get(0)[Config.TRAN_DESC].equals(Config.SUCCESS)) {
                        curRoom[0] = Config.SUCCESS;
                        stay = false;
                    } else {
                        probHold = probTrans(rand, currTrans);
                        if (probHold == null) {
                            userTran = promptInt(scnr, "Choose: ", -2, currTrans.size() - 1);
                            if (userTran == -1) {
                                userChar = promptChar(scnr,
                                    "Are you sure you want to quit the adventure? ");

                                if (userChar == 'y') {
                                    curRoom[0] = Config.FAIL;
                                    stay = false;
                                } else {
                                    stay = true;

                                }
                            }else if(userTran == -2) {
                                bookMark = promptString(scnr, "Bookmarking current location: " + curRoom[0] + " Enter bookmark filename: ");
                                if(saveBookmark(fileName, curRoom[0], bookMark)) {
                                    System.out.println("Bookmark saved in " + bookMark);
                                }else {
                                    System.out.println("Error saving bookmark in " + bookMark);
                                }
                            } else {
                                stay = true;
                                curRoom[0] = trans.get(getRoomIndex(curRoom[0], rooms))
                                    .get(userTran)[Config.TRAN_ROOM_ID];
                            }
                        }else {
                            curRoom[0] = probHold;
                        }
                    }
                } while (stay);

                if (curRoom[0] == Config.FAIL) {
                    System.out
                        .println("You failed to complete the adventure. Better luck next time!");
                } else if (curRoom[0] == Config.SUCCESS) {
                    System.out
                        .println("Congratulations! You successfully completed the adventure!");
                }

            }

            repeat = promptChar(scnr, "Do you want to try again? ");
            if (repeat != 'n') {
                trans.clear();
                rooms.clear();
                currTrans.clear();
                
                stay = true;

            }
        } while (repeat != 'n');

        System.out.println("Thank you for playing!");

    }
}
