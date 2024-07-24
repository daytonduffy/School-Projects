import numpy as np
import torch
import torch.nn as nn
import torch.nn.functional as F
import torch.optim as optim
from torchvision import datasets, transforms
from random import randrange

# Feel free to import other packages, if needed.
# As long as they are supported by CSL machines.


def get_data_loader(training = True):
    """
    TODO: implement this function.

    INPUT: 
        An optional boolean argument (default value is True for training dataset)

    RETURNS:
        Dataloader for the training set (if training = True) or the test set (if training = False)
    """
    custom_transform=transforms.Compose([
        transforms.ToTensor(),
        transforms.Normalize((0.1307,), (0.3081,))
        ])

    train_set = datasets.MNIST('./data', train=True, download=True, transform=custom_transform)
    test_set = datasets.MNIST('./data', train=False, transform=custom_transform)

    if training == True:
        train_loader = torch.utils.data.DataLoader(train_set, batch_size = 50)
        return train_loader
    else:
        test_loader = torch.utils.data.DataLoader(test_set, batch_size = 50, shuffle=False)
        return test_loader


def build_model():
    """
    TODO: implement this function.

    INPUT: 
        None

    RETURNS:
        An untrained neural network model
    """

    # nn = Net()

    model = nn.Sequential(
            nn.Flatten(),
            nn.Linear(784, 128), 
            nn.ReLU(), 
            nn.Linear(128, 64), 
            nn.ReLU(),
            nn.Linear(64, 10)
            )
    
    return model


def train_model(model, train_loader, criterion, T):
    """
    TODO: implement this function.

    INPUT: 
        model - the model produced by the previous function
        train_loader  - the train DataLoader produced by the first function
        criterion   - cross-entropy 
        T - number of epochs for training

    RETURNS:
        None
    """

    model.train()
    opt = optim.SGD(model.parameters(), lr=0.001, momentum=0.9)
    
    for epoch in range(T):
        running_loss = 0.0
        good_guess = 0
        total = 0
        for i, data in enumerate(train_loader, 0):
           
            inputs, labels = data
            opt.zero_grad()
            outputs = model(inputs)  
            loss = criterion(outputs, labels)
            loss.backward()
            opt.step()


            matches = predict(outputs, labels)
            total = total + len(outputs)
            good_guess = good_guess + matches
            
            running_loss = running_loss + loss.item()
        # print after each epoch
        # accuracy = good_guess / 60000
        # epoch is just epoch
        # loss running_loss / 60000?
        print('Train Epoch: %d  ' % epoch, end='')
        percent = (good_guess/total) * 100
        print('Accuracy: %d/60000' % good_guess, end='')
        print('(%.2f' % percent, end='')
        print('%)  ', end='')
        ls = running_loss/len(train_loader)
        print('Loss: %.4f' % ls)

def predict(outputs, labels):
    maxVals = []
    match = 0

    for i in outputs:
        i = list(i)
        m = i.index(max(i))
        maxVals.append(m)

    for i in range(len(maxVals)):
        if maxVals[i] == labels[i]:
            match = match + 1

    return match        

def evaluate_model(model, test_loader, criterion, show_loss = True):
    """
    TODO: implement this function.

    INPUT: 
        model - the the trained model produced by the previous function
        
        test_loader    - the test DataLoader
        criterion   - cropy-entropy 

    RETURNS:
        None
    """
    model.eval()
    opt = optim.SGD(model.parameters(), lr=0.001, momentum=0.9)


    running_loss = 0.0
    good_guess = 0
    total = 0

    with torch.no_grad():
        for i, data in enumerate(test_loader, 0):

            inputs, labels = data
            #opt.zerograd()
            outputs = model(inputs)
            loss = criterion(outputs, labels)
            #loss.backward()
            #opt.step()
        
        
            matches = predict(outputs, labels)
            total = total + len(outputs)
            good_guess = good_guess + matches

            running_loss = running_loss + loss.item()

    if show_loss:
        averageLoss = running_loss/len(test_loader)
        print('Average loss: %.4f' % averageLoss)

    perc = (good_guess/total) * 100
    print('Accuracy: %.2f' % perc, end='')
    print('%')
    


def predict_label(model, test_images, index):
    """
    TODO: implement this function.

    INPUT: 
        model - the trained model
        test_images   -  test image set of shape Nx1x28x28
        index   -  specific index  i of the image to be tested: 0 <= i <= N - 1


    RETURNS:
        None
    """
    class_names = ['zero', 'one', 'two', 'three', 'four', 'five', 'six', 'seven', 'eight', 'nine']

    logits = test_images[index, :]
    logits = model(logits)
    prob = F.softmax(logits, dim=1)
 

    # print top 3 percentages from prob list
    # top value
    mem = 0
    bIndex = 0
    mIndex = 0
    sIndex = 0
    
    p = prob[0, :]

    for i in range(len(p)):
        if p[i] > mem:
            mem = p[i]
            bIndex = i
    mem = 0
    for i in range(len(p)):
        if i != bIndex:
            if p[i] > mem:
                mem = p[i]
                mIndex = i
    mem = 0
    for i in range(len(p)):
        if i != bIndex:
            if i != mIndex:
                 if p[i] > mem:
                     mem = p[i]
                     sIndex = i

    # big medium and small indexes all saved, not pretty but works
    print(class_names[bIndex], end='')
    bPer = p[bIndex] * 100
    print(': %.2f' % bPer, end='')
    print('%')

    print(class_names[mIndex], end='')
    mPer = p[mIndex] * 100
    print(': %.2f' % mPer, end='')
    print('%')

    print(class_names[sIndex], end='')
    sPer = p[sIndex] * 100
    print(': %.2f' % sPer, end='')
    print('%')



if __name__ == '__main__':
    '''
    Feel free to write your own test code here to exaime the correctness of your functions. 
    Note that this part will not be graded.
    '''
    criterion = nn.CrossEntropyLoss()
    
    train_loader = get_data_loader()
    model = build_model()
    
    train_model(model, train_loader, criterion, T = 5)

    test_loader = get_data_loader(False)
    pred_set = torch.arange(39200).reshape(50,1,28,28)
    for i, data in enumerate(test_loader, 0):
            end = randrange(20)

            inputs, labels = data
            
            if i == end:
                pred_set = inputs
                break

    #evaluate_model(model, test_loader, criterion, show_loss = True)
    
    predict_label(model, pred_set, 1)
    

