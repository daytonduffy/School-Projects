/*
 * music.h
 *
 *      Author: Dayton Duffy
 */

#ifndef MUSIC_H_
#define MUSIC_H_

#include <stdint.h>
#include <stdbool.h>
#include "msp.h"
#include "ece353.h"


//*****************************************************************************
// You will need to determine the number of SMCLKs it will
// take for the following notes and their associated frequencies.
//
// Assume that your MCU is running at 24MHz. assuming duty cycle is done elsewhere and we just do full ticks
//*****************************************************************************
#define NOTE_C6_fake        12944
#define NOTE_A5_fake        30000
#define NOTE_fake           33000

#define NOTE_A5        27120 // 880 Hz      0.00113s      REAL A5
#define NOTE_A5S       25680 // 932 Hz      0.00107s
#define NOTE_B5        24240 // 988 Hz      0.00101s

#define NOTE_C6        32944 // 1046 Hz     9.56e-4

#define NOTE_C6S       21648 // 1109 Hz     9.02e-4
#define NOTE_D6        30424 // 1175 Hz     8.51e-4

#define NOTE_D6S       19272 // 1245 Hz     8.03e-4
#define NOTE_E6        28192 // 1319 Hz     7.58e-4

#define NOTE_F6        17160 // 1397 Hz     7.15e-4
#define NOTE_F6S       16200 // 1480 Hz     6.75e-4
#define NOTE_G6        15288 // 1568 Hz     6.37e-4
#define NOTE_G6S       14448 // 1661 Hz     6.02e-4
#define NOTE_A6        13632 // 1760 Hz     5.68e-4

#define NOTE_A6S       32864 // 1865 Hz     5.36e-4
#define NOTE_B6        12144 // 1976 Hz     5.06e-4
#define NOTE_B6S       31376


#define NOTE_C7        11448 // 2093 Hz     4.77e-4
#define NOTE_C7S       10824 // 2217 Hz     4.51e-4
#define NOTE_D7        10200 // 2349 Hz     4.25e-4
#define NOTE_D7S       9624  // 2489 Hz     4.01e-4
#define NOTE_E7        9096  // 2637 Hz     3.79e-4
#define NOTE_F7        8568  // 2794 Hz     3.57e-4
#define NOTE_F7S       8088  // 2960 Hz     3.37e-4
#define NOTE_G7        7632  // 3136 Hz     3.18e-4
#define NOTE_G7S       7224  // 3322 Hz     3.01e-4

//*****************************************************************************
// DO NOT MODIFY ANYTHING BELOW
//*****************************************************************************
#define MEASURE_DURATION 12000000    // 500 milliseconds
#define MEASURE_RATIO           2    // 2/4 time
#define DELAY_AMOUNT       960000    // 10  milliseconds
#define SONG_NUM_NOTES 27

typedef enum measure_time_t {
    ONE_QUARTER,
    ONE_HALF,
    ONE_EIGTH,
    THREE_EIGTH
} measure_time_t;

typedef struct{
    uint32_t period;
    measure_time_t time;
    bool delay;
}Note_t;

//***************************************************************
//***************************************************************
void music_init(void);

//***************************************************************
//***************************************************************
void music_play_song(uint8_t song);

#endif /* MUSIC_H_ */
