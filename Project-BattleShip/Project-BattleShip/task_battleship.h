/*
 * battleship.h
 *
 *  Created on: Dec 5, 2020
 *      Author: Owner
 */

#ifndef TASK_BATTLESHIP_H_
#define TASK_BATTLESHIP_H_

#include <main.h>

//different states of the game
typedef enum
{
    PLACING, GAME, END
} STATE_t;

//controls state machine
extern STATE_t state;

//commands that need to be processed
typedef enum
{
    CMD_LEFT,
    CMD_RIGHT,
    CMD_UP,
    CMD_DOWN,
    CMD_SELECT,
    CMD_ROTATE,
    CMD_COLOR_PALETTE,
} CMD_t;

//msgs to send to queue
typedef struct
{
    CMD_t cmd;
    uint8_t value;
} MSG_t;

//ships for the game
typedef struct
{
    uint8_t length;
    uint8_t coords[5];
} SHIP_t;

//structure holds all player information
typedef struct
{
    SHIP_t ships[5];
    // Only 17 possible hits per game
    uint8_t hits[17];
    uint8_t misses[83];
} PLAYER_t;

//two player game
extern PLAYER_t p1;
extern PLAYER_t p2;

//turn flag
extern uint8_t currPlayer;

extern TaskHandle_t Task_BattleShip_Handle;
extern QueueHandle_t Queue_BattleShip;

/******************************************************************************
 * This function will initialize Queue_BattleShip
 ******************************************************************************/
void Task_BattleShip_Init(void);

/******************************************************************************
 * This task manages the whole game's state machine
 ******************************************************************************/
void Task_BattleShip(void *pvParameters);

#endif /* TASK_BATTLESHIP_H_ */
