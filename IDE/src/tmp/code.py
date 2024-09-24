import nltk
def split_sentence(sentence):
    words = nltk.word_tokenize(sentence)
    return words

sentence = 'This is an example sentence to split.'
words = split_sentence(sentence)
print(f'Words in the sentence: {words}')
