#include "msp.h"
#include "ece353.h"


void delay_tick(uint32_t ticks){
    uint32_t i;
    for(i=0; i < ticks; ++i){
        //do nothing
    }
}
/**
 * main.c
 */
void main(void)
{
	WDT_A->CTL = WDT_A_CTL_PW | WDT_A_CTL_HOLD;		// stop watchdog timer

	ece353_led1_init();

	ece353_button1_init();

	while(1){
	    if(ece353_button1()){
	        ece353_led1(true); // on
	        delay_tick(50000);

	        ece353_led1(false);// off
	        delay_tick(50000);
	    }else{
	        ece353_led1(false);//off
	    }
	}
}
