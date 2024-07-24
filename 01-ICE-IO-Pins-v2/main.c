#include "msp.h"

/**
 * main.c
 */
int main(void)
{
    WDT_A->CTL = WDT_A_CTL_PW | WDT_A_CTL_HOLD; //stop watch dog timer

    //button one
    P1->DIR &= ~BIT1;//input
    P1->REN |= BIT1;//pull up
    P1->OUT |= BIT1;//output

    //button two
    P1->DIR &= ~BIT4;//input
    P1->REN |= BIT4;//pull up
    P1->OUT |= BIT4;//output

    //RGBLED outputs
    P2->DIR |= BIT0;//red
    P2->DIR |= BIT1;//green
    P2->DIR |= BIT2;//blue

    while(1){

        //Wait for the sequential button presses
        while(1){
            if((P1->IN & BIT1) == 0){
                break;
            }
        }
        while(1){
            if((P1->IN & BIT4) == 0){
                break;
            }
        }
        //RED ON
        P2->OUT |= BIT0;


        //Wait for the sequential button presses
        while(1){
             if((P1->IN & BIT1) == 0){
                 break;
              }
        }
        while(1){
             if((P1->IN & BIT4) == 0){
                 break;
             }
        }
        //RED OFF GREEN ON
        P2->OUT &= ~BIT0;
        P2->OUT |= BIT1;

        //Wait for the sequential button presses
        while(1){
            if((P1->IN & BIT1) == 0){
                break;
            }
        }
        while(1){
            if((P1->IN & BIT4) == 0){
                break;
            }
        }
        //GREEN OFF BLUE ON
        P2->OUT &= ~BIT1;
        P2->OUT |= BIT2;

        //Wait for the sequential button presses
        while(1){
            if((P1->IN & BIT1) == 0){
                break;
            }
        }
        while(1){
            if((P1->IN & BIT4) == 0){
                break;
            }
        }
        //BLUE OFF
        P2->OUT &= ~BIT2;

    }

}
