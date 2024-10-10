import pandas as pd
from sklearn.linear_model import LinearRegression
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_squared_error
data = [{'newspaper': 69.2, 'TV': 230.1, 'sales': 22.1, 'radio': 37.8}, {'newspaper': 45.1, 'TV': 44.5, 'sales': 10.4, 'radio': 39.3}, {'newspaper': 69.3, 'TV': 17.2, 'sales': 9.3, 'radio': 45.9}, {'newspaper': 58.5, 'TV': 151.5, 'sales': 18.5, 'radio': 41.3}, {'newspaper': 58.4, 'TV': 180.8, 'sales': 12.9, 'radio': 10.8}, {'newspaper': 75, 'TV': 8.7, 'sales': 7.2, 'radio': 48.9}, {'newspaper': 23.5, 'TV': 57.5, 'sales': 11.8, 'radio': 32.8}, {'newspaper': 11.6, 'TV': 120.2, 'sales': 13.2, 'radio': 19.6}, {'newspaper': 1, 'TV': 8.6, 'sales': 4.8, 'radio': 2.1}, {'newspaper': 21.2, 'TV': 199.8, 'sales': 10.6, 'radio': 2.6}]
df = pd.DataFrame(data)
X = df[['TV', 'radio', 'newspaper']]
y = df['sales']
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
model = LinearRegression()
model.fit(X_train, y_train)
y_pred = model.predict(X_test)
rmse = mean_squared_error(y_test, y_pred, squared=False)
print('Root Mean Squared Error (RMSE):', rmse)
new_data = [[200, 30, 10]]  # Example advertising spend
predicted_sales = model.predict(new_data)
print('Predicted Sales:', predicted_sales[0])