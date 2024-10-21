import pandas as pd
orders = pd.DataFrame({'order_id': [1, 2, 3, 4, 5],
                         'product_id': [101, 102, 103, 101, 102],
                         'quantity': [2, 1, 3, 4, 2],
                         'price': [10.0, 20.0, 15.0, 10.0, 20.0]})
def calculate_total_revenue(orders):
    total_revenue = orders['quantity'] * orders['price']
    return total_revenue.sum()
total_revenue = calculate_total_revenue(orders)
print(f'Total Revenue: ${total_revenue:.2f}')