import numpy as np
from scipy.linalg import eigh
from pca import load_and_center_dataset
from pca import get_covariance
from pca import get_eig
from pca import get_eig_perc
from pca import project_image
from pca import display_image

x = load_and_center_dataset('YaleB_32x32.npy')
S = get_covariance(x)
Lambda, U = get_eig(S, 2)
projection = project_image(x[0], U)
display_image(x[0], projection)