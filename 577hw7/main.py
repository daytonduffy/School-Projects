
def load_data():
    n = int(input())

    leaves = input()
    treeLeaves = list()

    for leaf in leaves:
        temp = list()
        temp.append(1)
        temp.append(int(leaf))
        treeLeaves.append(temp)
        # store each point as (N 1 node, number value 0 or 1)

    return n, treeLeaves


def runGame(n, leaves):

    andor = n % 2  # tells us if we start by doing ands or ors
    sapling = branch(leaves, andor)

    # flip andor to the next round
    if andor == 0:
        andor = 1
    else:
        andor = 0

    # n-1 cuz we id the first one already
    for i in range(n-1):  # iterate over each layer of the tree
        sapling = branch(sapling, andor)
        if andor == 0:
            andor = 1
        else:
            andor = 0
    return sapling[0][0]  # length 1 and 1st index holds the N


def branch(data, andor):
    newLeaves = list()

    leftExpected = 0
    rightExpected = 0
    for i in range(len(data)):  # we need to look at pairs

        if i % 2 == 0:

            leaf = list()  # reset holder list
            if andor == 1:  # and round

                if data[i][1] == 0:  # and detects 0 we good to go
                    leftExpected = data[i][0]  # just however N was there continues
                elif data[i][1] == 1:  # and detects 1 you need to include the other side
                    leftExpected = data[i][0] + data[i+1][0]  # need to check both trees

                if data[i+1][1] == 0:  # and detects 0 we good to go
                    rightExpected = data[i+1][0]  # just however N was there continues
                elif data[i+1][1] == 1:  # and detects 1 you need to include the other side
                    rightExpected = data[i][0] + data[i+1][0]  # need to check both trees

                leaf.append((.5 * leftExpected) + (.5 * rightExpected))
                leaf.append(min(data[i][1], data[i+1][1]))

            elif andor == 0:  # or round

                if data[i][1] == 1:  # or detects 1 we good to go
                    leftExpected = data[i][0]  # just however N was there continues
                elif data[i][1] == 0:  # or detects 0 you need to include the other side
                    leftExpected = data[i][0] + data[i + 1][0]  # need to check both trees

                if data[i + 1][1] == 1:  # or detects 1 we good to go
                    rightExpected = data[i + 1][0]  # just however N was there continues
                elif data[i + 1][1] == 0:  # or detects 0 you need to include the other side
                    rightExpected = data[i][0] + data[i + 1][0]  # need to check both trees

                leaf.append((.5 * leftExpected) + (.5 * rightExpected))
                leaf.append(max(data[i][1], data[i + 1][1]))

            newLeaves.append(leaf)  # add next node pair to list

    return newLeaves


n, leaf = load_data()

print('%g' % runGame(n, leaf))
