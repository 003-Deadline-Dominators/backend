import pandas as pd
import numpy as np
data = {
    'Age': [45, 52, 38, 61, 49, 58, 35, 42, 55, 68],
    'BMI': [28.5, 32.1, 25.8, 30.2, 27.6, 34.9, 24.2, 29.7, 31.5, 36.4],
    'Physical Activity': [2, 3, 1, 2, 0, 1, 3, 2, 1, 0],
    'Dietary Habits': [4, 3, 5, 2, 4, 1, 5, 3, 2, 1],
    'Type 2 Diabetes': [1, 1, 0, 1, 0, 1, 0, 0, 1, 1]
}
df = pd.DataFrame(data)
df_corr = df.corr()
print(df_corr)