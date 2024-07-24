/*
 * tic_tac_toe.c
 *
 *  Created on: Oct 30, 2020
 *      Author: Dayton Duffy
 */

#include "tic_tac_toe.h"
#include <stdio.h>

#define VOLT_0P60 ((int)(0.60/(3.3/4096))) // 1056      // 0.60 /(3.3/4096)
#define VOLT_2P70 ((int)(2.70/(3.3/4096))) // 3103      // 2.70 /(3.3/4096)

// 0 // 1 // 2
/////////////////
// 3 // 4 // 5
/////////////////
// 6 // 7 // 8



uint8_t Board[9];           // integer array holds the current status of the 9 squares 0 = unclaimed, 1 = X's, 2 = Y's
uint8_t activeSquare;       // int holds active square number 0->8
uint8_t turnCnt;            // int holds turn number, helps speed test
uint8_t currTurn;           // int holds whose turn it is 0 = X's, 1 = Y's
uint8_t first = 0;          // int holds which player went first this game 0 = X's, 1 = Y's
uint8_t state = 2;          // int holds the current state of the game, 0 = Gameplay, 1 = Endscreen, 2 = Init Game(starts here)
uint8_t winState = 3;       // 0 = X's, 1 = O's, 2 = Ties, 3 = No Win;
uint8_t waitFlag = 5;       // Gets set to signal which direction to wait to return to center
bool PS2_CAN_UPDATE = true; // boolean used to make sure Joystick returns to center before next move


