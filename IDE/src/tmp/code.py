import pandas as pd
data = {'Product': ['A', 'B', 'C', 'D', 'E'], 'Category': ['Electronics', 'Books', 'Clothing', 'Electronics', 'Books'], 'Sales': [100, 50, 75, 120, 60]}
products = pd.DataFrame(data)
def calculate_total_sales_by_category(products):
    total_sales = products.groupby('Category')['Sales'].sum().reset_index()
    return total_sales
total_sales_df = calculate_total_sales_by_category(products)
print("Total Sales by Category:")
print(total_sales_df)