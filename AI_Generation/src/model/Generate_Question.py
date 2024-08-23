import sys
import google.generativeai as genai
import os
from dotenv import load_dotenv, find_dotenv
import json

# 加载API密钥
_ = load_dotenv(find_dotenv())
genai.configure(api_key=os.getenv('GEMINI_API_KEY'))

model = genai.GenerativeModel('gemini-1.5-flash')

def generate_problem_prompt(topic, context):
    prompt = f"""
    You are an AI assistant for a website that generates Parson's problems. Based on the user's selected topic and context, create a complete problem with the following structure:

    1. **Scenario Description**: Provide a brief and clear scenario description that aligns with both the topic and the context.
    2. **Task Description**: Give a detailed task that the user needs to accomplish using the given topic and context. This should be actionable and clear.
    3. **Data (if applicable)**: If the task requires data, provide a sample data set in an easily usable format (e.g., table, list).

    The selected **topic** is: {topic}
    The selected **context** is: {context}

    The output format should be in the following JSON structure for easier extraction:
    ```json
    {{
      "scenario": "Scenario Description",
      "task": "Task Description",
      "data": "Data or null if not applicable"
    }}
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
    topic = sys.argv[1]  # 从命令行参数获取 topic
    context = sys.argv[2]  # 从命令行参数获取 context
    
    prompt = generate_problem_prompt(topic, context)
    response = model.generate_content(prompt)  # 生成AI的回复
    result = extract_text_from_response(response)  # 提取AI回复内容
    
    # problem_content = extract_problem_content(result)
    extract_problem_content(result)
    # print(json.dumps(problem_content))  # 输出JSON

if __name__ == "__main__":
    main()
