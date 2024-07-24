import csv
import numpy as np
import math
import scipy.cluster
from random import randrange
import matplotlib.pyplot as plt


def load_data(filepath):
    with open(filepath, newline='') as csvfile:
        reader = csv.DictReader(csvfile)

        i = 0
        pokedex = list()

        for row in reader:
            i = i + 1
            pokemon = dict()
            pokemon['#'] = int(row['#'])
            pokemon['Name'] = row['Name']
            pokemon['Type 1'] = row['Type 1']
            pokemon['Type 2'] = row['Type 2']
            pokemon['Total'] = int(row['Total'])
            pokemon['HP'] = int(row['HP'])
            pokemon['Attack'] = int(row['Attack'])
            pokemon['Defense'] = int(row['Defense'])
            pokemon['Sp. Atk'] = int(row['Sp. Atk'])
            pokemon['Sp. Def'] = int(row['Sp. Def'])
            pokemon['Speed'] = int(row['Speed'])
            pokemon['Generation'] = int(row['Generation'])

            pokedex.append(pokemon)
            if i >= 20:
                break

    return pokedex


def calculate_x_y(pokemon):
    x = pokemon['Attack'] + pokemon['Sp. Atk'] + pokemon['Speed']
    y = pokemon['Defense'] + pokemon['Sp. Def'] + pokemon["HP"]

    return x, y


# get the distance between two points x, y
def distance(point1, point2):
    return pow(pow(point1[0] - point2[0], 2) + pow(point1[1] - point2[1], 2), .5)


def minCluster(clusters):
    # clusters[i] = ((cluster#, #originals), C1(x,y), C2(x,y), Cn(x,Y=y))
    minDistance = 10000
    pair = list()  # should only need one cuz of the use of <
    options = list(list())
    temp = list()
    temp.append(100000)
    options.append(temp)

    closestPoints = list(list())
    cpoints = list(list())
    cpo = list(list(list()))  # after first large loops holds options equivalent for points

    for i in range(len(clusters) - 1):  # compare all clusters to all other clusters
        for j in range(i + 1, len(clusters)):
            for k in range(clusters[i][0][1]):  # for each original node in cluster i
                for l in range(clusters[j][0][1]):  # for each original node in cluster j
                    nodeD = distance(clusters[i][k + 1], clusters[j][l + 1])
                    if nodeD < minDistance:  # only care about < here, just need the distance that is smallest
                        minDistance = nodeD  # reset minDistance, min between clusters i and j
                        # actual points only accesable here
                        cpoints.clear()  # new one
                        cpoints.append(clusters[i][k + 1])
                        cpoints.append(clusters[j][l + 1])
                        # mindistance just keeps track f shortest path between CA and CB no other info
            # ONCE HERE TO LATE FOR CLOSEPOINTS
            # before j changes then cuz that's a new compare i j
            if minDistance < options[0][0]:  # options[0][0] always holds the min distance
                # < means reset options and add this new point
                options.clear()
                opt = list()  # list that holds (distance, cluster i, cluster j)
                opt.append(minDistance)
                # smaller cluster number first
                if clusters[i][0][0] < clusters[j][0][0]:  # [i][0][0] always holds the cluster number
                    opt.append(i)
                    opt.append(j)
                else:
                    opt.append(j)
                    opt.append(i)
                options.append(opt)

                # now for closest points
                cpo.clear()
                cpo.append(cpoints)
            elif minDistance == options[0][0]:
                # == means just add to options list
                opt = list()  # list that holds (distance, cluster i, cluster j)
                opt.append(minDistance)
                # smaller cluster number first
                if clusters[i][0][0] < clusters[j][0][0]:  # [i][0][0] always holds the cluster number
                    opt.append(i)
                    opt.append(j)
                else:
                    opt.append(j)
                    opt.append(i)
                options.append(opt)

                # now for closeset points
                cpo.append(cpoints)

    # tiebreaker between potential options list
    if len(options) > 1:  # sort through multiple options
        more = list(list())
        morecp = list(list())
        lowC = 100000
        for i in range(len(options)):  # iterate over all possibilities
            if clusters[options[i][1]][0][0] < lowC:  # options[i][1] goes to smaller index of cluster pair
                more.clear()
                more.append(options[i])
                lowC = clusters[options[i][1]][0][0]

                # now for closest points
                morecp.clear()
                morecp.append(cpo[i])
            elif clusters[options[i][1]][0][0] == lowC:  # if equal add to list
                more.append(options[i])

                # now for closest points
                morecp.append(cpo[i])
        if len(more) > 1:  # have to look at second index :(
            min = 10000
            tie = list()
            cp = list()
            for i in range(len(more)):
                if clusters[more[i][2]][0][0] < min:  # Only one min can exist no == this time
                    tie = more[i]  # set min pair
                    min = clusters[more[i][2]][0][0]

                    # now for closest points
                    cp = morecp[i]
            pair.append(tie[0])
            pair.append(tie[1])
            pair.append(tie[2])

            # now for closest points
            closestPoints.append(cp[0])
            closestPoints.append(cp[1])
        else:  # done we found the smallest first index
            pair.append(more[0][0])  # add the min distance
            pair.append(more[0][1])  # cluster number of lower cluster
            pair.append(more[0][2])  # cluster number of higher cluster

            # now for closest points
            closestPoints.append(morecp[0][0])
            closestPoints.append(morecp[0][1])
    elif len(options) == 1:  # only one option we're done set return value
        pair.append(options[0][0])  # add the min distance
        pair.append(options[0][1])  # cluster number of lower cluster
        pair.append(options[0][2])  # cluster number of higher cluster

        # now for closest points
        closestPoints.append(cpo[0][0])
        closestPoints.append(cpo[0][1])

    # pair = (minDistance, Lower Cluster Number, Higher Cluster Number)
    # not real cluster number but index to access correct cluster
    return pair, closestPoints


