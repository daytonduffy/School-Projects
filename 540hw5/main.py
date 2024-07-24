import csv

import numpy as np
from matplotlib import pyplot as plt

# Feel free to import other packages, if needed.
# As long as they are supported by CSL machines.
from numpy import math


def get_dataset(filename):
    """
    TODO: implement this function.

    INPUT:
        filename - a string representing the path to the csv file.

    RETURNS:
        An n by m+1 array, where n is # data points and m is # features.
        The labels y should be in the first column.
    """
    with open(filename, newline='') as csvfile:
        reader = csv.DictReader(csvfile)

        dataset = list(list())

        for col in reader:
            patient = list()
            patient.append(float(col['BODYFAT']))
            patient.append(float(col['DENSITY']))
            patient.append(float(col['AGE']))
            patient.append(float(col['WEIGHT']))
            patient.append(float(col['HEIGHT']))
            patient.append(float(col['ADIPOSITY']))
            patient.append(float(col['NECK']))
            patient.append(float(col['CHEST']))
            patient.append(float(col['ABDOMEN']))
            patient.append(float(col['HIP']))
            patient.append(float(col['THIGH']))
            patient.append(float(col['KNEE']))
            patient.append(float(col['ANKLE']))
            patient.append(float(col['BICEPS']))
            patient.append(float(col['FOREARM']))
            patient.append(float(col['WRIST']))

            dataset.append(patient)

    return dataset


def print_stats(dataset, col):
    """
    TODO: implement this function.

    INPUT:
        dataset - the body fat n by m+1 array
        col     - the index of feature to summarize on.
                  For example, 1 refers to density.

    RETURNS:
        None
    """
    n = len(dataset)  # number of data points
    x = 0  # mean
    for i in range(n):
        x = x + dataset[i][col]
    x = x / n

    sigma = 0
    for i in range(n):
        sigma = sigma + math.pow(dataset[i][col] - x, 2)
    sigma = math.pow(sigma * (1 / (n - 1)), 0.5)

    print(n)
    print("%.2f" % x)
    print("%.2f" % sigma)


def regression(dataset, cols, betas):
    """
    TODO: implement this function.

    INPUT:
        dataset - the body fat n by m+1 array
        cols    - a list of feature indices to learn.
                  For example, [1,8] refers to density and abdomen.
        betas   - a list of elements chosen from [beta0, beta1, ..., betam]

    RETURNS:
        mse of the regression model
    """
    n = len(dataset)  # number of data points
    mse = 0
    for i in range(n):  # iterating over all data points
        patient = betas[0]  # add beta 0 to this iteration
        for j in range(len(cols)):  # for each col
            patient = patient + (betas[j + 1] * dataset[i][cols[j]])  # proper beta * corresponding col value

        patient = patient - dataset[i][0]
        patient = math.pow(patient, 2)
        # patient = math.pow(patient - dataset[i][0], 2)  # after f(x) subtract y and square value
        mse = mse + patient

    mse = mse * (1 / n)
    return mse


def gradient_descent(dataset, cols, betas):
    """
    TODO: implement this function.

    INPUT:
        dataset - the body fat n by m+1 array
        cols    - a list of feature indices to learn.
                  For example, [1,8] refers to density and abdomen.
        betas   - a list of elements chosen from [beta0, beta1, ..., betam]

    RETURNS:
        An 1D array of gradients
    """
    n = len(dataset)  # number of data points
    grads = list()

    for i in range(len(betas)):  # iterate for each beta
        indi = 0
        total = 0
        for j in range(n):  # iterating over all data points
            indi = betas[0]  # add beta 0 to this iteration
            for k in range(len(cols)):  # for each col
                indi = indi + (betas[k + 1] * dataset[j][cols[k]])  # proper beta * corresponding col value

            indi = indi - dataset[j][0]  # after f(x) subtract y

            if i != 0:
                total = total + indi * dataset[j][cols[i - 1]]  # multiply by that x value
            else:
                total = total + indi

        total = total * (2 / n)
        grads.append(total)

    return grads


def iterate_gradient(dataset, cols, betas, T, eta):
    """
    TODO: implement this function.

    INPUT:
        dataset - the body fat n by m+1 array
        cols    - a list of feature indices to learn.
                  For example, [1,8] refers to density and abdomen.
        betas   - a list of elements chosen from [beta0, beta1, ..., betam]
        T       - # iterations to run
        eta     - learning rate

    RETURNS:
        None
    """

    MSEgrad = list(list())
    MSEgrad.append(gradient_descent(dataset, cols, betas))  # take betas and get the 0 set
    BigBeta = list(list())
    BigBeta.append(betas)

    for t in range(1, T + 1):  # iterate for as long as t is
        newBeta = list()
        for b in range(len(betas)):  # iterate over all betas
            newBeta.append(BigBeta[t - 1][b] - (eta * MSEgrad[t - 1][b]))  # appends new value of beta
        BigBeta.append(newBeta)  # put new betas in the list for next iteration
        MSEgrad.append(gradient_descent(dataset, cols, newBeta))  # get new mse grad values using new betas

        print(t, end=' ')
        print("%.2f" % regression(dataset, cols, newBeta), end=' ')
        for beta in newBeta:
            print("%.2f" % beta, end=' ')
        print()


