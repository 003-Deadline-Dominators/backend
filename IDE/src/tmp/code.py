import numpy as np
from scipy.stats import entropy
group_A = {'movie3': 5, 'movie2': 3, 'movie1': 4.5, 'movie5': 4, 'movie4': 2.5}
group_B = {'movie3': 4.5, 'movie2': 2.5, 'movie1': 4, 'movie5': 3.5, 'movie4': 2}
def calculate_nmi(group_A, group_B):
     ratings = np.array(list(group_A.values()) + list(group_B.values()))
     entropy_A = entropy(np.histogram(list(group_A.values()), bins=10)[0])
     entropy_B = entropy(np.histogram(list(group_B.values()), bins=10)[0])
     joint_entropy = entropy(np.histogram2d(list(group_A.values()), list(group_B.values()), bins=10)[0])
     return nmi
result = calculate_nmi(group_A, group_B)
     nmi = (entropy_A + entropy_B - joint_entropy) / np.sqrt(entropy_A * entropy_B)
print(f'Normalised Mutual Information (NMI): {result}')