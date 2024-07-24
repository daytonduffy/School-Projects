/*
 * battleship.c
 *
 *  Created on: Dec 5, 2020
 *      Author: Dayton Duffy & Reece Lardy
 */

#include <main.h>
#include <task_battleship.h>

#define SHIP_QUEUE_LEN  2

PLAYER_t p1;
PLAYER_t p2;
STATE_t state;
uint8_t currPlayer;
TaskHandle_t Task_BattleShip_Handle;
QueueHandle_t Queue_BattleShip;

void setup_ships(void)
{
    uint8_t i;

    //set up player's stuff
    p1.ships[4].length = 5;
    p1.ships[3].length = 4;
    p1.ships[2].length = 3;
    p1.ships[1].length = 3;
    p1.ships[0].length = 2;
    for (i = 0; i < 5; ++i)
    {
        p1.ships[0].coords[i] = 100;
        p1.ships[1].coords[i] = 100;
        p1.ships[2].coords[i] = 100;
        p1.ships[3].coords[i] = 100;
        p1.ships[4].coords[i] = 100;
    }
    //preset the arrays to 100
    for (i = 0; i < 17; ++i)
    {
        p1.hits[i] = 100;
    }
    for (i = 0; i < 83; ++i)
    {
        p1.misses[i] = 100;
    }
    //set up player's stuff
    p2.ships[4].length = 5;
    p2.ships[3].length = 4;
    p2.ships[2].length = 3;
    p2.ships[1].length = 3;
    p2.ships[0].length = 2;
    for (i = 0; i < 5; ++i)
    {
        p2.ships[0].coords[i] = 100;
        p2.ships[1].coords[i] = 100;
        p2.ships[2].coords[i] = 100;
        p2.ships[3].coords[i] = 100;
        p2.ships[4].coords[i] = 100;
    }
    //preset the arrays to -1
    for (i = 0; i < 17; ++i)
    {
        p2.hits[i] = 100;
    }
    for (i = 0; i < 83; ++i)
    {
        p2.misses[i] = 100;
    }
}

/******************************************************************************
 * This function will initialize Queue_Space_Ship and initialize the LCD
 ******************************************************************************/
void Task_BattleShip_Init(void)
{
    Queue_BattleShip = xQueueCreate(SHIP_QUEUE_LEN, sizeof(MSG_t));

    currPlayer = 1;

    setup_ships();

    // initialize the LCD
    Crystalfontz128x128_Init();
}

bool valid_shot(uint8_t activeTile)
{
    uint8_t i;
    if (currPlayer == 1)
    {
        for (i = 0; i < 17; i++)
        {
            // if it hit before
            if (activeTile == p1.hits[i])
            {
                return false;
            }
        }
        for (i = 0; i < 83; i++)
        {
            // if it missed before
            if (activeTile == p1.misses[i])
            {
                return false;
            }
        }
    }
    else
    { // p2 turn
        for (i = 0; i < 17; i++)
        {
            // if it hit before
            if (activeTile == p2.hits[i])
            {
                return false;
            }
        }
        for (i = 0; i < 83; i++)
        {
            // if it missed before
            if (activeTile == p2.misses[i])
            {
                return false;
            }
        }
    }
    return true;
}

bool valid_select(SHIP_t activeShip)
{
    uint8_t i;
    uint8_t j;
    uint8_t k;

    for (k = 0; k < activeShip.length; ++k)
    { //iterate over each coord of active ship
        for (j = 0; j < 5; ++j)
        { //iterate over each ship
            for (i = 0; i < p1.ships[j].length; ++i)
            { //iterate over each coord of the ship, both p1 and p2 alwasy same ship lengths
                if (currPlayer == 1)
                {
                    if (p1.ships[j].coords[i] == activeShip.coords[k])
                    {
                        return false; //if active ship ever is on a pre placed ship BAD
                    }
                }
                else if (currPlayer == 2)
                {
                    if (p2.ships[j].coords[i] == activeShip.coords[k])
                    {
                        return false; //if active ship ever is on a pre placed ship BAD
                    }
                }
            }
        }
    }
    return true;
}

