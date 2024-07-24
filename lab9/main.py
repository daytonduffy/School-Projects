import math
from math import atan2

import numpy
import numpy as np
import matplotlib.pyplot as plt
from numpy import genfromtxt
from scipy.signal import find_peaks


def detectStep():
    # z accel
    my_data = genfromtxt('WALKING_AND_TURNING.csv', delimiter=',', usecols=10)

    steps, _ = find_peaks(my_data, prominence=5.5)  # BEST!

    # this is all i need, detects number of peaks
    #print(steps)  # gives row number of peak, super fucking good
    #print()
    #print(len(steps))

    # plot only needed to show prominence points
    # plt.plot(peaks, my_data[peaks], "ob")
    # plt.plot(my_data)
    # plt.legend(['prominence'])

    plt.show()

    return steps


def detectTurn():
    # geo z
    my_data = genfromtxt('WALKING_AND_TURNING.csv', delimiter=',', usecols=11)

    # distance 50 is about .25 seconds
    peaks, _ = find_peaks(my_data, height=2, distance=50)  # BEST!
    valleys, _ = find_peaks(-my_data, height=2, distance=50)  # BEST!

    # i want to combine and sort those two lists

    # this is all i need, detects number of peaks
    #print(valleys)  # gives row number of peak, super fucking good
    #print(peaks)
    #print()
    # print(len(valleys))

    # A REAL IMPLEMENTATION WOULD NEED BOTH COMBINED AND ORGANIZED V AND P TO USE BUT IM LAZY
    return valleys


def averageDirection(startPeak, endPeak):
    # mag x mag y
    y_data = genfromtxt('WALKING_AND_TURNING.csv', delimiter=',', usecols=13)
    x_data = genfromtxt('WALKING_AND_TURNING.csv', delimiter=',', usecols=12)

    # take the average of mag_y between two detected turns
    averageY = 0
    averageX = 0
    for i in range(startPeak, endPeak):
        averageY += y_data[i]
        averageX += x_data[i]
    averageY = averageY / (endPeak - startPeak)
    averageX = averageX / (endPeak - startPeak)

    # print(averageY)
    # print(averageX)

    # plug into formula i found to get direction
    angle = atan2(averageY, averageX) * (180 / math.pi)

    if angle < 0:
        angle = angle + 360
    if angle > 360:
        angle = angle - 360

    if angle > 337.25 or angle <= 22.5:
        #print("North")
        return "North"
    elif 292.5 < angle <= 337.25:
        #print("North-West")
        return "North-West"
    elif 247.5 < angle <= 292.5:
        #print("West")
        return "West"
    elif 202.5 < angle <= 247.5:
        #print("South-West")
        return "South-West"
    elif 157.5 < angle <= 202.5:
        #print("South")
        return "South"
    elif 112.5 < angle <= 157.5:
        #print("South-East")
        return "South-East"
    elif 67.5 < angle < 112.5:
        #print("East")
        return "East"
    elif 22.5 < angle <= 67.5:
        #print("North-East")
        return "North-East"

    #print(angle)
    #print()


def solve():
    turnDirections = [averageDirection(1, 2398),
                      averageDirection(2399, 4212),
                      averageDirection(4213, 6550),
                      averageDirection(6551, 8346),
                      averageDirection(8347, 9626)]

    return turnDirections


