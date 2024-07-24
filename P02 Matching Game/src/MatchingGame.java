import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import processing.core.PApplet;
import processing.core.PImage;


public class MatchingGame {
    // Congratulations message
    private final static String CONGRA_MSG = "CONGRATULATIONS! YOU WON!";
    
    // Cards not matched message
    private final static String NOT_MATCHED = "CARDS NOT MATCHED. Try again!";
    
    // Cards matched message
    private final static String MATCHED = "CARDS MATCHED! Good Job!";
    
    // 2D-array which stores cards coordinates on the window display
    private final static float[][] CARDS_COORDINATES =
    new float[][] {{170, 170}, {324, 170}, {478, 170}, {632, 170},
    {170, 324}, {324, 324}, {478, 324}, {632, 324},
    {170, 478}, {324, 478}, {478, 478}, {632, 478}};
    
    // Array that stores the card images filenames
    private final static String[] CARD_IMAGES_NAMES = new String[] {"apple.png",
    "ball.png", "peach.png", "redFlower.png", "shark.png", "yellowFlower.png"};
    
    
    private static PApplet processing; // PApplet object that represents
    // the graphic display window
    private static Card[] cards; // one dimensional array of cards
    private static PImage[] images; // array of images of the different cards
    private static Random randGen; // generator of random numbers
    private static Card selectedCard1; // First selected card
    private static Card selectedCard2; // Second selected card
    private static boolean winner = false; // boolean evaluated true if the game is won,
    // and false otherwise
    private static int cardCount; //cards visible
    private static int matchedCardsCount; // number of cards matched so far
    // in one session of the game
    private static String message; // Displayed message to the display window
    
    
    /**
    * Defines the initial environment properties of this game as the program starts
    */
    public static void setup(PApplet inProcessing) {
        processing = inProcessing;
        
        images = new PImage[CARD_IMAGES_NAMES.length];
        for(int i=0; i < images.length; ++i) {
            images[i] = processing.loadImage("images" + File.separator + CARD_IMAGES_NAMES[i]);
        }
        initGame();
    }

    /**
    * Initializes the Game
    */
    public static void initGame() {
        randGen = new Random(Utility.getSeed());
        selectedCard1 = null;
        selectedCard2 = null;
        matchedCardsCount = 0;
        winner = false;
        message = "";
        
        cards = new Card[CARD_IMAGES_NAMES.length*2];
        
        ArrayList<Integer> temp = new ArrayList<Integer>();
        
        for(int i=0; i < 12; ++i) {
            temp.add(i);
        }
        Collections.shuffle(temp);
        
        
        int k = 0;
        int check = 1;
        for(int j=0; j < cards.length; ++j) {
            cards[j] = new Card(images[k], CARDS_COORDINATES[temp.get(j)][0], CARDS_COORDINATES[temp.get(j)][1]);
            if(check == 2) {
                k = k + 1;
                check = 0;
            }
            check = check + 1;
        }
    }
    
    /**
    * Callback method called each time the user presses a key
    */
    public static void keyPressed() {
        char userIn = processing.key;
        if(userIn == 'N' || userIn == 'n') {
            winner = false;
            initGame();
        }
    }
    
    /**
    * Callback method draws continuously this application window display
    */
    public static void draw() {
        processing.background(245, 255, 250); // Mint cream color
        for(int m=0; m < cards.length; ++m) {
            cards[m].draw();
        }
        displayMessage(message);
    }

    /**
    * Displays a given message to the display window
    * @param message to be displayed to the display window
    */
    public static void displayMessage(String message) {
        processing.fill(0);
        processing.textSize(20);
        processing.text(message, processing.width / 2, 50);
        processing.textSize(12);
    }

    /**
    * Checks whether the mouse is over a given Card
    * @return true if the mouse is over the storage list, false otherwise
    */
    public static boolean isMouseOver(Card card) {
        boolean over = false;
        
        int height = card.getHeight();
        int width = card.getWidth();
        
        int xCoord = (int) card.getX();
        int yCoord = (int)card.getY();
        
        
        
        if(processing.mouseY <= (yCoord + height/2)){
            if(processing.mouseY >= (yCoord - height/2)) {
                if(processing.mouseX <= (xCoord + width/2)) {
                    if(processing.mouseX >= (xCoord - width/2)) {
                        over = true;
                    }
                }
            }
        }
        
        
        return over;
    }
    
    /**
    * Callback method called each time the user presses the mouse
    */
    public static void mousePressed() {
        if(winner == false) {
        if(cardCount == 2) {
            if(selectedCard1.isVisible()) {
                if(selectedCard2.isVisible()) {
                    cardCount = 0;
                    selectedCard1.deselect();
                    selectedCard2.deselect();
                    selectedCard1.setVisible(false);
                    selectedCard2.setVisible(false);
                }
            }
        }
    
        for(int i = 0; i < cards.length; ++i) {
            if(isMouseOver(cards[i]) && cards[i] != selectedCard1) {
                cards[i].setVisible(true);
                cards[i].select();
                cardCount = cardCount + 1;
                
                if(cardCount == 1) {
                    selectedCard1 = cards[i]; 
                }else if(cardCount == 2) {
                    selectedCard2 = cards[i];
                }
            }
        }
        }
        
        if(cardCount == 2) {
            //cardCount = 0;
            
            if(!matchingCards(selectedCard1, selectedCard2)) {
                message = NOT_MATCHED;
            }else {
                selectedCard1.deselect();
                selectedCard2.deselect();
                cardCount = 0;
                matchedCardsCount = matchedCardsCount + 1;
                message = MATCHED;
            }
            
        }
        if(matchedCardsCount == 6) {
            message = CONGRA_MSG;
            winner = true;
        }
        
    }
    
    /**
    * Checks whether two cards match or not
    * @param card1 reference to the first card
    * @param card2 reference to the second card
    * @return true if card1 and card2 image references are the same, false otherwise
    */
    public static boolean matchingCards(Card card1, Card card2) {
        boolean match = false;
        if(card1.getImage() == card2.getImage()) {
            match = true;
        }
        return match;
    }
    
    
    public static void main(String[] args) {
        
        Utility.runApplication();    
    }
}