void draw_board_placing(uint8_t cp, SHIP_t activeShip)
{
    uint8_t x;
    uint8_t y;
    uint8_t i;
    uint8_t j;

    //Colors!
    uint16_t lines;
    uint16_t water;
    uint16_t t0;
    uint16_t t1;
    uint16_t t2;
    uint16_t t3;
    uint16_t t4;
    uint16_t t5;
    uint16_t t6;
    uint16_t t7;
    uint16_t t8;
    uint16_t t9;
    uint16_t l0;
    uint16_t l1;
    uint16_t l2;
    uint16_t l3;
    uint16_t l4;
    uint16_t l5;
    uint16_t l6;
    uint16_t l7;
    uint16_t l8;
    uint16_t l9;
    uint16_t active;
    uint16_t ship;

    if (cp == 2)
    {           //light
        active = LCD_COLOR_RED;
        ship = LCD_COLOR_GREEN;
        water = LCD_COLOR_WHITE;
        lines = LCD_COLOR_WHITE;

        l0 = LCD_COLOR_RED;
        l1 = LCD_COLOR_GREEN;
        l2 = LCD_COLOR_RED;
        l3 = LCD_COLOR_GREEN;
        l4 = LCD_COLOR_RED;
        l5 = LCD_COLOR_GREEN;
        l6 = LCD_COLOR_RED;
        l7 = LCD_COLOR_GREEN;
        l8 = LCD_COLOR_RED;
        l9 = LCD_COLOR_GREEN;

        t0 = LCD_COLOR_GREEN;
        t1 = LCD_COLOR_RED;
        t2 = LCD_COLOR_GREEN;
        t3 = LCD_COLOR_RED;
        t4 = LCD_COLOR_GREEN;
        t5 = LCD_COLOR_RED;
        t6 = LCD_COLOR_GREEN;
        t7 = LCD_COLOR_RED;
        t8 = LCD_COLOR_GREEN;
        t9 = LCD_COLOR_RED;

    }
    else if (cp == 0)
    {           //dark
        active = LCD_COLOR_WHITE;
        ship = LCD_COLOR_GRAY;
        water = LCD_COLOR_BLACK;
        lines = LCD_COLOR_BLACK;

        l0 = LCD_COLOR_WHITE;
        l1 = LCD_COLOR_WHITE;
        l2 = LCD_COLOR_WHITE;
        l3 = LCD_COLOR_WHITE;
        l4 = LCD_COLOR_WHITE;
        l5 = LCD_COLOR_WHITE;
        l6 = LCD_COLOR_WHITE;
        l7 = LCD_COLOR_WHITE;
        l8 = LCD_COLOR_WHITE;
        l9 = LCD_COLOR_WHITE;

        t0 = LCD_COLOR_WHITE;
        t1 = LCD_COLOR_WHITE;
        t2 = LCD_COLOR_WHITE;
        t3 = LCD_COLOR_WHITE;
        t4 = LCD_COLOR_WHITE;
        t5 = LCD_COLOR_WHITE;
        t6 = LCD_COLOR_WHITE;
        t7 = LCD_COLOR_WHITE;
        t8 = LCD_COLOR_WHITE;
        t9 = LCD_COLOR_WHITE;
    }
    else
    {           //cp==1 normal
        active = LCD_COLOR_PINK;
        ship = LCD_COLOR_GRAY;
        water = LCD_COLOR_BLUE2;
        lines = LCD_COLOR_BLACK;

        l0 = LCD_COLOR_WHITE;
        l1 = LCD_COLOR_RED;
        l2 = LCD_COLOR_ORANGE;
        l3 = LCD_COLOR_YELLOW;
        l4 = LCD_COLOR_GREEN;
        l5 = LCD_COLOR_CYAN;
        l6 = LCD_COLOR_BLUE;
        l7 = LCD_COLOR_MAGENTA;
        l8 = LCD_COLOR_PURPLE;
        l9 = LCD_COLOR_BROWN;

        t0 = LCD_COLOR_WHITE;
        t1 = LCD_COLOR_RED;
        t2 = LCD_COLOR_ORANGE;
        t3 = LCD_COLOR_YELLOW;
        t4 = LCD_COLOR_GREEN;
        t5 = LCD_COLOR_CYAN;
        t6 = LCD_COLOR_BLUE;
        t7 = LCD_COLOR_MAGENTA;
        t8 = LCD_COLOR_PURPLE;
        t9 = LCD_COLOR_BROWN;
    }

    //draw basic board
    lcd_draw_image(64, 64, boardWidthPixels, boardHeightPixels, boardBitmaps,
                   lines, water);

    //TOP ROW
    lcd_draw_rectangle(16, 4, 10, 10, t0);
    lcd_draw_rectangle(28, 4, 10, 10, t1);
    lcd_draw_rectangle(40, 4, 10, 10, t2);
    lcd_draw_rectangle(52, 4, 10, 10, t3);
    lcd_draw_rectangle(64, 4, 10, 10, t4);
    lcd_draw_rectangle(76, 4, 10, 10, t5);
    lcd_draw_rectangle(88, 4, 10, 10, t6);
    lcd_draw_rectangle(100, 4, 10, 10, t7);
    lcd_draw_rectangle(112, 4, 10, 10, t8);
    lcd_draw_rectangle(124, 4, 10, 10, t9);

    //LEFT ROW
    lcd_draw_rectangle(4, 16, 10, 10, l0);
    lcd_draw_rectangle(4, 28, 10, 10, l1);
    lcd_draw_rectangle(4, 40, 10, 10, l2);
    lcd_draw_rectangle(4, 52, 10, 10, l3);
    lcd_draw_rectangle(4, 64, 10, 10, l4);
    lcd_draw_rectangle(4, 76, 10, 10, l5);
    lcd_draw_rectangle(4, 88, 10, 10, l6);
    lcd_draw_rectangle(4, 100, 10, 10, l7);
    lcd_draw_rectangle(4, 112, 10, 10, l8);
    lcd_draw_rectangle(4, 124, 10, 10, l9);

    //draw placed ships locations

    if (currPlayer == 1)
    {
        for (i = 0; i < 5; ++i)
        {           //for each ship
            if (p1.ships[i].coords[0] != 100)
            {
                for (j = 0; j < p1.ships[i].length; ++j)
                {           //each square in ship
                    //draw square
                    x = ((p1.ships[i].coords[j] % 10) * 12) + 16;
                    y = (((p1.ships[i].coords[j] - (p1.ships[i].coords[j] % 10))
                            / 10) * 12) + 16;
                    lcd_draw_rectangle(x, y, 10, 10, ship);
                }
            }
        }
    }
    else if (currPlayer == 2)
    {
        for (i = 0; i < 5; ++i)
        {                    //for each ship
            if (p2.ships[i].coords[0] != 100)
            {
                for (j = 0; j < p2.ships[i].length; ++j)
                {                    //each square in ship
                    //draw square
                    x = ((p2.ships[i].coords[j] % 10) * 12) + 16;
                    y = (((p2.ships[i].coords[j] - (p2.ships[i].coords[j] % 10))
                            / 10) * 12) + 16;
                    lcd_draw_rectangle(x, y, 10, 10, ship);
                }
            }
        }
    }

    //draw active ship locations
    if (activeShip.coords[0] != 100)
    {
        for (i = 0; i < activeShip.length; ++i)
        {                    //each square in ship
            //draw square
            x = ((activeShip.coords[i] % 10) * 12) + 16;
            y = (((activeShip.coords[i] - (activeShip.coords[i] % 10)) / 10)
                    * 12) + 16;
            lcd_draw_rectangle(x, y, 10, 10, active);
        }
    }
}

