import nltk
papers = [ "This paper explores the use of deep learning techniques for natural language processing. The proposed model utilizes a recurrent neural network architecture to achieve state-of-the-art results on various NLP tasks.", "Our experiments demonstrate the effectiveness of the proposed model, achieving significant improvements over existing approaches. The findings highlight the potential of deep learning for advancing the field of natural language understanding.", "Future research directions include investigating the use of more sophisticated architectures and exploring the application of the model to other NLP tasks.", "This paper analyzes the sentiment expressed in online reviews using a combination of sentiment analysis and machine learning techniques. The proposed approach identifies key features that influence customer sentiment and provides insights into the factors driving positive or negative reviews.", "The results demonstrate the effectiveness of the proposed approach in accurately predicting customer sentiment, providing valuable insights for businesses to improve their products and services." ]
def split_sentences(papers):
    sentences_list = []
    for paper in papers:
        sentences = nltk.sent_tokenize(paper)
        sentences_list.append(sentences)
    return sentences_list
sentences_by_paper = split_sentences(papers)
print(sentences_by_paper)