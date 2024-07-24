/*
 * task_mkII_s1.c
 *
 *  Created on: Dec 8, 2020
 *      Author: Dayton Duffy, Reece Lardy
 */

#include "task_mkII_s1.h"

TaskHandle_t Task_mkII_s1_Handle;

/******************************************************************************
 * De-bounce switch S1.
 *****************************************************************************/
bool debounce_s1(void)
{
    static uint8_t debounce_state = 0x00;

    // Shift the de-bounce variable to the left
    debounce_state = debounce_state << 1;

    // If S1 is being pressed, set the LSBit of debounce_state to a 1;
    if (ece353_MKII_S1())
    {
        debounce_state |= 0x01;
    }

    // If the de-bounce variable is equal to 0x7F, action
    if (debounce_state == 0x7F)
    {
        return true;
    }
    else
    {
        return false;
    }

}

/******************************************************************************
 * De-bounce switch S1.  If is has been pressed, action
 *****************************************************************************/
void Task_mkII_s1_Bottom_Half(void *pvParameters)
{
    MSG_t msg;

    while (1)
    {
        //Switch one is for SELECTING (SELECT ALSO STARTS NEW GAME IN END STATE...)
        //just send message saying select current square that is all
        ulTaskNotifyTake(pdTRUE, portMAX_DELAY);

        //set msg to Select and send to queue
        msg.cmd = CMD_SELECT;

        //send to queue
        xQueueSend(Queue_BattleShip, &msg, portMAX_DELAY);
    }
}

//Top half of task
//on periodic interrupts check button state
void T32_INT1_IRQHandler(void)
{
    BaseType_t xHigherPriorityTaskWoken;

    bool alert = debounce_s1();

    // clear the interrupt
    TIMER32_1->INTCLR = BIT0;

    if (alert)
    {

        /* Send a notification directly to the task to which interrupt processing is
         being deferred. */
        vTaskNotifyGiveFromISR(Task_mkII_s1_Handle, &xHigherPriorityTaskWoken);

        portYIELD_FROM_ISR(xHigherPriorityTaskWoken);

    }
}

