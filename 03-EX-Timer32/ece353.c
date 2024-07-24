#include "ece353.h"
//****************************************************
//****************************************************
// 02-Ex-Code-Organization
//****************************************************
//****************************************************

/* ***************************************************
 * Initialize hardware resources used to control LED1
 *****************************************************/
void ece353_led1_init(void)
{
    P1->DIR |= BIT0; //Set direction as an output
    P1->OUT &= ~BIT0; //Turn off LED
}

/*****************************************************
 * Initialize hardware resources used to control Button1
 *****************************************************/
void ece353_button1_init(void)
{
    P1->DIR &= ~BIT1; //Set to input
    P1->REN |= BIT1; //enable resistor
    P1->OUT |= BIT1; //select pull up
}

/*****************************************************
 * Turn LED1 ON/Off.
 *
 * Parameters
 *  on: if true,  turn LED on
 *      if false, turn LED off
 *****************************************************/
void ece353_led1(bool on)
{
    if(on){
        P1->OUT |= BIT0;//turns LED on
    }else{
        P1->OUT &= ~BIT0;//turns LED off
    }
}

/*****************************************************
 * Returns if Button1 is currently pressed.
 *
 * Parameters
 *
 * Returns
 *      true    :   Button1 is pressed
 *      false   :   Button1 is NOT pressed
 *****************************************************/
bool ece353_button1(void)
{
    if((P1->IN & BIT1) == 0){
        return true;//button being pressed
    }else{
        return false;//not pressed
    }
}



//****************************************************
//****************************************************
// 02-ICE-Code-Organization
//****************************************************
//****************************************************

/*****************************************************
 * Initialize hardware resources used to control RGBLED
 *****************************************************/
void ece353_rgb_init(void)
{
    //Red
    P2->DIR |= BIT0; //Set direction as an output
    P2->OUT &= ~BIT0; //Turn off LED
    //Green
    P2->DIR |= BIT1; //Set direction as an output
    P2->OUT &= ~BIT1; //Turn off LED
    //Blue
    P2->DIR |= BIT2; //Set direction as an output
    P2->OUT &= ~BIT2; //Turn off LED
}

/*****************************************************
 * Turn RGBLED ON/Off.
 *
 * Parameters
 *  red_on      :   if true,  turn RGBLED.RED on
 *                  if false, turn RGBLED.RED off
 *  green_on    :   if true,  turn RGBLED.GREEN on
 *                  if false, turn RGBLED.GREEN off
 *  blue_on     :   if true,  turn RGBLED.BLUE on
 *                  if false, turn RGBLED.BLUE off
 *****************************************************/
void ece353_rgb(bool red_on, bool green_on, bool blue_on)
{
    //Red
    if(red_on){
        P2->OUT |= BIT0;//turns LED on
    }else{
        P2->OUT &= ~BIT0;//turns LED off
    }
    //Green
    if(green_on){
        P2->OUT |= BIT1;//turns LED on
    }else{
        P2->OUT &= ~BIT1;//turns LED off
    }
    //Blue
    if(blue_on){
        P2->OUT |= BIT2;//turns LED on
    }else{
        P2->OUT &= ~BIT2;//turns LED off
    }
}

/*****************************************************
 * Initialize hardware resources used to control Button2
 *****************************************************/
void ece353_button2_init(void)
{
    P1->DIR &= ~BIT4; //Set to input
    P1->REN |= BIT4; //enable resistor
    P1->OUT |= BIT4; //select pull up
}

/*****************************************************
 * Returns if Button2 is currently pressed.
 *
 * Parameters
 *
 * Returns
 *      true    :   Button2 is pressed
 *      false   :   Button2 is NOT pressed
 *****************************************************/
bool ece353_button2(void)
{
    if((P1->IN & BIT4) == 0){
        return true;//being pressed
    }else{
        return false;//not pressed
    }
}

/*****************************************************
 * Busy waits for a given number of clock cycles
 *
 * Parameters
 *      ticks   :   Number of ticks to wait
 * Returns
 *      None
 *****************************************************/
void ece353_T32_1_wait(uint32_t ticks){
    //Stop the timer kinda a reset
    TIMER32_1->CONTROL = 0;

    //Set the timer to be a 32-bit, one-shot
    TIMER32_1->CONTROL = TIMER32_CONTROL_ONESHOT | TIMER32_CONTROL_SIZE;

    //Set the load register
    TIMER32_1->LOAD = ticks;

    //Start the timer
    TIMER32_1->CONTROL |= TIMER32_CONTROL_ENABLE;

    //Wait until it reaches 0
    while(TIMER32_1->VALUE != 0){
        //Timer is busy wait
    }
}
