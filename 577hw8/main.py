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


def load_data():
    # take in l and r
    # form the graph structure to run FF on
    n, b = input().strip().split(' ')  # nuts, bolts
    n = int(n)
    b = int(b)
    row = n + b + 2  # number of nodes is bolts + nuts + s + t

    # create data structure fill with zeros
    graph = list()
    for i in range(row):  # equal number of rows and cols
        node = [0] * row  # need edge space for each node to each node
        graph.append(node)  # add to whole structure

    # set up s -> nuts edges, t is set up already as all 0s
    for i in range(n):  # s has 1 capacity to all nuts
        graph[0][i + 1] = 1

    for i in range(n):
        string_bolts = input().strip().split(' ')
        bolts = list()
        for bolt in string_bolts:
            bolts.append(int(bolt))

        for j in range(b):  # set up the nuts -> bolts edges
            graph[i + 1][j + n + 1] = bolts[j]  # (+1's) to avoid s and nuts

    for i in range(b):  # set up bolts -> t edges
        graph[i + n + 1][row - 1] = 1  # each bolt needs just the one edge  (n+1 to avoid s and nuts)

    source = 0
    sink = row - 1

    return graph, source, sink


graph, source, sink = load_data()
g = Graph(graph)

print(g.ford_fulkerson(source, sink))
