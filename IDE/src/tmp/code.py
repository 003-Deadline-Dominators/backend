import pandas as pd
from sklearn.linear_model import LinearRegression
from sklearn.model_selection import train_test_split
from sklearn.metrics import r2_score, mean_squared_error
import matplotlib.pyplot as plt
data = {'Month': ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
        'Sales': [5000, 5500, 6000, 6500, 7000, 7500, 8000, 8500, 9000, 9500, 10000, 10500],
        'Advertising': [1000, 1200, 1400, 1600, 1800, 2000, 2200, 2400, 2600, 2800, 3000, 3200]}
df = pd.DataFrame(data)
X = df[['Advertising']]
y = df['Sales']
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
model = LinearRegression()
model.fit(X_train, y_train)
y_pred = model.predict(X_test)
r_squared = r2_score(y_test, y_pred)
mean_squared_error = mean_squared_error(y_test, y_pred)
print(f'R-squared: {r_squared}')
print(f'Mean Squared Error: {mean_squared_error}')
plt.scatter(X_test['Advertising'], y_test, label='Actual Sales')
plt.plot(X_test['Advertising'], y_pred, color='red', label='Predicted Sales')
plt.xlabel('Advertising Spend')
plt.ylabel('Sales')
plt.legend()
plt.title('Predicted vs. Actual Sales')
plt.show()