import numpy as np
from sklearn.metrics import normalized_mutual_info_score
user_ratings = np.array([1, 2, 3, 4, 5, 1, 2, 3, 4, 5])
recommendations = np.array([2, 3, 4, 1, 5, 3, 4, 1, 2, 5])
def calculate_nmi(user_ratings, recommendations):
    nmi = normalized_mutual_info_score(user_ratings, recommendations)
    return nmi
nmi_score = calculate_nmi(user_ratings, recommendations)
print(f"Normalized Mutual Information (NMI): {nmi_score}")