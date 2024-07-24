#include "msp.h"
#include "ece353.h"
#include "i2c.h"
#include "tmp006.h"
#include <stdint.h>
#include <stdio.h>



void display_temp(float lux)
{
    if(lux < 20.0)
    {
        ece353_MKII_RGB_LED(false, false, true); // turn on BLUE
    }
    else if (lux < 200.0)
    {
        ece353_MKII_RGB_LED(false, true, false); // turn on GREEN
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
    float lux = 0.0;

    WDT_A->CTL = WDT_A_CTL_PW | WDT_A_CTL_HOLD;     // stop watchdog timer

    ece353_MKII_RGB_IO_Init(false);
    i2c_init();
    tmp006_init();

    while(1){
        lux = tmp006_read_temp();
        display_temp(lux);
    };



}

