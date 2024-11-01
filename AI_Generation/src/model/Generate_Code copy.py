import sys
import google.generativeai as genai
import os
from dotenv import load_dotenv, find_dotenv
import json
import re  # 添加 re 库来处理正则表达式

# 加载API密钥
_ = load_dotenv(find_dotenv())
genai.configure(api_key=os.getenv('GEMINI_API_KEY'))

model = genai.GenerativeModel('gemini-1.5-flash')

def generate_code_prompt(topic, scenario, task, data=None):
    prompt = f"""
    You are tasked with generating Python code to solve the following problem.

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

    prompt += """
    Ensure that the generated Python code meets the following criteria:

    1. **Complete Solution:** The code must fully address all parts of the task. Partial solutions or minimal code snippets are not acceptable. The solution must be comprehensive and cover any necessary data processing, analysis, and final outputs (e.g., print output)
    2. **Correctness:** The code should be error-free and capable of running successfully on the provided dataset (or a similar dataset structure).
    3. **Structured Code:** The solution must include:
        - All import statements must be placed at the very top.
        - Steps for loading and processing the data (if applicable).  
        - Any intermediate calculations or transformations needed to meet the task requirements.
        - Final calculations or results that directly solve the task.
    4. **Modular Approach:** Where applicable, break the solution into functions or logical blocks to enhance readability and reusability.
    5. **Libraries:** If external libraries (like pandas, NumPy, etc.) are required, import them at the beginning of the code and explain their use where necessary.
    6. **Procedural Structure**: Avoid object-oriented programming (OOP) patterns. Write the code in a clear, structured, and procedural style.
    6. **Output:** Ensure the code includes appropriate print statements or return values to display the final results clearly.
    7. **Robustness:** Handle potential errors, such as missing or malformed data, if they are likely to occur in the dataset.
    8. **Readable Output:** The code should display or return the final results in a human-readable format, such as printing the top 5 best-selling products in a clean table or returning the total revenue by category in a sorted list.
    9. **No Partial Code:** The solution should not be a minimal code snippet. All parts of the task should be covered in the generated code.
    10. **Filename Consistency**: Use the exact filenames specified in the scenario, task, or data sections when reading from or writing to files.
    11. **Data Consistency**: If data is provided, that data should be used in solution.
    11. **Clear Separation of Sections**: The code must be divided into two distinct sections:
        - **Imports and Data Definition**: All import statements must be placed at the very top, followed by any predefined data (e.g., lists or dictionaries).
        - **Code**: The rest of the code should follow, including data loading (such as converting predefined data to a DataFrame) and the main logic required to solve the task.

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

def extract_code_content(code_json_str):
    cleaned_str = code_json_str.replace("```json", "").replace("```", "").strip()
    code_data = json.loads(cleaned_str)
    code = code_data.get("code", "")

    cleaned_code = code.replace('python', '').strip()
    cleaned_code = re.sub(r'#.*', '', cleaned_code)
    cleaned_code = re.sub(r'(\'\'\'[\s\S]*?\'\'\'|"""[\s\S]*?""")', '', cleaned_code)
    cleaned_code = re.sub(r'\s+', ' ', cleaned_code).strip()
    print(cleaned_code)
    # print(json.dumps({"code": cleaned_code}))

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
