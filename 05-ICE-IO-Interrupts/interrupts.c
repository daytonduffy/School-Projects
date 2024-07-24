/*
 * interrupts.c
 *
 *  Created on: Sep 27, 2020
 *      Author: Owner
 */

#include "interrupts.h"
#include <stdint.h>

volatile bool ALERT_S1_PRESSED = false;

void T32_INT1_IRQHandler(){
    static uint8_t button_state = 0x00;

    bool button_pressed = ece353_MKII_S1();

    //debounce button
    button_state = button_state << 1;

    if(button_pressed){
        button_state |= 0x01;
    }

    if(button_state == 0x7F){
        ALERT_S1_PRESSED = true;
    }

    //clear the interrupt
    TIMER32_1->INTCLR = BIT0;
}



// Create a global variable that will
// be used to alert the main application
// that 10 IO interrupts have been
// received.
volatile uint32_t cnt;


//*****************************************************************************
// Function used to initialize the IO pin
// connected to the S1 button on the MKII
//*****************************************************************************
void ice05_init_s1(void)
{
    // Configure the pin as an input
    P5->DIR &= ~BIT1;

    // Set the interrupt even to be a Low to High transition
    P5->IES &= ~BIT1;

    // Enable interrupts in the peripheral
    P5->IE |= BIT1;

    // Enable interrupts in the NVIC
    __enable_irq();

    // Enable the IO Port interrupts in the NVIC
    NVIC_EnableIRQ(PORT5_IRQn);

    // Set the priority of the IO Port interrupt to 0
    NVIC_SetPriority(PORT5_IRQn, 0);

}



//*****************************************************************************
// Interrupt Handler for the IO Port connected to S1
//
// Be sure to change the name of the handler to the correct handler
// name
//*****************************************************************************
void PORT5_IRQHandler()
{
    // Variables needed for ISR
    // Do NOT add additional variables
    static int irq_count = 0;
    uint32_t reg_val;

    // increment the count.
    irq_count = irq_count + 1;

    //If the count is > 10, alert the main application
    if(irq_count > 10){
        cnt = 10;//set global to alert main application?
    }

    // Clear the interrupt.  Be sure to read the section of the
    // MSP432 TRM related to IO Interrupts to determine how to clear
    // an interrupt.
    P5->IFG &= ~BIT1;
    //turn off interrupt flag?
}
