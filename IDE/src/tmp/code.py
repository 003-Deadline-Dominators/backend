import pandas as pd
<<<<<<< Updated upstream
from sklearn.linear_model import LinearRegression
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_squared_error, r2_score

data = [
    {"Month": "January", "Sales Revenue": 50000, "Advertising Spend": 10000},
    {"Month": "February", "Sales Revenue": 60000, "Advertising Spend": 12000},
    {"Month": "March", "Sales Revenue": 75000, "Advertising Spend": 15000},
    {"Month": "April", "Sales Revenue": 90000, "Advertising Spend": 18000},
    {"Month": "May", "Sales Revenue": 100000, "Advertising Spend": 20000},
    {"Month": "June", "Sales Revenue": 110000, "Advertising Spend": 22000},
    {"Month": "July", "Sales Revenue": 125000, "Advertising Spend": 25000},
    {"Month": "August", "Sales Revenue": 140000, "Advertising Spend": 28000},
    {"Month": "September", "Sales Revenue": 150000, "Advertising Spend": 30000},
    {"Month": "October", "Sales Revenue": 160000, "Advertising Spend": 32000},
    {"Month": "November", "Sales Revenue": 175000, "Advertising Spend": 35000},
    {"Month": "December", "Sales Revenue": 190000, "Advertising Spend": 38000}
]

df = pd.DataFrame(data)
model = LinearRegression()
X = df['Advertising Spend'].values.reshape(-1, 1)  # Reshape for linear regression
y = df['Sales Revenue'].values
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
model.fit(X_train, y_train)
intercept = model.intercept_
coefficient = model.coef_[0]
y_pred = model.predict(X_test)
rmse = mean_squared_error(y_test, y_pred, squared=False)  # Root Mean Squared Error
r_squared = r2_score(y_test, y_pred)  # R-squared value
print(f"Intercept: {intercept}")
print(f"Coefficient: {coefficient}")
print(f"RMSE: {rmse}")
print(f"R-squared: {r_squared}")

predictions = model.predict(X)
increased_budget = df['Advertising Spend'].values[-1] * 1.20  # Increase by 20%
predicted_sales = model.predict([[increased_budget]])
print(f"Predicted sales with increased budget: {predicted_sales[0]}")
=======
data = pd.read_csv('advertising_data.csv')
data['Sales per Dollar Spent'] = data['Sales (USD)'] / data['Advertising Spend (USD)']
print(data)
data.to_csv('processed_advertising_data.csv', index=False)
>>>>>>> Stashed changes