//HELPER FUNCTIONS//
/*******************************************************************************
* Function Name: updateLCD
********************************************************************************
* Summary: When called updates all 9 game tiles, has to check each for active square
*
* Returns:
*  Nothing
*******************************************************************************/
void updateLCD(void){

    //Print Square 0
    if(activeSquare == 0){ //Print Square with active conditions
        if(Board[activeSquare] == 0){//Unclaimed active Square
            lcd_draw_rectangle(LEFT_COL, UPPER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_GREEN);//print green background
            if(currTurn == 0){//X's turn Black X
                lcd_draw_image(LEFT_COL, UPPER_ROW, X_WIDTH, X_HEIGHT, Bitmaps_X, LCD_COLOR_BLACK, LCD_COLOR_GREEN);
            }else if(currTurn == 1){//O's turn Black O
                lcd_draw_image(LEFT_COL, UPPER_ROW, O_WIDTH, O_HEIGHT, Bitmaps_O, LCD_COLOR_BLACK, LCD_COLOR_GREEN);
            }
        }else if(Board[activeSquare] == 1){//Black X Claimed Active Square
            lcd_draw_rectangle(LEFT_COL, UPPER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_RED);//print red background
            lcd_draw_image(LEFT_COL, UPPER_ROW, X_WIDTH, X_HEIGHT, Bitmaps_X, LCD_COLOR_BLACK, LCD_COLOR_RED); //X's Square
        }else if(Board[activeSquare] == 2){//Blakc O Claimed Active Square
            lcd_draw_rectangle(LEFT_COL, UPPER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_RED);//print red background
            lcd_draw_image(LEFT_COL, UPPER_ROW, O_WIDTH, O_HEIGHT, Bitmaps_O, LCD_COLOR_BLACK, LCD_COLOR_RED);//O's Square
        }
    }else{//Print Square with non active conditions
        lcd_draw_rectangle(LEFT_COL, UPPER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_BLACK);//print black background
        if(Board[0] == 1){ //Yellow X
            lcd_draw_image(LEFT_COL, UPPER_ROW, X_WIDTH, X_HEIGHT, Bitmaps_X, LCD_COLOR_YELLOW, LCD_COLOR_BLACK);
        }else if(Board[0] == 2){//Cyan O
            lcd_draw_image(LEFT_COL, UPPER_ROW, O_WIDTH, O_HEIGHT, Bitmaps_O, LCD_COLOR_CYAN, LCD_COLOR_BLACK);
        }//Dont print any symbol if unclaimed
    }

    //Print Square 1
    if(activeSquare == 1){ //Print Square with active conditions
            if(Board[activeSquare] == 0){//Unclaimed active Square
                lcd_draw_rectangle(CENTER_COL, UPPER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_GREEN);//print green background
                if(currTurn == 0){//X's turn Black X
                    lcd_draw_image(CENTER_COL, UPPER_ROW, X_WIDTH, X_HEIGHT, Bitmaps_X, LCD_COLOR_BLACK, LCD_COLOR_GREEN);
                }else if(currTurn == 1){//O's turn Black O
                    lcd_draw_image(CENTER_COL, UPPER_ROW, O_WIDTH, O_HEIGHT, Bitmaps_O, LCD_COLOR_BLACK, LCD_COLOR_GREEN);
                }
            }else if(Board[activeSquare] == 1){//Black X Claimed Active Square
                lcd_draw_rectangle(CENTER_COL, UPPER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_RED);//print red background
                lcd_draw_image(CENTER_COL, UPPER_ROW, X_WIDTH, X_HEIGHT, Bitmaps_X, LCD_COLOR_BLACK, LCD_COLOR_RED); //X's Square
            }else if(Board[activeSquare] == 2){//Blakc O Claimed Active Square
                lcd_draw_rectangle(CENTER_COL, UPPER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_RED);//print red background
                lcd_draw_image(CENTER_COL, UPPER_ROW, O_WIDTH, O_HEIGHT, Bitmaps_O, LCD_COLOR_BLACK, LCD_COLOR_RED);//O's Square
            }
    }else{//Print Square with non active conditions
            lcd_draw_rectangle(CENTER_COL, UPPER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_BLACK);//print black background
            if(Board[1] == 1){ //Yellow X
                lcd_draw_image(CENTER_COL, UPPER_ROW, X_WIDTH, X_HEIGHT, Bitmaps_X, LCD_COLOR_YELLOW, LCD_COLOR_BLACK);
            }else if(Board[1] == 2){//Cyan O
                lcd_draw_image(CENTER_COL, UPPER_ROW, O_WIDTH, O_HEIGHT, Bitmaps_O, LCD_COLOR_CYAN, LCD_COLOR_BLACK);
            }//Dont print any symbol if unclaimed
    }

    //Print Square 2
    if(activeSquare == 2){ //Print Square with active conditions
            if(Board[activeSquare] == 0){//Unclaimed active Square
                lcd_draw_rectangle(RIGHT_COL, UPPER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_GREEN);//print green background
                if(currTurn == 0){//X's turn Black X
                    lcd_draw_image(RIGHT_COL, UPPER_ROW, X_WIDTH, X_HEIGHT, Bitmaps_X, LCD_COLOR_BLACK, LCD_COLOR_GREEN);
                }else if(currTurn == 1){//O's turn Black O
                    lcd_draw_image(RIGHT_COL, UPPER_ROW, O_WIDTH, O_HEIGHT, Bitmaps_O, LCD_COLOR_BLACK, LCD_COLOR_GREEN);
                }
            }else if(Board[activeSquare] == 1){//Black X Claimed Active Square
                lcd_draw_rectangle(RIGHT_COL, UPPER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_RED);//print red background
                lcd_draw_image(RIGHT_COL, UPPER_ROW, X_WIDTH, X_HEIGHT, Bitmaps_X, LCD_COLOR_BLACK, LCD_COLOR_RED); //X's Square
            }else if(Board[activeSquare] == 2){//Blakc O Claimed Active Square
                lcd_draw_rectangle(RIGHT_COL, UPPER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_RED);//print red background
                lcd_draw_image(RIGHT_COL, UPPER_ROW, O_WIDTH, O_HEIGHT, Bitmaps_O, LCD_COLOR_BLACK, LCD_COLOR_RED);//O's Square
            }
    }else{//Print Square with non active conditions
            lcd_draw_rectangle(RIGHT_COL, UPPER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_BLACK);//print black background
            if(Board[2] == 1){ //Yellow X
                lcd_draw_image(RIGHT_COL, UPPER_ROW, X_WIDTH, X_HEIGHT, Bitmaps_X, LCD_COLOR_YELLOW, LCD_COLOR_BLACK);
            }else if(Board[2] == 2){//Cyan O
                lcd_draw_image(RIGHT_COL, UPPER_ROW, O_WIDTH, O_HEIGHT, Bitmaps_O, LCD_COLOR_CYAN, LCD_COLOR_BLACK);
            }//Dont print any symbol if unclaimed
    }

    //Print Square 3
    if(activeSquare == 3){ //Print Square with active conditions
            if(Board[activeSquare] == 0){//Unclaimed active Square
                lcd_draw_rectangle(LEFT_COL, CENTER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_GREEN);//print green background
                if(currTurn == 0){//X's turn Black X
                    lcd_draw_image(LEFT_COL, CENTER_ROW, X_WIDTH, X_HEIGHT, Bitmaps_X, LCD_COLOR_BLACK, LCD_COLOR_GREEN);
                }else if(currTurn == 1){//O's turn Black O
                    lcd_draw_image(LEFT_COL, CENTER_ROW, O_WIDTH, O_HEIGHT, Bitmaps_O, LCD_COLOR_BLACK, LCD_COLOR_GREEN);
                }
            }else if(Board[activeSquare] == 1){//Black X Claimed Active Square
                lcd_draw_rectangle(LEFT_COL, CENTER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_RED);//print red background
                lcd_draw_image(LEFT_COL, CENTER_ROW, X_WIDTH, X_HEIGHT, Bitmaps_X, LCD_COLOR_BLACK, LCD_COLOR_RED); //X's Square
            }else if(Board[activeSquare] == 2){//Blakc O Claimed Active Square
                lcd_draw_rectangle(LEFT_COL, CENTER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_RED);//print red background
                lcd_draw_image(LEFT_COL, CENTER_ROW, O_WIDTH, O_HEIGHT, Bitmaps_O, LCD_COLOR_BLACK, LCD_COLOR_RED);//O's Square
            }
        }else{//Print Square with non active conditions
            lcd_draw_rectangle(LEFT_COL, CENTER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_BLACK);//print black background
            if(Board[3] == 1){ //Yellow X
                lcd_draw_image(LEFT_COL, CENTER_ROW, X_WIDTH, X_HEIGHT, Bitmaps_X, LCD_COLOR_YELLOW, LCD_COLOR_BLACK);
            }else if(Board[3] == 2){//Cyan O
                lcd_draw_image(LEFT_COL, CENTER_ROW, O_WIDTH, O_HEIGHT, Bitmaps_O, LCD_COLOR_CYAN, LCD_COLOR_BLACK);
            }//Dont print any symbol if unclaimed
        }

    //Print Square 4
    if(activeSquare == 4){ //Print Square with active conditions
            if(Board[activeSquare] == 0){//Unclaimed active Square
                lcd_draw_rectangle(CENTER_COL, CENTER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_GREEN);//print green background
                if(currTurn == 0){//X's turn Black X
                    lcd_draw_image(CENTER_COL, CENTER_ROW, X_WIDTH, X_HEIGHT, Bitmaps_X, LCD_COLOR_BLACK, LCD_COLOR_GREEN);
                }else if(currTurn == 1){//O's turn Black O
                    lcd_draw_image(CENTER_COL, CENTER_ROW, O_WIDTH, O_HEIGHT, Bitmaps_O, LCD_COLOR_BLACK, LCD_COLOR_GREEN);
                }
            }else if(Board[activeSquare] == 1){//Black X Claimed Active Square
                lcd_draw_rectangle(CENTER_COL, CENTER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_RED);//print red background
                lcd_draw_image(CENTER_COL, CENTER_ROW, X_WIDTH, X_HEIGHT, Bitmaps_X, LCD_COLOR_BLACK, LCD_COLOR_RED); //X's Square
            }else if(Board[activeSquare] == 2){//Blakc O Claimed Active Square
                lcd_draw_rectangle(CENTER_COL, CENTER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_RED);//print red background
                lcd_draw_image(CENTER_COL, CENTER_ROW, O_WIDTH, O_HEIGHT, Bitmaps_O, LCD_COLOR_BLACK, LCD_COLOR_RED);//O's Square
            }
        }else{//Print Square with non active conditions
            lcd_draw_rectangle(CENTER_COL, CENTER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_BLACK);//print black background
            if(Board[4] == 1){ //Yellow X
                lcd_draw_image(CENTER_COL, CENTER_ROW, X_WIDTH, X_HEIGHT, Bitmaps_X, LCD_COLOR_YELLOW, LCD_COLOR_BLACK);
            }else if(Board[4] == 2){//Cyan O
                lcd_draw_image(CENTER_COL, CENTER_ROW, O_WIDTH, O_HEIGHT, Bitmaps_O, LCD_COLOR_CYAN, LCD_COLOR_BLACK);
            }//Dont print any symbol if unclaimed
        }

    //Print Square 5
    if(activeSquare == 5){ //Print Square with active conditions
            if(Board[activeSquare] == 0){//Unclaimed active Square
                lcd_draw_rectangle(RIGHT_COL, CENTER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_GREEN);//print green background
                if(currTurn == 0){//X's turn Black X
                    lcd_draw_image(RIGHT_COL, CENTER_ROW, X_WIDTH, X_HEIGHT, Bitmaps_X, LCD_COLOR_BLACK, LCD_COLOR_GREEN);
                }else if(currTurn == 1){//O's turn Black O
                    lcd_draw_image(RIGHT_COL, CENTER_ROW, O_WIDTH, O_HEIGHT, Bitmaps_O, LCD_COLOR_BLACK, LCD_COLOR_GREEN);
                }
            }else if(Board[activeSquare] == 1){//Black X Claimed Active Square
                lcd_draw_rectangle(RIGHT_COL, CENTER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_RED);//print red background
                lcd_draw_image(RIGHT_COL, CENTER_ROW, X_WIDTH, X_HEIGHT, Bitmaps_X, LCD_COLOR_BLACK, LCD_COLOR_RED); //X's Square
            }else if(Board[activeSquare] == 2){//Blakc O Claimed Active Square
                lcd_draw_rectangle(RIGHT_COL, CENTER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_RED);//print red background
                lcd_draw_image(RIGHT_COL, CENTER_ROW, O_WIDTH, O_HEIGHT, Bitmaps_O, LCD_COLOR_BLACK, LCD_COLOR_RED);//O's Square
            }
        }else{//Print Square with non active conditions
            lcd_draw_rectangle(RIGHT_COL, CENTER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_BLACK);//print black background
            if(Board[5] == 1){ //Yellow X
                lcd_draw_image(RIGHT_COL, CENTER_ROW, X_WIDTH, X_HEIGHT, Bitmaps_X, LCD_COLOR_YELLOW, LCD_COLOR_BLACK);
            }else if(Board[5] == 2){//Cyan O
                lcd_draw_image(RIGHT_COL, CENTER_ROW, O_WIDTH, O_HEIGHT, Bitmaps_O, LCD_COLOR_CYAN, LCD_COLOR_BLACK);
            }//Dont print any symbol if unclaimed
        }

    //Print Square 6
    if(activeSquare == 6){ //Print Square with active conditions
            if(Board[activeSquare] == 0){//Unclaimed active Square
                lcd_draw_rectangle(LEFT_COL, LOWER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_GREEN);//print green background
                if(currTurn == 0){//X's turn Black X
                    lcd_draw_image(LEFT_COL, LOWER_ROW, X_WIDTH, X_HEIGHT, Bitmaps_X, LCD_COLOR_BLACK, LCD_COLOR_GREEN);
                }else if(currTurn == 1){//O's turn Black O
                    lcd_draw_image(LEFT_COL, LOWER_ROW, O_WIDTH, O_HEIGHT, Bitmaps_O, LCD_COLOR_BLACK, LCD_COLOR_GREEN);
                }
            }else if(Board[activeSquare] == 1){//Black X Claimed Active Square
                lcd_draw_rectangle(LEFT_COL, LOWER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_RED);//print red background
                lcd_draw_image(LEFT_COL, LOWER_ROW, X_WIDTH, X_HEIGHT, Bitmaps_X, LCD_COLOR_BLACK, LCD_COLOR_RED); //X's Square
            }else if(Board[activeSquare] == 2){//Blakc O Claimed Active Square
                lcd_draw_rectangle(LEFT_COL, LOWER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_RED);//print red background
                lcd_draw_image(LEFT_COL, LOWER_ROW, O_WIDTH, O_HEIGHT, Bitmaps_O, LCD_COLOR_BLACK, LCD_COLOR_RED);//O's Square
            }
        }else{//Print Square with non active conditions
            lcd_draw_rectangle(LEFT_COL, LOWER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_BLACK);//print black background
            if(Board[6] == 1){ //Yellow X
                lcd_draw_image(LEFT_COL, LOWER_ROW, X_WIDTH, X_HEIGHT, Bitmaps_X, LCD_COLOR_YELLOW, LCD_COLOR_BLACK);
            }else if(Board[6] == 2){//Cyan O
                lcd_draw_image(LEFT_COL, LOWER_ROW, O_WIDTH, O_HEIGHT, Bitmaps_O, LCD_COLOR_CYAN, LCD_COLOR_BLACK);
            }//Dont print any symbol if unclaimed
        }

    //Print Square 7
    if(activeSquare == 7){ //Print Square with active conditions
            if(Board[activeSquare] == 0){//Unclaimed active Square
                lcd_draw_rectangle(CENTER_COL, LOWER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_GREEN);//print green background
                if(currTurn == 0){//X's turn Black X
                    lcd_draw_image(CENTER_COL, LOWER_ROW, X_WIDTH, X_HEIGHT, Bitmaps_X, LCD_COLOR_BLACK, LCD_COLOR_GREEN);
                }else if(currTurn == 1){//O's turn Black O
                    lcd_draw_image(CENTER_COL, LOWER_ROW, O_WIDTH, O_HEIGHT, Bitmaps_O, LCD_COLOR_BLACK, LCD_COLOR_GREEN);
                }
            }else if(Board[activeSquare] == 1){//Black X Claimed Active Square
                lcd_draw_rectangle(CENTER_COL, LOWER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_RED);//print red background
                lcd_draw_image(CENTER_COL, LOWER_ROW, X_WIDTH, X_HEIGHT, Bitmaps_X, LCD_COLOR_BLACK, LCD_COLOR_RED); //X's Square
            }else if(Board[activeSquare] == 2){//Blakc O Claimed Active Square
                lcd_draw_rectangle(CENTER_COL, LOWER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_RED);//print red background
                lcd_draw_image(CENTER_COL, LOWER_ROW, O_WIDTH, O_HEIGHT, Bitmaps_O, LCD_COLOR_BLACK, LCD_COLOR_RED);//O's Square
            }
        }else{//Print Square with non active conditions
            lcd_draw_rectangle(CENTER_COL, LOWER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_BLACK);//print black background
            if(Board[7] == 1){ //Yellow X
                lcd_draw_image(CENTER_COL, LOWER_ROW, X_WIDTH, X_HEIGHT, Bitmaps_X, LCD_COLOR_YELLOW, LCD_COLOR_BLACK);
            }else if(Board[7] == 2){//Cyan O
                lcd_draw_image(CENTER_COL, LOWER_ROW, O_WIDTH, O_HEIGHT, Bitmaps_O, LCD_COLOR_CYAN, LCD_COLOR_BLACK);
            }//Dont print any symbol if unclaimed
        }

    //Print Square 8
    if(activeSquare == 8){ //Print Square with active conditions
            if(Board[activeSquare] == 0){//Unclaimed active Square
                lcd_draw_rectangle(RIGHT_COL, LOWER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_GREEN);//print green background
                if(currTurn == 0){//X's turn Black X
                    lcd_draw_image(RIGHT_COL, LOWER_ROW, X_WIDTH, X_HEIGHT, Bitmaps_X, LCD_COLOR_BLACK, LCD_COLOR_GREEN);
                }else if(currTurn == 1){//O's turn Black O
                    lcd_draw_image(RIGHT_COL, LOWER_ROW, O_WIDTH, O_HEIGHT, Bitmaps_O, LCD_COLOR_BLACK, LCD_COLOR_GREEN);
                }
            }else if(Board[activeSquare] == 1){//Black X Claimed Active Square
                lcd_draw_rectangle(RIGHT_COL, LOWER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_RED);//print red background
                lcd_draw_image(RIGHT_COL, LOWER_ROW, X_WIDTH, X_HEIGHT, Bitmaps_X, LCD_COLOR_BLACK, LCD_COLOR_RED); //X's Square
            }else if(Board[activeSquare] == 2){//Blakc O Claimed Active Square
                lcd_draw_rectangle(RIGHT_COL, LOWER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_RED);//print red background
                lcd_draw_image(RIGHT_COL, LOWER_ROW, O_WIDTH, O_HEIGHT, Bitmaps_O, LCD_COLOR_BLACK, LCD_COLOR_RED);//O's Square
            }
        }else{//Print Square with non active conditions
            lcd_draw_rectangle(RIGHT_COL, LOWER_ROW, SQUARE_SIZE, SQUARE_SIZE, LCD_COLOR_BLACK);//print black background
            if(Board[8] == 1){ //Yellow X
                lcd_draw_image(RIGHT_COL, LOWER_ROW, X_WIDTH, X_HEIGHT, Bitmaps_X, LCD_COLOR_YELLOW, LCD_COLOR_BLACK);
            }else if(Board[8] == 2){//Cyan O
                lcd_draw_image(RIGHT_COL, LOWER_ROW, O_WIDTH, O_HEIGHT, Bitmaps_O, LCD_COLOR_CYAN, LCD_COLOR_BLACK);
            }//Dont print any symbol if unclaimed
        }
}


