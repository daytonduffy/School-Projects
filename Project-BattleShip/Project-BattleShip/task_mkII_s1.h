/*
 * task_mkII_s1.h
 *
 *  Created on: Dec 8, 2020
 *      Author: Dayton Duffy, Reece Lardy
 */

#ifndef TASK_MKII_S1_H_
#define TASK_MKII_S1_H_

#include <main.h>

extern TaskHandle_t Task_mkII_s1_Handle;

void Task_mkII_s1_Bottom_Half(void *pvParameters);

#endif /* TASK_MKII_S1_H_ */
