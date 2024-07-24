#include "msp.h"
#include <stdint.h>
#include <stdbool.h>
#include "lcd.h"
#include "image.h"


#define RED_5   0x1F
#define GREEN_6 0x0F
#define BLUE_5  0x0F
#define BLACK 0x0000

#define FG_COLOR (RED_5 << 11)
#define BG_COLOR BLACK

#define X_COORD 63
#define Y_COOR  63
#define X_LEN   20
#define Y_LEN   20

/**
 * main.c
 */
void main(void)
{
    WDT_A->CTL = WDT_A_CTL_PW | WDT_A_CTL_HOLD;     // stop watchdog timer

    Crystalfontz128x128_Init();

#if 0
    lcd_draw_rectangle(
            X_COORD,
            Y_COOR,
            X_LEN,
            Y_LEN,
            FG_COLOR
    );
#else
    lcd_draw_image(
      64,
      64,
      wisconsinWidthPixels,
      wisconsinHeightPixels,
      wisconsinBitmaps,
      FG_COLOR,
      BG_COLOR
    );
#endif

    while(1)
    {

    }

}

