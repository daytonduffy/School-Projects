import java.util.Scanner;

/**
 * This class and models a simple AsciiArt generator
 *
 */
public class AsciiArtDriver {
  // THESE STRINGS WILL BE GIVEN FOR PROMPT PRINTOUTS
  // USE System.out.print() INSTEAD OF System.out.println()
  // FOR MATCHING OUTPUT
  private static String PROMPT_MENU = "" + "======== MENU ========\n" + "[1] Create a new canvas\n"
      + "[2] Draw a character\n" + "[3] Undo drawing\n" + "[4] Redo drawing\n"
      + "[5] Show current canvas\n" + "[6] Show drawing history\n" + "[7] Exit\n" + "> ";

  private static String PROMPT_INPUT_WIDTH = "Width > ";
  private static String PROMPT_INPUT_HEIGHT = "Height > ";
  private static String PROMPT_INPUT_ROW = "Row > ";
  private static String PROMPT_INPUT_COL = "Col > ";
  private static String PROMPT_INPUT_CHARACTER = "Character > ";

  private static String ERROR_INPUT_WRONG_COMMAND = "Wrong command.\n";
  private static String ERROR_CANVAS_NOT_CREATED = "Canvas is not created.\n";
  private static String ERROR_NOTHING_ON_UNDO = "No job to undo.\n";
  private static String ERROR_NOTHING_ON_REDO = "No job to redo.\n";
  private static String ERROR_INPUT_CHARACTER_MORE_THAN_ONE =
      "only 1 chracter should be entered.\n";

  // Only one canvas can be edited at a time
  private static Canvas canvas;

  /**
   * Take commands and process them
   * 
   * @param scn Scanner with System.in
   */
  private static boolean processCommand(Scanner scn) {
    String cmd = scn.nextLine().trim(); // read user command line

    switch (cmd) {
      case "1": // Create a new canvas
        try {
          System.out.print(PROMPT_INPUT_WIDTH);
          String widthStr = scn.nextLine().trim();
          int width = Integer.parseInt(widthStr);
          System.out.print(PROMPT_INPUT_HEIGHT);
          String heightStr = scn.nextLine().trim();
          int height = Integer.parseInt(heightStr);
          canvas = new Canvas(width, height);
        } catch (Exception e) {
          e.printStackTrace();
        }
        break;
      case "2": // Draw a character
        try {
          System.out.print(PROMPT_INPUT_ROW);
          String xStr = scn.nextLine().trim();
          int row = Integer.parseInt(xStr);
          System.out.print(PROMPT_INPUT_COL);
          String yStr = scn.nextLine().trim();
          int col = Integer.parseInt(yStr);
          System.out.print(PROMPT_INPUT_CHARACTER);
          String charStr = scn.nextLine();
          if (charStr.length() != 1) {
            throw new IllegalArgumentException(ERROR_INPUT_CHARACTER_MORE_THAN_ONE);
          }
          canvas.draw(row, col, charStr.charAt(0));
        } catch (Exception e) {
          System.out.println("WARNING:  " + e.getMessage());
        }
        break;
      case "3": // Undo drawing
        if (canvas == null) {
          System.out.print(ERROR_CANVAS_NOT_CREATED);
          break;
        }
        if (!canvas.undo()) {
          System.out.print(ERROR_NOTHING_ON_UNDO);
        }
        break;
      case "4": // Redo drawing
        if (canvas == null) {
          System.out.print(ERROR_CANVAS_NOT_CREATED);
          break;
        }
        if (!canvas.redo()) {
          System.out.print(ERROR_NOTHING_ON_REDO);
        }
        break;
      case "5": // Print current canvas
        if (canvas == null) {
          System.out.print(ERROR_CANVAS_NOT_CREATED);
          break;
        }
        canvas.printDrawing();
        break;
      case "6": // Print drawing history from undo stack
        if (canvas == null) {
          System.out.print(ERROR_CANVAS_NOT_CREATED);
          break;
        }
        canvas.printHistory();
        break;
      case "7": // Quit
        return true;
      default: // Error message for wrong input
        System.out.print(ERROR_INPUT_WRONG_COMMAND);
        break;
    }
    return false;
  }

  /**
   * Painter driver method
   * 
   * @param scn Scanner with System.in
   */
  private static void driver(Scanner scn) {
    boolean isTerminated = false;
    while (!isTerminated) {
      System.out.print(PROMPT_MENU);
      isTerminated = processCommand(scn);
    }

    System.out.print("Bye!\n");
  }

  /**
   * Main method of the program
   * 
   * @param args the string arguments from the command line
   */
  public static void main(String[] args) {
    Scanner scn = new Scanner(System.in);
    driver(scn);
  }
}
