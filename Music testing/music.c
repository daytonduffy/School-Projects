/*
 * music.c
 *
 * Author: Dayton Duffy
 *
 */

#include "music.h"


Note_t Song[] =
{
    {NOTE_C6,ONE_QUARTER,true},  // Tone, Time, Delay
    {NOTE_B5,ONE_QUARTER,true},
    {NOTE_D6,ONE_QUARTER,true},
    {NOTE_C6,ONE_QUARTER,true},
    {NOTE_F6,ONE_QUARTER,true},
    {NOTE_E6,ONE_QUARTER,true},
    {NOTE_G6,ONE_QUARTER,true},
    {NOTE_F6,ONE_QUARTER,true},
    {NOTE_A6,ONE_QUARTER,true},
    {NOTE_A6,ONE_QUARTER,true},
    {NOTE_A6,ONE_QUARTER,true},
    {NOTE_A6,ONE_QUARTER,true},
    {NOTE_A6,ONE_HALF,false},
    {NOTE_A6,ONE_HALF,true},
    {NOTE_G6,ONE_QUARTER,true},
    {NOTE_F6S,ONE_QUARTER,true},
    {NOTE_G6,ONE_QUARTER,true},
    {NOTE_A6,ONE_QUARTER,true},
    {NOTE_F6,ONE_QUARTER,true},
    {NOTE_G6,ONE_QUARTER,true},
    {NOTE_A6,ONE_QUARTER,true},
    {NOTE_F6,ONE_QUARTER,true},
    {NOTE_E6,ONE_QUARTER,true},
    {NOTE_D6,ONE_QUARTER,true},
    {NOTE_E6,ONE_QUARTER,true},
    {NOTE_F6,ONE_QUARTER,true},
    {NOTE_G6,ONE_HALF,false},
    {NOTE_G6,ONE_HALF,true}
};

Note_t Win[] =
{
    {NOTE_C6,ONE_EIGTH,true},  // Tone, Time, Delay
    {NOTE_C6,ONE_EIGTH,true},
    {NOTE_C6,ONE_EIGTH,true},
    {NOTE_C6,ONE_QUARTER,true},

    {NOTE_A6S,ONE_QUARTER,true},

    {NOTE_B6S,ONE_QUARTER,true},

    {NOTE_C6,ONE_QUARTER,true},

    {NOTE_B6S,ONE_EIGTH,true},

    {NOTE_C6,ONE_HALF,true},

};

Note_t Open[] =
{
    {NOTE_C6,ONE_HALF,true},  // Tone, Time, Delay
    {NOTE_C6,ONE_HALF,true},
    {NOTE_C6,ONE_HALF,true},

    {NOTE_D6,ONE_HALF,true},  // Tone, Time, Delay
    {NOTE_D6,ONE_HALF,true},
    {NOTE_D6,ONE_HALF,true},

    {NOTE_E6,ONE_HALF,true},  // Tone, Time, Delay
    {NOTE_E6,ONE_HALF,true},
    {NOTE_E6,ONE_HALF,true},

    {NOTE_F6,ONE_HALF,true},
};

Note_t Miss[] =
{
    {NOTE_A5_fake,ONE_QUARTER,true},  // Tone, Time, Delay
    {NOTE_fake,ONE_QUARTER,true},
};

Note_t Hit[] =
{
    {NOTE_C6_fake,ONE_QUARTER,true},  // Tone, Time, Delay
    {NOTE_G6,ONE_QUARTER,true},
};

//***************************************************************
// This function returns how long an individual  notes is played
//***************************************************************
uint32_t music_get_time_delay(measure_time_t time)
{
    uint32_t time_return = 0;

    time_return  = MEASURE_DURATION * MEASURE_RATIO;

    switch(time)
    {
        case ONE_QUARTER:
        {
            time_return  = time_return / 4;
            break;
        }
        case ONE_HALF:
        {
            time_return  = time_return / 2;
            break;
        }
        case ONE_EIGTH:
        {
            time_return  = time_return / 8;
            break;
        }
        case THREE_EIGTH:
        {
            time_return = time_return * 3;
            time_return  = time_return / 8;
            break;
        }
        default:
        {
            break;
        }
    }

    return time_return - DELAY_AMOUNT;

}


