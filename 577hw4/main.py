class Graph:

    # init function to declare class variables
    def __init__(G, n):
        G.n = n
        G.adj = [[] for i in range(n)]
        G.wt = [[0 for i in range(n)] for i in range(n)]

    # method to add an undirected edge
    def addEdge(G, v, u, w):
        # set adjacency list, good for going over all edges
        G.adj[v].append(u)
        G.adj[u].append(v)

        # set weight matrix, easy to find edge weights, bad for iteration
        # i guess i could just test for 0 but then that's a comp everytime
        G.wt[v][u] = w
        G.wt[u][v] = w

    def removeEdge(G, v, u):
        # set adjacency list, good for going over all edges
        G.adj[v].remove(u)
        G.adj[u].remove(v)

        # set weight matrix, easy to find edge weights, bad for iteration
        # i guess i could just test for 0 but then that's a comp everytime
        G.wt[v][u] = 0
        G.wt[u][v] = 0

    def helperDFS(G, temp, v, visited):

        # Mark the current vertex as visited
        visited[v] = True

        # Store the vertex to list
        temp.append(v)

        # Repeat for all vertices adjacent
        # to this vertex v
        for i in G.adj[v]:
            if visited[i] == False:
                # Update the list
                temp = G.helperDFS(temp, i, visited)
        return temp

    # Method to retrieve connected components
    # in an undirected graph
    def connectedComponents(G):
        k = 0
        visited = []
        cc = []

        for i in range(G.n):
            visited.append(False)
        for v in range(G.n):
            if visited[v] == False:
                temp = []
                helper= G.helperDFS(temp, v, visited)
                cc.append(helper)
                k = k + 1
        return cc, k

    def longestEdge(G):
        weight = 0
        for i in range(len(G.wt)):
            for j in range(len(G.wt)):
                if G.wt[i][j] > weight:
                    weight = G.wt[i][j]
        return weight


# need to sort the edges by weight!
# arr[i] = (u, v, w) sort by W
# augmented merge sort, takes edges and weights sorts the edges from greatest to least weight
def mergeSort(weights, edges):
    if len(weights) > 1:

        # Finding the mid of the array
        mid = len(weights) // 2

        # Dividing the array elements
        L = weights[:mid]
        fL = edges[:mid]

        # into 2 halves
        R = weights[mid:]
        fR = edges[mid:]

        # Sorting the first half
        mergeSort(L, fL)

        # Sorting the second half
        mergeSort(R, fR)

        i = j = k = 0

        # Copy data to temp arrays L[] and R[]
        while i < len(L) and j < len(R):
            if L[i] > R[j]:
                weights[k] = L[i]
                edges[k] = fL[i]
                i += 1
            else:
                weights[k] = R[j]
                edges[k] = fR[j]
                j += 1
            k += 1

        # Checking if any element was left
        while i < len(L):
            weights[k] = L[i]
            edges[k] = fL[i]
            i += 1
            k += 1

        while j < len(R):
            weights[k] = R[j]
            edges[k] = fR[j]
            j += 1
            k += 1


# add method for taking in the data n, k, m and the edges
def loadData():
    nkm = input().split(' ')
    n = int(nkm[0])
    k = int(nkm[1])
    m = int(nkm[2])

    edgeList = list(list())
    g = Graph(n)
    weights = list()

    for i in range(m):
        edge = input().split(' ')
        tempEdge = [int(edge[0]) - 1, int(edge[1]) - 1, int(edge[2])]
        edgeList.append(tempEdge)
        g.addEdge(int(edge[0]) - 1, int(edge[1]) - 1, int(edge[2]))
        weights.append(int(edge[2]))

    return g, edgeList, k, weights


# Function that runs the whole process of taking in data and printing the output
def distributeVaccines():
    g, edgeList, kGiven, weights = loadData()  # get data into structures
    mergeSort(weights, edgeList)  # sort the edge list by weight greatest to lowest

    k = 1  # k is the current number of connected components
    # iterate over each edge from largest to smallest
    # get through list and we'll be done
    # Once k == kGiven we cant let the graph gain a cc so it will keep ignoring most of the remaining edges
    # maybe we dont need to be testing for a cycle
    # dont need to remove from edge list becuase it just gets iterated past
    for edge in edgeList:
        if k == kGiven:  # once we're at the amount of connected components allowed we need to be careful
            g.removeEdge(edge[0], edge[1])
            cc, kTemp = g.connectedComponents()
            if kTemp != k:  # taking this edge away makes K go to high, add back and ignore
                g.addEdge(edge[0], edge[1], edge[2])  # need to keep edge in graph
        else:  # if k < kGiven we just need to remove the largest edge and update k
            g.removeEdge(edge[0], edge[1])

            cc, k = g.connectedComponents()

    # once we exit the for loop every edge will have been looked at
    # Assuming it worked as expected no cycles should be present and the G structure
    # should have kGiven connected components now we just need to find shortest remaining path
    # pretty sure to be totally correct it's more complex but i'm gunna take largest weight
    longest = g.longestEdge()
    if longest == 0:
        longest = -1
    print(longest)


if __name__ == "__main__":
    distributeVaccines()
