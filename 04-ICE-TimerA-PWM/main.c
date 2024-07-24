#include "msp.h"
#include <stdint.h>
#include "ece353.h"
int main(void) {
    WDT_A->CTL = WDT_A_CTL_PW |  WDT_A_CTL_HOLD;// Stop WDT

    ece353_MKII_RGB_PWM(1000, 5, 5, 200);

    while(1){
        __no_operation();
    }

}
