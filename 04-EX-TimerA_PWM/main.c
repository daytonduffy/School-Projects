#include "msp.h"
#include "ece353.h"

/**
 * main.c
 */
void main(void)
{
    uint16_t pwm_count = (uint16_t)(SystemCoreClock/2000) - 1;//2.0k

	WDT_A->CTL = WDT_A_CTL_PW | WDT_A_CTL_HOLD;		// stop watchdog timer

	//configure SW1
	ece353_MKII_S1_Init();

	//configure TimerA0
	ece353_MKII_Buzzer_Init(pwm_count);

	while(1){
	    if(ece353_MKII_S1()){//if S1 pressed
	        //only turn on if its currently off
	        if(eece353_MKII_Buzzer_Run_Status() == false){
	            ece353_MKII_Buzzer_On();
	        }
	    }else{
	        ece353_MKII_Buzzer_Off();
	    }
	}
}
