import numpy as np
import torch
import torch.nn as nn
import torch.nn.functional as F
import torch.optim as optim

from student_code import LeNet, count_model_params

# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    data = torch.zeros(1, 3, 32, 32)
    model = LeNet()
    out, shape_dict = model(data)
    print(shape_dict)
    print(count_model_params())
