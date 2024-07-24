#include "msp.h"
#include "serial_debug.h"

/**
 * main.c
 */
void main(void)
{
    WDT_A->CTL = WDT_A_CTL_PW | WDT_A_CTL_HOLD;     // stop watchdog timer

    serial_debug_init();

    __enable_irq();

    serial_debug_put_string("\n\r\n\r");
    serial_debug_put_string("**************************************************\n\r");
    serial_debug_put_string("ECE353 ICE 08 UART\n\r");
    serial_debug_put_string("**************************************************\n\r");


    while(1)
    {
        // check for when a new string has been received and print it to the
        // terminal.
        if(RX_ALERT){//global alert signal


            serial_debug_put_string("Data Rxed: ");
            serial_debug_put_string(RX);
            serial_debug_put_string("\n");

            //reset alert signals
            RX_ALERT = false;
            RX_cnt = 0;
            memset(RX, 0, 80);
        }

    }
}
