import pandas as pd
import numpy as np
data = {
    'bedrooms': [3, 2, 4, 3, 5],
    'bathrooms': [2, 1, 3, 2, 3],
    'sqft': [1500, 1200, 2000, 1800, 2500],
    'location': ['Downtown', 'Suburbs', 'Downtown', 'Suburbs', 'Downtown'],
    'age': [10, 25, 5, 15, 2],
    'price': [300000, 200000, 400000, 350000, 500000]
}
df = pd.DataFrame(data)
df['location'] = pd.factorize(df['location'])[0]
correlation = df.corr()['price']
print(correlation)