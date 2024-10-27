import nltk
from nltk.tokenize import sent_tokenize
def split_sentences(text):
    sentences = sent_tokenize(text)
    return sentences
user_query = "I'm having trouble logging in. My password isn't working. Can you help?"
split_sentences = split_sentences(user_query)
print(f"Split sentences: {split_sentences}")