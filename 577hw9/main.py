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
            depends_on = int(dependencies[j+1])  # (+1) to ignore source node
            graph[first][depends_on] = math.inf  # set up the dependency with infinite capacity

    # that should b s, t, and all mining nodes set up
    source = 0
    sink = len(graph) - 1

    return graph, source, sink, profit


graph, source, sink, profit = load_data()
g = Graph(graph)

mincut_maxflow = g.ford_fulkerson(source, sink)

print(profit - mincut_maxflow)
