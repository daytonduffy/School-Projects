#include "msp.h"
#include "ece353.h"

#define TICKS_ON    1500000
#define TICKS_OFF   1500000
/**
 * main.c
 */
void main(void)
{
	WDT_A->CTL = WDT_A_CTL_PW | WDT_A_CTL_HOLD;		// stop watchdog timer

	ece353_led1_init();

	while(1){

	    //toggle the led
	    ece353_led1(true); //LED is on
	    ece353_T32_1_wait(TICKS_ON);

	    ece353_led1(false); //LED is off
	    ece353_T32_1_wait(TICKS_OFF);
	}

}
