/*
 * task_print1.c
 *
 *  Created on: Oct 19, 2020
 *      Author: Joe Krachey
 */

#include <task1.h>

TaskHandle_t TaskHandle_Task1;

/******************************************************************************
* Prints a message to the Console
******************************************************************************/
void Task1(void *pvParameters)
{
    BaseType_t status;
    bool current_state = true;

    while(1)
    {
        ece353_led1(current_state);

        // Wait for ISR to let us know that the button
        // has been pressed
        ulTaskNotifyTake(pdTRUE, portMAX_DELAY);

        // Toggle the state
        current_state = ! current_state;

    }
}

