#include "msp.h"
#include "ece353.h"
#include "i2c.h"
#include "tmp006.h"
#include <stdint.h>
#include <stdio.h>



void display_temp(float temp)
{
    if(temp < 25.0)
    {
        ece353_MKII_RGB_LED(false, false, true); // turn on BLUE
    }
    else if (temp < 26.0)
    {
        ece353_MKII_RGB_LED(false, true, false); // turn on GREEN
    }
    else if (temp < 27.0)
    {
        ece353_MKII_RGB_LED(true, true, false); // turn on YELLOW
    }
    else
    {
        ece353_MKII_RGB_LED(true, false, false); // turn on RED
    }
}
/**
 * main.c
 */
void main(void)
{
    float temp = 0.0;

    WDT_A->CTL = WDT_A_CTL_PW | WDT_A_CTL_HOLD;     // stop watchdog timer

    ece353_MKII_RGB_IO_Init(false);
    i2c_init();
    tmp006_init();

    while(1){
        temp = tmp006_read_temp();
        display_temp(temp);
    };



}

