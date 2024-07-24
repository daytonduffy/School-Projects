/*
 * interrupts.c
 *
 *  Created on: Aug 11, 2020
 *      Author: Joe Krachey
 */
#include "interrupts.h"

// Add a global variable that holds the most recent value of the X direction
volatile uint32_t PS2_X_DIR = 0;
// Add a global variable that holds the most recent value of the Y direction
volatile uint32_t PS2_Y_DIR = 0;

void T32_INT1_IRQHandler(void)
{
    // Start the ADC conversion
    ADC14->CTL0 |= ADC14_CTL0_SC | ADC14_CTL0_ENC;

    // Clear the timer interrupt
    TIMER32_1->INTCLR = BIT0;
}

void ADC14_IRQHandler(void)
{
    // Read the X value
    PS2_X_DIR = ADC14->MEM[0];
    // Read the Y value
    PS2_Y_DIR = ADC14->MEM[1];
}
