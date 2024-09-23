import nltk
paper = "This is a sample academic paper. It contains multiple sentences. Sentence splitting is essential for text analysis. This is the fourth sentence in the paper. And this is the fifth sentence."
sentences = nltk.sent_tokenize(paper)
print(sentences)