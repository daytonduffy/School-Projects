/*
 * task_light.c
 *
 *  Created on: Dec 8, 2020
 *      Author: Dayton Duffy, Reece Lardy
 */

#include <task_light.h>


TaskHandle_t Task_Light_Handle;

/******************************************************************************
 * Initialize the tmp006 temperature sensor on the MKII.  This function assumes
 * that the I2C interface has already been configured to operate at 100KHz.
 ******************************************************************************/
void Task_Light_Init(void)
{

    i2c_init();

    int i;

    // Reset the device using the CONFIG register
    i2c_write_16(I2C_LIGHT_ADDR, I2C_LIGHT_CONFIG, LIGHT_RST);

    // delay
    for(i = 0; i < 50000; i++){};


    // Set high Limit
    i2c_write_16(I2C_LIGHT_ADDR, I2C_LIGHT_HIGH, LIGHT_LIM_HIGH);


    // delay
    for(i = 0; i < 50000; i++){};


    // Set low limit
    i2c_write_16(I2C_LIGHT_ADDR, I2C_LIGHT_LOW, LIGHT_LIM_LOW);

    // delay
    for(i = 0; i < 50000; i++){};


    // Program the CONFIG register to POWER_UP and begin
    i2c_write_16(I2C_LIGHT_ADDR, I2C_LIGHT_CONFIG, LIGHT_POWER_UP);


}

/******************************************************************************
 * Returns the current temperature in degrees C.
 ******************************************************************************/
void Task_Light(void *pvParameters)
{
    uint16_t res; //light value

    uint8_t last = 1;
    uint8_t curr = 1;

    MSG_t msg;
    msg.cmd = CMD_COLOR_PALETTE;

    while(1){
           // Read the ambiant temperature
           res = i2c_read_16(I2C_LIGHT_ADDR, I2C_LIGHT_RES);
           // Return the data in degrees C.  (See TMP006 Data Sheet)
           // You will need to modify the line below to return this value
           uint16_t exp = (res >> 12)&0x00F;      //get just first 4 bits
           uint32_t result = res & 0x0FFF;    // get just last 12 bits

           //change to proper LUX value
           switch(exp){
           case 0:
                   result = result >> 6;
                   break;
           case 1:
                       result = result >> 5;
                       break;
           case 2:
                       result = result >> 4;
                       break;
           case 3:
                       result = result >> 3;
                       break;
           case 4:
                       result = result >> 2;
                       break;
           case 5:
                       result = result >> 1;
                       break;
           case 6:
                       result = result;
                       break;
           case 7:
                       result = result << 1;
                       break;
           case 8:
                       result = result << 2;
                       break;
           case 9:
                       result = result << 3;
                       break;
           case 10:
                       result = result << 4;
                       break;
           case 11:
                       result = result << 5;
                       break;
           }

           ;

           if(result < 20.0){//dark
               curr = 0;
           }else if(result < 200.0){//normal
               curr = 1;
           }else{//light
               curr = 2;
           }

           //send to queue when color changes
           if(curr != last){
               last = curr;
               msg.value = curr;
               xQueueSend(Queue_BattleShip, &msg, portMAX_DELAY);
           }

           //wait a sec before checking again
           vTaskDelay(pdMS_TO_TICKS(20));

    }

}

