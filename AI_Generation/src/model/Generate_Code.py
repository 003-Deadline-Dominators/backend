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

def generate_code_prompt(scenario, task, data=None):
    prompt = f"""
    You are an AI model tasked with solving a problem based on the following context.

    ### Scenario:
    {scenario}

    ### Task:
    {task}

    Ensure that the generated Python code meets the following criteria:

    1. **Complete Solution:** The code must fully address all parts of the task. Partial solutions or minimal code snippets are not acceptable. The solution must be comprehensive and cover any necessary data processing, analysis, and final outputs.
    2. **Correctness:** The code should be error-free and capable of running successfully on the provided dataset (or a similar dataset structure).
    3. **Structured Code:** The solution must include:
        - Steps for loading and processing the data (if applicable).
        - Any intermediate calculations or transformations needed to meet the task requirements.
        - Final calculations or results that directly solve the task.
    4. **Modular Approach:** Where applicable, break the solution into functions or logical blocks to enhance readability and reusability.
    5. **Libraries:** If external libraries (like pandas, NumPy, etc.) are required, import them at the beginning of the code and explain their use where necessary.
    6. **Output:** Ensure the code includes appropriate print statements or return values to display the final results clearly.
    7. **Robustness:** Handle potential errors, such as missing or malformed data, if they are likely to occur in the dataset.
    8. **Intermediate Steps:** Make sure the code includes all steps required for data loading, processing, and transforming it before reaching the final output. Address how data is cleaned or modified before any analysis.
    9. **Error Handling:** Include proper error handling for any potential issues, such as missing values, incorrect data types, or unexpected data structures, and provide informative error messages.
    10. **Readable Output:** The code should display or return the final results in a human-readable format, such as printing the top 5 best-selling products in a clean table or returning the total revenue by category in a sorted list.
    11. **No Partial Code:** The solution should not be a minimal code snippet. All parts of the task should be covered in the generated code.
    """

    if data:
        prompt += f"""
        ### Data:
        {data}
        """

    prompt += """
    The output format should be as follows:
    ```json
    {
      "code": "Generated Python Code"
    }
    ```

    Please ensure that the AI model generates code that addresses all the points mentioned above and produces a solution that can run smoothly without issues.
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
    code = code_data.get("code", "").replace('python', '').strip()

    # 清理代码
    cleaned_code = clean_code(code)

    print(json.dumps({"code": cleaned_code}))

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
