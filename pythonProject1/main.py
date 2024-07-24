# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.


# take input to set up problem
n = int(input())
A = []
for value in range(n):
    A.append(int(input()))

# iterate over total array should be simple actions for each element
# that should lead to O(Xn) where X is some constant from the number of operations
# needed values
cnt = 0
neg = 0
profit = 0

for i in range(len(A)):
    if neg == 0:  # mode 0 for on regular time
        if A[i] > 0:  # if value is positive just add it to the count
            cnt = cnt + A[i]
        elif A[i] < 0:
            neg = A[i]  # don't add to self, first one, count total negatives
    else:  # count up consecutive negative values
        if A[i] < 0:  # add to count
            neg = neg + A[i]
        elif A[i] > 0:  # time to exit this mode !
            if cnt + neg < 0:  # if there's enough negatives to take away all profit
                if cnt > profit:  # set new values and continue
                    profit = cnt
                cnt = A[i]  # curr value can't ignore
            else:  # worth continuing counting
                cnt = cnt + neg + A[i]
            neg = 0

# for loop exits after last value in A, if on positives final check here for new profit max
# if in negatives the current count after exiting that section is always accurate so good final check
if cnt > profit:
    profit = cnt
print(profit)
