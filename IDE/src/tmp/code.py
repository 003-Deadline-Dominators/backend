import pandas as pd
import statsmodels.formula.api as sm
import matplotlib.pyplot as plt
data = [
    {"Income": 50000, "Education Level": "High School Diploma"},
    {"Income": 75000, "Education Level": "Bachelor's Degree"},
    {"Income": 100000, "Education Level": "Master's Degree"},
    {"Income": 125000, "Education Level": "Doctoral Degree"},
    {"Income": 45000, "Education Level": "High School Diploma"},
    {"Income": 65000, "Education Level": "Bachelor's Degree"},
    {"Income": 90000, "Education Level": "Master's Degree"},
    {"Income": 110000, "Education Level": "Doctoral Degree"},
    {"Income": 55000, "Education Level": "High School Diploma"},
    {"Income": 80000, "Education Level": "Bachelor's Degree"},
    {"Income": 105000, "Education Level": "Master's Degree"},
    {"Income": 130000, "Education Level": "Doctoral Degree"}
]
df = pd.DataFrame(data)
education_levels = {
    "High School Diploma": 1,
    "Bachelor's Degree": 2,
    "Master's Degree": 3,
    "Doctoral Degree": 4
}
df['Education Level Code'] = df['Education Level'].map(education_levels)
model = sm.ols('Income ~ Education Level Code', data=df)
results = model.fit()
print(results.summary())
print('Intercept:', results.params[0])
print('Slope:', results.params[1])
new_data = pd.DataFrame({'Education Level Code': [1, 2, 3, 4]})
predicted_income = results.predict(new_data)
print('Predicted Income for Different Education Levels:')
print(predicted_income)
plt.scatter(df['Education Level Code'], df['Income'])
plt.plot(new_data['Education Level Code'], predicted_income, color='red')
plt.xlabel('Education Level Code')
plt.ylabel('Income')
plt.title('Relationship between Education Level and Income')
plt.show()