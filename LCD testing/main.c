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

//row defines, 10 y values
#define s0 16
#define s1 28
#define s2 40
#define s3 52
#define s4 64
#define s5 76
#define s6 88
#define s7 100
#define s8 112
#define s9 124



/**
 * main.c
 */
void main(void)
{
    WDT_A->CTL = WDT_A_CTL_PW | WDT_A_CTL_HOLD;     // stop watchdog timer

    Crystalfontz128x128_Init();



    lcd_draw_image(
      64,
      64,
      wisconsinWidthPixels,
      wisconsinHeightPixels,
      wisconsinBitmaps,
      LCD_COLOR_BLACK,
      LCD_COLOR_BLUE2
    );

    //TOP ROW
    lcd_draw_rectangle(
      16,
      4,
      10,
      10,
      LCD_COLOR_WHITE
    );
    lcd_draw_rectangle(
      28,
      4,
      10,
      10,
      LCD_COLOR_YELLOW
    );
    lcd_draw_rectangle(
      40,
      4,
      10,
      10,
      LCD_COLOR_RED
    );
    lcd_draw_rectangle(
      52,
      4,
      10,
      10,
      LCD_COLOR_GREEN
    );
    lcd_draw_rectangle(
      64,
      4,
      10,
      10,
      LCD_COLOR_BLUE
    );
    lcd_draw_rectangle(
      76,
      4,
      10,
      10,
      LCD_COLOR_ORANGE
    );
    lcd_draw_rectangle(
      88,
      4,
      10,
      10,
      LCD_COLOR_CYAN
    );
    lcd_draw_rectangle(
      100,
      4,
      10,
      10,
      LCD_COLOR_BROWN
    );
    lcd_draw_rectangle(
      112,
      4,
      10,
      10,
      LCD_COLOR_MAGENTA
    );
    lcd_draw_rectangle(
      124,
      4,
      10,
      10,
      LCD_COLOR_GRAY
    );



    //LEFT ROW
    lcd_draw_rectangle(
      4,
      16,
      10,
      10,
      LCD_COLOR_WHITE
    );
    lcd_draw_rectangle(
      4,
      28,
      10,
      10,
      LCD_COLOR_YELLOW
    );
    lcd_draw_rectangle(
      4,
      40,
      10,
      10,
      LCD_COLOR_RED
    );
    lcd_draw_rectangle(
      4,
      52,
      10,
      10,
      LCD_COLOR_GREEN
    );
    lcd_draw_rectangle(
      4,
      64,
      10,
      10,
      LCD_COLOR_BLUE
    );
    lcd_draw_rectangle(
      4,
      76,
      10,
      10,
      LCD_COLOR_ORANGE
    );
    lcd_draw_rectangle(
      4,
      88,
      10,
      10,
      LCD_COLOR_CYAN
    );
    lcd_draw_rectangle(
      4,
      100,
      10,
      10,
      LCD_COLOR_BROWN
    );
    lcd_draw_rectangle(
      4,
      112,
      10,
      10,
      LCD_COLOR_MAGENTA
    );
    lcd_draw_rectangle(
      4,
      124,
      10,
      10,
      LCD_COLOR_GRAY
    );




    while(1)
    {

    }

}

