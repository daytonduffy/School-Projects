/*
 * serial_debug.c
 *
 *  Created on: Oct 6, 2020
 *      Author: Owner
 */

#include "serial_debug.h"

volatile char Rx_String[];
volatile uint16_t Rx_Char_Count = 0;
volatile bool ALERT_STRING = false;
Circular_Buffer *Tx_Buffer;


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

    // Prime the pump -- ADD CODE
    EUSCI_A0->TXBUF = 0;
}

//****************************************************************************
// Initializes the EUSCI_A0 peripheral to be a UART with a baud rate of 115200
//
// NOTE: This function assumes that the SMCLK has been configured to run at
// 24MHz.  Please change __SYSTEM_CLOCK in system_msp432p401r.c to the// correct value of 24000000
// ****************************************************************************/
void serial_debug_init(void){
    //Initialize UART
    serial_debug_init_uart();
    Tx_Buffer = circular_buffer_init(80);
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

//****************************************************************************
// This function is called from MicroLIB's stdio library.  By implementing
// this function, MicroLIB's putchar(), puts(), printf(), etc will now work.
// ****************************************************************************/
int fputc(int c, FILE* stream){
    // Busy wait while the circular buffer is full
    while(circular_buffer_full(Tx_Buffer)){
        //wait
    }

    // globally disable interrupts
    __disable_irq();

    // add the character to the circular buffer  -- ADD CODE
    circular_buffer_add(Tx_Buffer, c);

    // globally disable interrupts
    __enable_irq();

    // Enable Tx Empty Interrupts  -- ADD CODE
    EUSCI_A0->IE |= BIT3;//transmit complete interrupt enable (p930)

    return 0;
}

//UART interrupt services routine
void EUSCIA0_IRQHandler(void){
    char c;

    if (EUSCI_A0->IFG & EUSCI_A_IFG_RXIFG){
        ALERT_STRING = true;
    }
    // Check for Tx Interrupts
    if (EUSCI_A0->IFG & EUSCI_A_IFG_TXIFG){
        //Check to see if the Tx circular buffer is empty
        if(circular_buffer_empty(Tx_Buffer)){
            // Disable Tx Interrupts
            //not disable tx empty interrupts? just tx interrupts?
            EUSCI_A0->IE &= ~BIT1;
        }else{
            // Get the next char from the circular buffer
            c = circular_buffer_remove(Tx_Buffer);

            // Transmit the character
            EUSCI_A0->TXBUF = c;
        }
    }

}
