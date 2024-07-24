#include "msp.h"
#include "ece353.h"

/**
 * main.c
 */
void main(void)
{
	WDT_A->CTL = WDT_A_CTL_PW | WDT_A_CTL_HOLD;		// stop watchdog timer

	// Configure SW1

	    ece353_MKII_S1_Init();

	    int i = 1;

	    int j = 0;

	    while (1)

	    {

	        ece353_MKII_Buzzer_Init((SystemCoreClock / (i)) - 1);

	        if (ece353_MKII_S1())

	        {

	            // Only turn the buzzer on if its current status is off

	            if (ece353_MKII_Buzzer_Run_Status() == false)

	            {

	                ece353_MKII_Buzzer_On();

	            }

	                ece353_MKII_RGB_PWM(1000, i % 19, i % 3, i % 5);

	                for (j = 0; j < 50000; j++) {}; // Delay

	                i++;

	        }

	        else    // SW1 is not pressed, so turn the Buzzer off

	        {

	            ece353_MKII_Buzzer_Off();

	        }

	        i++;

	    }
}
