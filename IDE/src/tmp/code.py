import nltk
paper_text = "This is an example of an academic paper. It contains sentences of varying lengths and complexities. We need to split this paper into individual sentences for further analysis. This will help us extract key insights and trends from the research."
def split_sentences(text):
   sentences = nltk.sent_tokenize(text)
   return sentences
paper_sentences = split_sentences(paper_text)
print(paper_sentences)