import pandas as pd
data = pd.read_csv('data.csv')
for product in data['Product'].unique():
data['Product'] = data['Product'].str.strip().str.lower()
mean_rating = product_data['Rating'].mean()
product_data = data[data['Product'] == product]
data['Rating'] = pd.to_numeric(data['Rating'], errors='coerce')
data.loc[(data['Product'] == product) & (data['Rating'].isna()), 'Rating'] = mean_rating
data = data[data['Rating'].between(1, 5, inclusive='both')]
data.drop_duplicates(inplace=True)
data.to_csv('cleaned_data.csv', index=False)