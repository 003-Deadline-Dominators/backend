import nltk
email_text = "This is a sample email. It contains multiple sentences. Let's see how it works."
def split_email_into_sentences(email_text):
     sentences = nltk.sent_tokenize(email_text)
     return sentences
sentences = split_email_into_sentences(email_text)
print(f"Sentences in the email: {sentences}")