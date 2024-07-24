# We need to do a sort on P list
def mergeSort(P):
    if len(P) > 1:

        # The midpoint of the array
        mid = len(P) // 2

        # Split the elements for divide and Conquer
        L = P[:mid]
        R = P[mid:]

        # Recursively sort each half
        mergeSort(L)
        mergeSort(R)

        i = 0
        j = 0
        k = 0

        while i < len(L) and j < len(R):
            if L[i] > R[j]:
                P[k] = L[i]
                i = i + 1
            else:
                P[k] = R[j]
                j = j + 1
            k = k + 1

        # Check left over elements
        while i < len(L):
            P[k] = L[i]
            i = i + 1
            k = k + 1

        while j < len(R):
            P[k] = R[j]
            j = j + 1
            k = k + 1


# take in empty array as input
def load_data(coins):
    # get number of coins first
    n = int(input())
    total = 0
    # iterate for each coin and grab its value
    for i in range(n):
        coin = int(input())
        total = total + coin
        coins.append(coin)

    # get the coins in order now
    mergeSort(coins)

    # if total is odd it fails automatically
    return total


# try to split the coins into S1 and S2 return true/false
def splitCoins(coins):
    S1 = 0
    S2 = 0
    # to get even lists you should always add the largest number
    # to the smallest set at the time
    for i in range(len(coins)):
        if S1 < S2:
            S1 = S1 + coins[i]
        else:
            S2 = S2 + coins[i]

    return S1, S2


coins = list()  # coins is our coin purse
total = load_data(coins)  # loading data could auto fail the data
if total % 2 != 0:  # that auto fail is on an odd total
    print('F')
else:
    S1, S2 = splitCoins(coins)
    if S1 == S2:
        print('T')
    else:
        print('F')
