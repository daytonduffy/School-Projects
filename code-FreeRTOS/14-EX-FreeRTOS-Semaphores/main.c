/*
 *  ======== main_freertos.c ========
 */
#include "main.h"

SemaphoreHandle_t Sem_Print_Lock;

/*
 *  ======== main ========
 */
int main(void)
{
    WDT_A->CTL = WDT_A_CTL_PW | WDT_A_CTL_HOLD;     // stop watchdog timer
    ece353_staff_init(true);

    printf("\n\r");
    printf("*********************************************\n\r");
    printf("* Ex-14-Semaphores\n\r");
    printf("*********************************************\n\r");
    printf("\n\r");

    Sem_Print_Lock =  xSemaphoreCreateBinary();

    // Release print semaphore.
    xSemaphoreGive(Sem_Print_Lock);

    xTaskCreate
    (   Task1,
        "Task_Print1",
        configMINIMAL_STACK_SIZE,
        NULL,
        1,
        &TaskHandle_Task1
    );

    xTaskCreate
     (   Task2,
         "Task_Print2",
         configMINIMAL_STACK_SIZE,
         NULL,
         1,
         &TaskHandle_Task2
     );

    /* Start the FreeRTOS scheduler */
    vTaskStartScheduler();

    while(1){};
    return (0);
}

//*****************************************************************************
//
//! \brief Application defined malloc failed hook
//!
//! \param  none
//!
//! \return none
//!
//*****************************************************************************
void vApplicationMallocFailedHook()
{
    /* Handle Memory Allocation Errors */
    while(1)
    {
    }
}

//*****************************************************************************
//
//! \brief Application defined stack overflow hook
//!
//! \param  none
//!
//! \return none
//!
//*****************************************************************************
void vApplicationStackOverflowHook(TaskHandle_t pxTask, char *pcTaskName)
{
    //Handle FreeRTOS Stack Overflow
    while(1)
    {
    }
}
