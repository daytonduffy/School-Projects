import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 *  Java IO practice: Oracle 
 *  Dayton Duffy 
 *  drduffy@wisc.edu
 *  Lecture 2
 *      This program is used to run "oracle" file types in order to run an oracle game.
 *  The program asks the user what file to read and when the file is "oracle" type
 *  reads said file and uses it to run the game with the user. Taking various user 
 *  input to run the game and produce output to the console as well as an output file.
 *  
 *  *NOTE* 
 *  The current build of this program is only able to run properly with five questions, five answers, and five endings. 
 *  Any more breaks things, any less works except for the endings. The introduction can be anything.
 */
public class Main {
    private static String[] terms = {"Q", "A", "Stop", "F", "I", "End", "Oracle"};// Key lines in
                                                                                  // the File type
    private static final Scanner scnr = new Scanner(System.in);// User input scanner
    private static final String title = "Dayton Duffy, drduffy@wisc.edu, 2.";// my information
    private static String[] questions = new String[5];// holds the quesions from the file
    private static String[] answers = new String[5];// holds the answers from the file
    private static String[] endings = new String[5];// holds the assortment of endings from the file
    private static int numQs = 0;// counter used for the number of questions
    private static boolean special = false;// triggers the special forced ending
    private static boolean done = false;// triggers the end of the game
    private static PrintWriter write;// output writer to be initialized later


    /**
     *  Main method used to call the other methods 
     *  that are used to read the file and run the oracle game
     */
    public static void main(String[] args) {
        try {
            String repeat = "y";// string used to decide if user is done
            File output = new File("output.txt");// sets output file
            write = new PrintWriter(output);// initializes the writer to the output file

            System.out.println(title);

            do {
                System.out.println("What file would you like to use?");
                String fileInput = scnr.nextLine();// file the user would like to run

                if (parseFile(fileInput)) {// if the file works use the methods that run the game
                    runGame();

                    finishGame();
                } else {// otherwise see if the user wants to try the filename again
                    System.out.println("Would you like to try again?");
                    System.out.println("y/n");
                    repeat = scnr.nextLine();
                }

                if (done) {// if game is signaled over force the end of game
                    repeat = "n";
                }

            } while (!repeat.equals("n"));// loop until the user decides they don't want to continue
            System.out.println("Thank you for our time together");
            write.close();
        } catch (IOException e) {// catch poor file inputs
            System.out.println("Something wrong with output file: " + e);
        }
    }

    /**
     *  Method used to check if File type works and if so
     *  organize the information in the file and begin the game
     *  @param fName file name given by the user in Main
     *  @return returns false if file doesn't work for the game
     *  
     */
    private static boolean parseFile(String fName) {
        try {
            File fileName = new File(fName);
            Scanner file = new Scanner(fileName);

            String firstLine = file.nextLine();
            if (!firstLine.equals(terms[6])) {// Tests if file type is right
                System.out.println("Aren't you looking for the Oracle?");
                file.close();
                return false;
            }


            String nextLine = file.nextLine();

            boolean keep = true;
            do {
                if (nextLine.equals(terms[0])) {// questions
                    question(file);
                } else if (nextLine.equals(terms[1])) {// answers
                    answer(file);
                } else if (nextLine.equals(terms[3])) {// finals
                    ends(file);
                } else if (nextLine.equals(terms[4])) {// Intro
                    startGame(file);
                } else if (nextLine.equals(terms[5])) {// End of file
                    keep = false;
                }
                nextLine = file.nextLine();
            } while (keep);

            file.close();
        } catch (IOException e) {
            System.out.println("File name not found: " + fName);
            return false;
        } catch (Exception e) {
            System.out.println("Unable to read lines from file: " + fName);
            return false;// return false if Exception
        }

        return true;
    }

    /**
     *  Sorts the questions from the Oracle file into the 
     *  array field
     *  @param file the file to be read from
     */
    private static void question(Scanner file) {
        String line = file.nextLine();// Make sure the next line is a question

        while (!line.equals(terms[2])) {// run until stop signal is reached
            questions[numQs] = line;// fill array
            numQs = numQs + 1;// count number of questions
            line = file.nextLine();
        }
    }

