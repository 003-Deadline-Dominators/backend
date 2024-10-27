# -*- coding: utf-8 -*-
import sys
import google.generativeai as genai
import os
from dotenv import load_dotenv, find_dotenv
import json
import re  # 添加 re 库来处理正则表达式
import textwrap

# 加载API密钥
_ = load_dotenv(find_dotenv())
# 获取API Key
api_key = os.getenv('GEMINI_API_KEY')
# 配置genai
genai.configure(api_key=api_key)

model = genai.GenerativeModel('gemini-1.5-flash')

def generate_code_prompt(topic, scenario, task, data=None):
    prompt = f"""
    You are an AI model tasked with generating Python code to solve the following problem based on the topic '{topic}'.

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

    common_instructions = """
    Ensure the generated Python code meets the following criteria:
    - **Understand the Problem**: Carefully read the scenario and task to fully grasp the requirements.
    - **Complete Solution:** Write Python code that must fully address all parts of the task. Partial solutions or minimal code snippets are not acceptable. The solution must be comprehensive and cover any necessary data processing, analysis, and provided print statement.
    - **Correctness:** The code should be error-free and capable of running successfully on the provided dataset (or a similar dataset structure).
    - **Procedural Structure**: Avoid object-oriented programming (OOP) patterns. Write the code in a clear, structured, and procedural style.
    - **Structured Code:** Include steps for loading and processing the data (if applicable), any intermediate calculations or transformations needed to meet the task requirements, and final calculations or results that directly solve the task.
    - **Libraries:** If external libraries (like pandas, NumPy, etc.) are required, import them at the beginning of the code and explain their use where necessary.
    - **Modular Approach:** Where applicable, break the solution into functions or logical blocks to enhance readability and reusability.
    - **Output:** Ensure the code includes appropriate print statements or return values to display the final results clearly.
    - **Print Final Result:** Each solution must include a print statement at the end that clearly outputs the final result.
    - **No Partial Code:** The solution should not be a minimal code snippet. All parts of the task should be covered in the generated code.
    - **Filename Consistency**: Use the exact filenames specified in the scenario, task, or data sections when reading from or writing to files.
    - **Data Consistency**: If data is provided, ensure the code correctly loads, processes, and uses it as per the task requirements.
    - **Formatted Code**: Indentation should be consistent and uses four spaces per indentation level.
    - **Clear Separation of Sections:** The code must be divided into two distinct sections:
        - **Imports and Data Definition:** All import statements must be placed at the very top, followed by any predefined data (e.g., lists or dictionaries).
        - **Code:** The rest of the code should follow, including data loading (such as converting predefined data to a DataFrame) and the main logic required to solve the task.
    - **No Single-line Symbols:** Ensure that no symbols (such as braces, brackets, parentheses, or commas) appear alone on a line to maintain clean and professional code formatting.
    - **No Visualization**: Do not include any data visualization code, like plot graphs.
    """

    # 特定topic的指南
    specific_instructions = ""
    if topic == "DataFrame":
        specific_instructions = """
        - Focus on operations such as filtering, merging, sorting, and aggregation to manage and manipulate tabular data.
        - Address common issues like missing values, data type conversions, and conditional filtering.
        - Ensure that these operations are applicable regardless of the specific data manipulation library used.
        """
    elif topic == "NMI Normalised Mutual Information":
        specific_instructions = """
        - Discuss the application of Normalised Mutual Information to measure statistical dependencies between datasets.
        - Ensure proper data preprocessing, including alignment and encoding, is discussed to facilitate accurate calculations.
        - Emphasize the interpretation and significance of NMI scores in understanding data relationships.
        """
    elif topic == "Sentence Splitting Using nltk":
        specific_instructions = """
        - Explain methods for splitting text into sentences, considering various punctuation and special cases.
        - Include preprocessing considerations to ensure robust sentence detection across different text formats.
        - Discuss extending functionality to include analysis like frequency of specific words or phrases.
        """
    elif topic == "Correlation":
        specific_instructions = """
        - Discuss the computation of correlation coefficients to assess relationships between variables.
        - Address the preparation of data, including managing different data types and missing values.
        - Explain how correlation findings can be interpreted to identify relationships between variables.
        """
    elif topic == "Linear Regression":
        specific_instructions = """
        - Describe setting up and implementing linear regression models, including data preparation and result interpretation.
        - Discuss various performance metrics and their importance in evaluating the effectiveness of the model.
        - Cover considerations such as data scaling, feature selection, and model diagnostics.
        """
    elif topic == "Decision Tree Classifiers":
        specific_instructions = """
        - Outline the usage of decision tree classifiers, focusing on data preparation, model training, and evaluation.
        - Discuss the handling of categorical data, parameter tuning, and visualization of the model's structure.
        - Highlight model evaluation techniques and key performance metrics.
        """
    elif topic == "Reading/Writing CSV files":
        specific_instructions = """
        - Discuss the use of tools for robustly reading from and writing to CSV files, ensuring data integrity.
        - Include error handling for common issues such as missing files, incorrect paths, or parsing errors.
        - Emphasize checks for data integrity and formatting during the read/write process.
        """

    prompt += f"""
    **Instructions:**
    {common_instructions}
    
    **Specific Instructions for {topic}:**
    {specific_instructions}
    
    The output format should be as follows:
    {{
        "import and data define": "import statements and predefined data here",
        "code": "Generated Python Code"
    }}
    """

    return prompt


def extract_text_from_response(response):
    return response.candidates[0].content.parts[0].text

def clean_code(code):
    # 删除所有以#开头的单行注释
    code = re.sub(r'(?m)^ *#.*\n?', '', code)
    # 删除所有多行注释块
    code = re.sub(r'(\'\'\'[\s\S]*?\'\'\'|"""[\s\S]*?""")', '', code)
    code = textwrap.dedent(code)
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
