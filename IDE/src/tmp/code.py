import pandas as pd
sales_data = [{"product_id": "A1", "product_name": "Laptop", "category": "Electronics", "quantity_sold": 100, "price": 1200}, {"product_id": "B2", "product_name": "Tablet", "category": "Electronics", "quantity_sold": 50, "price": 300}, {"product_id": "C3", "product_name": "T-Shirt", "category": "Clothing", "quantity_sold": 200, "price": 20}, {"product_id": "D4", "product_name": "Jeans", "category": "Clothing", "quantity_sold": 150, "price": 50}, {"product_id": "E5", "product_name": "Book", "category": "Books", "quantity_sold": 100, "price": 15}]
df = pd.DataFrame(sales_data)
df['revenue'] = df['quantity_sold'] * df['price']
top_sellers = df.sort_values(by=['revenue'], ascending=False)
top_5_products = top_sellers.head(5)
print("Top 5 Best-Selling Products:")
print(top_5_products[['product_name', 'revenue']])