    /**
     *  Sorts the answers from the Oracle file into the 
     *  array field
     *  @param file the file to be read from
     */
    private static void answer(Scanner file) {
        String line = file.nextLine();// Make sure the next line is an answer
        int num = 0;// count number of questions
        while (!line.equals(terms[2])) {// repeat until stop signal is reached
            answers[num] = line;
            num = num + 1;
            line = file.nextLine();
        }
    }

    /**
     *  Sorts the endings from the Oracle file into the 
     *  array field
     *  @param file the file to be read from
     */
    private static void ends(Scanner file) {
        String line = file.nextLine();// make sure next line is an ending
        int num = 0;// count number of endings
        while (!line.equals(terms[2])) {// repeat until stop signal is reached
            endings[num] = line;// fill array
            num = num + 1;
            line = file.nextLine();
        }
    }

    /**
     *  Starts the game by asking the user's name
     *  and printing the introduction given by the oracle file
     *  @param file the file to be read from
     */
    private static void startGame(Scanner file) {
        System.out.println("What would you like me to call you?");
        String name = scnr.nextLine();// users name
        System.out.println("Nice to meet you, " + name);
        System.out.println();

        // print the name to the output file
        write.println("----------------------------------");
        write.print(name + ": ");

        // print introduction loop
        String line = file.nextLine();

        while (!line.equals(terms[2])) {
            System.out.print(line);
            System.out.println();
            line = file.nextLine();
        }

        System.out.println();


    }

    /**
     *  Method used to run the main body of the game.
     *  Takes user input to give answers to the questions asked by the user
     */
    private static void runGame() {
        int qCount = 0; // counts number of questions asked
        String userIn; // holds the changing user input

        do {
            System.out.println("q to leave me");
            for (int i = 0; i < numQs; ++i) {// print the questions each loop
                System.out.println(questions[i]);
            }
            userIn = scnr.nextLine();

            // check user input for match to the questions if true print response
            // also prints questions asked to the output file
            if (userIn.equalsIgnoreCase("Q1") || userIn.equals("1") || userIn.equalsIgnoreCase(questions[0])) {
                System.out.println(answers[0]);
                System.out.println();
                qCount = qCount + 1;
                write.print("Q1 ");
            } else if (userIn.equalsIgnoreCase("Q2") || userIn.equals("2") || userIn.equalsIgnoreCase(questions[1])) {
                System.out.println(answers[1]);
                System.out.println();
                qCount = qCount + 1;
                write.print("Q2 ");
            } else if (userIn.equalsIgnoreCase("Q3") || userIn.equals("3") || userIn.equalsIgnoreCase(questions[2])) {
                System.out.println(answers[2]);
                System.out.println();
                qCount = qCount + 1;
                write.print("Q3 ");
            } else if (userIn.equalsIgnoreCase("Q4") || userIn.equals("4") || userIn.equalsIgnoreCase(questions[3])) {
                System.out.println(answers[3]);
                System.out.println();
                qCount = qCount + 1;
                write.print("Q4 ");
            } else if (userIn.equalsIgnoreCase("Q5") || userIn.equals("5") || userIn.equalsIgnoreCase(questions[4])) {
                System.out.println(answers[4]);
                System.out.println();
                qCount = qCount + 1;
                write.print("Q5 ");
            } else if (userIn.equals("q")) { // quit if the user wants to
                write.println();
                return;
            } else {// catches when user enters unwanted text
                System.out.println("I don't know what you mean.");
                System.out.println();
            }

            // each loop ask user if they want to continue
            System.out.println("Would you like to know more?");
            System.out.println("y/n");
            String test = scnr.nextLine();
            if (test.equals("n")) {
                write.println();
                return;
            } else if (test.equals("q")) {
                write.println();
                return;
            }
            System.out.println();

        } while (qCount < 5);// force quit after five questions
        special = true;
        write.println();// end questions line in output file
        return;
    }

    /**
     *  Method used to print the final message of the oracle 
     *  as well as print that message to the output file
     */
    private static void finishGame() {
        if (special) {// special force ending method in last file space
            System.out.println(endings[4]);
            write.println("Final message: " + endings[4]);
            done = true;
        } else {// else choose a random ending of those that remain
            Random rand = new Random();
            int num = rand.nextInt(4);
            System.out.println(endings[num]);
            System.out.println();
            done = true;
            write.println("Final message: " + endings[num]);
        }
        write.println("----------------------------------");
    }
}
