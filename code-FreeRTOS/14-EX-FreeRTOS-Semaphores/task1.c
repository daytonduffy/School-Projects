/*
 * task_print1.c
 *
 *  Created on: Oct 19, 2020
 *      Author: Joe Krachey
 */

#include <main.h>

TaskHandle_t TaskHandle_Task1;

/******************************************************************************
* Prints a message to the Console
******************************************************************************/
void Task1(void *pvParameters)
{
    BaseType_t status;

    while(1)
    {

        status = xSemaphoreTake(Sem_Print_Lock, portMAX_DELAY);

        printf("Printing in Task_Print1\n\r");

        xSemaphoreGive(Sem_Print_Lock);

        // Sleep
        vTaskDelay(pdMS_TO_TICKS(10));

    }
}



