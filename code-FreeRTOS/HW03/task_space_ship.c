/*
 * task_space_ship.c
 *
 *  Created on: Nov 21, 2020
 *      Author: Dayton Duffy
 */

#include <main.h>

#define SHIP_QUEUE_LEN  2

TaskHandle_t Task_Space_Ship_Handle;
QueueHandle_t Queue_Space_Ship;

/******************************************************************************
 * This function will initialize Queue_Space_Ship and initialize the LCD
 ******************************************************************************/
void Task_Space_Ship_Init(void)
{
    Queue_Space_Ship = xQueueCreate(SHIP_QUEUE_LEN,sizeof(SHIP_MSG_t));

    // ADD CODE to initialize the LCD
    Crystalfontz128x128_Init();
}


/******************************************************************************
 * This task manages the movement of the space ship. The joystick task or the
 * console task can send messages to SHIP_QUEUE_LEN
 ******************************************************************************/
void Task_Space_Ship(void *pvParameters)
{
    uint8_t x = 64;
    uint8_t y = 64;
    uint8_t delay = 25;
    uint8_t i;
    SHIP_MSG_t msg;


    // Draw the initial starting image of the spaceship.
    lcd_draw_image(
            x,
            y,
            space_shipWidthPixels,
            space_shipHeightPixels,
            space_shipBitmaps,
            LCD_COLOR_RED,
            LCD_COLOR_BLACK
    );


    while(1)
    {
        //wait for something to be sitting in the queue
        xQueueReceive(Queue_Space_Ship, &msg, portMAX_DELAY);


        //do different tasks depending on command
        if(msg.cmd == SHIP_CMD_LEFT){                   //move the ship left
            //joystick commands will iterate just the one time, while console commands can be bigger numbers
            for(i = 0; i < msg.value; ++i){
                if((x - 28) != 0){                      //when x - 28 = 0 the edge of the image is at the edge of the screen
                    x--;                                //move left one pixel
                    //redraw the image with the single pixel difference
                    lcd_draw_image(x, y, space_shipWidthPixels, space_shipHeightPixels, space_shipBitmaps, LCD_COLOR_RED, LCD_COLOR_BLACK);
                    vTaskDelay(pdMS_TO_TICKS(delay));   //depending on speed wait (delay)mS before another command can happen
                }
            }
        }else if(msg.cmd == SHIP_CMD_RIGHT){            //move the ship right
            //joystick commands will iterate just the one time, while console commands can be bigger numbers
            for(i = 0; i < msg.value; ++i){
                if((x + 28) != 132){                    //when x + 28 = 132 the edge of the image is at the edge of the screen
                    x++;                                //move right one pixel
                    //redraw the image with the single pixel difference
                    lcd_draw_image(x, y, space_shipWidthPixels, space_shipHeightPixels, space_shipBitmaps, LCD_COLOR_RED, LCD_COLOR_BLACK);
                    vTaskDelay(pdMS_TO_TICKS(delay));   //depending on speed wait (delay)mS before another command can happen
                }
            }
        }else if(msg.cmd == SHIP_CMD_DOWN){             //move the ship down
            //joystick commands will iterate just the one time, while console commands can be bigger numbers
            for(i = 0; i < msg.value; ++i){
                if((y + 20) != 132){                    //when y - 20 = 132 the edge of the image is at the edge of the screen
                    y++;                                //move down one pixel
                    //redraw the image with the single pixel difference
                    lcd_draw_image(x, y, space_shipWidthPixels, space_shipHeightPixels, space_shipBitmaps, LCD_COLOR_RED, LCD_COLOR_BLACK);
                    vTaskDelay(pdMS_TO_TICKS(delay));   //depending on speed wait (delay)mS before another command can happen
                }
            }
        }else if(msg.cmd == SHIP_CMD_UP){               //move the ship up
            //joystick commands will iterate just the one time, while console commands can be bigger numbers
            for(i = 0; i < msg.value; ++i){
                if((y - 20) != 0){                      //when y + 20 = 132 the edge of the image is at the edge of the screen
                    y--;                                //move up one pixel
                    //redraw the image with the single pixel difference
                    lcd_draw_image(x, y, space_shipWidthPixels, space_shipHeightPixels, space_shipBitmaps, LCD_COLOR_RED, LCD_COLOR_BLACK);
                    vTaskDelay(pdMS_TO_TICKS(delay));   //depending on speed wait (delay)mS before another command can happen
                }
            }
        }else if(msg.cmd == SHIP_CMD_SPEED){            //change the speed at which the ship moves
            delay = msg.value;                          //msg.value holds the new time to wait before single pixel movements
        }                                               //SHIP_CMD_CENTER would just not move so don't do anything

    }
}


