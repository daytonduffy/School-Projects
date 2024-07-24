/*
 * opt3001.c
 *
 *  Created on: Oct 20, 2020
 *      Author: Joe Krachey
 */

#include "tmp006.h"

/******************************************************************************
 * Initialize the tmp006 temperature sensor on the MKII.  This function assumes
 * that the I2C interface has already been configured to operate at 100KHz.
 ******************************************************************************/
void tmp006_init(void)
{
    int i;

    // Reset the device using the CONFIG register
    i2c_write_16(I2C_LIGHT_ADDR, I2C_LIGHT_CONFIG, LIGHT_RST);

    // delay
    for(i = 0; i < 50000; i++){};

    // Set high Limit
    i2c_write_16(I2C_LIGHT_ADDR, I2C_LIGHT_HIGH, LIGHT_LIM_HIGH);

    // delay
    for(i = 0; i < 50000; i++){};

    // Set low limit
    i2c_write_16(I2C_LIGHT_ADDR, I2C_LIGHT_LOW, LIGHT_LIM_LOW);

    // delay
    for(i = 0; i < 50000; i++){};

    // Program the CONFIG register to POWER_UP and begin
    i2c_write_16(I2C_LIGHT_ADDR, I2C_LIGHT_CONFIG, LIGHT_POWER_UP);
}

/******************************************************************************
 * Returns the current temperature in degrees C.
 ******************************************************************************/
float tmp006_read_temp(void)
{
    uint16_t res;

    // Read the ambiant temperature
    res = i2c_read_16(I2C_LIGHT_ADDR, I2C_LIGHT_RES);
    // Return the data in degrees C.  (See TMP006 Data Sheet)
    // You will need to modify the line below to return this value
    uint16_t exp = (res >> 12)&0x00F;      //get just first 4 bits
    uint32_t result = res & 0x0FFF;    // get just last 12 bits

    switch(exp){
    case 0:
            result = result >> 6;
            break;
    case 1:
                result = result >> 5;
                break;
    case 2:
                result = result >> 4;
                break;
    case 3:
                result = result >> 3;
                break;
    case 4:
                result = result >> 2;
                break;
    case 5:
                result = result >> 1;
                break;
    case 6:
                result = result;
                break;
    case 7:
                result = result << 1;
                break;
    case 8:
                result = result << 2;
                break;
    case 9:
                result = result << 3;
                break;
    case 10:
                result = result << 4;
                break;
    case 11:
                result = result << 5;
                break;
    }

    ;
    return (float)result;
}

