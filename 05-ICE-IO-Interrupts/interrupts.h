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

extern volatile bool ALERT_S1_PRESSED;
extern volatile uint32_t cnt;

void T32_INT1_IRQHandler();
void ice05_init_s1();
void PORT5_IRQHandler();

#endif /* INTERRUPTS_H_ */