/*******************************************************************************
* Function Name: updateActiveSquare
********************************************************************************
* Summary: Receives a direction signal and updates the active square accordingly
*
* Parameter: dir = 0(right), 1(left), 2(up), 3(down)
*
* Returns:
*  Nothing
*******************************************************************************/
void updateActiveSquare(uint8_t dir){
    if(dir == 0){//RIGHT
        if((activeSquare == 0) || (activeSquare == 1) || (activeSquare == 3) || (activeSquare == 4) || (activeSquare == 6) || (activeSquare == 7)){
            activeSquare = activeSquare + 1; //Just how the grid works, the other three are across screen
        }else if((activeSquare == 2) || (activeSquare == 5) || (activeSquare == 8)){
            activeSquare = activeSquare - 2; //2->0. 5->3. 8->6
        }
    }else if(dir == 1){//LEFT
        if((activeSquare == 1) || (activeSquare == 2) || (activeSquare == 4) || (activeSquare == 5) || (activeSquare == 7) || (activeSquare == 8)){
            activeSquare = activeSquare - 1; //Just how the grid works, the other three are across screen
        }else if((activeSquare == 0) || (activeSquare == 3) || (activeSquare == 6)){
            activeSquare = activeSquare + 2; //0->2. 3->5. 6->8
        }
    }else if(dir == 2){//UP
        if((activeSquare == 3) || (activeSquare == 4) || (activeSquare == 5) || (activeSquare == 6) || (activeSquare == 7) || (activeSquare == 8)){
            activeSquare = activeSquare - 3; //Just how the grid works, the other three are across screen
        }else if((activeSquare == 0) || (activeSquare == 1) || (activeSquare == 2)){
            activeSquare = activeSquare + 6; //0->6. 1->7. 2->8
        }
    }else if(dir == 3){//DOWN
        if((activeSquare == 3) || (activeSquare == 4) || (activeSquare == 5) || (activeSquare == 0) || (activeSquare == 1) || (activeSquare == 2)){
            activeSquare = activeSquare + 3; //Just how the grid works, the other three are across screen
        }else if((activeSquare == 6) || (activeSquare == 7) || (activeSquare == 8)){
            activeSquare = activeSquare - 6; //6->0. 7->1. 8->2
        }
    }

    //Update the board after move
    if(ALERT_LCD_CAN_UPDATE){
        ALERT_LCD_CAN_UPDATE = false; //reset signal
        updateLCD(); //Helper Function Updates All 9 Squares
    }
}

