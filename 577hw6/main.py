# This is a sample Python script.


# undirected graph class
import math
from collections import defaultdict


class Graph:

    # Constructor
    def __init__(G, n):
        G.n = n
        G.adj = [[] for i in range(n)]
        G.wt = [[0 for i in range(n)] for i in range(n)]

    # add an edge to graph
    def addEdge(G, v, u, w):
        G.adj[v].append(u)
        G.wt[v][u] = w

    # helper function
    def helperDFS(G, v, visited, target, w, l, maxrate, flag):
        if v == target:  # first iteration and any hits on a cycle
            if flag:
                # HIT! store data in maxrate if it's good
                if w > maxrate[0]:  # if new is better store it!
                    maxrate = [w, l]
                return maxrate  # dont continue the loop
        else:  # any random node
            # put node into visited list
            visited.add(v)

        # Recur for all the vertices
        # adjacent to this vertex
        for neighbour in G.adj[v]:
            if neighbour not in visited:
                # go to next node, keep target, weight for that iteration is current * the path being taken
                # length of the path has increased by one here, max rate changes elsewhere
                maxrate = G.helperDFS(neighbour, visited, target, w * G.wt[v][neighbour], l + 1, maxrate, True)
        return maxrate

    # function to do DFS traversal
    def DFS(G, v):

        # to store visited vertices
        visited = set()

        # holds w max and l of that path
        bestrate = [0, 0]
        l = 0  # need l to start at 0 no path taken
        w = 1  # w starts at 1, needed for multiplication, and if it doesn't get > 1 its not worth doing
        # call recursive helper function
        bestrate = G.helperDFS(v, visited, v, w, l, bestrate, False)

        return bestrate


def loadData():
    nm = input().split(' ')
    n = int(nm[0])  # number of exchanges
    m = int(nm[1])  # number of edges, possible trades

    g = Graph(n)
    basic = list()

    for i in range(m):
        edge = input().split(' ')
        # get index relations for each stock exchange
        if edge[0] not in basic:
            basic.append(edge[0])
        if edge[1] not in basic:
            basic.append(edge[1])
        w = float(edge[2]) / 100  # get weights in easy to use form from the jump
        g.addEdge(basic.index(edge[0]), basic.index(edge[1]), w)

    return g, basic


def stocks():
    g, basic = loadData()

    stockrate = [1, 0]

    for node in range(len(basic)):
        rate = g.DFS(node)

        if rate[0] > stockrate[0]:
            stockrate = rate

    # get percent value
    if stockrate[0] == 1:
        print(0)
    else:

        percent = math.ceil(((1/stockrate[0])**(1/stockrate[1]) - 1) * -100)

        print(percent)


if __name__ == "__main__":
    stocks()
