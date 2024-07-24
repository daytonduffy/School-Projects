from collections import defaultdict

# global for number of nodes
n = 0
tried = list()


# undirected graph class
class Graph:

    # Constructor
    def __init__(G):

        # use dic to store graph
        G.graph = defaultdict(list)

    # add an edge to graph, both for undirected
    def addEdge(G, u, v):
        G.graph[v].append(u)
        G.graph[u].append(v)

    # when depth = 0 add that node to the list of nodes, if not already there I guess, and return
    # maybe check that value for being N every time could help a lil
    # Otherwise once returned totally we want to inc the count of weeks
    # and call DFS again wth the node in the list closest to N
    # if no paths exist from that node backtrack to the next closest from the list
    # repeat

    # helper function
    def helperDFS(G, v, visited, path, paths, depth):

        if depth == 0:
            if v not in paths:
                paths.append(v)
            return

        # for all the vertices adjacent to this vertex
        for neighbour in G.graph[v]:
            if neighbour not in visited:
                path.append(v)
                visited.add(v)  # mark the current node as visited
                G.helperDFS(neighbour, visited, path, paths, depth - 1)
                visited.remove(path.pop())

    # function to do DFS traversal
    def DFS(G, v):

        # to store visited vertices
        visited = set()
        # set of paths that work
        paths = list()
        # stores current path for backtracking help
        path = list()

        # call recursive helper function
        G.helperDFS(v, visited, path, paths, 5)
        # paths now holds good paths from given v

        # currently paths gets found with a set of all available 6 city paths
        return paths


# run's algorithm, calls DFS and sorts data
def greedyBand(G):
    # list to hold all the active sets
    tourPlan = list()

    # there should be a loop here but what to loop over
    i = 0
    city = 1
    week = 0
    # when to increment week
    while i == 0:
        # store each week in the tour plan
        tourPlan.append(G.DFS(city))
        tried.append(city)
        for j in tried:  # don't try old things
            if j in tourPlan[week]:
                tourPlan[week].remove(j)
        # I think this first if has the most complicated edge cases
        # if the current week has no possibilities theres lots that can happen
        # 1. this was the first week and there are no paths
        # 2. tourPlan holds more values for the previous week to look through
        # 3. tourPlan has any number of now empty sets waiting in the weeks above
        # 4. plus we need to break out if we ever run out of weeks
        # What we need is to get from the current week to the first available week with a length > 0
        if len(tourPlan[week]) == 0:  # No 6 city path's available from this city
            # decrement weeks until we're back to a week with possibilities left
            while len(tourPlan[week]) == 0:
                tourPlan.remove(tourPlan[week])
                week = week - 1
                if week == -1:
                    i = 2
                    break
            # have to keep the none cases out!
            if i == 0:
                # now it's the same as the last else condition
                # give city the Saturday/New Monday node
                city = maxPath(tourPlan[week])
                tourPlan[week].remove(city)
                # increment week
                week = week + 1

        elif n in tourPlan[week]:  # we are done then the path has been found
            i = 1
        else:  # call the DFS again on the v in paths closest to N  (lots of edge cases here)
            # give city the Saturday/New Monday node
            city = maxPath(tourPlan[week])
            tourPlan[week].remove(city)
            # increment week
            week = week + 1

    if i == 1:  # We have found the correct number of weeks
        return week + 1
    else:  # there is no possible way
        return -1


# this should be a sort for large data sets
def maxPath(path):
    max = 0
    for i in path:
        if i > max:
            max = i

    return max


# create graph
g = Graph()

# get N and M as input split them
nm = input().split()
n = int(nm[0])

# for each edge we're gunna have
for i in range(int(nm[1])):
    edge = input().split()
    g.addEdge(int(edge[0]), int(edge[1]))

print(greedyBand(g))
