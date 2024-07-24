/*
 * opt3001.h
 *
 *  Created on: Oct 20, 2020
 *      Author: Joe Krachey
 */

#ifndef TMP006_H_
#define TMP006_H_

#include "msp.h"
#include "i2c.h"
#include <stdint.h>
#include <stdbool.h>

#define I2C_TEMP_ADDR   0x40

#define I2C_TEMP_VOBJ           0x00
#define I2C_TEMP_AMBIENT_TEMP   0x01
#define I2C_TEMP_CONFIG         0x02
#define I2C_TEMP_DEV_ID         0xFF

/* CONFIGURATION REGISTER SETTINGS */
#define TMP006_RST              0x8000
#define TMP006_POWER_DOWN       0x0000
#define TMP006_POWER_UP         0x7000
#define TMP006_CR_4             0x0000
#define TMP006_CR_2             0x0200
#define TMP006_CR_1             0x0400
#define TMP006_CR_0_5           0x0600
#define TMP006_CR_0_25          0x0800
#define TMP006_EN               0x0100
#define TMP006_DRDY             0x0080

/******************************************************************************
 * Initialize the tmp006 temperature sensor on the MKII.  This function assumes
 * that the I2C interface has already been configured to operate at 100KHz.
 ******************************************************************************/
void tmp006_init(void);

/******************************************************************************
 * Returns the current temperature in degrees C.
 ******************************************************************************/
float tmp006_read_temp(void);

#endif /* TMP006_H_ */
