/*
 * timer32.c
 *
 *  Created on: Oct 30, 2020
 *      Author: Dayton Duffy
 */

#include "timer32.h"

volatile bool ALERT_LCD_CAN_UPDATE = false;
volatile bool ALERT_S2_PRESSED = false;


//HELPER FUNCTIONS
/*****************************************************
 * Configure Timer32 to generate a periodic timer
 *
 * Parameters
 *      ticks   :   number of ms per interrupt
 * Returns
 *      None
 *****************************************************/
void ece353_T32_1_Interrupt_Ms(uint16_t ms){

    //ticks = desired period / core clock period
    //20e-3/(1/3e6) = (3e6 * 20)/1000
    uint32_t ticks = ((SystemCoreClock * ms)/1000) - 1;

    //stop the timer
    TIMER32_1->CONTROL = 0;

    //set the load register
    TIMER32_1->LOAD = ticks;

    //enable the TImer32 interrupt in NVIC
    __enable_irq();
    NVIC_EnableIRQ(T32_INT1_IRQn);
    NVIC_SetPriority(T32_INT1_IRQn, 0);

    //start Timer32 and enable interrupt
    TIMER32_1->CONTROL = TIMER32_CONTROL_ENABLE |   // turn timer on
                         TIMER32_CONTROL_MODE |     // periodic mode
                         TIMER32_CONTROL_SIZE |     // 32 bit timer
                         TIMER32_CONTROL_IE;        // enable interrupts

}

/*****************************************************
 * Configure Timer32 to generate a periodic timer
 *
 * Parameters
 *      ticks   :   number of ms per interrupt
 * Returns
 *      None
 *****************************************************/
void ece353_T32_2_Interrupt_Ms(uint16_t ms){

    //ticks = desired period / core clock period
    //20e-3/(1/3e6) = (3e6 * 20)/1000
    uint32_t ticks = ((SystemCoreClock * ms)/1000) - 1;

    //stop the timer
    TIMER32_2->CONTROL = 0;

    //set the load register
    TIMER32_2->LOAD = ticks;

    //enable the TImer32 interrupt in NVIC
    __enable_irq();
    NVIC_EnableIRQ(T32_INT2_IRQn);
    NVIC_SetPriority(T32_INT2_IRQn, 0);

    //start Timer32 and enable interrupt
    TIMER32_2->CONTROL = TIMER32_CONTROL_ENABLE |   // turn timer on
                         TIMER32_CONTROL_MODE |     // periodic mode
                         TIMER32_CONTROL_SIZE |     // 32 bit timer
                         TIMER32_CONTROL_IE;        // enable interrupts

}
/*****************************************************
 * Returns if MKII.S2 is currently pressed
 *
 * Parameters
 *      None
 * Returns
 *      true    :   Button2 is pressed
 *      false   :   Button2 is NOT pressed
 *****************************************************/
bool ece353_MKII_S2(void){
    if((P3->IN & BIT5) == 0){
        return true;
    }else{
        return false;
    }
}
/*****************************************************
 * Initialize Hardware resources used to control S2
 * on the MK11
 *****************************************************/
void ece353_MKII_S2_Init(void){
    //configure as input
    P3->DIR &= ~BIT5;

}
//HELPER FUNCTIONS


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
void T32_inits(){
    ece353_MKII_S2_Init();         //Initialize Switch 2 on the MKII
    ece353_T32_1_Interrupt_Ms(10); //Timer32 1 generate interrupt every 10ms
    ece353_T32_2_Interrupt_Ms(100);//Timer32 2 generate interrupt every 100ms
}

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
void T32_INT1_IRQHandler(){
    //Start ADC14 conversion
    ADC14->CTL0 |= ADC14_CTL0_SC | ADC14_CTL0_ENC;

    if(!ALERT_S2_PRESSED){//can't do anything if it's pressed
        //debounce push button
        static uint8_t button_state = 0x00;

        bool button_pressed = ece353_MKII_S2();

        button_state = button_state << 1;

        if(button_pressed){
            button_state |= 0x01;
        }

        //70ms of pressing so true, needs to be set false somewhere else
        if(button_state == 0x7F){
            ALERT_S2_PRESSED = true;
        }
    }

    //clear the interrupt
    TIMER32_1->INTCLR = BIT0;
}

/*******************************************************************************
* Function Name: T32_inits
********************************************************************************
* Summary: Set a global variable that will be used to indicate that the LCD screen
*               can be updated.
*
* Returns:
*  Nothing
*******************************************************************************/
void T32_INT2_IRQHandler(){
    //let the LCD update
    ALERT_LCD_CAN_UPDATE = true;

    //clear the interrupt
    TIMER32_2->INTCLR = BIT0;
}

