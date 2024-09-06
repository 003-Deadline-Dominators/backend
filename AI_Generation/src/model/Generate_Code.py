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
    """

    # 如果 data 不为空，则包含 Data 部分
    if data:
        prompt += f"""
        ### Data:
        {data}
        """

    prompt += """
    Please generate a Python code framework that can address the task described above. Ensure that the code includes:

    1. Data processing steps (e.g., loading, cleaning, etc.).
    2. The construction of a linear regression model using appropriate Python libraries (such as scikit-learn).
    3. Fitting the model to the provided data.
    4. A step for analyzing the model's coefficients to determine which factors have the strongest influence on the outcome.
    5. Outputting the model's predictions for the provided data.
    6. Commenting important parts of the code for clarity.

    The output format should be as follows:
    ```json
    {
      "code": "Generated Python Code"
    }
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
