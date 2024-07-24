from classify import train
from classify import classify
from classify import create_vocabulary
from classify import load_training_data
from classify import p_word_given_label
from classify import prior

model = train('./corpus/training/', 2)

print(classify(model, './corpus/test/2016/0.txt'))


