import copy
import heapq


def heuristic(state):
    # ith location holds target for that i
    # (i+1)th location holds where i is looking at
    squares = [(0, 0), (0, 1), (0, 2), (1, 0), (1, 1), (1, 2), (2, 0), (2, 1), (2, 2)]

    value = 0
    for i in range(len(state)):
        if state[i] != 0:  # zero is the blank space it doesn't count
            target = squares[state[i] - 1]
            current = squares[i]

            manhattan = abs(current[0] - target[0]) + abs(current[1] - target[1])

            value = value + manhattan

    return value


def gen_succ(state):
    # ith position holds all the places the zero could swap to if 0 is at i
    possible = [(1, 3), (0, 2, 4), (1, 5), (0, 4, 6), (1, 3, 5, 7), (2, 4, 8), (3, 7), (6, 4, 8), (5, 7)]
    blank = state.index(0)  # index of the blank space

    neighbors = list()

    for index in possible[blank]:  # need to iterate over all possibilities for 0 where it is
        neighbor = copy.deepcopy(state)

        # at the position the 0 is currently at, place the other number
        neighbor[blank] = neighbor[index]
        # replace the other position with the blank space
        neighbor[index] = 0

        # add new board state to the list
        neighbors.append(neighbor)
    neighbors = sorted(neighbors)

    return neighbors


def print_succ(state):
    neighbors = gen_succ(state)

    # print out the ordered neighbors
    for i in range(len(neighbors)):
        print(neighbors[i], end=' ')
        print('h=', end='')
        print(heuristic(neighbors[i]))


def in_list(visited, neighbor):
    for i in range(len(visited)):
        if visited[i][1] == neighbor:  # if n's state has already been visited
            return True, i
    return False, -1


# A* search main recursive function
def super_mario_A_stars(state):
    start_h = heuristic(state)
    open = []
    visited = []

    # first in line is initial state with g=0, find h, parent is -1
    # (f=g+h, state, (g, h, parent index))
    n = [start_h, state, (0, start_h, -1)]
    heapq.heappush(open, n)

    while len(open) != 0:

        # pop off top value
        # n[0] = f, n[1] = state, n[2][0] = g, n[2][1] = h, n[2][2] = parent index
        n = heapq.heappop(open)

        parent_index = -1  # for successors
        visit, vindex = in_list(visited, n[1])
        if visit:
            visited[vindex] = n
        else:
            visited.append(n)
            parent_index = len(visited) - 1

        # option 1, if n is a goal node
        if n[2][1] == 0:  # everything is in place
            return visited, n

        # otherwise we gotta look at states
        neighbors = gen_succ(n[1])

        # for each successor n' of n
        for neighbor in neighbors:
            old = False
            new_g = n[2][0] + 1
            new_h = heuristic(neighbor)
            new_f = new_g + new_h
            n_prime = (new_f, neighbor, (new_g, new_h, parent_index))

            # check if in visited
            visit, vindex = in_list(visited, neighbor)
            if visit:  # no being on open doesn't mean you've been put in visited yet, visited is modified closed
                old = True
                if new_g < visited[vindex][2][0]:
                    heapq.heappush(open, n_prime)

            # check if in open
            visit, vindex = in_list(open, neighbor)
            if visit:
                old = True
                if new_g < open[vindex][2][0]:  # if g' < g
                    heapq.heappush(open, n_prime)  # just send in the new better version

            # meaning neighbor was not in either queues
            if not old:  # just add to the list
                heapq.heappush(open, n_prime)

    # if you get out of the while loop w/o returning that's a failure
    exit()


def solve(state):
    # implement A* search and print the win order

    # run A star with open set up
    closed, n = super_mario_A_stars(state)

    reverse_win = []
    # look from n to S in closed that's the reverse of our order
    # n is currently the last value in closed as well
    while n[2][2] != -1:  # iterate until at the initial state
        reverse_win.append(n)  # add move to list
        n = closed[n[2][2]]  # change n to the parent node of n

    reverse_win.append(n)  # initial state wasn't stored in loop

    endInd = len(reverse_win) - 1

    for i in range(len(reverse_win)):  # iterate over all the moves
        print(reverse_win[endInd - i][1], end=' ')
        print('h=', end='')
        print(reverse_win[endInd - i][2][1], end=' ')
        print('moves: ', end='')
        print(i)


# test = [8, 7, 6, 5, 4, 3, 2, 1, 0]
# solve(test)

# test = [8,7,6,5,4,3,2,1,0]
# print_succ(test)

# test = [2, 5, 8, 4, 3, 6, 7, 1, 0]
# print(heuristic(test))

