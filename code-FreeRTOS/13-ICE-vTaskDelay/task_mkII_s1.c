/*
 * task_mkII_s1.c
 *
 *  Created on: Oct 14, 2020
 *      Author: Joe Krachey
 */

#include <main.h>


TaskHandle_t Task_mkII_s1_Handle = NULL;

/******************************************************************************
 * De-bounce switch S1.  If is has been pressed, change the tri-Color LED on
 * the MSP432 Launchpad. Everytime S1 is pressed, the color should change in
 * the following order:
 *      RED->GREEN->BLUE->RED->GREEN....
 *****************************************************************************/
void task_mkII_s1(void *pvParameters)
{
    // Declare a uint8_t variable that will be used to de-bounce S1
    uint8_t debounce_state = 0x00;

    uint8_t cState = 0;

    while(1)
    {

        // Shift the de-bounce variable to the right
        debounce_state = debounce_state << 1;

        // If S1 is being pressed, set the LSBit of debounce_state to a 1;
        if(ece353_staff_MKII_S1()){
            debounce_state |= 0x01;
        }

        // If the de-bounce variable is equal to 0x7F, change the color of the tri-color LED.
        if(debounce_state == 0x7F){
            if(cState == 0){//if red
                ece353_staff_RGB_LED(false, true, false);
                cState = 1;
            }else if(cState == 1){//if green
                ece353_staff_RGB_LED(false, false, true);
                cState = 2;
            }else if(cState == 2){//if blue
                ece353_staff_RGB_LED(true, false, false);
                cState = 0;
            }
        }


        // Delay for 10mS using vTaskDelay
        vTaskDelay(pdMS_TO_TICKS(10));
    }

}
