from collections import defaultdict


# undirected graph using adjacency list
class Graph:

    # constructor
    def __init__(self):

        # default dictionary to store graph
        self.graph = defaultdict(list)

    # function to add an edge to graph
    # undirected puts it in both
    def addEdge(self, u, v):
        self.graph[u].append(v)
        self.graph[v].append(u)

    # BFS to run the k coloring problem
    def BFS(self, n, k):

        # create visited array
        visited = [False] * n

        # start with all the same color
        colors = [1] * n

        # create a queue for BFS
        queue = []

        for i in range(n):
            if visited[i]:
                continue

            # mark first node as visited
            queue.append(i)
            visited[i] = True

            while queue:
                # pop off top value
                s = queue.pop(0)

                # get all adjacent vertices
                for j in self.graph[s]:

                    if colors[s] == colors[j]:  # if adjacent color is the same increase by one
                        colors[j] = colors[j] + 1
                        if colors[j] > k:  # if the number of colors used has gone to high
                            return False

                    if not visited[j]:
                        queue.append(j)
                        visited[j] = True

        return True


def load_data():
    nmk = input().strip().split(' ')
    n = int(nmk[0])  # number of exchanges
    m = int(nmk[1])  # number of edges, possible trades
    k = int(nmk[2])

    g = Graph()

    for i in range(m):
        edge = input().strip().split(' ')

        g.addEdge(int(edge[0]) - 1, int(edge[1]) - 1)

    return g, n, k


g, n, k = load_data()

print(g.BFS(n, k))
