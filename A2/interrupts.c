/*
 * interrupts.c
 *
 *  Created on: Aug 20, 2020
 *      Author: Joe Krachey
 */

#include "interrupts.h"

// Add a global variable that holds the most recent value of the X direction
volatile uint32_t PS2_X_DIR = 0;
// Add a global variable that informs the application that the Window Camparator
// had detected a change
volatile bool ALERT_PS2_UPDATE = false;

void T32_INT1_IRQHandler(void)
{
    // Start the ADC conversion
    ADC14->CTL0 |= ADC14_CTL0_SC | ADC14_CTL0_ENC;

    // Clear the timer interrupt
    TIMER32_1->INTCLR = BIT0;
}

void ADC14_IRQHandler(void)
{
    // set the global variable that informs the application that the Window Camparator
    // had detected a change
    ALERT_PS2_UPDATE = true;

    // Determine if the HIIFG interrupt is active
    if ((ADC14->IFGR1 && BIT3) != 0)//and with relevant bit, if 0 not active
    {
        // Clear interrupt flag
        ADC14->CLRIFGR1 &= ~BIT3;

        // Turn off the HI interrupt
        ADC14->IER1 &= ~BIT3;

        // Turn on LOW and IN interrupts
        ADC14->IER1 |= BIT2;
        ADC14->IER1 |= BIT1;
    }

    // Determine if the LOIFG interrupt is active
    if ((ADC14->IFGR1 && BIT2) != 0)
    {
        // Clear interrupt flag
        ADC14->CLRIFGR1 &= ~BIT2;

        // Turn off the LOW interrupt
        ADC14->IER1 &= ~BIT2;

        // Turn on HI and IN interrupts
        ADC14->IER1 |= BIT3;
        ADC14->IER1 |= BIT1;
    }

    // Determine if the INIFG interrupt is active
    if ((ADC14->IFGR1 && BIT1) != 0)
    {
        // Clear interrupt flag
        ADC14->CLRIFGR1 &= ~BIT1;

        // Turn off the IN interrupt
        ADC14->IER1 &= ~BIT1;

        // Turn on LOW and HI interrupts
        ADC14->IER1 |= BIT3;
        ADC14->IER1 |= BIT2;
    }

    // Read the X channel
    PS2_X_DIR = ADC14->MEM[0];
}



