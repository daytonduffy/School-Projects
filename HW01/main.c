/*
 * main.c
 *
 * Author: Dayton Duffy
 *
 */
#include "msp.h"
#include "ece353.h"
#include "music.h"

/**
 * main.c
 */
void main(void)
{
    WDT_A->CTL = WDT_A_CTL_PW | WDT_A_CTL_HOLD;     // stop watchdog timer

    // Initialize S1 on MKII
    ece353_MKII_S1_Init();

    while(1) {
        // When S1 is pressed, play the song
        if(ece353_MKII_S1()){
            music_play_song();
        }
    }
}
