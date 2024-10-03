import pandas as pd
sales_data = pd.read_csv('sales_data.csv')
total_revenue = sales_data['Quantity'] * sales_data['Price']
revenue_by_category = sales_data.groupby('Category')['Quantity', 'Price'].agg({'Quantity': 'sum', 'Price': 'sum'})
revenue_by_category['Total Revenue'] = revenue_by_category['Quantity'] * revenue_by_category['Price']
sorted_revenue = revenue_by_category.sort_values(by='Total Revenue', ascending=False)
print('Top 5 Best-Selling Products:')
print(sales_data['Product'].value_counts().head(5))
print('\nRevenue by Category:')
print(sorted_revenue[['Total Revenue']])