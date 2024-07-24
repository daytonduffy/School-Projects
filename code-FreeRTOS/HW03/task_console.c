/*
 * task_console.c
 *
 *  Created on: Nov 21, 2020
 *      Author: Dayton Duffy
 */
#include <main.h>

TaskHandle_t Task_Console_Handle;
SemaphoreHandle_t Sem_Console;

#define RX_ARRAY_SIZE 10

// Global variables used to store incoming data from RXBUF.
volatile char RX_ARRAY[RX_ARRAY_SIZE];
volatile uint16_t RX_INDEX=0;


/******************************************************************************
 * This function configures the eUSCI_A0 to be a UART that communicates at
 * 115200 8N1.
 *
 * The configuration should be identical to what you have done
 * in a previous ICE-08 EXCEPT that you will need to set the priority of the
 * eUSCI to be a value that is equal to 2
 ******************************************************************************/
static void console_hw_init(void)
{
    //Configure the IO pins used for the UART
    //set 2-UART pin as secondary function
    P1->SEL0 |= BIT2 | BIT3;
    P1->SEL1 &= ~(BIT2 | BIT3);

    EUSCI_A0->CTLW0 |= EUSCI_A_CTLW0_SWRST;// Put eUSCI in reset
    EUSCI_A0->CTLW0 = EUSCI_A_CTLW0_SWRST |
               EUSCI_B_CTLW0_SSEL__SMCLK; //Source select

    //baud rate calculation
    //240000000/(16*115200) = 13.02083333
    //Fractional portion = 0.2083333
    //UCBRFx = int ( (13.0208333 - 13)*16) = 0
    EUSCI_A0->BRW = 13;                          //240000000/16/115200

    //Set the fractional portion of the baud rate
    //and turn on oversampling
    EUSCI_A0->MCTLW = (0 << EUSCI_A_MCTLW_BRF_OFS) | EUSCI_A_MCTLW_OS16;

    //Enable eUSCI in UART mode
    EUSCI_A0->CTLW0 &= ~EUSCI_A_CTLW0_SWRST;

    //Clear any outstanding interrupts
    EUSCI_A0->IFG &= ~(EUSCI_A_IFG_RXIFG | EUSCI_A_IFG_TXIFG);

    //enable  Rx interrupt
    EUSCI_A0->IE |= EUSCI_A_IE_RXIE;

    //enable EUSCI
    NVIC_EnableIRQ(EUSCIA0_IRQn);

    //set priority of 2
    NVIC_SetPriority(EUSCIA0_IRQn, 2);
}

/******************************************************************************
 * This function initializes the eUSCI_A0 hardware by calling console_hw_init().
 * It will also initialize Sem_Console.
 *
 * NOTE:  This function must be run prior to the FreeRTOS scheduler being
 * started.
 ******************************************************************************/
void Task_Console_Init(void)
{
    // Initialize the array used to hold UART data
    memset(RX_ARRAY,0,RX_ARRAY_SIZE);

    // Initialize UART
    console_hw_init();

    // Initialize the binary semaphore used to provide mutual exclusion to
    // the UART.
    Sem_Console = xSemaphoreCreateBinary();

}

/*****************************************************
 * Needed to get printf to work using polling when
 * transmitting characters.
 *****************************************************/
int fputc(int c, FILE* stream)
{
    // while UART is busy, wait
    while(EUSCI_A0->STATW & EUSCI_A_STATW_BUSY){};

    EUSCI_A0->TXBUF = c;

    return 0;
}

//****************************************************************************
// UART interrupt service routine
// ****************************************************************************/
void EUSCIA0_IRQHandler(void)
{
    BaseType_t xHigherPriorityTaskWoken = pdFALSE;

    if(EUSCI_A0->IFG & EUSCI_A_IFG_RXIFG){
            EUSCI_A0->TXBUF = EUSCI_A0->RXBUF;                                  //ECHO TO TERMINAL -BEGIN

            if(EUSCI_A0->RXBUF == 0x0D){                                        //IF CARRAIGE RETURN ALSO SEND A NEW LINE
                while(EUSCI_A0->STATW & EUSCI_A_STATW_BUSY){
                    //wait a sec
                }
                EUSCI_A0->TXBUF = 0x0A;
            }

            while(EUSCI_A0->STATW & EUSCI_A_STATW_BUSY){                        //BUSY WAITS FOR SENDING TO TERMINAL
                //busy wait
            }                                                                   //ECHO TO TERMINAL -END


            RX_ARRAY[RX_INDEX] = EUSCI_A0->RXBUF;                               //STORE CHARACTER IN STRING

            if((RX_ARRAY[RX_INDEX] == 0x0A) || (RX_ARRAY[RX_INDEX] == 0x0D)){     //IF CARRAIGE RETURN OR NEW LINE ALERT
                       //send task notification to bottom half task
                       vTaskNotifyGiveFromISR(
                               Task_Console_Handle,
                               &xHigherPriorityTaskWoken
                       );
                       portYIELD_FROM_ISR( xHigherPriorityTaskWoken );
            }

            RX_INDEX++;                                                         //INCREMENT CHAR INDEX
    }
}

/******************************************************************************
* Task used to parse the commands received by the UART
*
* Command  |    Description
*
* L 10          Move Left by 10 pixels
* R 5           Move Right by 5 pixels
* U 20          Move Up by 20 pixels
* D 4           Move Down by 4 pixels
* S 1           Set Ship Speed to 1mS/Pixel
******************************************************************************/
void Task_Console_Bottom_Half(void *pvParameters)
{
    SHIP_MSG_t msg;
    msg.value = 0;
    char data[8];

    while(1){

        //wait for task notification
        ulTaskNotifyTake(pdTRUE, portMAX_DELAY);

        strcpy(data, RX_ARRAY + 2); //data is the last 8 chars of the char array, used to atoi later

        //SORT THROUGH ARRAY
        if((RX_ARRAY[0] == 'L') && (RX_ARRAY[1] == ' ')){
            msg.value = atoi(data); //will just return 0 if bad as far as I'm aware and that wont cause problems
            msg.cmd = SHIP_CMD_LEFT;
        }else if(RX_ARRAY[0] == 'R' && RX_ARRAY[1] == ' '){
            msg.value = atoi(data); //will just return 0 if bad as far as I'm aware and that wont cause problems
            msg.cmd = SHIP_CMD_RIGHT;
        }else if(RX_ARRAY[0] == 'D' && RX_ARRAY[1] == ' '){
            msg.value = atoi(data); //will just return 0 if bad as far as I'm aware and that wont cause problems
            msg.cmd = SHIP_CMD_DOWN;
        }else if(RX_ARRAY[0] == 'U' && RX_ARRAY[1] == ' '){
            msg.value = atoi(data); //will just return 0 if bad as far as I'm aware and that wont cause problems
            msg.cmd = SHIP_CMD_UP;
        }else if(RX_ARRAY[0] == 'S' && RX_ARRAY[1] == ' '){
            msg.value = atoi(data); //will just return 0 if bad as far as I'm aware and that wont cause problems
            msg.cmd = SHIP_CMD_SPEED;
        }//else invalid go away

        //if the amount to move is 0 or bad input don't send to Queue
        if(msg.value != 0){
            xQueueSend(Queue_Space_Ship, &msg, portMAX_DELAY);
        }

        //Reset for next command
        memset(RX_ARRAY,0,RX_ARRAY_SIZE);  //reset the char array
        RX_INDEX = 0;                      //back to the beginning

    }
}


