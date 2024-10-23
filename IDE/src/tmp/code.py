import pandas as pd
sales_data = [  
    {'Product': 'Laptop', 'Category': 'Electronics', 'Quantity': 2, 'Price': 1200},  
    {'Product': 'Keyboard', 'Category': 'Electronics', 'Quantity': 5, 'Price': 50},  
    {'Product': 'Mouse', 'Category': 'Electronics', 'Quantity': 10, 'Price': 25},  
    {'Product': 'Shirt', 'Category': 'Clothing', 'Quantity': 8, 'Price': 20},  
    {'Product': 'Jeans', 'Category': 'Clothing', 'Quantity': 3, 'Price': 60},  
    {'Product': 'Book', 'Category': 'Books', 'Quantity': 15, 'Price': 15},  
    {'Product': 'Charger', 'Category': 'Electronics', 'Quantity': 7, 'Price': 30}  
]
df = pd.DataFrame(sales_data)
df['Revenue'] = df['Quantity'] * df['Price']
revenue_by_category = df.groupby('Category')['Revenue'].sum()
print('Top 5 Best-Selling Products:')
print(df.groupby('Product')['Quantity'].sum().sort_values(ascending=False).head(5))
print('\nTotal Revenue by Category:')
print(revenue_by_category.sort_values(ascending=False))