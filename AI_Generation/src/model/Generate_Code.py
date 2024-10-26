# -*- coding: utf-8 -*-
import sys
import google.generativeai as genai
import os
from dotenv import load_dotenv, find_dotenv
import json
import re  # 添加 re 库来处理正则表达式

# 加载API密钥
_ = load_dotenv(find_dotenv())
# 获取API Key
api_key = os.getenv('GEMINI_API_KEY')
# 配置genai
genai.configure(api_key=api_key)

model = genai.GenerativeModel('gemini-1.5-flash')

def generate_code_prompt(topic, scenario, task, data=None):

    common_instructions = """
    Ensure the generated Python code meets the following criteria:
    - **Understand the Problem**: Carefully read the scenario and task to fully grasp the requirements.
    - **Complete Solution:** Write Python code that must fully address all parts of the task. Partial solutions or minimal code snippets are not acceptable. The solution must be comprehensive and cover any necessary data processing, analysis, and final outputs (e.g., print output)
    - **Correctness:** The code should be error-free and capable of running successfully on the provided dataset (or a similar dataset structure).
    - **Structured Code:** Include steps for loading and processing the data (if applicable), any intermediate calculations or transformations needed to meet the task requirements, and final calculations or results that directly solve the task.
    - **Libraries:** If external libraries (like pandas, NumPy, etc.) are required, import them at the beginning of the code and explain their use where necessary.
    - **Modular Approach:** Where applicable, break the solution into functions or logical blocks to enhance readability and reusability.
    - **Output:** Ensure the code includes appropriate print statements or return values to display the final results clearly.
    - **No Partial Code:** The solution should not be a minimal code snippet. All parts of the task should be covered in the generated code.
    - **Filename Consistency**: Use the exact filenames specified in the scenario, task, or data sections when reading from or writing to files.
    - **Data Consistency**: If data is provided, ensure the code correctly loads, processes, and uses it as per the task requirements.
    - **Formatted Code**: The code should follow a consistent formatting guideline, such as PEP 8 for Python. Specifically, ensure that:
        - Indentation is consistent and uses four spaces per indentation level.
        - No lines of code are broken improperly; continuation lines should align wrapped elements either vertically or using a hanging indent.
        - There are no extraneous spaces within lines of code, especially inside parentheses, brackets, or braces.
        - Each block of code (including loops, conditionals, and function definitions) starts with correct indentation.
    - **Clear Separation of Sections:** The code must be divided into two distinct sections:
        - **Imports and Data Definition:** All import statements must be placed at the very top, followed by any predefined data (e.g., lists or dictionaries).
        - **Code:** The rest of the code should follow, including data loading (such as converting predefined data to a DataFrame) and the main logic required to solve the task.
    - **No Single-line Symbols:** Ensure that no symbols (such as braces, brackets, parentheses, or commas) appear alone on a line to maintain clean and professional code formatting.
    - **No Visualization**: Do not include any data visualization code, like plot graph.
    """

    # 特定topic的指南
    specific_instructions = ""
    if topic == "DataFrame":
        specific_instructions = """
        - Use pandas to perform operations such as filtering, merging, and aggregation on DataFrame.
        - Handle missing values using methods like fillna or dropna.
        - Convert data types of DataFrame columns as required to ensure correct data processing.
        - Perform conditional operations on data using query or loc methods.
        """
    elif topic == "NMI Normalised Mutual Information":
        specific_instructions = """
        - Use the sklearn.metrics or scipy.stats modules to compute the Normalised Mutual Information.
        - Preprocess the datasets to ensure that they are properly aligned and that categorical data is appropriately encoded.
        - Handle any data inconsistencies or missing data before performing calculations.
        - Explain the significance of the NMI score in the context of data dependency and correlation.
        """
    elif topic == "Sentence Splitting Using nltk":
        specific_instructions = """
        - Use the nltk.tokenize module to split text into sentences.
        - Ensure the tokenizer handles various sentence terminators beyond just periods (e.g., exclamation points, question marks).
        - Implement exception handling for encoding issues or malformed inputs.
        - Optionally, include functionality to count and display the frequency of certain words or phrases within the sentences.
        """
    elif topic == "Correlation":
        specific_instructions = """
       - Use pandas to calculate Pearson or Spearman correlation coefficients between different variables in a dataset.
       - Handle non-numeric data by applying appropriate conversions or exclusions.
       - Include functionality to handle and report any missing or infinite values before performing correlation analysis.
       - Optionally, generate a correlation matrix and describe its implications for data relationships.
       """
    elif topic == "Linear Regression":
        specific_instructions = """
        - Use scikit-learn to implement a linear regression model.
        - Preprocess the data by scaling or normalizing it as necessary to improve model performance.
        - Include code to split the data into training and testing sets.
        - Provide output that includes regression coefficients, intercept, and model performance metrics (e.g., R-squared, MSE).
        """
    elif topic == "Decision Tree Classifiers":
        specific_instructions = """
        - Use scikit-learn to implement a decision tree classifier.
        - Handle categorical data by encoding it appropriately using methods like OneHotEncoder or LabelEncoder.
        - Split the dataset into training and test sets to evaluate the model's performance.
        - Output the decision tree's structure visually if possible, and provide accuracy metrics and classification report.
        """
    elif topic == "Reading/Writing CSV files":
        specific_instructions = """
        - Use pandas to read and write CSV files.
        - Include error handling for common issues like missing files, incorrect paths, or parsing errors.
        - Ensure that data is correctly formatted and aligned when written back to CSV.
        - Implement checks to verify data integrity during the read/write process.
        """

    # 构建完整的Prompt
    prompt = f"""
    You are tasked with generating Python code to solve the following problem based on the topic '{topic}'.
    
    ### Scenario:
    {scenario}
    
    ### Task:
    {task}
    """

    if data:
        prompt += f"""
        ### Data:
        {data}
        """

    prompt += f"""
    **Instructions:**
    {common_instructions}
    
    **Specific Instructions for {topic}:**
    {specific_instructions}
    
    The output format should be as follows:
    ```json
    {
    "import and data define": "import statements and predefined data here",
    "code": "Generated Python Code"
    }
    ```

    Please ensure that the AI model generates code that addresses all the points mentioned above, placing imports and any predefined data (like lists or dictionaries) at the top, and functional code (including data loading) afterward.
    """

    return prompt



