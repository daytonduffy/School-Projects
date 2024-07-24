#include "msp.h"
#include "serial_debug.h"

/**
 * main.c
 */
void main(void)
{
	WDT_A->CTL = WDT_A_CTL_PW | WDT_A_CTL_HOLD;		// stop watchdog timer

	serial_debug_init();

	__enable_irq();
	serial_debug_put_string("\n\r\n\r");
	serial_debug_put_string("*****************************************\n\r");
	serial_debug_put_string("ECE353 ex 08 UART\n\r");
	serial_debug_put_string("*****************************************\n\r");
	serial_debug_put_string("All characters typed into the terminal will be\n\r");
	serial_debug_put_string("echoed back to the user by the UART ISR\n\r");
	serial_debug_put_string("*****************************************\n\r");
	while(1){

	}
}
