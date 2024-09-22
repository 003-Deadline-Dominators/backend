package com.parsons.ide;

import org.json.JSONObject;

public class Driver {

    public static void main(String[] args) {
        // Create instances of com.parsons.ide.PythonFileWriter and com.parsons.ide.DockerExecutor
        PythonFileWriter writer = new PythonFileWriter();
        DockerExecutor executor = new DockerExecutor("rita6667/gemini-app:latest");

        // Create instance of com.parsons.ide.PythonFileExecutor
        PythonFileExecutor pythonExecutor = new PythonFileExecutor(writer, executor);

        // Example Python code as a string with proper indentation
//        String pythonCode =
//                "def greet(name):\n" +
//                        "    if name:\n" +
//                        "        return 'Hello, ' + name + '!'\n" +
//                        "    else:\n" +
//                        "        return 'Hello, World!'\n\n" +
//                        "name = 'Deadline Dominators'\n" +
//                        "print(greet(name))\n";

//        String pythonCode =
//                "def add_numbers(a, b):\n" +
//                        "    return a + b\n\n" +
//                        "num1 = 5\n" +
//                        "num2 = 10\n" +
//                        "result = add_numbers(num1, num2)\n" +
//                        "print('The sum of', num1, 'and', num2, 'is:', result)\n";

        // Missing closing parenthesis
//        String pythonCode =
//                "def greet(name):\n" +
//                        "    if name:\n" +
//                        "        return 'Hello, ' + name + '!'\n" +
//                        "    else:\n" +
//                        "        return 'Hello, World!'\n\n" +
//                        "name = 'ChatGPT'\n" +
//                        "print(greet(name)\n";

        // division zero
//        String pythonCode =
//                "def divide(a, b):\n" +
//                        "    return a / b\n\n" +
//                        "num1 = 10\n" +
//                        "num2 = 0  # Dividing by zero\n" +
//                        "result = divide(num1, num2)\n" +
//                        "print('Result:', result)\n";

        // import error
//        String pythonCode =
//                "import non_existent_module\n" +  // Module doesn't exist
//                        "print('This will not run due to the import error.')\n";

        // type error
//        String pythonCode =
//                "def add(a, b):\n" +
//                        "    return a + b\n\n" +
//                        "result = add(10, '5')  # Trying to add a number and a string\n" +
//                        "print('Result:', result)\n";

        // read file error
//        String pythonCode =
//                "def read_file(filepath):\n" +
//                        "    with open(filepath, 'r') as f:\n" +
//                        "        return f.read()\n\n" +
//                        "content = read_file('/app/non_existent_file.txt')  # File doesn't exist\n" +
//                        "print(content)\n";

        // index error
//        String pythonCode =
//                "def get_element(lst, index):\n" +
//                        "    return lst[index]\n\n" +
//                        "my_list = [1, 2, 3]\n" +
//                        "print('Element at index 5:', get_element(my_list, 5))  # Index out of range\n";

        // actual example
//        String pythonCode =
//                "import pandas as pd\n" +
//                        "from sklearn.linear_model import LinearRegression\n" +
//                        "from sklearn.model_selection import train_test_split\n" +
//                        "from sklearn.metrics import mean_squared_error, r2_score\n\n" +
//                        "data = [\n" +
//                        "    {\"Month\": \"January\", \"Sales Revenue\": 50000, \"Advertising Spend\": 10000},\n" +
//                        "    {\"Month\": \"February\", \"Sales Revenue\": 60000, \"Advertising Spend\": 12000},\n" +
//                        "    {\"Month\": \"March\", \"Sales Revenue\": 75000, \"Advertising Spend\": 15000},\n" +
//                        "    {\"Month\": \"April\", \"Sales Revenue\": 90000, \"Advertising Spend\": 18000},\n" +
//                        "    {\"Month\": \"May\", \"Sales Revenue\": 100000, \"Advertising Spend\": 20000},\n" +
//                        "    {\"Month\": \"June\", \"Sales Revenue\": 110000, \"Advertising Spend\": 22000},\n" +
//                        "    {\"Month\": \"July\", \"Sales Revenue\": 125000, \"Advertising Spend\": 25000},\n" +
//                        "    {\"Month\": \"August\", \"Sales Revenue\": 140000, \"Advertising Spend\": 28000},\n" +
//                        "    {\"Month\": \"September\", \"Sales Revenue\": 150000, \"Advertising Spend\": 30000},\n" +
//                        "    {\"Month\": \"October\", \"Sales Revenue\": 160000, \"Advertising Spend\": 32000},\n" +
//                        "    {\"Month\": \"November\", \"Sales Revenue\": 175000, \"Advertising Spend\": 35000},\n" +
//                        "    {\"Month\": \"December\", \"Sales Revenue\": 190000, \"Advertising Spend\": 38000}\n" +
//                        "]\n\n" +
//                        "df = pd.DataFrame(data)\n" +
//                        "model = LinearRegression()\n" +
//                        "X = df['Advertising Spend'].values.reshape(-1, 1)  # Reshape for linear regression\n" +
//                        "y = df['Sales Revenue'].values\n" +
//                        "X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)\n" +
//                        "model.fit(X_train, y_train)\n" +
//                        "intercept = model.intercept_\n" +
//                        "coefficient = model.coef_[0]\n" +
//                        "y_pred = model.predict(X_test)\n" +
//                        "rmse = mean_squared_error(y_test, y_pred, squared=False)  # Root Mean Squared Error\n" +
//                        "r_squared = r2_score(y_test, y_pred)  # R-squared value\n" +
//                        "print(f\"Intercept: {intercept}\")\n" +
//                        "print(f\"Coefficient: {coefficient}\")\n" +
//                        "print(f\"RMSE: {rmse}\")\n" +
//                        "print(f\"R-squared: {r_squared}\")\n\n" +
//                        "predictions = model.predict(X)\n" +
//                        "increased_budget = df['Advertising Spend'].values[-1] * 1.20  # Increase by 20%\n" +
//                        "predicted_sales = model.predict([[increased_budget]])\n" +
//                        "print(f\"Predicted sales with increased budget: {predicted_sales[0]}\")\n";

        String pythonCode =
                "import pandas as pd\n" +
                        "data = pd.read_csv('advertising_data.csv')\n" +
                        "data['Sales per Dollar Spent'] = data['Sales (USD)'] / data['Advertising Spend (USD)']\n" +
                        "print(data)\n" +
                        "data.to_csv('processed_advertising_data.csv', index=False)\n";


        // Define the directory and script name
        String currentDir = System.getProperty("user.dir");
        String directoryPath = currentDir + "/IDE/src/tmp";
        String scriptName = "code.py";  // The script name

        // Execute the Python code and get the result
        JSONObject result = pythonExecutor.executePythonCode(pythonCode, directoryPath, scriptName);

        // Print the result
        if (result != null) {
            System.out.println("Stdout:");
            System.out.println(result.getString("stdout"));

            System.out.println("Stderr:");
            System.out.println(result.getString("stderr"));
        } else {
            System.out.println("Execution failed.");
        }
    }
}
