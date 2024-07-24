/*
 * serial_debug.c
 *
 *  Created on: Oct 6, 2020
 *      Author: Owner
 */

#include "serial_debug.h"

// **********************************************************************************************
// Initializes the circular buffers used to transmit and receive data from the
// serial debug interface. It will also initialize the UART interface to enable interrupts
// **********************************************************************************************
void serial_debug_init_uart(void){
    //Configure the IO pins used for the UART
    //set 2-UART pin as secondary function
    P1->SEL0 |= BIT2 | BIT3;
    P1->SEL1 &= ~(BIT2 | BIT3);

    EUSCI_A0->CTLW0 |= EUSCI_A_CTLW0_SWRST;// Put eUSCI in reset
    EUSCI_A0->CTLW0 = EUSCI_A_CTLW0_SWRST |
               EUSCI_B_CTLW0_SSEL__SMCLK; //Source select

    //baud rate calculation
    //240000000/(16*115200) = 13.02083333
    //Fractional portion = 0.2083333
    //UCBRFx = int ( (13.0208333 - 13)*16) = 0
    EUSCI_A0->BRW = 13;                          //240000000/16/115200

    //Set the fractional portion of the baud rate
    //and turn on oversampling
    EUSCI_A0->MCTLW = (0 << EUSCI_A_MCTLW_BRF_OFS) | EUSCI_A_MCTLW_OS16;

    //Enable eUSCI in UART mode
    EUSCI_A0->CTLW0 &= ~EUSCI_A_CTLW0_SWRST;

    //Clear any outstanding interrupts
    EUSCI_A0->IFG &= ~(EUSCI_A_IFG_RXIFG | EUSCI_A_IFG_TXIFG);

    //enable  Rx interrupt
    EUSCI_A0->IE |= EUSCI_A_IE_RXIE;

    //enable EUSCI
    NVIC_EnableIRQ(EUSCIA0_IRQn);
}


//initializes the EUSCI_A0 peripheral to be a UART with a baud rate of 115200
//Requires system clock to be at 24Mhz!
void serial_debug_init(void){
    //Initialize UART
    serial_debug_init_uart();

}


// **********************************************************************************************
// Prints a string to the serial debug UART
// **********************************************************************************************
void serial_debug_put_string(char * s){
    while(*s != 0){
        while(EUSCI_A0->STATW & EUSCI_A_STATW_BUSY){

        }
        EUSCI_A0->TXBUF = *s;

        //advance to nxt char
        s++;
    }
}

//Echoing chars typed to the putty terminal sending data over UART
//UART interrupt services routine
void EUSCIA0_IRQHandler(void){
    if(EUSCI_A0->IFG & EUSCI_A_IFG_RXIFG){
        EUSCI_A0->TXBUF = EUSCI_A0->RXBUF;

        if(EUSCI_A0->RXBUF == 0x0D){
            while(EUSCI_A0->STATW & EUSCI_A_STATW_BUSY){
                //wait a sec
            }
            EUSCI_A0->TXBUF = 0x0A;
        }
    }
}
