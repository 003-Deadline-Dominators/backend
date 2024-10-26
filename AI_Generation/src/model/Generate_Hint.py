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

def generate_hint_prompt(scenario, task, original_code):
    prompt = f"""
    You are an AI assistant tasked with providing helpful hints for solving a Parsons problem.

    ### Scenario:
    {scenario}

    ### Task:
    {task}

    ### Original Code:
    {original_code}

    The user has been presented with scrambled code blocks. Provide only the necessary number of hints that will help the user arrange the blocks in the correct order. Each hint should be short, direct, and focus on key steps or logic in the code, without revealing the exact solution.

    - Focus on logical dependencies, control structures (loops, conditionals), and code flow.
    - Make sure each hint addresses one specific part of the code structure.

    Output the hints in plain text format, not using Markdown:
    Hint 1: <first key hint>
    Hint 2: <second key hint, if needed>
    Hint 3: <third key hint, if needed>
    """

    return prompt

def extract_text_from_response(response):
    return response.candidates[0].content.parts[0].text


def main():
    scenario = sys.argv[1]  # 从命令行参数获取 scenario
    task = sys.argv[2]  # 从命令行参数获取 task
    original_code = sys.argv[3]  # 从命令行参数获取原始代码

    prompt = generate_hint_prompt(scenario, task, original_code)
    response = model.generate_content(prompt)  # 生成AI的回复
    hint = extract_text_from_response(response)  # 提取AI回复内容

    print(json.dumps({"hint": hint}))

if __name__ == "__main__":
    main()