/*******************************************************************************
* Function Name: printEmptyBoard
********************************************************************************
* Summary: Prints blank game board with black background and cyan lines
*
* Returns:
*  Nothing
*******************************************************************************/
void printEmptyBoard(void){
    lcd_draw_rectangle(CENTER_COL, CENTER_ROW ,SCREEN_X, SCREEN_Y, LCD_COLOR_BLACK); //Print Full Black Screen

    lcd_draw_rectangle(CENTER_COL, UPPER_HORIZONTAL_LINE_Y ,LINE_LENGTH, LINE_WIDTH, LCD_COLOR_CYAN); //Horizontal Divider Upper
    lcd_draw_rectangle(CENTER_COL, LOWER_HORIZONTAL_LINE_Y ,LINE_LENGTH, LINE_WIDTH, LCD_COLOR_CYAN); //Horizontal Divider Lower

    lcd_draw_rectangle(LEFT_HORIZONTAL_LINE_X, CENTER_ROW ,LINE_WIDTH, LINE_LENGTH, LCD_COLOR_CYAN); //Vertical Divider Left
    lcd_draw_rectangle(RIGHT_HORIZONTAL_LINE_X, CENTER_ROW ,LINE_WIDTH, LINE_LENGTH, LCD_COLOR_CYAN); //Vertical Divider Right
}

