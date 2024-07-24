/*
 * serial_debug.h
 *
 *  Created on: Oct 6, 2020
 *      Author: Owner
 */

#ifndef SERIAL_DEBUG_H_
#define SERIAL_DEBUG_H_

#include <stdbool.h>
#include <stdint.h>
#include <stdio.h>
#include <string.h>
#include "msp.h"

extern volatile char RX[80];
extern volatile uint16_t RX_cnt;
extern volatile bool RX_ALERT;

// **********************************************************************************************
// Initializes the circular buffers used to transmit and receive data from the
// serial debug interface. It will also initialize the UART interface to enable interrupts
// **********************************************************************************************
void serial_debug_init(void);


// **********************************************************************************************
// Prints a string to the serial debug UART
// **********************************************************************************************
void serial_debug_put_string(char * s);

#endif /* SERIAL_DEBUG_H_ */