//***************************************************************************
// Plays a single note of the song based on note_index.  After
// the note is played, there is an optional delay in between
// notes.
//
// Examples
// Song[note_index].period       -- Used to determine the period of the
//                                  PWM pulse.
//
// Song[note_index].time         -- 1/4 or 1/2 time note.  Call
//                                  music_get_time_delay() to determine how
//                                  long to play the note
//
// Song[note_index].delay        -- If true, add a period of silence for
//                                  DELAY_AMOUNT
//
//                                  If false, return without delaying.
//*************************************************************************
static void music_play_note_1(uint16_t note_index){

    //configure the timer and buzzer
    ece353_MKII_Buzzer_Init(Hit[note_index].period);

    //turn the buzzer on
    ece353_MKII_Buzzer_On();

    //wait given amount of time for the note to play
    //tells timer to wait time given by helper method
    ece353_T32_1_wait(music_get_time_delay(Hit[note_index].time));

    //turn off the buzzer
    ece353_MKII_Buzzer_Off();

    //if delay is true have a period of silence before returning
    if(Hit[note_index].delay){
        ece353_T32_1_wait(DELAY_AMOUNT);
    }
}

static void music_play_note_2(uint16_t note_index){

    //configure the timer and buzzer
    ece353_MKII_Buzzer_Init(Miss[note_index].period);

    //turn the buzzer on
    ece353_MKII_Buzzer_On();

    //wait given amount of time for the note to play
    //tells timer to wait time given by helper method
    ece353_T32_1_wait(music_get_time_delay(Miss[note_index].time));

    //turn off the buzzer
    ece353_MKII_Buzzer_Off();

    //if delay is true have a period of silence before returning
    if(Miss[note_index].delay){
        ece353_T32_1_wait(DELAY_AMOUNT);
    }
}

static void music_play_note_3(uint16_t note_index){

    //configure the timer and buzzer
    ece353_MKII_Buzzer_Init(Win[note_index].period);

    //turn the buzzer on
    ece353_MKII_Buzzer_On();

    //wait given amount of time for the note to play
    //tells timer to wait time given by helper method
    ece353_T32_1_wait(music_get_time_delay(Win[note_index].time));

    //turn off the buzzer
    ece353_MKII_Buzzer_Off();

    //if delay is true have a period of silence before returning
    if(Win[note_index].delay){
        ece353_T32_1_wait(DELAY_AMOUNT);
    }
}

static void music_play_note_4(uint16_t note_index){

    //configure the timer and buzzer
    ece353_MKII_Buzzer_Init(Open[note_index].period);

    //turn the buzzer on
    ece353_MKII_Buzzer_On();

    //wait given amount of time for the note to play
    //tells timer to wait time given by helper method
    ece353_T32_1_wait(music_get_time_delay(Open[note_index].time));

    //turn off the buzzer
    ece353_MKII_Buzzer_Off();

    //if delay is true have a period of silence before returning
    if(Open[note_index].delay){
        ece353_T32_1_wait(DELAY_AMOUNT);
    }
}
//***************************************************************
// Plays the song (loop through, playing each note)
// and then returns
//***************************************************************
void music_play_song(uint8_t song){
    //for each entry in song array
    //play one note with given method
    uint16_t i;
    if(song == 3){
        for(i=0; i < 9; ++i){
           music_play_note_3(i); //Hit switch 1
        }
    }else if(song == 4){
        for(i=0; i < 10; ++i){
           music_play_note_4(i); //Hit switch 1
        }
    }else{
        for(i=0; i < 2; ++i){
            if(song == 1){
                music_play_note_1(i); //Hit switch 1
            }else if(song == 2){
                music_play_note_2(i); //Miss switch 2
            }
        }
    }
}
