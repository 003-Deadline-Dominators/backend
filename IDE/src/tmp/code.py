
import pandas as pd
import numpy as np
from sklearn.linear_model import LinearRegression
from sklearn.metrics import mean_squared_error, r2_score
import matplotlib.pyplot as plt
sales_data = {
    'Month': ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
    'Sales': [1000, 1200, 1500, 1800, 2000, 2200, 2500, 2300, 2000, 1800, 1500, 1200],
    'SeasonalIndex': [0.8, 0.9, 1.0, 1.2, 1.3, 1.4, 1.5, 1.4, 1.2, 1.0, 0.9, 0.8]
}
data = pd.DataFrame(sales_data)
def build_and_evaluate_model(data):
    X = data[['SeasonalIndex']]
    y = data['Sales']
    model = LinearRegression()
    model.fit(X, y)
    future_months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun']
    future_seasonal_indices = [0.8, 0.9, 1.0, 1.2, 1.3, 1.4]
    future_data = pd.DataFrame({'Month': future_months, 'SeasonalIndex': future_seasonal_indices})
    predicted_sales = model.predict(future_data[['SeasonalIndex']])
    y_pred = model.predict(X)
    mse = mean_squared_error(y, y_pred)
    r2 = r2_score(y, y_pred)
    print('Mean Squared Error:', mse)
    print('R-squared:', r2)
    plt.plot(data['Month'], data['Sales'], label='Actual Sales')
    plt.plot(future_months, predicted_sales, label='Predicted Sales')
    plt.xlabel('Month')
    plt.ylabel('Sales')
    plt.legend()
    plt.title('Actual vs. Predicted Sales')
    plt.show()
    print('Coefficients:', model.coef_)
    print('Intercept:', model.intercept_)
build_and_evaluate_model(data)
