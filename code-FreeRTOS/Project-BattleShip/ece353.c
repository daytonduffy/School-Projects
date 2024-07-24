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
    if (on)
    {
        P1->OUT |= BIT0; //turns LED on
    }
    else
    {
        P1->OUT &= ~BIT0; //turns LED off
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
    if ((P1->IN & BIT1 ) == 0)
    {
        return true; //button being pressed
    }
    else
    {
        return false; //not pressed
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
void ece353_RBG_LED_Init(void)
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
void ece353_RGB_LED(bool red_on, bool green_on, bool blue_on)
{
    //Red
    if (red_on)
    {
        P2->OUT |= BIT0; //turns LED on
    }
    else
    {
        P2->OUT &= ~BIT0; //turns LED off
    }
    //Green
    if (green_on)
    {
        P2->OUT |= BIT1; //turns LED on
    }
    else
    {
        P2->OUT &= ~BIT1; //turns LED off
    }
    //Blue
    if (blue_on)
    {
        P2->OUT |= BIT2; //turns LED on
    }
    else
    {
        P2->OUT &= ~BIT2; //turns LED off
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
    if ((P1->IN & BIT4 ) == 0)
    {
        return true; //being pressed
    }
    else
    {
        return false; //not pressed
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
void ece353_T32_1_wait(uint32_t ticks)
{
    //Stop the timer kinda a reset
    TIMER32_1->CONTROL = 0;

    //Set the timer to be a 32-bit, one-shot
    TIMER32_1->CONTROL = TIMER32_CONTROL_ONESHOT | TIMER32_CONTROL_SIZE;

    //Set the load register
    TIMER32_1->LOAD = ticks;

    //Start the timer
    TIMER32_1->CONTROL |= TIMER32_CONTROL_ENABLE;

    //Wait until it reaches 0
    while (TIMER32_1->VALUE != 0)
    {
        //Timer is busy wait
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
void ece353_T32_2_wait(uint32_t ticks)
{
    //Stop the timer kinda a reset
    TIMER32_2->CONTROL = 0;

    //Set the timer to be a 32-bit, one-shot
    TIMER32_2->CONTROL = TIMER32_CONTROL_ONESHOT | TIMER32_CONTROL_SIZE;

    //Set the load register
    TIMER32_2->LOAD = ticks;

    //Start the timer
    TIMER32_2->CONTROL |= TIMER32_CONTROL_ENABLE;

    //Wait until it reaches 0
    while (TIMER32_2->VALUE != 0)
    {
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
void ece353_T32_1_wait_100mS(void)
{
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
    while (TIMER32_1->VALUE != 0)
    {
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
void ece353_button1_wait_for_press(void)
{
    uint32_t cnt = 0;
    while (1)
    {        //wait for the button to hit 5 seconds of time
        cnt = 0;        //cnt needs to be 0 at the start of button push
        while (ece353_button1() == true)
        {        //when pressed enter loop until stop or condition met
            ece353_T32_1_wait_100mS();        //wait 1/50th of the time
            cnt = cnt + 1;        //add to the counter

            if (cnt == 50)
            {        //done all 5 seconds of waiting
                return;        //leave this loop if button still pressed
            }
        }
    }

}

// ICE 4
/*****************************************************
 * Initialize Hardware resources used to control S1
 * on the MK11
 *****************************************************/
void ece353_MKII_S1_Init(void)
{
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
void ece353_MKII_S2_Init(void)
{
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
bool ece353_MKII_S1(void)
{

    if ((P5->IN & BIT1 ) == 0)
    {
        return true;
    }
    else
    {
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
bool ece353_MKII_S2(void)
{
    if ((P3->IN & BIT5 ) == 0)
    {
        return true;
    }
    else
    {
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
void ece353_MKII_Buzzer_Init(uint16_t ticks_period)
{
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
    TIMER_A0->CCR[4] = (ticks_period / 2) - 1;

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
void ece353_MKII_Buzzer_On(void)
{
    //clear the current mode control bits
    TIMER_A0->CTL &= ~TIMER_A_CTL_MC_MASK;

    //set mode control to UP and clear current count
    TIMER_A0->CTL |= TIMER_A_CTL_MC__UP | TIMER_A_CTL_CLR;
}

/*****************************************************
 * Turns the Buzzer off
 *
 * Parameters
 *      None
 * Returns
 *      None
 *****************************************************/
void ece353_MKII_Buzzer_Off(void)
{
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
bool ece353_MKII_Buzzer_Run_Status(void)
{
    if ((TIMER_A0->CTL & TIMER_A_CTL_MC_MASK) == TIMER_A_CTL_MC__STOP)
    {
        return false;
    }
    else
    {
        return true;
    }
}

//*****************************************************************************
//*****************************************************************************
// ICE 04 - PWMing MKII tri-color LED.
//*****************************************************************************
//*****************************************************************************

/*****************************************************
 * Initialize the 3 GPIO pins that control the RGB
 * LED on the MKII.
 *
 * Parameters
 *      None
 * Returns
 *      None
 *****************************************************/
void ece353_MKII_RGB_IO_Init(bool en_primary_function)
{

    // Complete the comments below to identify which pins
    // control which LEDs.
    //
    // Replace a and c with the correct port number
    // Replace b and d with the correct pin numbers.
    // RED      : P2.6
    // GREEN    : P2.4
    // BLUE     : P5.6

    // ADD CODE that configures the RED, GREEN, and
    // BLUE LEDs to be outputs

    //Red
    P2->DIR |= BIT6; //Set direction as an output
    P2->OUT &= ~BIT6; //Turn off LED
    //Green
    P2->DIR |= BIT4; //Set direction as an output
    P2->OUT &= ~BIT4; //Turn off LED
    //Blue
    P5->DIR |= BIT6; //Set direction as an output
    P5->OUT &= ~BIT6; //Turn off LED

    // ADD CODE that selects the Primary module function
    // for all 3 pins
    if (en_primary_function)
    {
        //RED
        P2->SEL0 |= BIT6;
        P2->SEL1 &= ~BIT6;
        //GREEN
        P2->SEL0 |= BIT4;
        P2->SEL1 &= ~BIT4;
        //BLUE
        P5->SEL0 |= BIT6;
        P5->SEL1 &= ~BIT6;
    }

}

/*****************************************************
 * Sets the PWM levels for the MKII RGBLED
 *
 * Parameters
 *      ticks_period    :   Period of PWM Pulse
 *      red_num         :   RED RGB Number
 *      green_num       :   GREEN RGB Number
 *      blue_num        :   BLUE RGB Number
 * Returns
 *      None
 *****************************************************/
void ece353_MKII_RGB_PWM(uint16_t ticks_period, uint16_t red_num,
                         uint16_t green_num, uint16_t blue_num)
{
    // This code converts the HTML color codes into a
    // number of clock ticks used to generate the duty cyle of
    // the PWM signal.
    uint16_t ticks_red_on = (red_num * ticks_period) / 255;
    uint16_t ticks_green_on = (green_num * ticks_period) / 255;
    uint16_t ticks_blue_on = (blue_num * ticks_period) / 255;

    // Initialze the IO pins used to control the
    // tri-color LED.
    ece353_MKII_RGB_IO_Init(true);

    // Complete the comments below that identify which
    // TimerA outputs will control the IO pins.
    //
    // Replace w and y with the correct TimerA number
    // Replace x and z with the correct CCR number.
    // RED      <--> TA0.3
    // GREEN    <--> TA0.1
    // BLUE     <--> TA2.1

    // ADD CODE BELOW
    //
    // Turn off the timer peripherals
    TIMER_A0->CTL = 0;
    TIMER_A2->CTL = 0;

    // Set the period for both TimerA peripherals.
    TIMER_A0->CCR[0] = ticks_period - 1;
    TIMER_A2->CCR[0] = ticks_period - 1;

    // Configure RED PWM duty cycle
    TIMER_A0->CCR[3] = ticks_red_on;

    // Configure GREEN PWM duty cycle
    TIMER_A0->CCR[1] = ticks_green_on;

    // Configure BLUE PWM duty cycle
    TIMER_A2->CCR[1] = ticks_blue_on;

    // Set the OUT MODE to be mode 7 for each
    // PWM output
    TIMER_A0->CCTL[3] = TIMER_A_CCTLN_OUTMOD_7;
    TIMER_A0->CCTL[1] = TIMER_A_CCTLN_OUTMOD_7;
    TIMER_A2->CCTL[1] = TIMER_A_CCTLN_OUTMOD_7;

    // Turn the first TimerA peripheral
    TIMER_A0->CTL = TIMER_A_CTL_SSEL__SMCLK;
    TIMER_A0->CTL |= TIMER_A_CTL_MC__UP | TIMER_A_CTL_CLR;

    // Turn the second TimerA peripheral
    TIMER_A2->CTL = TIMER_A_CTL_SSEL__SMCLK;
    TIMER_A2->CTL |= TIMER_A_CTL_MC__UP | TIMER_A_CTL_CLR;
}

/*****************************************************
 * Configure Timer32 to generate a periodic timer
 *
 * Parameters
 *      ticks   :   number of ms per interrupt
 * Returns
 *      None
 *****************************************************/
void ece353_T32_1_Interrupt_Ms(uint16_t ms)
{

    //ticks = desired period / core clock period
    //20e-3/(1/3e6) = (3e6 * 20)/1000
    uint32_t ticks = ((SystemCoreClock * ms) / 1000) - 1;

    //stop the timer
    TIMER32_1->CONTROL = 0;

    //set the load register
    TIMER32_1->LOAD = ticks;

    //enable the TImer32 interrupt in NVIC
    //__enable_irq();
    NVIC_EnableIRQ(T32_INT1_IRQn);
    NVIC_SetPriority(T32_INT1_IRQn, 2);

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
void ece353_T32_2_Interrupt_Ms(uint16_t ms)
{

    //ticks = desired period / core clock period
    //20e-3/(1/3e6) = (3e6 * 20)/1000
    uint32_t ticks = ((SystemCoreClock * ms) / 1000) - 1;

    //stop the timer
    TIMER32_2->CONTROL = 0;

    //set the load register
    TIMER32_2->LOAD = ticks;

    //enable the TImer32 interrupt in NVIC
    __enable_irq();
    NVIC_EnableIRQ(T32_INT2_IRQn);
    NVIC_SetPriority(T32_INT2_IRQn, 2);    ///CHANGED FOR THIS PROJECT

    //start Timer32 and enable interrupt
    TIMER32_2->CONTROL = TIMER32_CONTROL_ENABLE |   // turn timer on
            TIMER32_CONTROL_MODE | // periodic mode MAKE ONE SHOT TIMER32_CONTROL_ONESHOT
            TIMER32_CONTROL_SIZE |     // 32 bit timer
            TIMER32_CONTROL_IE;        // enable interrupts

}

/*****************************************************
 * Configure ADC14 X direction
 *
 * Parameters
 *      None
 * Returns
 *      None
 *****************************************************/
void ece353_ADC14_PS2_X()
{

    //Configure p6.0 (A15) the X direction as an analog input pin
    P6->SEL0 |= BIT0;
    P6->SEL1 |= BIT0;

    //Configure the ADC14 Control Registers
    //Sample time of 16 ADC cycles for the first 8 analog channels
    //Allow the ADC timer to control pulsed samples
    ADC14->CTL0 = ADC14_CTL0_SHP | ADC14_CTL0_SHT02;

    //Use sampling timer, 12-bit conversion results
    ADC14->CTL1 = ADC14_CTL1_RES_2;

    //Configure Memory Control register so that we associate the correct
    //analog channel with MEM[0]
    ADC14->MCTL[0] = ADC14_MCTLN_INCH_15;

    //Enable Interrupts after the conversion of MEM[0] completes
    ADC14->IER0 = ADC14_IER0_IE0;

    //Enable ADC Interrupt
    NVIC_EnableIRQ(ADC14_IRQn);

    //Turn ADC ON
    ADC14->CTL0 |= ADC14_CTL0_ON;

}

//*****************************************************************************
//*****************************************************************************
// ICE 06 - ADC14
//*****************************************************************************

/******************************************************************************
 * Configure the IO pins for BOTH the X and Y directions of the analog
 * joystick.  The X direction should be configured to place the results in
 * MEM[0].  The Y direction should be configured to place the results in MEM[1].
 *
 * After BOTH analog signals have finished being converted, a SINGLE interrupt
 * should be generated.
 *
 * Parameters
 *      None
 * Returns
 *      None
 ******************************************************************************/
void ece353_ADC14_PS2_XY(void)
{
    // Configure the X direction as an analog input pin.
    P6->SEL0 |= BIT0;
    P6->SEL1 |= BIT0;

    // Configure the Y direction as an analog input pin.
    P4->SEL0 |= BIT4;
    P4->SEL1 |= BIT4;

    // Configure CTL0 to sample 16-times in pulsed sample mode.
    // NEW -- Indicate that this is a sequence-of-channels.
    ADC14->CTL0 = ADC14_CTL0_SHP | ADC14_CTL0_SHT02;
    ADC14->CTL0 |= 0x00020000;
    //sets bits 18-17 to b01 or sequence of channels mode

    // Configure ADC to return 12-bit values
    ADC14->CTL1 = ADC14_CTL1_RES_2;

    // Associate the X direction analog signal with MEM[0]
    ADC14->MCTL[0] = ADC14_MCTLN_INCH_15;

    // Associate the Y direction analog signal with MEM[1]
    // NEW -- Make sure to indicate this is the end of a sequence.
    ADC14->MCTL[1] = ADC14_MCTLN_INCH_9;

    ADC14->MCTL[1] |= BIT7; //bit 7 tells machine that this is end of sequence

    // Enable interrupts in the ADC AFTER a value is written into MEM[1].
    //
    // NEW: This is not the same as what is demonstrated in the example
    // coding video.
    ADC14->IER0 = ADC14_IER0_IE1;

    // Enable ADC Interrupt in the NVIC
    NVIC_EnableIRQ(ADC14_IRQn);

    // Turn ADC ON
    ADC14->CTL0 |= ADC14_CTL0_ON;

}

/*****************************************************
 * Turn RGB on the MKII LED ON/Off.
 *
 * Parameters
 *  red:    if true,  turn RED LED on
 *          if false, turn RED LED off
 *  green:  if true,  turn GREEN LED on
 *          if false, turn GREEN LED off
 *  blue:   if true,  turn BLUE LED on
 *          if false, turn BLUE LED off
 *****************************************************/
void ece353_MKII_RGB_LED(bool red, bool green, bool blue)
{
    //Red
    if (red)
    {
        P2->OUT |= BIT6;    //turns LED on
    }
    else
    {
        P2->OUT &= ~BIT6;    //turns LED off
    }
    //Green
    if (green)
    {
        P2->OUT |= BIT4;    //turns LED on
    }
    else
    {
        P2->OUT &= ~BIT4;    //turns LED off
    }
    //Blue
    if (blue)
    {
        P5->OUT |= BIT6;    //turns LED on
    }
    else
    {
        P5->OUT &= ~BIT6;    //turns LED off
    }

}

//*****************************************************************************
//*****************************************************************************
// ICE 07 - ADC14 Comparator
//*****************************************************************************
#define VOLT_0P85 1056      // 0.85 /(3.3/4096)
#define VOLT_2P50 3103      // 2.50 /(3.3/4096)

/******************************************************************************
 * Configure the IO pins for the X direction of the analog
 * joystick.  The X direction should be configured to place the results in
 * MEM[0].
 *
 * The ADC14 should be configured to generate interrupts using the Window
 * comparator.  The HI0 threshold should be set to 2.5V.  The LO0 threshold
 * should be set to 0.85V.
 *
 * Parameters
 *      None
 * Returns
 *      None
 ******************************************************************************/
void ece353_ADC14_PS2_XY_COMP(void)
{
    // Configure the X direction as an analog input pin.
    P6->SEL0 |= BIT0;
    P6->SEL1 |= BIT0;

    // Configure CTL0 to sample 16-times in pulsed sample mode.
    // Indicate that this is a sequence of samples. I AM ASSUMING THIS IS SUPPOSED TO SAY CHANNELS
    ADC14->CTL0 = ADC14_CTL0_SHP | ADC14_CTL0_SHT02;
    ADC14->CTL0 |= 0x00020000;

    // Configure CTL1 to return 12-bit values
    ADC14->CTL1 = ADC14_CTL1_RES_2;

    // Set up the HI0 Window
    ADC14->HI0 = VOLT_2P50;

    // Set up the LO0 Window
    ADC14->LO0 = VOLT_0P85;

    // Associate the X direction analog signal with MEM[0], indicate the end of sequence,
    // turn on the window comparator
    ADC14->MCTL[0] = ADC14_MCTLN_INCH_15;
    ADC14->MCTL[0] |= BIT7; //bit 7 tells machine that this is end of sequence
    ADC14->MCTL[0] |= 0x00004000; //bit 14 enables compare window

    // Enable interrupts when either the HI or LO thresholds of the window
    // comparator has been exceeded.  Disable all other interrupts
    ADC14->IER1 |= BIT3; //enables for HI0
    ADC14->IER1 |= BIT2; //enables for LO0
    ADC14->IER1 |= BIT1; //enables for IN
    //no others are enabled ?

    // Enable ADC Interrupt
    NVIC_EnableIRQ(ADC14_IRQn);

    // Turn ADC ON
    ADC14->CTL0 |= ADC14_CTL0_ON;

}
