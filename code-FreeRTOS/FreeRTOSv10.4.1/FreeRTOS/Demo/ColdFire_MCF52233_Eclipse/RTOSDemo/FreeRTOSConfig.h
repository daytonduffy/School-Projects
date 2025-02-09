/*
 * FreeRTOS Kernel V10.4.1
 * Copyright (C) 2017 Amazon.com, Inc. or its affiliates.  All Rights Reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * http://www.FreeRTOS.org
 * http://aws.amazon.com/freertos
 *
 * 1 tab == 4 spaces!
 */

#ifndef FREERTOS_CONFIG_H
#define FREERTOS_CONFIG_H

#include "MCF52235.h"

/*-----------------------------------------------------------
 * Application specific definitions.
 *
 * These definitions should be adjusted for your particular hardware and
 * application requirements.
 *
 * THESE PARAMETERS ARE DESCRIBED WITHIN THE 'CONFIGURATION' SECTION OF THE
 * FreeRTOS API DOCUMENTATION AVAILABLE ON THE FreeRTOS.org WEB SITE.
 *
 * See http://www.freertos.org/a00110.html.
 *----------------------------------------------------------*/

#define configUSE_PREEMPTION			1
#define configUSE_IDLE_HOOK				0
#define configUSE_TICK_HOOK				0
#define configCPU_CLOCK_HZ				( ( unsigned long ) 60000000 )
#define configTICK_RATE_HZ				( ( portTickType ) 1000 )
#define configMINIMAL_STACK_SIZE		( ( unsigned short ) 100 )
#define configTOTAL_HEAP_SIZE			( ( size_t ) ( 19000 ) )
#define configMAX_TASK_NAME_LEN			( 12 )
#define configUSE_TRACE_FACILITY		1
#define configUSE_16_BIT_TICKS			0
#define configIDLE_SHOULD_YIELD			0
#define configUSE_CO_ROUTINES 			0
#define configUSE_MUTEXES				1
#define configCHECK_FOR_STACK_OVERFLOW	1
#define configUSE_RECURSIVE_MUTEXES		1
#define configQUEUE_REGISTRY_SIZE		10
#define configUSE_COUNTING_SEMAPHORES	0

#define configMAX_PRIORITIES		( 6 )
#define configMAX_CO_ROUTINE_PRIORITIES ( 2 )

/* Set the following definitions to 1 to include the API function, or zero
to exclude the API function. */

#define INCLUDE_vTaskPrioritySet			1
#define INCLUDE_uxTaskPriorityGet			1
#define INCLUDE_vTaskDelete					1
#define INCLUDE_vTaskCleanUpResources		0
#define INCLUDE_vTaskSuspend				1
#define INCLUDE_vTaskDelayUntil				1
#define INCLUDE_vTaskDelay					1
#define INCLUDE_uxTaskGetStackHighWaterMark	1

/* This demo makes use of one or more example stats formatting functions.  These
format the raw data provided by the uxTaskGetSystemState() function in to human
readable ASCII form.  See the notes in the implementation of vTaskList() within
FreeRTOS/Source/tasks.c for limitations. */
#define configUSE_STATS_FORMATTING_FUNCTIONS	1

/* Port specific definitions. */
#define configYIELD_INTERRUPT_VECTOR			16UL
#define configKERNEL_INTERRUPT_PRIORITY 		1
#define configMAX_SYSCALL_INTERRUPT_PRIORITY 	4

/* The function that initialises the tick and context switch interrupts.  This
function is part of the application side (rather than kernel) to allow users to
change the peripherals and vectors being used should they conflict in any way
with the application itself. */
void vApplicationSetupInterrupts( void );

/* Ethernet configuration. **************************/

/* Defines the MAC address to be used. */
#define configMAC_0	0x00
#define configMAC_1	0x04
#define configMAC_2	0x9F
#define configMAC_3	0x00
#define configMAC_4	0xAB
#define configMAC_5	0x2B

/* Defines the IP address to be used. */
#define configIP_ADDR0	192
#define configIP_ADDR1	168
#define configIP_ADDR2	0
#define configIP_ADDR3	11

/* Defines the gateway address to be used. */
#define configGW_ADDR0	192
#define configGW_ADDR1	168
#define configGW_ADDR2	0
#define configGW_ADDR3	1

/* Defins the net mask. */
#define configNET_MASK0	255
#define configNET_MASK1	255
#define configNET_MASK2	255
#define configNET_MASK3	0

/* FEC driver configuration. */
#define configNUM_FEC_RX_BUFFERS	3
#define configFEC_BUFFER_SIZE		1520
#define configUSE_PROMISCUOUS_MODE	0
#define configFEC_INTERRUPT_PRIORITY configMAX_SYSCALL_INTERRUPT_PRIORITY
#define configPHY_ADDRESS	0

#if ( configFEC_BUFFER_SIZE & 0x0F ) != 0
	#error configFEC_BUFFER_SIZE must be a multiple of 16.
#endif


#endif /* FREERTOS_CONFIG_H */
