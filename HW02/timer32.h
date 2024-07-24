/*
 * timer32.h
 *
 *  Created on: Oct 30, 2020
 *      Author: Dayton Duffy
 */

#ifndef TIMER32_H_
#define TIMER32_H_

#include "msp.h"
#include <stdbool.h>
#include <stdint.h>

extern volatile bool ALERT_LCD_CAN_UPDATE;
extern volatile bool ALERT_S2_PRESSED;

/*******************************************************************************
* Function Name: T32_inits
********************************************************************************
* Summary: Initializes all the hardware resources required for Timer32_1, Timer32_2,
*           and Switch 2 on the MKII
*           Generates interrupt in Timer32_1 every 10ms
*           Generates interrupt in Timer32_2 every 100ms
* Returns:
*  Nothing
*******************************************************************************/
void T32_inits();

/*******************************************************************************
* Function Name: T32_INT1_IRQHandler
********************************************************************************
* Summary: Detect when S2 has been pressed for a minimum of 70mS. This will require you to de-bounce S2.
*                When S2 has been pressed, set a global variable that indicates that the button has been pressed.
*                The user must release S2 before it can detect another button press.
*                Every 10ms start an ADC14 conversion
*
* Returns:
*  Nothing
*******************************************************************************/
void T32_INT1_IRQHandler();

/*******************************************************************************
* Function Name: T32_INT2_IRQHandler
********************************************************************************
* Summary: Set a global variable that will be used to indicate that the LCD screen
*               can be updated.
*
* Returns:
*  Nothing
*******************************************************************************/
void T32_INT2_IRQHandler();

#endif /* TIMER32_H_ */
