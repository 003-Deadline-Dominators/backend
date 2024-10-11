import pandas as pd
sales_data = [
    {'date': '2023-01-15', 'quantity': 100, 'product_id': 'P001', 'category': 'Dairy', 'product_name': 'Milk', 'store_location': 'Sydney'},
    {'date': '2023-01-15', 'quantity': 50, 'product_id': 'P002', 'category': 'Bakery', 'product_name': 'Bread', 'store_location': 'Melbourne'},
    {'date': '2023-01-15', 'quantity': 75, 'product_id': 'P003', 'category': 'Dairy', 'product_name': 'Eggs', 'store_location': 'Brisbane'},
    {'date': '2023-01-15', 'quantity': 200, 'product_id': 'P004', 'category': 'Fruit', 'product_name': 'Apples', 'store_location': 'Sydney'},
    {'date': '2023-01-15', 'quantity': 150, 'product_id': 'P005', 'category': 'Beverages', 'product_name': 'Orange', 'store_location': 'Melbourne'}
]
df = pd.DataFrame(sales_data)
top_products = df.groupby('product_name')['quantity'].sum().sort_values(ascending=False).head(5)
print('Top 5 best-selling products:')
print(top_products)
average_transaction_value = df.groupby('store_location')['quantity'].mean()
highest_avg_store = average_transaction_value.idxmax()
print(f'Store with highest average transaction value: {highest_avg_store}')
import matplotlib.pyplot as plt
df['date'] = pd.to_datetime(df['date'])
df['month'] = df['date'].dt.month
sales_by_category_month = df.groupby(['category', 'month'])['quantity'].sum().reset_index()
fig, ax = plt.subplots(figsize=(10, 6))
for category in sales_by_category_month['category'].unique():
    category_data = sales_by_category_month[sales_by_category_month['category'] == category]
    ax.plot(category_data['month'], category_data['quantity'], label=category)
ax.set_xlabel('Month')
ax.set_ylabel('Quantity Sold')
ax.set_title('Monthly Sales Trend by Category')
ax.legend()
plt.show()