//method returns true and sets global for win condition if game has ended
//otherwise returns false
/*******************************************************************************
* Function Name: checkGame
********************************************************************************
* Summary: checks all win possibilities and sets global if endgame condition is met
*
* Returns:method returns true and sets global for win condition if game has ended
*           otherwise returns false
*******************************************************************************/
bool checkGame(void){
    if(turnCnt > 4){//no win condition is possible until 5 turns have happened
        //Three Horizontal Checks
        if((Board[0] == Board[1]) && (Board[0] == Board[2])){//All three in a row are the same
            if(Board[0] == 1){
                winState = 0; //X win
                return true;
            }else if(Board[0] == 2){
                winState = 1; //O win
                return true;
            }//do nothing on they're all unclaimed
        }else if((Board[3] == Board[4]) && (Board[3] == Board[5])){//All three in a row are the same
            if(Board[3] == 1){
                winState = 0; //X win
                return true;
            }else if(Board[3] == 2){
                winState = 1; //O win
                return true;
            }//do nothing on they're all unclaimed
        }else if((Board[6] == Board[7]) && (Board[6] == Board[8])){//All three in a row are the same
            if(Board[6] == 1){
                winState = 0; //X win
                return true;
            }else if(Board[6] == 2){
                winState = 1; //O win
                return true;
            }//do nothing on they're all unclaimed
        }

        //Three Vertical Checks
        if((Board[0] == Board[3]) && (Board[0] == Board[6])){//All three in a row are the same
            if(Board[0] == 1){
                winState = 0; //X win
                return true;
            }else if(Board[0] == 2){
                winState = 1; //O win
                return true;
            }//do nothing on they're all unclaimed
        }else if((Board[1] == Board[4]) && (Board[1] == Board[7])){//All three in a row are the same
            if(Board[1] == 1){
                winState = 0; //X win
                return true;
            }else if(Board[1] == 2){
                winState = 1; //O win
                return true;
            }//do nothing on they're all unclaimed
        }else if((Board[2] == Board[5]) && (Board[2] == Board[8])){//All three in a row are the same
            if(Board[2] == 1){
                winState = 0; //X win
                return true;
            }else if(Board[2] == 2){
                winState = 1; //O win
                return true;
            }//do nothing on they're all unclaimed
        }

        //Two Diagonal Checks
        if((Board[0] == Board[4]) && (Board[0] == Board[8])){//All three in a row are the same
            if(Board[0] == 1){
                winState = 0; //X win
                return true;
            }else if(Board[0] == 2){
                winState = 1; //O win
                return true;
            }//do nothing on they're all unclaimed
        }else if((Board[2] == Board[4]) && (Board[2] == Board[6])){//All three in a row are the same
            if(Board[2] == 1){
                winState = 0; //X win
                return true;
            }else if(Board[2] == 2){
                winState = 1; //O win
                return true;
            }//do nothing on they're all unclaimed
        }
    }
    if((winState == 3) && (turnCnt == 9)){//final square is full and no codition has been met yet
        winState = 2; //tie condition
        return true;
    }
    return false;
}
//HELPER FUNCTIONS//



