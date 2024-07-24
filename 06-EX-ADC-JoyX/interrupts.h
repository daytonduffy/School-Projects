/*
 * interrupts.h
 *
 *  Created on: Sep 27, 2020
 *      Author: Owner
 */

#ifndef INTERRUPTS_H_
#define INTERRUPTS_H_

#include <stdbool.h>
#include <stdint.h>
#include "msp.h"
#include "ece353.h"

extern volatile uint32_t PS2_X_DIR;

void ADC14_IRQHandler();
void T32_INT1_IRQHandler();

#endif /* INTERRUPTS_H_ */