void draw_board_game(uint8_t cp, uint8_t activeTile)
{
    //set up variables
    uint8_t x;
    uint8_t y;
    uint8_t i;

    //Colors!
    uint16_t lines;
    uint16_t water;
    uint16_t t0;
    uint16_t t1;
    uint16_t t2;
    uint16_t t3;
    uint16_t t4;
    uint16_t t5;
    uint16_t t6;
    uint16_t t7;
    uint16_t t8;
    uint16_t t9;
    uint16_t l0;
    uint16_t l1;
    uint16_t l2;
    uint16_t l3;
    uint16_t l4;
    uint16_t l5;
    uint16_t l6;
    uint16_t l7;
    uint16_t l8;
    uint16_t l9;
    uint16_t hit;
    uint16_t miss;
    uint16_t active;

    if (cp == 2)
    {            //light
        active = LCD_COLOR_RED;
        hit = LCD_COLOR_RED;
        miss = LCD_COLOR_GRAY;
        water = LCD_COLOR_WHITE;
        lines = LCD_COLOR_WHITE;

        l0 = LCD_COLOR_RED;
        l1 = LCD_COLOR_GREEN;
        l2 = LCD_COLOR_RED;
        l3 = LCD_COLOR_GREEN;
        l4 = LCD_COLOR_RED;
        l5 = LCD_COLOR_GREEN;
        l6 = LCD_COLOR_RED;
        l7 = LCD_COLOR_GREEN;
        l8 = LCD_COLOR_RED;
        l9 = LCD_COLOR_GREEN;

        t0 = LCD_COLOR_GREEN;
        t1 = LCD_COLOR_RED;
        t2 = LCD_COLOR_GREEN;
        t3 = LCD_COLOR_RED;
        t4 = LCD_COLOR_GREEN;
        t5 = LCD_COLOR_RED;
        t6 = LCD_COLOR_GREEN;
        t7 = LCD_COLOR_RED;
        t8 = LCD_COLOR_GREEN;
        t9 = LCD_COLOR_RED;

    }
    else if (cp == 0)
    {            //dark
        active = LCD_COLOR_WHITE;
        hit = LCD_COLOR_GRAY;
        miss = LCD_COLOR_WHITE;
        water = LCD_COLOR_BLACK;
        lines = LCD_COLOR_BLACK;

        l0 = LCD_COLOR_WHITE;
        l1 = LCD_COLOR_WHITE;
        l2 = LCD_COLOR_WHITE;
        l3 = LCD_COLOR_WHITE;
        l4 = LCD_COLOR_WHITE;
        l5 = LCD_COLOR_WHITE;
        l6 = LCD_COLOR_WHITE;
        l7 = LCD_COLOR_WHITE;
        l8 = LCD_COLOR_WHITE;
        l9 = LCD_COLOR_WHITE;

        t0 = LCD_COLOR_WHITE;
        t1 = LCD_COLOR_WHITE;
        t2 = LCD_COLOR_WHITE;
        t3 = LCD_COLOR_WHITE;
        t4 = LCD_COLOR_WHITE;
        t5 = LCD_COLOR_WHITE;
        t6 = LCD_COLOR_WHITE;
        t7 = LCD_COLOR_WHITE;
        t8 = LCD_COLOR_WHITE;
        t9 = LCD_COLOR_WHITE;
    }
    else
    {            //cp==1 normal
        active = LCD_COLOR_PINK;
        hit = LCD_COLOR_RED;
        miss = LCD_COLOR_WHITE;
        water = LCD_COLOR_BLUE2;
        lines = LCD_COLOR_BLACK;

        l0 = LCD_COLOR_WHITE;
        l1 = LCD_COLOR_RED;
        l2 = LCD_COLOR_ORANGE;
        l3 = LCD_COLOR_YELLOW;
        l4 = LCD_COLOR_GREEN;
        l5 = LCD_COLOR_CYAN;
        l6 = LCD_COLOR_BLUE;
        l7 = LCD_COLOR_MAGENTA;
        l8 = LCD_COLOR_PURPLE;
        l9 = LCD_COLOR_BROWN;

        t0 = LCD_COLOR_WHITE;
        t1 = LCD_COLOR_RED;
        t2 = LCD_COLOR_ORANGE;
        t3 = LCD_COLOR_YELLOW;
        t4 = LCD_COLOR_GREEN;
        t5 = LCD_COLOR_CYAN;
        t6 = LCD_COLOR_BLUE;
        t7 = LCD_COLOR_MAGENTA;
        t8 = LCD_COLOR_PURPLE;
        t9 = LCD_COLOR_BROWN;
    }

    //draw basic board
    lcd_draw_image(64, 64, boardWidthPixels, boardHeightPixels, boardBitmaps,
                   lines, water);

    //TOP ROW
    lcd_draw_rectangle(16, 4, 10, 10, t0);
    lcd_draw_rectangle(28, 4, 10, 10, t1);
    lcd_draw_rectangle(40, 4, 10, 10, t2);
    lcd_draw_rectangle(52, 4, 10, 10, t3);
    lcd_draw_rectangle(64, 4, 10, 10, t4);
    lcd_draw_rectangle(76, 4, 10, 10, t5);
    lcd_draw_rectangle(88, 4, 10, 10, t6);
    lcd_draw_rectangle(100, 4, 10, 10, t7);
    lcd_draw_rectangle(112, 4, 10, 10, t8);
    lcd_draw_rectangle(124, 4, 10, 10, t9);

    //LEFT ROW
    lcd_draw_rectangle(4, 16, 10, 10, l0);
    lcd_draw_rectangle(4, 28, 10, 10, l1);
    lcd_draw_rectangle(4, 40, 10, 10, l2);
    lcd_draw_rectangle(4, 52, 10, 10, l3);
    lcd_draw_rectangle(4, 64, 10, 10, l4);
    lcd_draw_rectangle(4, 76, 10, 10, l5);
    lcd_draw_rectangle(4, 88, 10, 10, l6);
    lcd_draw_rectangle(4, 100, 10, 10, l7);
    lcd_draw_rectangle(4, 112, 10, 10, l8);
    lcd_draw_rectangle(4, 124, 10, 10, l9);

    //draw Hits
    if (currPlayer == 1)
    {
        for (i = 0; i < 17; ++i)
        {            //for each hit
            if (p1.hits[i] != 100)
            {
                x = ((p1.hits[i] % 10) * 12) + 16;
                y = (((p1.hits[i] - (p1.hits[i] % 10)) / 10) * 12) + 16;
                lcd_draw_rectangle(x, y, 10, 10, hit);
            }
        }
    }
    else if (currPlayer == 2)
    {
        for (i = 0; i < 17; ++i)
        {            //for each hit
            if (p2.hits[i] != 100)
            {
                x = ((p2.hits[i] % 10) * 12) + 16;
                y = (((p2.hits[i] - (p2.hits[i] % 10)) / 10) * 12) + 16;
                lcd_draw_rectangle(x, y, 10, 10, hit);
            }
        }
    }
    //draw Misses
    if (currPlayer == 1)
    {
        for (i = 0; i < 83; ++i)
        {            //for each miss
            if (p1.misses[i] != 100)
            {
                x = ((p1.misses[i] % 10) * 12) + 16;
                y = (((p1.misses[i] - (p1.misses[i] % 10)) / 10) * 12) + 16;
                lcd_draw_rectangle(x, y, 10, 10, miss);
            }
        }
    }
    else if (currPlayer == 2)
    {
        for (i = 0; i < 83; ++i)
        {            //for each miss
            if (p2.misses[i] != 100)
            {
                x = ((p2.misses[i] % 10) * 12) + 16;
                y = (((p2.misses[i] - (p2.misses[i] % 10)) / 10) * 12) + 16;
                lcd_draw_rectangle(x, y, 10, 10, miss);
            }
        }
    }

    //draw active square

    x = ((activeTile % 10) * 12) + 16;
    y = (((activeTile - (activeTile % 10)) / 10) * 12) + 16;
    lcd_draw_rectangle(x, y, 10, 10, active);
}

