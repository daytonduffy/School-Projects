import os
import math
import numpy as np


# These first two functions require os operations and so are completed for you
# Completed for you


def load_training_data(vocab, directory):
    """ Create the list of dictionaries """
    top_level = os.listdir(directory)
    dataset = []
    for d in top_level:
        if d[-1] == '/':
            label = d[:-1]
            subdir = d
        else:
            label = d
            subdir = d + "/"
        files = os.listdir(directory + subdir)
        for f in files:
            bow = create_bow(vocab, directory + subdir + f)
            dataset.append({'label': label, 'bow': bow})
    return dataset


# Completed for you
def create_vocabulary(directory, cutoff):
    """ Create a vocabulary from the training directory
        return a sorted vocabulary list
    """

    top_level = os.listdir(directory)
    vocab = {}
    for d in top_level:
        subdir = d if d[-1] == '/' else d + '/'
        files = os.listdir(directory + subdir)
        for f in files:
            with open(directory + subdir + f, 'r', encoding='utf-8') as doc:
                for word in doc:
                    word = word.strip()
                    if not word in vocab and len(word) > 0:
                        vocab[word] = 1
                    elif len(word) > 0:
                        vocab[word] += 1
    return sorted([word for word in vocab if vocab[word] >= cutoff])


# The rest of the functions need modifications ------------------------------
# Needs modifications
def create_bow(vocab, filepath):
    """ Create a single dictionary for the data
        Note: label may be None
    """
    bow = {}
    # TODO: add your code here
    f = open(filepath, 'r', encoding='utf-8')  # open given filename
    for word in f:
        word = word.strip()
        # look at each line, check against vocab list (holds each allowed word for the dic)
        if word in vocab:  # current word found in vocab list
            if word in bow:  # check if in bag already
                bow[word] = bow[word] + 1
            else:  # just add new key
                bow[word] = 1
        else:  # none cases
            if None in bow:
                bow[None] = bow[None] + 1
            else:  # just add new key
                bow[None] = 1

    return bow


# Needs modifications
def prior(training_data, label_list):
    """ return the prior probability of the label in the training set
        => frequency of DOCUMENTS
    """

    smooth = 1  # smoothing factor
    logprob = {}
    # TODO: add your code here

    # formula bottom is number of distinct words + number of total words
    denominator = len(label_list) + len(training_data)

    # training_data[entry]['label'] prolly hw you check it
    # find number of each label used
    for doc in training_data:
        # if label already counted in output just inc
        if doc['label'] in logprob:
            logprob[doc['label']] = logprob[doc['label']] + 1
        # if label new add to list
        else:
            logprob[doc['label']] = 2  # take care of + 1 from the start right here

    # at this point logprob holds N+1 for each label
    for label in logprob:
        logprob[label] = np.log(logprob[label] / denominator)

    # formula above does the ln of the smoothed probability
    return logprob


# Needs modifications
def p_word_given_label(vocab, training_data, label):
    """ return the class conditional probability of label over all words, with smoothing """

    n = 0
    smooth = 1  # smoothing factor
    word_prob = {}
    # TODO: add your code here
    # currently this system only adds words in relevant files, it does not cover every word in the vocab
    for file in training_data:
        if file['label'] == label:
            for word in file['bow']:
                n = file['bow'][word] + n  # total word counter
                if word in word_prob:  # current count + new count
                    word_prob[word] = word_prob[word] + file['bow'][word]
                else:  # just add the new word with the number
                    word_prob[word] = file['bow'][word]

    for vword in vocab:  # now for each word apply the formula
        if vword not in word_prob:  # any words not already there need to be included with 0 frequency
            word_prob[vword] = 0
        # at this point word_prob holds the count for each word nw
        word_prob[vword] = np.log((word_prob[vword] + smooth * 1) / (n + smooth * (len(vocab) + 1)))

    if None in word_prob:
        word_prob[None] = np.log((word_prob[None] + smooth * 1) / (n + smooth * (len(vocab) + 1)))  # not covered above

    # should now contain all ln of probabilities
    return word_prob


##################################################################################
# Needs modifications
def train(training_directory, cutoff):
    """ return a dictionary formatted as follows:
            {
             'vocabulary': <the training set vocabulary>,
             'log prior': <the output of prior()>,
             'log p(w|y=2016)': <the output of p_word_given_label() for 2016>,
             'log p(w|y=2020)': <the output of p_word_given_label() for 2020>
            }
    """
    retval = {}
    label_list = os.listdir(training_directory)
    # TODO: add your code here
    vocab = create_vocabulary(training_directory, cutoff)
    training_data = load_training_data(vocab, training_directory)

    retval['vocabulary'] = vocab
    retval['log prior'] = prior(training_data, label_list)
    retval['log p(w|y=2016)'] = p_word_given_label(vocab, training_data, '2016')
    retval['log p(w|y=2020)'] = p_word_given_label(vocab, training_data, '2020')

    return retval


# Needs modifications
def classify(model, filepath):
    """ return a dictionary formatted as follows:
            {
             'predicted y': <'2016' or '2020'>,
             'log p(y=2016|x)': <log probability of 2016 label for the document>,
             'log p(y=2020|x)': <log probability of 2020 label for the document>
            }
    """
    retval = {}
    # TODO: add your code here

    # set up values in dic, used as storage
    retval['log p(y=2020|x)'] = 0
    retval['log p(y=2016|x)'] = 0

    bow = create_bow(model['vocabulary'], filepath)

    for wordi in bow:
        # each of these lines adds one P value for each word in the file for each year
        # now multiply hat by number of occurrences of that word
        retval['log p(y=2020|x)'] = retval['log p(y=2020|x)'] + (model['log p(w|y=2020)'][wordi] * bow[wordi])
        retval['log p(y=2016|x)'] = retval['log p(y=2016|x)'] + (model['log p(w|y=2016)'][wordi] * bow[wordi])

    retval['log p(y=2016|x)'] = retval['log p(y=2016|x)'] + model['log prior']['2016']
    retval['log p(y=2020|x)'] = retval['log p(y=2020|x)'] + model['log prior']['2020']

    # this is experimental bb

    if retval['log p(y=2020|x)'] > retval['log p(y=2016|x)']:
        retval['predicted y'] = '2020'
    else:
        retval['predicted y'] = '2016'

    return retval
