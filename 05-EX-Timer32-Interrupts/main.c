#include "msp.h"
#include "ece353.h"

/**
 * main.c
 */
void main(void)
{
    bool led_state = false;

	WDT_A->CTL = WDT_A_CTL_PW | WDT_A_CTL_HOLD;		// stop watchdog timer

	//initiate S1 on MKII
	ece353_MKII_S1_Init();

	//enable LED 1
	ece353_led1_init();

	//set up timer to generate interrupt every
	//5ms
	ece353_Timer32_1_Interrupt_Ms(5);

	while(1){
	    if(ALERT_S1_PRESSED){
	        ALERT_S1_PRESSED = false;

	        //toggle led
	        led_state = !led_state;
	        ece353_led1(led_state);
	    }
	}

}
