/*
 * task_joystick.c
 *
 *  Created on: Dec 8, 2020
 *      Author: Dayton Duffy, Reece Lardy
 */

#include <main.h>

TaskHandle_t Task_Accel_Handle;
TaskHandle_t Task_Accel_Timer_Handle;
TaskHandle_t Task_Joystick_Handle;
TaskHandle_t Task_Joystick_Timer_Handle;

volatile uint32_t accel = 0;
volatile uint32_t JOYSTICK_X_DIR = 0;
volatile uint32_t JOYSTICK_Y_DIR = 0;

/******************************************************************************
 * This function will run the same configuration code that you would have
 * written for HW02.
 ******************************************************************************/
void Task_Joystick_Accel_Init(void)
{
    // Configure the X direction as an analog input pin.
    P6->SEL0 |= BIT0;
    P6->SEL1 |= BIT0;

    // Configure the Y direction as an analog input pin.
    P4->SEL0 |= BIT4;
    P4->SEL1 |= BIT4;

    // Configure the Z direction as an analog input pin.
    P4->SEL0 |= BIT2;
    P4->SEL1 |= BIT2;

    // Configure CTL0 to sample 16-times in pulsed sample mode.
    // NEW -- Indicate that this is a sequence of samples.
    ADC14->CTL0 = ADC14_CTL0_SHT0_2 | ADC14_CTL0_SHP | ADC14_CTL0_CONSEQ_1;

    // Configure CTL1 to return 12-bit values
    ADC14->CTL1 = ADC14_CTL1_RES_2; // Use sampling timer, 12-bit conversion results

    // Associate the X direction analog signal with MEM[0]
    ADC14->MCTL[0] |= ADC14_MCTLN_INCH_15;

    // Associate the Y direction analog signal with MEM[1]
    // NEW -- Make sure to indicate this is the end of a sequence.
    ADC14->MCTL[1] |= ADC14_MCTLN_INCH_9;

    //Store accel value into mem 2
    ADC14->MCTL[2] |= ADC14_MCTLN_INCH_11 | ADC14_MCTLN_EOS;

    // Enable interrupts AFTER a value is written into MEM[1].
    ADC14->IER0 |= ADC14_IER0_IE2;

    // Enable ADC Interrupt
    NVIC_EnableIRQ(ADC14_IRQn);

    // Interrupt priorities must NOT be set to 0.  It is preferred if they are set to a value that is > than 1 too.
    NVIC_SetPriority(ADC14_IRQn, 2);

    // Turn ADC ON
    ADC14->CTL0 |= ADC14_CTL0_ON;

}

/******************************************************************************
 * Used to start an ADC14 Conversion
 ******************************************************************************/
void Task_Joystick_Timer(void *pvParameters)
{
    while (1)
    {
        /*
         * Start the ADC conversion
         */
        ADC14->CTL0 |= ADC14_CTL0_SC | ADC14_CTL0_ENC;

        /*
         * Delay 5mS
         */
        vTaskDelay(pdMS_TO_TICKS(5));

    }
}

/******************************************************************************
 * Bottom Half Task.  Examines the ADC data from the joy stick on the MKII
 ******************************************************************************/
void Task_Joystick_Bottom_Half(void *pvParameters)
{

    MSG_t msg;
    msg.value = 1; //always one square with joystick
    bool cleared = true;
    uint8_t dir;

    while (1)
    {
        /*
         * Wait until we get a task notification from the ADC14 ISR
         */
        ulTaskNotifyTake(pdTRUE, portMAX_DELAY);


        if (cleared)
        { //can send new command
            /*
             * Set the dir variable to one of the following values based
             * on the values of JOYSTICK_X_DIR and JOYSTIC_Y_DIR
             */
            if (JOYSTICK_X_DIR < VOLT_0P85)
            {
                msg.cmd = CMD_LEFT;
                xQueueSend(Queue_BattleShip, &msg, portMAX_DELAY);
                cleared = false;
                dir = 0;
            }
            else if (JOYSTICK_X_DIR > VOLT_2P50)
            {
                msg.cmd = CMD_RIGHT;
                xQueueSend(Queue_BattleShip, &msg, portMAX_DELAY);
                cleared = false;
                dir = 1;
            }
            else if (JOYSTICK_Y_DIR < VOLT_0P85)
            {
                msg.cmd = CMD_DOWN;
                xQueueSend(Queue_BattleShip, &msg, portMAX_DELAY);
                cleared = false;
                dir = 2;
            }
            else if (JOYSTICK_Y_DIR > VOLT_2P50)
            {
                msg.cmd = CMD_UP;
                xQueueSend(Queue_BattleShip, &msg, portMAX_DELAY);
                cleared = false;
                dir = 3;
            }
        }
        else
        { //waiting for it to go back to center
            if (dir == 0)
            {
                if (JOYSTICK_X_DIR > VOLT_0P85)
                {
                    cleared = true;
                }
            }
            else if (dir == 1)
            {
                if (JOYSTICK_X_DIR < VOLT_2P50)
                {
                    cleared = true;
                }
            }
            else if (dir == 2)
            {
                if (JOYSTICK_Y_DIR > VOLT_0P85)
                {
                    cleared = true;
                }
            }
            else
            {
                if (JOYSTICK_Y_DIR < VOLT_2P50)
                {
                    cleared = true;
                }
            }
        }

    }
}

/******************************************************************************
 * Top Half of ADC14 Handler.
 ******************************************************************************/
void ADC14_IRQHandler(void)
{
    BaseType_t xHigherPriorityTaskWoken1 = pdFALSE;
    BaseType_t xHigherPriorityTaskWoken2 = pdFALSE;

    JOYSTICK_X_DIR = ADC14->MEM[0]; // Read the value and clear the interrupt
    JOYSTICK_Y_DIR = ADC14->MEM[1]; // Read the value and clear the interrupt
    accel = ADC14->MEM[2]; // Read the value and clear the interrupt

    /*
     * Send a task notification to Task_Joystick_Bottom_Half
     */
    vTaskNotifyGiveFromISR(Task_Joystick_Handle, &xHigherPriorityTaskWoken1);
    portYIELD_FROM_ISR(xHigherPriorityTaskWoken1);

    //accelerometer task notification
    vTaskNotifyGiveFromISR(Task_Accel_Handle, &xHigherPriorityTaskWoken2);
    portYIELD_FROM_ISR(xHigherPriorityTaskWoken2);
}

//ACCELEROMETER SECTION
/******************************************************************************
 * Used to start an ADC14 Conversion
 ******************************************************************************/
void Task_Accel_Timer(void *pvParameters)
{
    while (1)
    {
        /*
         * Start the ADC conversion
         */
        ADC14->CTL0 |= ADC14_CTL0_SC | ADC14_CTL0_ENC;

        /*
         * Delay 5mS
         */
        vTaskDelay(pdMS_TO_TICKS(5));

    }
}

/******************************************************************************
 * Bottom Half Task.  Examines the ADC data from the joy stick on the MKII
 ******************************************************************************/
void Task_Accel_Bottom_Half(void *pvParameters)
{

    MSG_t msg;
    msg.value = 1; //always one pixel with joystick

    while (1)
    {
        /*
         * Wait until we get a task notification from the ADC14 ISR
         */
        ulTaskNotifyTake(pdTRUE, portMAX_DELAY);

        /*
         * Check if board is rotated
         */
        if (accel < 2048)
        {
            // ROTATE
            msg.cmd = CMD_ROTATE;
            xQueueSend(Queue_BattleShip, &msg, portMAX_DELAY);
            vTaskDelay(pdMS_TO_TICKS(500));
        }
    }
}
