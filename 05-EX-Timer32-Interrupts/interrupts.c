/*
 * interrupts.c
 *
 *  Created on: Sep 27, 2020
 *      Author: Owner
 */

#include "interrupts.h"

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



