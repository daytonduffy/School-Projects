# We need to do a sort on P list
# At the same time Q needs to be sorted to match P, keep pairs
# Q not sorted just matches P's sort
def mergeSortish(P, Q):
    if len(P) > 1:

        # The midpoint of the array should be the same for both
        mid = len(P) // 2

        # Split the elements for divide and Conquer
        pL = P[:mid]
        pR = P[mid:]
        qL = Q[:mid]
        qR = Q[mid:]

        # Recursively sort each half
        mergeSortish(pL, qL)
        mergeSortish(pR, qR)

        i = 0
        j = 0
        k = 0

        # Use p's for all comparisons
        # Copy assignments for p's and q's
        while i < len(pL) and j < len(pR):
            if pL[i] < pR[j]:
                P[k] = pL[i]
                Q[k] = qL[i]
                i = i + 1
            else:
                P[k] = pR[j]
                Q[k] = qR[j]
                j = j + 1
            k = k + 1

        # Check left over elements
        # Use p's for all comparisons
        # Copy assignments for p's and q's
        while i < len(pL):
            P[k] = pL[i]
            Q[k] = qL[i]
            i = i + 1
            k = k + 1

        while j < len(pR):
            P[k] = pR[j]
            Q[k] = qR[j]
            j = j + 1
            k = k + 1

# do a 'merge' of Q that counts inversions more importantly
# array will be in correct order when sent to function
def mergeCount(Q):
    total = 0
    if len(Q) > 1:

        # The midpoint of the array
        mid = len(Q) // 2

        # Split the elements for divide and Conquer
        L = Q[:mid]
        R = Q[mid:]

        # Recursively sort each half
        # keep track of the total as you go
        total = total + mergeCount(L)
        total = total + mergeCount(R)

        i = 0
        j = 0
        k = 0
        count = 0

        # run normal merge sort algorithm on Q
        # take count of inversions as you go, that's what we really want
        while i < len(L) and j < len(R):
            if L[i] < R[j]:
                Q[k] = L[i]
                i = i + 1
                total = total + count
            else:
                Q[k] = R[j]
                j = j + 1
                count = count + 1
            k = k + 1

        # run normal merge sort algorithm on Q
        # take count of inversions as you go, that's what we really want
        while j < len(R):
            Q[k] = R[j]
            j = j + 1
            k = k + 1
            count = count + 1
        while i < len(L):
            Q[k] = L[i]
            i = i + 1
            k = k + 1
            total = total + count

    return total


# take user input then run the two helper functions
# to find the number of inversions
if __name__ == '__main__':
    n = int(input())
    # set up arrays
    P = [0] * n
    Q = [0] * n
    for i in range(n):
        P[i] = int(input())
    for i in range(n):
        Q[i] = int(input())

    # I think i did P and Q backwards but the answer still works
    mergeSortish(P, Q)
    print(mergeCount(Q))
