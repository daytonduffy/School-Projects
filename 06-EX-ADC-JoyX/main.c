#include "msp.h"
#include "ece353.h"
#include "interrupts.h"

#define VOLT_0P85 ((int)(0.85/(3.3/4096))) // 1056  // 0.85 /(3.3/4096)
#define VOLT_2P50 ((int)(2.50/(3.3/4096))) // 3103  // 2.50 /(3.3/4096)

/**
 * main.c
 */
void main(void)
{
	WDT_A->CTL = WDT_A_CTL_PW | WDT_A_CTL_HOLD;		// stop watchdog timer

	//configure the RGB LED on the MSP432 Launchpad
	ece353_rgb_init();

	//configure just the X direction of the PS2 joystick
	//as a single channel, single sample in the ADC14
	ece353_ADC14_PS2_X();

	//Set up Timer32 to interrupt at a rate of 100ms
	//This interrupt will result in an ADC14 sample
	ece353_Timer32_1_Interrupt_Ms(100);

	//enable NVIC
	__enable_irq();

	//wait forever
	while(1){
	    if(PS2_X_DIR > VOLT_2P50){
	        ece353_rgb(true, false, false);//turn on red
	    }else if(PS2_X_DIR < VOLT_0P85){
	        ece353_rgb(false, true, false);//turn on green
	    }else{
	        ece353_rgb(false, false, true);//turn on blue
	    }
	}


}
