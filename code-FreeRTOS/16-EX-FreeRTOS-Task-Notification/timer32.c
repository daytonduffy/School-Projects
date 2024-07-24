/*
 * timer32.c
 *
 *  Created on: Oct 21, 2020
 *      Author: Joe Krachey
 */
#include <main.h>


/*****************************************************
 * Configures Timer32_1 to generate a periodic interrupt
 *
 * Parameters
 *      ticks   :   Number of milliseconds per interrupt
 * Returns
 *      None
 *****************************************************/
//void ece353_T32_1_Interrupt_Ms(uint16_t ms)
//{
    // ticks = desired period / core clock period
    // 20e-3/(1/3e6) = (3e6 * 20)/1000
//    uint32_t ticks = ((SystemCoreClock * ms)/1000) - 1;

    // Stop the timer
//    TIMER32_1->CONTROL = 0;

    // Set the load register
//    TIMER32_1->LOAD = ticks;

    // Enable the Timer32 interrupt in NVIC
//    __enable_irq();
//    NVIC_EnableIRQ(T32_INT1_IRQn);
//    NVIC_SetPriority(T32_INT1_IRQn, 2);

//    // Start Timer32 and enable interrupt
//    TIMER32_1->CONTROL = TIMER32_CONTROL_ENABLE |   // turn timer on
//                         TIMER32_CONTROL_MODE |     // periodic mode
//                         TIMER32_CONTROL_SIZE |     // 32 bit timer
//                         TIMER32_CONTROL_IE;        // enable interrupts
//}



/******************************************************************************
 * De-bounce switch S1.
 *****************************************************************************/
__inline bool debounce_s1(void)
{
    static uint8_t debounce_state = 0x00;

    // Shift the de-bounce variable to the right
    debounce_state = debounce_state << 1;

    // If S1 is being pressed, set the LSBit of debounce_state to a 1;
    if(ece353_MKII_S1())
    {
        debounce_state |= 0x01;
    }

    // If the de-bounce variable is equal to 0x7F, change the color of the tri-color LED.
    if(debounce_state == 0x7F)
    {
        return true;
    }
    else
    {
        return false;
    }

}

void T32_INT1_IRQHandler(void)
{
    BaseType_t xHigherPriorityTaskWoken;

    bool ALERT_S1 = false;

    ALERT_S1 = debounce_s1();

    // clear the interrupt
    TIMER32_1->INTCLR = BIT0;

    if(ALERT_S1)
    {
        /* Send a notification directly to the task to which interrupt processing is
        being deferred. */
        vTaskNotifyGiveFromISR(
                TaskHandle_Task1,
                &xHigherPriorityTaskWoken
        );

        portYIELD_FROM_ISR( xHigherPriorityTaskWoken );
    }
}