/*******************************************************************************
* Function Name: tic_tac_toe_hw_init
********************************************************************************
* Summary: Initializes all the hardware resources required for tic tac toe game
*          (IO Pins, ADC14, Timer32_1, Timer32_2).
* Returns:
*  Nothing
*******************************************************************************/
void tic_tac_toe_hw_init(void)
{
    T32_inits(); //Initializes S2 and both Timers
    ADC14_init();//Initializes ADC14 and the PS2 joystick
}

/*******************************************************************************
* Function Name: tic_tac_toe_example_board
********************************************************************************
* Summary: Prints out an example of what the tic-tac-toe board looks like
* Returns:
*  Nothing
*******************************************************************************/
void tic_tac_toe_example_board(void)
{
    // Horizontal Lines
    lcd_draw_rectangle(SCREEN_CENTER_COL, UPPER_HORIZONTAL_LINE_Y, LINE_LENGTH,LINE_WIDTH,LCD_COLOR_BLUE);
    lcd_draw_rectangle(SCREEN_CENTER_COL,LOWER_HORIZONTAL_LINE_Y, LINE_LENGTH,LINE_WIDTH,LCD_COLOR_BLUE);

    //Vertical Lines
    lcd_draw_rectangle(LEFT_HORIZONTAL_LINE_X,SCREEN_CENTER_ROW, LINE_WIDTH, LINE_LENGTH,LCD_COLOR_BLUE);
    lcd_draw_rectangle(RIGHT_HORIZONTAL_LINE_X,SCREEN_CENTER_ROW, LINE_WIDTH,LINE_LENGTH,LCD_COLOR_BLUE);

    // Top Row
    lcd_draw_image(LEFT_COL,UPPER_ROW,O_WIDTH,O_HEIGHT,Bitmaps_O,LCD_COLOR_RED,LCD_COLOR_BLACK);
    lcd_draw_image(CENTER_COL,UPPER_ROW,X_WIDTH,X_HEIGHT,Bitmaps_X,LCD_COLOR_YELLOW,LCD_COLOR_BLACK);
    lcd_draw_image(RIGHT_COL,UPPER_ROW,O_WIDTH,O_HEIGHT,Bitmaps_O,LCD_COLOR_GREEN,LCD_COLOR_BLACK);

    // Center Row
    lcd_draw_image(LEFT_COL,CENTER_ROW,O_WIDTH,O_HEIGHT,Bitmaps_X,LCD_COLOR_BLUE,LCD_COLOR_BLACK);
    lcd_draw_image(CENTER_COL,CENTER_ROW,X_WIDTH,X_HEIGHT,Bitmaps_O,LCD_COLOR_ORANGE,LCD_COLOR_BLACK);
    lcd_draw_image(RIGHT_COL,CENTER_ROW,O_WIDTH,O_HEIGHT,Bitmaps_X,LCD_COLOR_CYAN,LCD_COLOR_BLACK);

    // Lower Row
    lcd_draw_image(LEFT_COL,LOWER_ROW,O_WIDTH,O_HEIGHT,Bitmaps_O,LCD_COLOR_MAGENTA,LCD_COLOR_BLACK);
    lcd_draw_image(CENTER_COL,LOWER_ROW,X_WIDTH,X_HEIGHT,Bitmaps_X,LCD_COLOR_GRAY,LCD_COLOR_BLACK);
    lcd_draw_image(RIGHT_COL,LOWER_ROW,O_WIDTH,O_HEIGHT,Bitmaps_O,LCD_COLOR_BROWN,LCD_COLOR_BLACK);
}

