import unittest
from Generate_Code import generate_problem_prompt

class TestGenerateProblemPrompt(unittest.TestCase):

    def test_generate_prompt_format(self):
        topic = "data visualization"
        context = "analyzing trends in sales data"

        expected_prompt = f"""
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
    """.strip()

        generated_prompt = generate_problem_prompt(topic, context).strip()

        self.assertEqual(generated_prompt, expected_prompt)

if __name__ == '__main__':
    unittest.main()