def plotPath():
    stepTimes = detectStep()  # holds time at which step is taken
    turnTimes = detectTurn()  # holds when turn is made
    turnTimes = numpy.append(turnTimes, 9626)
    turnDirections = solve()  # holds direction going for each turn

    print(stepTimes)
    print(turnTimes)
    print(turnDirections)

    sqrt2 = 0.7071067812

    currentX = 0
    currentY = 0

    # iterate once for each turn
    for i in range(len(turnTimes)):

        # iterate once for each step
        for j in range(len(stepTimes)):
            if stepTimes[j] <= turnTimes[i]:  # step happens in this direction
                # plot the line segment
                # check which direction we need to move in
                if turnDirections[i] == "North":
                    plt.plot([currentX, currentX], [currentY, currentY + 1], "ob-")
                    currentY += 1
                elif turnDirections[i] == "North-West":
                    plt.plot([currentX, currentX - sqrt2], [currentY, currentY + sqrt2], "ob-")
                    currentX -= sqrt2
                    currentY += sqrt2
                elif turnDirections[i] == "West":
                    plt.plot([currentX, currentX - 1], [currentY, currentY], "ob-")
                    currentX -= 1
                elif turnDirections[i] == "South-West":
                    plt.plot([currentX, currentX - sqrt2], [currentY, currentY - sqrt2], "ob-")
                    currentX -= sqrt2
                    currentY -= sqrt2
                elif turnDirections[i] == "South":
                    plt.plot([currentX, currentX], [currentY, currentY - 1], "ob-")
                    currentY -= 1
                elif turnDirections[i] == "South-East":
                    plt.plot([currentX, currentX + sqrt2], [currentY, currentY - sqrt2], "ob-")
                    currentX += sqrt2
                    currentY -= sqrt2
                elif turnDirections[i] == "East":
                    plt.plot([currentX, currentX + 1], [currentY, currentY], "ob-")
                    currentX += 1
                elif turnDirections[i] == "North-East":
                    plt.plot([currentX, currentX + sqrt2], [currentY, currentY + sqrt2], "ob-")
                    currentX += sqrt2
                    currentY += sqrt2
            else:  # once were at a step that happens after the turn move on
                break

    # before the first turn

    # show completed plot
    plt.show()


def plotPath2():
    stepTimes = detectStep()  # holds time at which step is taken
    turnTimes = detectTurn()  # holds when turn is made
    turnTimes = numpy.append(turnTimes, 9626)
    turnDirections = solve()  # holds direction going for each turn

    sqrt2 = 0.7071067812

    currentX = 0
    currentY = 0

    for i in range(21):
        plt.plot([currentX, currentX + sqrt2], [currentY, currentY + sqrt2], "ob-")
        currentX += sqrt2
        currentY += sqrt2

    for i in range(15):
        plt.plot([currentX, currentX + 1], [currentY, currentY], "ob-")
        currentX += 1

    for i in range(19):
        plt.plot([currentX, currentX], [currentY, currentY - 1], "ob-")
        currentY -= 1

    for i in range(16):
        plt.plot([currentX, currentX - 1], [currentY, currentY], "ob-")
        currentX -= 1

    for i in range(10):
        plt.plot([currentX, currentX - sqrt2], [currentY, currentY + sqrt2], "ob-")
        currentX -= sqrt2
        currentY += sqrt2
    # before the first turn

    # show completed plot
    plt.show()


def countSteps():
    wt_data = genfromtxt('WALKING_AND_TURNING.csv', delimiter=',', usecols=10)
    w_data = genfromtxt('WALKING.csv', delimiter=',', usecols=12)
    wtsteps, _ = find_peaks(wt_data, prominence=5.5)
    wsteps, _ = find_peaks(w_data, prominence=5.5)

    print("WALKING.csv contains " + str(len(wsteps)) + " steps")
    print("WALKING_AND_TURNING contains " + str(len(wtsteps)) + " steps")
    print()


def turns():
    wt_data = genfromtxt('WALKING_AND_TURNING.csv', delimiter=',', usecols=11)

    # distance 50 is about .25 seconds
    wt_peaks, _ = find_peaks(wt_data, height=2, distance=50)
    wt_valleys, _ = find_peaks(-wt_data, height=2, distance=50)
    wt_Num = len(wt_valleys) + len(wt_peaks)

    t_data = genfromtxt('TURNING.csv', delimiter=',', usecols=12)

    # distance 50 is about .25 seconds
    t_peaks, _ = find_peaks(t_data, height=2, distance=50)
    t_valleys, _ = find_peaks(-t_data, height=2, distance=50)
    t_Num = len(t_valleys) + len(t_peaks)

    print("TURNING.csv contains " + str(t_Num) + " turns")
    print("WALKING_AND_TURNING contains " + str(wt_Num) + " turns")
    print()


def lab9():
    countSteps()
    turns()

    # plot the path of the WALKING_AND_TURNING.csv
    plotPath2()


#detectStep()
#detectTurn()
#solve()
#plotPath2()

lab9()
