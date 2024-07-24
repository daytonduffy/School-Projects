/*
 * ps2.h
 *
 *  Created on: Oct 30, 2020
 *      Author: Dayton Duffy
 */

#ifndef PS2_H_
#define PS2_H_

// Add a global variable that holds the most recent value of the X direction
extern volatile uint32_t PS2_X_DIR;
// Add a global variable that holds the most recent value of the Y direction
extern volatile uint32_t PS2_Y_DIR;

/*******************************************************************************
* Function Name: ADC14_init
********************************************************************************
* Summary: Initializes all the hardware resources required for ADC14 and
*           the PS2 X and Y components
* Returns:
*  Nothing
*******************************************************************************/
void ADC14_init(void);

/*******************************************************************************
* Function Name: ADC14_IRQHandler
********************************************************************************
* Summary: Sets global values for X and Y on the PS2 on interrupt
*
* Returns:
*  Nothing
*******************************************************************************/
void ADC14_IRQHandler(void);

#endif /* PS2_H_ */
