import math


# class to hold directed graph and do FF on it
class Graph:

    def __init__(self, graph):
        self.graph = graph
        self.ROW = len(graph)

    # Using BFS as a searching algorithm
    def search_BFS(self, s, t, parent):

        visited = [False] * self.ROW  # initialize visited list
        queue = list()  # initialize queue

        queue.append(s)  # source node is first node in queue
        visited[s] = True  # obviously visit first node first

        while queue:  # iterate until queue is empty

            u = queue.pop(0)  # pop off the front value, you know like a queue

            for i, val in enumerate(self.graph[u]):
                if visited[i] is False and val > 0:
                    queue.append(i)  # add new node to Q
                    visited[i] = True  # mark as visited
                    parent[i] = u  # mark this node's parent

        return True if visited[t] else False  # true if you made it to t

    # Ford-Fulkerson algorithm
    def ford_fulkerson(self, source, sink):
        parent = [-1] * self.ROW
        max_flow = 0

        while self.search_BFS(source, sink, parent):  # while search returns true

            path_flow = float("Inf")  # start flow at infinity
            s = sink  # set temp s for iteration
            while s != source:
                path_flow = min(path_flow, self.graph[parent[s]][s])
                s = parent[s]

            # Adding the path flows
            max_flow = max_flow + path_flow

            # Updating the residual values of edges
            v = sink
            while v != source:
                u = parent[v]
                self.graph[u][v] = self.graph[u][v] - path_flow
                self.graph[v][u] = self.graph[v][u] + path_flow
                v = parent[v]

        return max_flow


# (+1's) to ignore s at the beginning (+2's) to deal with s and t
def load_data():
    # take in the input baby
    n, m = input().strip().split(' ')
    n = int(n)  # how many mining locations there are
    m = int(m)  # nodes with dependencies

    profit = 0

    # create data structure fill with all edges at 0 cap ie no edges
    graph = list()
    for i in range(n + 2):  # s + t + n
        node = [0] * (n + 2)  # need edge space for each node to each node
        graph.append(node)  # add to whole structure
    # t is preset for us as it has no outgoing edges

    # set up all connections from s and to t
    # next line will be list of pi's, one for each n
    miningspots = input().strip().split(' ')
    for i in range(len(miningspots)):
        p = int(miningspots[i])  # change the string input into a number
        if p >= 0:  # positive value connect to s
            graph[0][1 + i] = p  # s -> ti with value p
            profit = profit + p  # take running total of profitable locations
        else:  # negative value connect to t
            graph[1 + i][n + 1] = abs(p)  # ti -> t with value |p|

    # set up all the inter-location dependencies
    for i in range(m):
        # first thing in the line is the
        # draw line from first node to each other node in the list with INF cap
        dependencies = input().strip().split(' ')
        first = int(dependencies[0])
        for j in range(len(dependencies) - 1):
            depends_on = int(dependencies[j + 1])  # (+1) to ignore source node
            graph[first][depends_on] = math.inf  # set up the dependency with infinite capacity

    # that should b s, t, and all mining nodes set up
    source = 0
    sink = len(graph) - 1

    return graph, source, sink, profit


# graph, source, sink, profit = load_data()

n = 5
numnodes = n * n + 3

# create data structure fill with all edges at 0 cap ie no edges
graph = list()
for i in range(numnodes):  # s + t + outside + n
    node = [0] * numnodes  # need edge space for each node to each node
    graph.append(node)  # add to whole structure
# t is preset for us as it has no outgoing edges

# set up edges s -> i
# for i in range(len(graph[0]) - 2):
# graph[0][i + 1] = math.inf
graph[0][5] = math.inf
graph[0][7] = math.inf
graph[0][8] = math.inf
graph[0][17] = math.inf
graph[0][18] = math.inf

c = 2
# set up edges i -> t
for i in range(len(graph[0]) - 3):
    graph[i + 1][len(graph) - 1] = c

# set up edges i -> j
graph[1][2] = 1
graph[1][6] = 1
graph[1][26] = 2

graph[2][1] = 1
graph[2][3] = 1
graph[2][7] = 1
graph[2][26] = 1

graph[3][2] = 1
graph[3][4] = 1
graph[3][8] = 1
graph[3][26] = 1

graph[4][3] = 1
graph[4][5] = 1
graph[4][9] = 1
graph[4][26] = 1

graph[5][4] = 1
graph[5][10] = 1
graph[5][26] = 2

graph[6][1] = 1
graph[6][7] = 1
graph[6][11] = 1
graph[6][26] = 1

graph[7][2] = 1
graph[7][6] = 1
graph[7][8] = 1
graph[7][12] = 1

graph[8][3] = 1
graph[8][7] = 1
graph[8][9] = 1
graph[8][13] = 1

graph[9][4] = 1
graph[9][8] = 1
graph[9][10] = 1
graph[9][14] = 1

graph[10][5] = 1
graph[10][9] = 1
graph[10][15] = 1
graph[10][26] = 1

graph[11][6] = 1
graph[11][12] = 1
graph[11][16] = 1
graph[11][26] = 1

graph[12][7] = 1
graph[12][11] = 1
graph[12][13] = 1
graph[12][17] = 1

graph[13][8] = 1
graph[13][12] = 1
graph[13][14] = 1
graph[13][18] = 1

graph[14][9] = 1
graph[14][13] = 1
graph[14][15] = 1
graph[14][19] = 1

graph[15][10] = 1
graph[15][14] = 1
graph[15][20] = 1
graph[15][26] = 1

graph[16][11] = 1
graph[16][17] = 1
graph[16][21] = 1
graph[16][26] = 1

graph[17][12] = 1
graph[17][16] = 1
graph[17][18] = 1
graph[17][22] = 1

graph[18][13] = 1
graph[18][17] = 1
graph[18][19] = 1
graph[18][23] = 1

graph[19][14] = 1
graph[19][18] = 1
graph[19][20] = 1
graph[19][24] = 1

graph[20][15] = 1
graph[20][19] = 1
graph[20][25] = 1
graph[20][26] = 1

graph[21][16] = 1
graph[21][22] = 1
graph[21][26] = 2

graph[22][17] = 1
graph[22][21] = 1
graph[22][23] = 1
graph[22][26] = 1

graph[23][18] = 1
graph[23][22] = 1
graph[23][24] = 1
graph[23][26] = 1

graph[24][19] = 1
graph[24][23] = 1
graph[24][25] = 1
graph[24][26] = 1

graph[25][20] = 1
graph[25][24] = 1
graph[25][26] = 2

# REMOVING s -> i -> t DIRECT EDGES AT T
graph[5][27] = 0
graph[7][27] = 0
graph[8][27] = 0
graph[17][27] = 0
graph[18][27] = 0

graph[26][27] = math.inf

g = Graph(graph)
source = 0
sink = len(graph) - 1

for i in range(len(graph)):
    print(i, end=' ')
    print(graph[i])
print()

mincut_maxflow = g.ford_fulkerson(source, sink)

for i in range(len(graph)):
    print(i, end=' ')
    print(graph[i])

print()

print(mincut_maxflow)
