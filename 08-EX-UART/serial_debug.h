/*
 * serial_debug.h
 *
 *  Created on: Oct 6, 2020
 *      Author: Owner
 */

#ifndef SERIAL_DEBUG_H_
#define SERIAL_DEBUG_H_


#include <stdio.h>
#include <string.h>
#include "msp.h"


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
