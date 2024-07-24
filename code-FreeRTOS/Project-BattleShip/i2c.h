/*
 * i2c.h
 *
 *  Created on: Dec 8, 2020
 *      Author: Dayton Duffy, Reece Lardy
 */

#ifndef I2C_H_
#define I2C_H_

#include "msp.h"
#include "main.h"
#include <stdint.h>

/**********************************************************************************************
 * initializes recourses needed to use the i2c
 **********************************************************************************************/
void i2c_init(void);

/**********************************************************************************************
 * reads a given address from the slave device over the i2c
 **********************************************************************************************/
uint16_t i2c_read_16(uint8_t slave_address, uint8_t dev_address);

/**********************************************************************************************
 * writes data to the given address in the slave device
 **********************************************************************************************/
void i2c_write_16(uint8_t slave_address, uint8_t dev_address, uint16_t data);

#endif /* I2C_H_ */
