/*
 * main.h
 *
 *  Created on: Dec 8, 2020
 *      Author: Dayton Duffy, Reece Lardy
 */

#ifndef MAIN_H_
#define MAIN_H_

#include "msp.h"
#include "msp432p401r.h"
#include <stdint.h>
#include <stdio.h>
#include <string.h>
#include <stdbool.h>

/* RTOS header files */
#include <FreeRTOS.h>
#include <task.h>
#include <semphr.h>

/* ECE353 Includes */
#include <task_joystick.h>
#include <task_mkII_s1.h>
#include <task_battleship.h>
#include <task_light.h>

#include <lcd.h>
#include <image.h>
#include <music.h>
#include <i2c.h>
#include <ece353.h>

#endif /* MAIN_H_ */
