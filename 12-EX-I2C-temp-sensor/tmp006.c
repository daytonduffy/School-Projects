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
    i2c_write_16(I2C_TEMP_ADDR, I2C_TEMP_CONFIG, TMP006_RST);

    // delay
    for(i = 0; i < 50000; i++){};

    // Program the CONFIG register to POWER_UP and bein CR_2 mode
    i2c_write_16(I2C_TEMP_ADDR, I2C_TEMP_CONFIG, (TMP006_POWER_UP | TMP006_CR_2));
}

/******************************************************************************
 * Returns the current temperature in degrees C.
 ******************************************************************************/
float tmp006_read_temp(void)
{
    uint16_t temp;

    // Read the ambiant temperature
    temp = i2c_read_16(I2C_TEMP_ADDR, I2C_TEMP_AMBIENT_TEMP);
    // Return the data in degrees C.  (See TMP006 Data Sheet)
    // You will need to modify the line below to return this value
    return ((float)(temp >> 2)/32);
}

