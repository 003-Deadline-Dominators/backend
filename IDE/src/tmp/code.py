import pandas as pd
sales_data = [
    {'Product': 'Laptop', 'Category': 'Electronics', 'Quantity': 5, 'Price': 1200},
    {'Product': 'Smartphone', 'Category': 'Electronics', 'Quantity': 10, 'Price': 600},
    {'Product': 'Keyboard', 'Category': 'Electronics', 'Quantity': 20, 'Price': 50},
    {'Product': 'T-Shirt', 'Category': 'Clothing', 'Quantity': 30, 'Price': 20},
    {'Product': 'Jeans', 'Category': 'Clothing', 'Quantity': 15, 'Price': 80},
    {'Product': 'Book', 'Category': 'Books', 'Quantity': 40, 'Price': 15}
]
def calculate_revenue(sales_data):
    df = pd.DataFrame(sales_data)
    df['Revenue'] = df['Quantity'] * df['Price']
    return df[['Product', 'Revenue']]
sales_revenue = calculate_revenue(sales_data)
print("Product Revenue:")
print(sales_revenue)