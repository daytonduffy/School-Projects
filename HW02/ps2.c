/*
 * ps2.c
 *
 *  Created on: Oct 30, 2020
 *      Author: Dayton Duffy
 */

#include "msp.h"
#include "ps2.h"

// Add a global variable that holds the most recent value of the X direction
volatile uint32_t PS2_X_DIR = 0;
// Add a global variable that holds the most recent value of the Y direction
volatile uint32_t PS2_Y_DIR = 0;

//HELPER FUNCTIONS//
/******************************************************************************
 * Configure the IO pins for BOTH the X and Y directions of the analog
 * joystick.  The X direction should be configured to place the results in
 * MEM[0].  The Y direction should be configured to place the results in MEM[1].
 *
 * After BOTH analog signals have finished being converted, a SINGLE interrupt
 * should be generated.
 *
 * Parameters
 *      None
 * Returns
 *      None
 ******************************************************************************/
void ece353_ADC14_PS2_XY(void)
{
    // Configure the X direction as an analog input pin.
    P6->SEL0 |= BIT0;
    P6->SEL1 |= BIT0;

    // Configure the Y direction as an analog input pin.
    P4->SEL0 |= BIT4;
    P4->SEL1 |= BIT4;

    // Configure CTL0 to sample 16-times in pulsed sample mode.
    // NEW -- Indicate that this is a sequence-of-channels.
    ADC14->CTL0 = ADC14_CTL0_SHP | ADC14_CTL0_SHT02;
    ADC14->CTL0 |= 0x00020000;
    //sets bits 18-17 to b01 or sequence of channels mode

    // Configure ADC to return 12-bit values
    ADC14->CTL1 = ADC14_CTL1_RES_2;

    // Associate the X direction analog signal with MEM[0]
    ADC14->MCTL[0] = ADC14_MCTLN_INCH_15;

    // Associate the Y direction analog signal with MEM[1]
    // NEW -- Make sure to indicate this is the end of a sequence.
    ADC14->MCTL[1] = ADC14_MCTLN_INCH_9;

    ADC14->MCTL[1] |= BIT7; //bit 7 tells machine that this is end of sequence


    // Enable interrupts in the ADC AFTER a value is written into MEM[1].
    //
    // NEW: This is not the same as what is demonstrated in the example
    // coding video.
    ADC14->IER0 = ADC14_IER0_IE1;

    // Enable ADC Interrupt in the NVIC
    NVIC_EnableIRQ(ADC14_IRQn);

    // Turn ADC ON
    ADC14->CTL0 |= ADC14_CTL0_ON;

}
//HELPER FUNCTIONS//

/*******************************************************************************
* Function Name: ADC14_init
********************************************************************************
* Summary: Initializes all the hardware resources required for ADC14 and
*           the PS2 X and Y components
* Returns:
*  Nothing
*******************************************************************************/
void ADC14_init(){
    //method takes care of setting ADC14 up
    //All requirements met
    ece353_ADC14_PS2_XY();
}

/*******************************************************************************
* Function Name: ADC14_IRQHandler
********************************************************************************
* Summary: Sets global values for X and Y on the PS2 on interrupt
*
* Returns:
*  Nothing
*******************************************************************************/
void ADC14_IRQHandler(){
    // Read the X value
    PS2_X_DIR = ADC14->MEM[0];
    // Read the Y value
    PS2_Y_DIR = ADC14->MEM[1];
}
