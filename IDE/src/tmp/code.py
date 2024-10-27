import nltk
from nltk.tokenize import sent_tokenize
def split_into_sentences(text):
    sentences = sent_tokenize(text)
    return sentences