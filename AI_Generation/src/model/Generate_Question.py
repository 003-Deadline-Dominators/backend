# -*- coding: utf-8 -*-
import sys
import google.generativeai as genai
import os
from dotenv import load_dotenv, find_dotenv
import json

# 加载API密钥
_ = load_dotenv(find_dotenv())
# 获取API Key
api_key = os.getenv('GEMINI_API_KEY')
# 配置genai
genai.configure(api_key=api_key)


model = genai.GenerativeModel('gemini-1.5-flash')

def generate_problem_prompt(topic, context):
    prompt = f"""
    You are an AI assistant for a website that generates Parsons' problems specifically designed for data science students. Based on the user's selected topic and context, create a realistic problem that can be solved using Python code. The problem should be meaningful and applicable to real-world scenarios.

    1. **Scenario Description**: Provide a brief, realistic scenario that aligns with the selected topic and context. The scenario should reflect a data science challenge, such as analyzing data, implementing algorithms, or performing tasks like visualization, prediction, or classification.
    2. **Task Description**: Clearly outline the task the user needs to accomplish using Python. Ensure the task is actionable and aligns with the scenario, topic, and context.
    3. **Data (if applicable)**:
       - If the task requires data and the selected **topic** is **not** 'read/write csv files', provide the actual data directly in your response in JSON or another suitable format. Do not provide CSV files or reference external files. Ensure the data is not too large.
       - If the selected **topic** is 'read/write csv files', provide the CSV filename and include the data content directly after it, formatted as:
         ```
         filename.csv
         Data content in CSV format (include headers and data rows)
         ```
       Ensure the data is not too large.

    The selected **topic** is: {topic}
    The selected **context** is: {context}

    The output format should be in the following JSON structure for easier extraction:
    ```json
    {{
      "scenario": "Scenario Description",
      "task": "Task Description",
      "data": "Data content in JSON or other format, or CSV filename and data if topic is 'read/write csv files'"
    }}
    ```
    """
    return prompt


def extract_text_from_response(response):
    return response.candidates[0].content.parts[0].text

def extract_problem_content(problem_json_str):
    cleaned_str = problem_json_str.replace("```json", "").replace("```", "").strip()
    problem_data = json.loads(cleaned_str)
    print(problem_data)
    # return problem_data

def main():
    topic = sys.argv[1]
    context = sys.argv[2]
    
    prompt = generate_problem_prompt(topic, context)
    response = model.generate_content(prompt)  # 生成AI的回复
    result = extract_text_from_response(response)  # 提取AI回复内容
    
    # problem_content = extract_problem_content(result)
    extract_problem_content(result)
    # print(json.dumps(problem_content))  # 输出JSON

if __name__ == "__main__":
    main()