void draw_board_end(uint8_t cp)
{
    uint8_t x;
    uint8_t y;
    uint8_t i;
    uint8_t j;

    //Colors!
    uint16_t lines;
    uint16_t water;
    uint16_t t0;
    uint16_t t1;
    uint16_t t2;
    uint16_t t3;
    uint16_t t4;
    uint16_t t5;
    uint16_t t6;
    uint16_t t7;
    uint16_t t8;
    uint16_t t9;
    uint16_t l0;
    uint16_t l1;
    uint16_t l2;
    uint16_t l3;
    uint16_t l4;
    uint16_t l5;
    uint16_t l6;
    uint16_t l7;
    uint16_t l8;
    uint16_t l9;
    uint16_t ship;
    uint16_t hit;
    uint16_t miss;

    if (cp == 2)
    {            //light
        hit = LCD_COLOR_RED;
        miss = LCD_COLOR_GRAY;
        ship = LCD_COLOR_GREEN;
        water = LCD_COLOR_WHITE;
        lines = LCD_COLOR_WHITE;

        l0 = LCD_COLOR_RED;
        l1 = LCD_COLOR_GREEN;
        l2 = LCD_COLOR_RED;
        l3 = LCD_COLOR_GREEN;
        l4 = LCD_COLOR_RED;
        l5 = LCD_COLOR_GREEN;
        l6 = LCD_COLOR_RED;
        l7 = LCD_COLOR_GREEN;
        l8 = LCD_COLOR_RED;
        l9 = LCD_COLOR_GREEN;

        t0 = LCD_COLOR_GREEN;
        t1 = LCD_COLOR_RED;
        t2 = LCD_COLOR_GREEN;
        t3 = LCD_COLOR_RED;
        t4 = LCD_COLOR_GREEN;
        t5 = LCD_COLOR_RED;
        t6 = LCD_COLOR_GREEN;
        t7 = LCD_COLOR_RED;
        t8 = LCD_COLOR_GREEN;
        t9 = LCD_COLOR_RED;

    }
    else if (cp == 0)
    {            //dark
        hit = LCD_COLOR_BLACK;
        miss = LCD_COLOR_WHITE;
        ship = LCD_COLOR_GRAY;
        water = LCD_COLOR_BLACK;
        lines = LCD_COLOR_BLACK;

        l0 = LCD_COLOR_WHITE;
        l1 = LCD_COLOR_WHITE;
        l2 = LCD_COLOR_WHITE;
        l3 = LCD_COLOR_WHITE;
        l4 = LCD_COLOR_WHITE;
        l5 = LCD_COLOR_WHITE;
        l6 = LCD_COLOR_WHITE;
        l7 = LCD_COLOR_WHITE;
        l8 = LCD_COLOR_WHITE;
        l9 = LCD_COLOR_WHITE;

        t0 = LCD_COLOR_WHITE;
        t1 = LCD_COLOR_WHITE;
        t2 = LCD_COLOR_WHITE;
        t3 = LCD_COLOR_WHITE;
        t4 = LCD_COLOR_WHITE;
        t5 = LCD_COLOR_WHITE;
        t6 = LCD_COLOR_WHITE;
        t7 = LCD_COLOR_WHITE;
        t8 = LCD_COLOR_WHITE;
        t9 = LCD_COLOR_WHITE;
    }
    else
    {            //cp==1 normal
        hit = LCD_COLOR_RED;
        miss = LCD_COLOR_WHITE;
        ship = LCD_COLOR_GRAY;
        water = LCD_COLOR_BLUE2;
        lines = LCD_COLOR_BLACK;

        l0 = LCD_COLOR_WHITE;
        l1 = LCD_COLOR_RED;
        l2 = LCD_COLOR_ORANGE;
        l3 = LCD_COLOR_YELLOW;
        l4 = LCD_COLOR_GREEN;
        l5 = LCD_COLOR_CYAN;
        l6 = LCD_COLOR_BLUE;
        l7 = LCD_COLOR_MAGENTA;
        l8 = LCD_COLOR_PURPLE;
        l9 = LCD_COLOR_BROWN;

        t0 = LCD_COLOR_WHITE;
        t1 = LCD_COLOR_RED;
        t2 = LCD_COLOR_ORANGE;
        t3 = LCD_COLOR_YELLOW;
        t4 = LCD_COLOR_GREEN;
        t5 = LCD_COLOR_CYAN;
        t6 = LCD_COLOR_BLUE;
        t7 = LCD_COLOR_MAGENTA;
        t8 = LCD_COLOR_PURPLE;
        t9 = LCD_COLOR_BROWN;
    }

    //draw basic board
    lcd_draw_image(64, 64, boardWidthPixels, boardHeightPixels, boardBitmaps,
                   lines, water);

    //TOP ROW
    lcd_draw_rectangle(16, 4, 10, 10, t0);
    lcd_draw_rectangle(28, 4, 10, 10, t1);
    lcd_draw_rectangle(40, 4, 10, 10, t2);
    lcd_draw_rectangle(52, 4, 10, 10, t3);
    lcd_draw_rectangle(64, 4, 10, 10, t4);
    lcd_draw_rectangle(76, 4, 10, 10, t5);
    lcd_draw_rectangle(88, 4, 10, 10, t6);
    lcd_draw_rectangle(100, 4, 10, 10, t7);
    lcd_draw_rectangle(112, 4, 10, 10, t8);
    lcd_draw_rectangle(124, 4, 10, 10, t9);

    //LEFT ROW
    lcd_draw_rectangle(4, 16, 10, 10, l0);
    lcd_draw_rectangle(4, 28, 10, 10, l1);
    lcd_draw_rectangle(4, 40, 10, 10, l2);
    lcd_draw_rectangle(4, 52, 10, 10, l3);
    lcd_draw_rectangle(4, 64, 10, 10, l4);
    lcd_draw_rectangle(4, 76, 10, 10, l5);
    lcd_draw_rectangle(4, 88, 10, 10, l6);
    lcd_draw_rectangle(4, 100, 10, 10, l7);
    lcd_draw_rectangle(4, 112, 10, 10, l8);
    lcd_draw_rectangle(4, 124, 10, 10, l9);

    //draw placed ships locations

    if (currPlayer == 1)
    {
        for (i = 0; i < 5; ++i)
        {            //for each ship
            if (p1.ships[i].coords[0] != 100)
            {
                for (j = 0; j < p1.ships[i].length; ++j)
                {            //each square in ship
                    //draw square
                    x = ((p1.ships[i].coords[j] % 10) * 12) + 16;
                    y = (((p1.ships[i].coords[j] - (p1.ships[i].coords[j] % 10))
                            / 10) * 12) + 16;
                    lcd_draw_rectangle(x, y, 10, 10, ship);
                }
            }
        }
    }
    else if (currPlayer == 2)
    {
        for (i = 0; i < 5; ++i)
        {                    //for each ship
            if (p2.ships[i].coords[0] != 100)
            {
                for (j = 0; j < p2.ships[i].length; ++j)
                {                    //each square in ship
                    //draw square
                    x = ((p2.ships[i].coords[j] % 10) * 12) + 16;
                    y = (((p2.ships[i].coords[j] - (p2.ships[i].coords[j] % 10))
                            / 10) * 12) + 16;
                    lcd_draw_rectangle(x, y, 10, 10, ship);
                }
            }
        }
    }

    //draw Hits
    if (currPlayer == 1)
    {
        for (i = 0; i < 17; ++i)
        {                    //for each hit
            if (p2.hits[i] != 100)
            {
                x = ((p2.hits[i] % 10) * 12) + 16;
                y = (((p2.hits[i] - (p2.hits[i] % 10)) / 10) * 12) + 16;
                lcd_draw_rectangle(x, y, 10, 10, hit);
            }
        }
    }
    else if (currPlayer == 2)
    {
        for (i = 0; i < 17; ++i)
        {                    //for each hit
            if (p1.hits[i] != 100)
            {
                x = ((p1.hits[i] % 10) * 12) + 16;
                y = (((p1.hits[i] - (p1.hits[i] % 10)) / 10) * 12) + 16;
                lcd_draw_rectangle(x, y, 10, 10, hit);
            }
        }
    }
    //draw Misses
    if (currPlayer == 1)
    {
        for (i = 0; i < 83; ++i)
        {                    //for each miss
            if (p2.misses[i] != 100)
            {
                x = ((p2.misses[i] % 10) * 12) + 16;
                y = (((p2.misses[i] - (p2.misses[i] % 10)) / 10) * 12) + 16;
                lcd_draw_rectangle(x, y, 10, 10, miss);
            }
        }
    }
    else if (currPlayer == 2)
    {
        for (i = 0; i < 83; ++i)
        {                    //for each miss
            if (p1.misses[i] != 100)
            {
                x = ((p1.misses[i] % 10) * 12) + 16;
                y = (((p1.misses[i] - (p1.misses[i] % 10)) / 10) * 12) + 16;
                lcd_draw_rectangle(x, y, 10, 10, miss);
            }
        }
    }
}