def extract_text_from_response(response):
    return response.candidates[0].content.parts[0].text

def clean_code(code):
    # 删除所有以#开头的单行注释
    code = re.sub(r'(?m)^ *#.*\n?', '', code)
    # 删除所有多行注释块
    code = re.sub(r'(\'\'\'[\s\S]*?\'\'\'|"""[\s\S]*?""")', '', code)
    # 删除连续的多个空行，只保留一个空行
    cleaned_code = re.sub(r'\n\s*\n', '\n\n', code)
    # 删除多余的空格和空行
    cleaned_code = re.sub(r'\n\s*\n', '\n', cleaned_code.strip())
    return cleaned_code

def extract_code_content(code_json_str):
    cleaned_str = code_json_str.replace("```json", "").replace("```", "").strip()
    code_data = json.loads(cleaned_str)

    # Extract import and data define
    import_and_data = code_data.get("import and data define", "").replace('python', '').strip()
    cleaned_import_and_data = clean_code(import_and_data)

    # Extract code
    code = code_data.get("code", "").replace('python', '').strip()

    # Clean the code
    cleaned_code = clean_code(code)

    # Output the result in the required format
    output = {
        "import and data define": cleaned_import_and_data,
        "code": cleaned_code
    }

    print(json.dumps(output, indent=4))

def main():
    scenario = sys.argv[1]  # 从命令行参数获取 scenario
    task = sys.argv[2]  # 从命令行参数获取 task
    data = sys.argv[3] if len(sys.argv) > 3 else None  # 从命令行参数获取 data，如果没有则为 None

    prompt = generate_code_prompt(scenario, task, data)
    response = model.generate_content(prompt)  # 生成AI的回复
    result = extract_text_from_response(response)  # 提取AI回复内容

    extract_code_content(result)

if __name__ == "__main__":
    main()
