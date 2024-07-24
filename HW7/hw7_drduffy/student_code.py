# python imports
import os
from tqdm import tqdm

# torch imports
import torch
import torch.nn as nn
import torch.optim as optim

# helper functions for computer vision
import torchvision
import torchvision.transforms as transforms


class LeNet(nn.Module):
    def __init__(self, input_shape=(32, 32), num_classes=100):
        super(LeNet, self).__init__()
        # certain definitions
        # step 1   maybe input is 32x32
        self.conv1 = torch.nn.Conv2d(3, 6, 5, 1, bias=True)
        self.relu1 = torch.nn.ReLU()
        self.pool1 = torch.nn.MaxPool2d(2, 2)

        # step 2    maybe input is 6x14x14
        self.conv2 = torch.nn.Conv2d(6, 16, 5, 1, bias=True)
        self.relu2 = torch.nn.ReLU()
        self.pool2 = torch.nn.MaxPool2d(2, 2)

        # step 3
        self.flat = torch.nn.Flatten()

        # linear steps 4-6     maybe input is 16x5x5
        self.linear1 = torch.nn.Linear(400, 256, bias=True)
        self.linear2 = torch.nn.Linear(256, 128, bias=True)
        self.linear3 = torch.nn.Linear(128, num_classes, bias=True)

    def forward(self, x):
        shape_dict = {}
        # certain operations
        # convolve, then perform ReLU non-linearity
        step1_1 = self.conv1(x)
        step1_2 = self.relu1(step1_1)
        step1_3 = self.pool1(step1_2)
        shape_dict[1] = list(step1_3.size())

        step2_1 = self.conv2(step1_3)
        step2_2 = self.relu2(step2_1)
        step2_3 = self.pool2(step2_2)
        shape_dict[2] = list(step2_3.size())

        step3 = self.flat(step2_3)
        shape_dict[3] = list(step3.size())

        step4 = self.linear1(step3)
        shape_dict[4] = list(step4.size())
        step5 = self.linear2(step4)
        shape_dict[5] = list(step5.size())
        out = self.linear3(step5)
        shape_dict[6] = list(out.size())

        # max-pooling with 2x2 grid
        return out, shape_dict


def count_model_params():
    '''
    return the number of trainable parameters of LeNet.
    '''
    model = LeNet()
    model_params = 0.0
    for i, j in model.named_parameters():
        if len(list(j.shape)) == 1:  # bias terms for 5 equations
            # bias terms are easily added directly onto the lump sum of params
            model_params = model_params + list(j.shape)[0]
        elif len(list(j.shape)) == 2:  # linear terms
            # linear terms just multiply the two values and add to the stack
            model_params = model_params + (list(j.shape)[0] * list(j.shape)[1])
        elif len(list(j.shape)) == 4:  # conv layers
            # params to add ((kernel dimensions * input channels + 1) * output channels)
            model_params = model_params + ((list(j.shape)[2] * list(j.shape)[3] * list(j.shape)[1] + 1) * list(j.shape)[0])

    return model_params / 1e6


def train_model(model, train_loader, optimizer, criterion, epoch):
    """
    model (torch.nn.module): The model created to train
    train_loader (pytorch data loader): Training data loader
    optimizer (optimizer.*): A instance of some sort of optimizer, usually SGD
    criterion (nn.CrossEntropyLoss) : Loss function used to train the network
    epoch (int): Current epoch number
    """
    model.train()
    train_loss = 0.0
    for input, target in tqdm(train_loader, total=len(train_loader)):
        ###################################
        # fill in the standard training loop of forward pass,
        # backward pass, loss computation and optimizer step
        ###################################

        # 1) zero the parameter gradients
        optimizer.zero_grad()
        # 2) forward + backward + optimize
        output, _ = model(input)
        loss = criterion(output, target)
        loss.backward()
        optimizer.step()

        # Update the train_loss variable
        # .item() detaches the node from the computational graph
        # Uncomment the below line after you fill block 1 and 2
        train_loss += loss.item()

    train_loss /= len(train_loader)
    print('[Training set] Epoch: {:d}, Average loss: {:.4f}'.format(epoch+1, train_loss))

    return train_loss


def test_model(model, test_loader, epoch):
    model.eval()
    correct = 0
    with torch.no_grad():
        for input, target in test_loader:
            output, _ = model(input)
            pred = output.max(1, keepdim=True)[1]
            correct += pred.eq(target.view_as(pred)).sum().item()

    test_acc = correct / len(test_loader.dataset)
    print('[Test set] Epoch: {:d}, Accuracy: {:.2f}%\n'.format(
        epoch+1, 100. * test_acc))

    return test_acc



