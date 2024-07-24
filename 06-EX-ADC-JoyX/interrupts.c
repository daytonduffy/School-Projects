/*
 * interrupts.c
 *
 *  Created on: Sep 27, 2020
 *      Author: Owner
 */

#include "interrupts.h"

volatile uint32_t PS2_X_DIR = 0;

void T32_INT1_IRQHandler(void){

    //Start an ADC Conversion
    ADC14->CTL0 |= ADC14_CTL0_SC | ADC14_CTL0_ENC;

    //Clear the Timer interrupt
    TIMER32_1->INTCLR = BIT0;

}

void ADC14_IRQHandler(void){
    PS2_X_DIR = ADC14->MEM[0];
}