/******************************************************************************
 * This task manages the whole game's state machine
 ******************************************************************************/
void Task_BattleShip(void *pvParameters)
{
    //set up variables and such

    state = PLACING;
    uint8_t activeOri = 0; //0 == horiz, 1 == vert
    SHIP_t activeShip;
    activeShip.length = 2;
    activeShip.coords[0] = 55;
    activeShip.coords[1] = 56;
    activeShip.coords[2] = 100;
    activeShip.coords[3] = 100;
    activeShip.coords[4] = 100;

    uint8_t ship_cnt = 0;
    uint8_t activeTile = 55; //start in the centerish
    MSG_t msg;
    uint8_t cp = 1; //cp used to define the colors for the board during play
    uint8_t i;
    bool can;
    currPlayer = 1;

    //PLAY OPENING SOUND
    //Draw board to start
    draw_board_placing(cp, activeShip);
    music_play_song(4);

    while (1)
    {
        if (state == PLACING)
        {

            //wait for queue
            xQueueReceive(Queue_BattleShip, &msg,
            portMAX_DELAY);

            //check msg for cmd type

            if (msg.cmd == CMD_SELECT)
            {    //sets new active ship

                //if valid set ship in player array
                if (valid_select(activeShip))
                {
                    if (currPlayer == 1)
                    {
                        for (i = 0; i < activeShip.length; ++i)
                        {    //each tile
                            p1.ships[ship_cnt].coords[i] = activeShip.coords[i];
                        }
                    }
                    else if (currPlayer == 2)
                    {
                        for (i = 0; i < activeShip.length; ++i)
                        {    //each tile
                            p2.ships[ship_cnt].coords[i] = activeShip.coords[i];
                        }
                    }

                    ship_cnt = ship_cnt + 1;
                    if (ship_cnt == 5)
                    {    //MOVE THE FUCK ON
                        if (currPlayer == 1)
                        {    //it was p1's turn
                             //set up for p2 placing
                            currPlayer = 2;
                            ship_cnt = 0;
                            activeShip.length = p2.ships[ship_cnt].length;
                            for (i = 0; i < p2.ships[ship_cnt].length; ++i)
                            {             //for size of nxt ship
                                activeShip.coords[i] = i + 55;
                            }
                            draw_board_placing(cp, activeShip); //redraw new board
                        }
                        else
                        {                     //it was p2's turn
                            state = GAME;
                            currPlayer = 1;
                            draw_board_game(cp, activeTile);
                        }
                    }
                    else
                    {                     //set the new bitch up
                        activeShip.length = p1.ships[ship_cnt].length;
                        for (i = 0; i < p1.ships[ship_cnt].length; ++i)
                        {                 //for size of nxt ship
                            activeShip.coords[i] = i + 55;
                        }
                        draw_board_placing(cp, activeShip); //redraw new board
                    }
                }

            }
            else if (msg.cmd == CMD_ROTATE)
            {
                //if rotate
                //  rotate active squares, put back in center for safety
                if (activeShip.length == 5)
                {
                    if (activeOri == 0)
                    {
                        activeShip.coords[0] = 55;
                        activeShip.coords[1] = 65;
                        activeShip.coords[2] = 75;
                        activeShip.coords[3] = 85;
                        activeShip.coords[4] = 95;
                        activeOri = 1;  //change it
                    }
                    else if (activeOri == 1)
                    {
                        activeShip.coords[0] = 55;
                        activeShip.coords[1] = 56;
                        activeShip.coords[2] = 57;
                        activeShip.coords[3] = 58;
                        activeShip.coords[4] = 59;
                        activeOri = 0;  //change it
                    }
                }
                else if (activeShip.length == 4)
                {
                    if (activeOri == 0)
                    {
                        activeShip.coords[0] = 55;
                        activeShip.coords[1] = 65;
                        activeShip.coords[2] = 75;
                        activeShip.coords[3] = 85;
                        activeOri = 1;  //change it
                    }
                    else if (activeOri == 1)
                    {
                        activeShip.coords[0] = 55;
                        activeShip.coords[1] = 56;
                        activeShip.coords[2] = 57;
                        activeShip.coords[3] = 58;
                        activeOri = 0;  //change it
                    }
                }
                else if (activeShip.length == 3)
                {
                    if (activeOri == 0)
                    {
                        activeShip.coords[0] = 55;
                        activeShip.coords[1] = 65;
                        activeShip.coords[2] = 75;
                        activeOri = 1;  //change it
                    }
                    else if (activeOri == 1)
                    {
                        activeShip.coords[0] = 55;
                        activeShip.coords[1] = 56;
                        activeShip.coords[2] = 57;
                        activeOri = 0;  //change it
                    }
                }
                else if (activeShip.length == 2)
                {
                    if (activeOri == 0)
                    {
                        activeShip.coords[0] = 55;
                        activeShip.coords[1] = 65;
                        activeOri = 1;  //change it
                    }
                    else if (activeOri == 1)
                    {
                        activeShip.coords[0] = 55;
                        activeShip.coords[1] = 56;
                        activeOri = 0;  //change it
                    }
                }
                draw_board_placing(cp, activeShip); //redraw new board
            }
            else if (msg.cmd == CMD_COLOR_PALETTE)
            {
                //if color palate
                //  change color pallet accordingly 0, 1, or 2
                cp = msg.value;
                draw_board_placing(cp, activeShip); //update the board color
            }
            else if (msg.cmd == CMD_UP)
            {
                can = true;
                for (i = 0; i < activeShip.length; ++i)
                { //iterate over all active squares
                    if (activeShip.coords[i] < 10)
                    { //if any square already on top line
                        can = false; //cannot move, wont move
                    }
                }
                if (can)
                {
                    for (i = 0; i < activeShip.length; ++i)
                    { //first for allowed, 2nd to set
                        activeShip.coords[i] = activeShip.coords[i] - 10; //all up one
                    }
                    draw_board_placing(cp, activeShip); //redraw new board
                }
            }
            else if (msg.cmd == CMD_DOWN)
            {
                can = true;
                for (i = 0; i < activeShip.length; ++i)
                { //iterate over all active squares
                    if (activeShip.coords[i] > 89)
                    { //if any square already on bottom line
                        can = false; //cannot move, wont move
                    }
                }
                if (can)
                {
                    for (i = 0; i < activeShip.length; ++i)
                    { //first for allowed, 2nd to set
                        activeShip.coords[i] = activeShip.coords[i] + 10; //all down one
                    }
                    draw_board_placing(cp, activeShip); //redraw new board
                }
            }
            else if (msg.cmd == CMD_LEFT)
            {
                bool can = true;
                for (i = 0; i < activeShip.length; ++i)
                { //iterate over all active squares
                    if (activeShip.coords[i] % 10 == 0)
                    { //if any square already on left line
                        can = false; //cannot move, wont move
                    }
                }
                if (can)
                {
                    for (i = 0; i < activeShip.length; ++i)
                    { //first for allowed, 2nd to set
                        activeShip.coords[i] = activeShip.coords[i] - 1; //all left one
                    }
                    draw_board_placing(cp, activeShip); //redraw new board
                }
            }
            else if (msg.cmd == CMD_RIGHT)
            {
                bool can = true;
                for (i = 0; i < activeShip.length; ++i)
                { //iterate over all active squares
                    if (activeShip.coords[i] % 10 == 9)
                    { //if any square already on right line
                        can = false; //cannot move, wont move
                    }
                }
                if (can)
                {
                    for (i = 0; i < activeShip.length; ++i)
                    { //first for allowed, 2nd to set
                        activeShip.coords[i] = activeShip.coords[i] + 1; //all right one
                    }
                    draw_board_placing(cp, activeShip); //redraw new board
                }
            }

        }
        else if (state == GAME)
        {

            // wait for queue
            xQueueReceive(Queue_BattleShip, &msg, portMAX_DELAY);

            // process msg
            // movement
            if (msg.cmd == CMD_UP)
            {
                if (activeTile > 9)
                {
                    activeTile = activeTile - 10; // up one
                    draw_board_game(cp, activeTile); //redraw new board
                }
            }
            else if (msg.cmd == CMD_DOWN)
            {
                if (activeTile < 90)
                {
                    activeTile = activeTile + 10; //down one
                    draw_board_game(cp, activeTile); //redraw new board
                }
            }
            else if (msg.cmd == CMD_LEFT)
            {
                if (activeTile % 10 != 0)
                {
                    activeTile = activeTile - 1; // left one

                    draw_board_game(cp, activeTile); //redraw new board

                }
            }
            else if (msg.cmd == CMD_RIGHT)
            {
                if (activeTile % 10 != 9)
                {
                    activeTile = activeTile + 1; // right one

                    draw_board_game(cp, activeTile); //redraw new board
                }
            }
            else if (msg.cmd == CMD_COLOR_PALETTE)
            {
                //if color palate
                //  change color pallet accordingly 0, 1, or 2
                cp = msg.value;
                draw_board_game(cp, activeTile); //update the board color
            }
            else if (msg.cmd == CMD_SELECT)
            {
                //check for valid shot
                if (valid_shot(activeTile))
                {
                    // check for hit
                    bool hit = false;
                    if (currPlayer == 1)
                    {
                        uint8_t j;
                        // check for a hit on p2 ships
                        for (j = 0; j < 5; ++j)
                        { //iterate over each ship
                            for (i = 0; i < p2.ships[j].length; ++i)
                            { //iterate over each coord of the ship, both p1 and p2 alwasy same ship lengths
                                if (p2.ships[j].coords[i] == activeTile)
                                {
                                    // P1 HIT P2
                                    hit = true;
                                }
                            }
                        }
                        if (hit)
                        {
                            //stay player on hit
                            music_play_song(1); //play hit sound
                            for (i = 0; i < 17; i++)
                            {
                                // update hit array
                                if (p1.hits[i] == 100)
                                {
                                    p1.hits[i] = activeTile;
                                    i = 17;
                                }
                            }
                            // check for win
                            if (p1.hits[16] != 100)
                            {
                                state = END;
                            }
                        }
                        else
                        {
                            music_play_song(2); //play miss sound
                            // update miss array
                            for (i = 0; i < 83; i++)
                            {
                                if (p1.misses[i] == 100)
                                {
                                    p1.misses[i] = activeTile;
                                    i = 83;
                                }
                            }
                            currPlayer = 2; //change player on miss
                            activeTile = 55; //reset active tile
                        }

                    }
                    else
                    // p2
                    {
                        int j;
                        // check for a hit on p2 ships
                        for (j = 0; j < 5; ++j)
                        { //iterate over each ship
                            for (i = 0; i < p1.ships[j].length; ++i)
                            { //iterate over each coord of the ship, both p1 and p2 always same ship lengths
                                if (p1.ships[j].coords[i] == activeTile)
                                {
                                    // P1 HIT P2
                                    hit = true;
                                }
                            }
                        }
                        if (hit)
                        {
                            //Player stays on hit
                            music_play_song(1); //play hit song
                            for (i = 0; i < 17; i++)
                            {
                                // update hit array
                                if (p2.hits[i] == 100)
                                {
                                    p2.hits[i] = activeTile;
                                    i = 17;
                                }
                            }
                            // check for win
                            if (p2.hits[16] != 100)
                            {
                                state = END;
                            }
                        }
                        else
                        {
                            music_play_song(2); //play miss sound
                            // update miss array
                            for (i = 0; i < 83; i++)
                            {
                                if (p2.misses[i] == 100)
                                {
                                    p2.misses[i] = activeTile;
                                    i = 83;
                                }
                            }
                            currPlayer = 1; //change player on miss
                            activeTile = 55; //reset active tile
                        }
                    }
                    draw_board_game(cp, activeTile);
                }
            }
        }
        else if (state == END)
        {
            //play end game song
            music_play_song(3);

            //display some end screen
            //  Print the winner's board with misses marked and the ships marked where hit, ships standard color otherwise
            draw_board_end(cp);

            //wait for CMD_SELECT to go to placing with reset variables
            xQueueReceive(Queue_BattleShip, &msg, portMAX_DELAY);

            if (msg.cmd == CMD_SELECT)
            {
                state = PLACING; // restart the game

                activeOri = 0; //0 == horiz, 1 == vert
                activeShip.length = 2;
                activeShip.coords[0] = 55;
                activeShip.coords[1] = 56;
                activeShip.coords[2] = 100;
                activeShip.coords[3] = 100;
                activeShip.coords[4] = 100;

                ship_cnt = 0;
                activeTile = 55; //start in the center-ish

                cp = 1; //cp used to define the colors for the board during play
                currPlayer = 1;
                setup_ships();

                music_play_song(4);
                draw_board_placing(cp, activeShip);
            }

        }

    }
}

//GAME
//Make sure Correct Player
//
//if movement
//  move active square in given direction
//  dont move if out of bounds
//
//if select
//  dont do anything if already guessed square
//  if valid space check other player ships for hit
//      if miss, mark in array, change turn
//      if hit, mark in array, check for 17 hits
//          if 17th go to endscreen
//          else player gets to select again
//////////////////////////////////////////////////////
