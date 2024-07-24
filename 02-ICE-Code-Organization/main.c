#include "msp.h"
#include "ece353.h"

/**
 * main.c
 */
void main(void)
{
	WDT_A->CTL = WDT_A_CTL_PW | WDT_A_CTL_HOLD;		// stop watchdog timer

	//Initialize parts needed
	ece353_button1_init();//button 1
	ece353_button2_init();//button 2
	ece353_rgb_init();//LEDS

	//O = all off, 1 = red on, 2 = blue on, 3 = green on
	int cnt = 0;


	while(1){
	    if(ece353_button1()){
	       while(1){
	           if(ece353_button2()){
	               if(cnt == 0){
	                   ece353_rgb(true, false, false);//turn on red
	                   cnt = cnt + 1;
	               }else if(cnt == 1){
	                   ece353_rgb(false, true, false);//turn on green
	                   cnt = cnt + 1;
	               }else if(cnt == 2){
	                   ece353_rgb(false, false, true);//turn on blue
	                   cnt = cnt + 1;
	               }else{//cnt == 3
	                   ece353_rgb(false, false, false);//off state
	                   cnt = 0;
	               }
	               break;
	           }
	       }
	    }
	}
}
