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

//*****************************************************************************
//*****************************************************************************
// ICE 03 - Timer32_1 Push Button Detection
//*****************************************************************************
//*****************************************************************************

/*****************************************************
 * Busy waits for 100mS and then returns.
 *
 * Timer32_1 MUST be configured as a 16-bit timer.
 * Assume that the MCU clock runs at 3MHz.  You will
 * need to use a pre-scalar in order to achieve a delay
 * of 100mS.
 *
 * Parameters
 *      None
 * Returns
 *      None
 *****************************************************/
void ece353_T32_1_wait_100mS(void){
        //Stop the timer kinda a reset
        TIMER32_1->CONTROL = 0;

        //Set the timer to be a 16-bit, one-shot
        TIMER32_1->CONTROL = TIMER32_CONTROL_ONESHOT;

        //Set the load register
        //timer period = 0.1s MCU period = 3.3e-7 s
        //ticks = timer period/MCU period
        uint32_t ticks = 300000;
        TIMER32_1->LOAD = ticks;

        //Start the timer
        TIMER32_1->CONTROL |= TIMER32_CONTROL_ENABLE;

        //Wait until it reaches 0
        while(TIMER32_1->VALUE != 0){
            //Timer is busy wait
        }
}


/*****************************************************
 * Debounces Button1 using Timer32_1.
 * This function does not return until Button 1 has
 * been pressed for a minimum of 5 seconds.
 *
 * Waiting 5 Seconds will require you to call
 * ece353_T32_1_wait_100mS multiple times.
 *
 * Parameters
 *      None
 * Returns
 *      None
 *****************************************************/
void ece353_button1_wait_for_press(void){
    bool pass = false;
    uint32_t cnt = 0;
    while(!pass){//wait for the button to hit 5 seconds of time
        cnt = 0;//cnt needs to be 0 at the start of button push
        while(ece353_button1() == true){//when pressed enter loop until stop or condition met
            ece353_T32_1_wait_100mS();//wait 1/50th of the time
            cnt = cnt + 1;//add to the counter

            if(cnt == 50){//done all 5 seconds of waiting
                pass = true;//can escape
                break;//leave this loop if button still pressed
            }
        }
    }

}


/*****************************************************
 * Initialize Hardware resources used to control S1
 * on the MK11
 *****************************************************/
void ece353_MKII_S1_Init(void){
    //configure as input
    P5->DIR &= ~BIT1;

    //enable resistor
    //P5->REN |= BIT1;

    //resistor as a pull-up
    //P5->OUT |= BIT1;
}

/*****************************************************
 * Initialize Hardware resources used to control S2
 * on the MK11
 *****************************************************/
void ece353_MKII_S2_Init(void){
    //configure as input
    P3->DIR &= ~BIT5;

    //enable resistor
    //P3->REN |= BIT5;

    //resistor as a pull-up
    //P3->OUT |= BIT5;
}


/*****************************************************
 * Returns if MKII.S1 is currently pressed
 *
 * Parameters
 *      None
 * Returns
 *      true    :   Button1 is pressed
 *      false   :   Button2 is NOT pressed
 *****************************************************/
bool ece353_MKII_S1(void){

    if((P5->IN & BIT1) == 0){
        return true;
    }else{
        return false;
    }
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
 * Set the PWM period of the Buzzer. The duty cycle
 * will be set to 50%
 *
 * Parameters
 *      ticks_period    :   Period of PWM pulse
 * Returns
 *      None
 *****************************************************/
void ece353_MKII_Buzzer_Init(uint16_t ticks_period){
    //set P2.7 to be GPIO output pin
    P2->DIR |= BIT7;

    //the TIMERA PWM controller will control the buzzer on the MKII
    //P2.7 <--> TA0.4
    P2->SEL0 |= BIT7;
    P2->SEL1 &= ~BIT7;

    //Turn off TA0
    TIMER_A0->CTL = 0;

    //Set timer period(minus 1 cuz zero start)
    TIMER_A0->CCR[0] = ticks_period - 1;

    //configure duty cycle to 50%
    TIMER_A0->CCR[4] = (ticks_period/2) - 1;

    //configure TA0.4 for reset/set mode
    TIMER_A0->CCTL[4] = TIMER_A_CCTLN_OUTMOD_7;

    //select the master clock as timer source
    TIMER_A0->CTL = TIMER_A_CTL_SSEL__SMCLK;

}


/*****************************************************
 * Turns the Buzzer on
 *
 * Parameters
 *      None
 * Returns
 *      None
 *****************************************************/
void ece353_MKII_Buzzer_On(void){
    //clear the current mode control bits
    TIMER_A0->CTL &= ~TIMER_A_CTL_MC_MASK;

    //set mode control to UP and clear current count
    TIMER_A0->CTL |= TIMER_A_CTL_MC_UP | TIMER_A_CTL_CLR;
}


/*****************************************************
 * Turns the Buzzer off
 *
 * Parameters
 *      None
 * Returns
 *      None
 *****************************************************/
void ece353_MKII_Buzzer_Off(void){
    //turn of the timer
    TIMER_A0->CTL &= ~TIMER_A_CTL_MC_MASK;
}

/*****************************************************
 * Check Buzzer Run Status
 *
 * Parameters
 *      None
 * Returns
 *      true    :   TimerA0 is on
 *      false   :   TimerA0 is off
 *****************************************************/
bool ece353_MKII_Buzzer_Run_Status(void){
    if((TIMER_A0->CTL & TIMER_A_CTL_MC_MASK) == TIMER_A_CTL_MC_STOP){
        return false;
    }else{
        return true;
    }
}