def hac(dataset):
    remove = list(list())
    for i in range(len(dataset)):
        if math.isinf(dataset[i][0]) or math.isinf(dataset[i][1]) or math.isnan(dataset[i][0]) or math.isnan(dataset[i][1]):
            remove.append(dataset[i])
    for i in range(len(remove)):
        dataset.remove(remove[i])
    # set up clusters structure
    # clusters[i] = ((cluster#, #originals), C1, C2, Cn)
    clusters = list(list(list()))
    for i in range(len(dataset)):
        info = list(list())
        tags = list()
        tags.append(i)  # cluster number, from iteration
        tags.append(1)  # 1 original in 20 originals duh
        info.append(tags)  # give the cluster its tags
        info.append(dataset[i])  # (x,y) for given cluster, only one for all starters

        clusters.append(info)  # put it in the big list
    # set up Z structure
    Z = list(list())
    for i in range(len(dataset) - 1):  # iterate m - 1 times
        minPair, closestPoints = minCluster(clusters)  # (distance, Cluster Index, Cluster Index) ((x1, y1),(x2, y2))

        zi = list()
        zi.append(clusters[minPair[1]][0][0])  # give it lower cluster index
        zi.append(clusters[minPair[2]][0][0])  # give it higher cluster index
        zi.append(minPair[0])  # give it the min distance
        zi.append(clusters[minPair[1]][0][1] + clusters[minPair[2]][0][1])  # add the two number of orig values
        Z.append(zi)  # that's z set up

        # make and add new cluster to clusters
        newcluster = list(list())
        newcluster.append((i + 20, zi[3]))  # add tags
        for i in range(clusters[minPair[1]][0][1]):  # add all orig clusters to the new one
            newcluster.append(clusters[minPair[1]][i + 1])  # add to list the clusters in the old
        for i in range(clusters[minPair[2]][0][1]):  # add all orig clusters to the new one
            newcluster.append(clusters[minPair[2]][i + 1])  # add to list the clusters in the old
        clusters.append(newcluster)
        # remove two that are combined
        trash1 = clusters[minPair[1]]
        trash2 = clusters[minPair[2]]
        clusters.remove(trash1)
        clusters.remove(trash2)

    # convert Z to a np array
    return np.array(Z)


# m times add random value (x,y) into randos list
def random_x_y(m):
    randos = list(list())
    for i in range(m):
        value = list()
        value.append(randrange(360) + 1)
        value.append(randrange(360) + 1)

        randos.append(value)
    return randos


def imshow_hac(dataset):
    remove = list(list())
    for i in range(len(dataset)):
        if math.isinf(dataset[i][0]) or math.isinf(dataset[i][1]) or math.isnan(dataset[i][0]) or math.isnan(dataset[i][1]):
            remove.append(dataset[i])
    for i in range(len(remove)):
        dataset.remove(remove[i])

    for i in range(len(dataset)):
        plt.scatter(dataset[i][0], dataset[i][1])
    plt.plot()
    plt.pause(0.1)

    # set up clusters structure
    # clusters[i] = ((cluster#, #originals), C1, C2, Cn)
    clusters = list(list(list()))
    for i in range(len(dataset)):
        info = list(list())
        tags = list()
        tags.append(i)  # cluster number, from iteration
        tags.append(1)  # 1 original in 20 originals duh
        info.append(tags)  # give the cluster its tags
        info.append(dataset[i])  # (x,y) for given cluster, only one for all starters

        clusters.append(info)  # put it in the big list
    # set up Z structure
    Z = list(list())
    for i in range(len(dataset) - 1):  # iterate m - 1 times
        minPair, closestPoints = minCluster(clusters)  # (distance, Cluster Index, Cluster Index) ((x1, y1),(x2, y2))

        zi = list()
        zi.append(clusters[minPair[1]][0][0])  # give it lower cluster index
        zi.append(clusters[minPair[2]][0][0])  # give it higher cluster index
        zi.append(minPair[0])  # give it the min distance
        zi.append(clusters[minPair[1]][0][1] + clusters[minPair[2]][0][1])  # add the two number of orig values
        Z.append(zi)  # that's z set up

        # make and add new cluster to clusters
        newcluster = list(list())
        newcluster.append((i + 20, zi[3]))  # add tags
        for i in range(clusters[minPair[1]][0][1]):  # add all orig clusters to the new one
            newcluster.append(clusters[minPair[1]][i + 1])  # add to list the clusters in the old
        for i in range(clusters[minPair[2]][0][1]):  # add all orig clusters to the new one
            newcluster.append(clusters[minPair[2]][i + 1])  # add to list the clusters in the old
        clusters.append(newcluster)
        # remove two that are combined
        trash1 = clusters[minPair[1]]
        trash2 = clusters[minPair[2]]
        clusters.remove(trash1)
        clusters.remove(trash2)

        xvalues = [closestPoints[0][0], closestPoints[1][0]]
        yvalues = [closestPoints[0][1], closestPoints[1][1]]
        plt.plot(xvalues, yvalues)
        plt.pause(0.1)

    plt.show()
    # convert Z to a np array
    return np.array(Z)


if __name__ == "__main__":

    dex = load_data('Pokemon.csv')
    dataset = list(list())
    for i in range(len(dex)):
        dataset.append(calculate_x_y(dex[i]))
    imshow_hac(dataset)


