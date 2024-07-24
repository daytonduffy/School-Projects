from scipy.linalg import eigh
import numpy as np
import matplotlib.pyplot as plt


def load_and_center_dataset(filename):
    # TODO: add your code here
    x = np.load(filename)
    mean = np.mean(x, axis=0)
    x = x - mean

    return x


def get_covariance(dataset):
    # TODO: add your code here
    n = len(dataset)
    n = n - 1
    dataset = np.dot(np.transpose(dataset), dataset)
    dataset = dataset * (1/n)

    return dataset


def get_eig(S, m):
    # TODO: add your code here
    w, v = eigh(S, subset_by_index=[len(S) - m, len(S) - 1])  # gets correct number of values w/ vectors
    w = np.flip(w)  # get those bad boys in descending order
    for i in range(len(v)):
        v[i] = np.flip(v[i])

    wDiag = np.zeros((m, m))  # gives the mxm matrix filled with zeros, just gotta add values
    for i in range(m):  # once for each eigenvalue
        wDiag[i][i] = w[i]  # place eigenvalue on diagonals

    return wDiag, v


def get_eig_perc(S, perc):
    # TODO: add your code here
    # sum all egienvalues
    w = eigh(S, eigvals_only=True)
    eigensum = np.sum(w)
    w = np.flip(w)  # get into descending order
    m = 0

    # find out how many values  meet perc
    # logically with a descending array there will be a clean cut between passing and failing values so use a count
    for i in w:  # for each eigenvalue
        if i/eigensum > perc:  # passing value
            m = m + 1  # inc count
        else:
            break  # again once you fail no value afterwords should be large enough to pass
    # m now holds number of passing egienvalues

    # get vectors for those values,
    # once m is known we can just use the other method for help
    diag, v = get_eig(S, m)
    # does not work if m = 0

    return diag, v


def project_image(img, U):
    # TODO: add your code here
    alphas = np.dot(np.transpose(U), img)  # this could be wrong I'm not 100%

    proj = np.zeros((len(img),), dtype=float)
    # proj = [a1 * u1 + a2 * u2]
    for i in range(len(img)):
        for j in range(len(alphas)):
            proj[i] = U[i][j] * alphas[j] + proj[i]

    return proj


def display_image(orig, proj):
    # TODO: add your code here
    # reshape the images
    orig = np.reshape(orig, (32, 32))
    proj = np.reshape(proj, (32, 32))
    for i in range(3):
        orig = np.rot90(orig)
        proj = np.rot90(proj)

    # Create a figure with one row of two subplots
    f, (ax1, ax2) = plt.subplots(1, 2)
    ax1.set_title('Original')
    ax2.set_title('Projection')
    cb1 = ax1.imshow(orig, aspect='equal')
    cb2 = ax2.imshow(proj, aspect='equal')
    f.colorbar(cb1, ax=ax1)
    f.colorbar(cb2, ax=ax2)
    plt.show()