/*******************************************************************************
* Function Name: tic_tac_toe_play_game
********************************************************************************
* Summary: Enters the code that plays the tic_tac_toe game.  Once called, this
*          function should never return!
* Returns:
*  Nothing
*******************************************************************************/
void tic_tac_toe_play_game(void)
{
    //initialize the recourses needed to run the game
    tic_tac_toe_hw_init();

    //loop to never leave
    while(1){
        if(state == 0){//Game state

            // else/if for moving the active square, annoying length to make sure it goes back to center position
            if(PS2_CAN_UPDATE){
                //Deal With Moving Active Square
                if(PS2_X_DIR > VOLT_2P70 )     {
                    updateActiveSquare(0);//0 = Right move
                    waitFlag = 0;
                    PS2_CAN_UPDATE = false;
                }
                else if(PS2_X_DIR < VOLT_0P60 ){
                    updateActiveSquare(1);//1 = Left move
                    waitFlag = 1;
                    PS2_CAN_UPDATE = false;
                }
                else if(PS2_Y_DIR > VOLT_2P70 ){
                    updateActiveSquare(2);//2 = Up move
                    waitFlag = 2;
                    PS2_CAN_UPDATE = false;
                }
                else if(PS2_Y_DIR < VOLT_0P60 ){
                    updateActiveSquare(3);//3 = Down move
                    waitFlag = 3;
                    PS2_CAN_UPDATE = false;
                }
            }
            else if(!PS2_CAN_UPDATE){//can't update
                if(waitFlag == 0){//depending on which way we moved we have to wait for a different condition
                    if(PS2_X_DIR < VOLT_2P70){
                        PS2_CAN_UPDATE = true;
                    }
                }else if(waitFlag == 1){
                    if(PS2_X_DIR > VOLT_0P60){
                        PS2_CAN_UPDATE = true;
                    }
                }else if(waitFlag == 2){
                    if(PS2_Y_DIR < VOLT_2P70){
                        PS2_CAN_UPDATE = true;
                    }
                }else if(waitFlag == 3){
                    if(PS2_Y_DIR > VOLT_0P60){
                        PS2_CAN_UPDATE = true;
                    }
                }
            }


            if(ALERT_S2_PRESSED){//Button press, changes square state, changes current turn,
                ALERT_S2_PRESSED = false; //reset signal
                //Check if allowed
                if(Board[activeSquare] == 0){//unclaimed, good

                    //Claim square in the board
                    Board[activeSquare] = 1 + currTurn; //1 = X. 2 = Y; Turn: X = 0, Y = 1;

                    //change current team
                    if(currTurn == 0){
                        currTurn = 1;
                    }else{
                        currTurn = 0;
                    }

                    //Update board after button press
                    if(ALERT_LCD_CAN_UPDATE){
                        ALERT_LCD_CAN_UPDATE = false; //reset signal
                        updateLCD(); //Helper Function Updates All 9 Squares
                    }

                    //update turn count
                    turnCnt = turnCnt + 1;

                    //Check for win condition or tie
                    if(checkGame()){//returns true when tie or win state
                        //Print Win Screen
                        if(winState == 0){
                            lcd_draw_rectangle(CENTER_COL, CENTER_ROW ,SCREEN_X, SCREEN_Y, LCD_COLOR_BLACK); //Print Full Black Screen
                            lcd_X_wins();
                        }else if(winState == 1){
                            lcd_draw_rectangle(CENTER_COL, CENTER_ROW ,SCREEN_X, SCREEN_Y, LCD_COLOR_BLACK); //Print Full Black Screen
                            lcd_O_wins();
                        }else if(winState == 2){
                            lcd_draw_rectangle(CENTER_COL, CENTER_ROW ,SCREEN_X, SCREEN_Y, LCD_COLOR_BLACK); //Print Full Black Screen
                            lcd_tie();
                        }

                        //set to end state
                        state = 1;
                    }

                }//do nothing to the game if the square is claimed, simply wait for next press

            }

        }else if(state == 1){//end screen state
            //Do nothing, leave end screen up until S2 is pressed
            if(ALERT_S2_PRESSED){
                ALERT_S2_PRESSED = false;   //reset signal
                state = 2;                  //init game
                if(first == 0){//X went first
                    first = 1;      //Y go first this time
                }else{//Y went first
                    first = 0;      //X go first next time
                }
            }
        }else if(state == 2){//initialize the game
            //initialize / clean board array
            int i;
            for(i=0; i < 9; ++i){
                Board[i] = 0; //all squares are unclaimed duh
            }

            //set active square
            activeSquare = 4;   // set center square as active square

            //print empty board
            printEmptyBoard();


            //Current turn
            currTurn = first;   //first was set in compile time and end state
            turnCnt = 0;        //No turns have happened at the start
            winState = 3;       //No winner at the start
            state = 0;          //Always go straight into the game once set up
            //update board with active square
            if(ALERT_LCD_CAN_UPDATE){
                ALERT_LCD_CAN_UPDATE = false; //reset signal
                updateLCD(); //Helper Function Updates All 9 Squares
            }
        }
    }
}


