/*
 * opt3001.h
 *
 *  Created on: Oct 20, 2020
 *      Author: Joe Krachey
 */

#ifndef TASK_LIGHT_H_
#define TASK_LIGHT_H_

#include "main.h"
#include "msp.h"
#include "i2c.h"
#include <stdint.h>
#include <stdbool.h>

#define I2C_LIGHT_ADDR     0x44

#define I2C_LIGHT_RES      0x00
#define I2C_LIGHT_CONFIG   0x01
#define I2C_LIGHT_LOW      0x02
#define I2C_LIGHT_HIGH     0x03

/* CONFIGURATION REGISTER SETTINGS */
#define LIGHT_RST              0xC810
#define LIGHT_POWER_UP         0xCE10
#define LIGHT_LIM_LOW          0x0000
#define LIGHT_LIM_HIGH         0xBFFF

extern TaskHandle_t Task_Light_Handle;

/******************************************************************************
 * Initialize the tmp006 temperature sensor on the MKII.  This function assumes
 * that the I2C interface has already been configured to operate at 100KHz.
 ******************************************************************************/
void Task_Light_Init(void);

/******************************************************************************
 * Returns the current temperature in degrees C.
 ******************************************************************************/
void Task_Light(void *pvParameters);

#endif /* TMP006_H_ */