def compute_betas(dataset, cols):
    """
    TODO: implement this function.

    INPUT:
        dataset - the body fat n by m+1 array
        cols    - a list of feature indices to learn.
                  For example, [1,8] refers to density and abdomen.

    RETURNS:
        A tuple containing corresponding mse and several learned betas
    """

    y = [[0 for i in range(1)] for j in range(len(dataset))]  # create 1 x n array
    X = [[1 for i in range(len(cols) + 1)] for j in range(len(dataset))]  # create array for X
    xT = []  # create array for XT

    for i in range(len(dataset)):
        y[i] = dataset[i][0]
        for j in range(len(cols) + 1):
            if j == 0:  # first index replace y with a 1
                X[i][j] = 1
            else:
                X[i][j] = dataset[i][cols[j - 1]]

    for i in range(len(X[0])):
        temp = []
        for item in X:
            temp.append(item[i])
        xT.append(temp)

    betasTemp = np.matmul(np.linalg.inv(np.matmul(xT, X)), np.matmul(xT, y))
    betas = list()
    betas.append(betasTemp[0])
    for col in cols:
        betas.append(betasTemp[col])

    mse = regression(dataset, cols, betas)
    return mse, *betas


def predict(dataset, cols, features):
    """
    TODO: implement this function.

    INPUT:
        dataset - the body fat n by m+1 array
        cols    - a list of feature indices to learn.
                  For example, [1,8] refers to density and abdomen.
        features- a list of observed values

    RETURNS:
        The predicted body fat percentage value
    """
    set = compute_betas(dataset, cols)
    betaVector = []
    for i in range(2, len(set)):
        betaVector.append(set[i])

    result = np.dot(betaVector, features) + set[1]

    return result


def synthetic_datasets(betas, alphas, X, sigma):
    """
    TODO: implement this function.

    Input:
        betas  - parameters of the linear model
        alphas - parameters of the quadratic model
        X      - the input array (shape is guaranteed to be (n,1))
        sigma  - standard deviation of noise

    RETURNS:
        Two datasets of shape (n,2) - linear one first, followed by quadratic.
    """

    # linear
    linear = [[0 for i in range(2)] for j in range(len(X))]
    quad = [[0 for i in range(2)] for j in range(len(X))]
    for i in range(len(X)):
        linear[i][1] = X[i]
        linear[i][0] = betas[0] + betas[1] * X[i] + np.random.normal(0, sigma)

        quad[i][1] = X[i]
        quad[i][0] = alphas[0] + alphas[1] * math.pow(X[i], 2) + np.random.normal(0, sigma)

    return linear, quad


def plot_mse():
    from sys import argv
    if len(argv) == 2 and argv[1] == 'csl':
        import matplotlib
        matplotlib.use('Agg')

    # TODO: Generate datasets and plot an MSE-sigma graph
    X = []
    alphas = []
    betas = []

    for i in range(1000):
        X.append(np.random.uniform(-100, 100))

    for i in range(2):
        alphas.append(np.random.uniform(-100, 100))
        betas.append(np.random.uniform(-100, 100))

    # put mse's in storage for plotting
    quadx = list()
    quady = list()
    linearx = list()
    lineary = list()
    sigmas = [0.0001, 0.001, 0.01, 0.1, 1, 10, 100, 1000, 10000, 10000]
    for sigma in sigmas:
        # generate two random sets
        linear, quad = synthetic_datasets(betas, alphas, X, sigma)

        # send both to compute_betas
        # pretty sure [1] cuz just the X col exists
        linearCB = compute_betas(linear, cols=[1])
        quadCB = compute_betas(quad, cols=[1])

        linearx.append(sigma)
        lineary.append(linearCB[0])
        quadx.append(sigma)
        quady.append(quadCB[0])

    # Plot
    f = plt.figure()
    plt.plot(linearx, lineary, '-ob', label='linear')
    plt.plot(quadx, quady, '-or', label='quadratic')
    plt.ylabel('mse')
    plt.yscale('log')
    plt.xlabel('sigma')
    plt.xscale('log')
    plt.legend(loc='upper left')
    # plt.show()
    f.savefig('mse.pdf')


if __name__ == '__main__':
    ### DO NOT CHANGE THIS SECTION ###
    plot_mse()

