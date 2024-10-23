package com.parsons.controller;

import com.parsons.aigeneration.PythonHintsGenerator;
import com.parsons.aigeneration.PythonCodeGenerator;
import com.parsons.aigeneration.PythonProblemGenerator;
import com.parsons.ide.DockerExecutor;
import com.parsons.ide.PythonFileExecutor;
import com.parsons.ide.PythonFileWriter;
import com.parsons.pojo.SubmitRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@CrossOrigin(origins = "http://54.252.5.239")
public class AIController {

    // 类级别变量，用于存储 problemDetails 生成的内容
    private String scenario = "";
    private String task = "";
    private String data = "";
    private String pythonCode = ""; // 存储生成的 Python 代码
    private JSONObject correctOutput = new JSONObject(); // 存储正确的输出

    @GetMapping("/problem")
    public String getProblemDetails(@RequestParam String variable1, @RequestParam String variable2) {
        while (true) { // Start the retry loop
            try {
                // 初始化问题生成器
                PythonProblemGenerator problemGenerator = new PythonProblemGenerator(variable1, variable2);
                JSONObject problemDetails = problemGenerator.generateProblem();  // Generate problem

                // 检查生成的问题是否为空或无效
                if (problemDetails == null || problemDetails.isEmpty()) {
                    return "Failed to generate problem details: Empty or invalid response.";
                }

                // 保存 scenario, task 和 data 到类级别变量中
                scenario = problemDetails.optString("scenario", "");
                task = problemDetails.optString("task", "");
                data = problemDetails.optString("data", "").replace("python", "").strip();

                System.out.println("generated scenario is: " + scenario);
                System.out.println("generated task is: " + task);
                System.out.println("generated data is: " + data);

                // 如果 variable1 是 "read/write csv files"，将 data 设置为 null
                if ("read/write csv files".equals(variable1)) {
                    problemDetails.put("data", JSONObject.NULL);
                }

                // 返回问题的 JSON 字符串
                return problemDetails.toString();

            } catch (JSONException e) {
                System.out.println("JSON error encountered: " + e.getMessage());
                // Retry automatically
            } catch (Exception e) {
                System.out.println("Error generating problem: " + e.getMessage());
                return "Failed to generate problem details: " + e.getMessage();
            }
        }
    }


    @GetMapping("/code")
    public String getCode() {
        // Initialize StringBuilder
        StringBuilder formattedContent = new StringBuilder();
        StringBuilder importAndDataDefine = new StringBuilder();

        System.out.println("Using scenario: " + scenario);
        System.out.println("Using task: " + task);
        System.out.println("Using data: " + data);

        JSONObject generatedCode = null;
        JSONObject result = null;

        // Repeat the generation process until valid stdout is obtained
        while (true) {
            try {
                // Generate AI code using the scenario, task, and data
                PythonCodeGenerator codeGenerator = new PythonCodeGenerator(scenario, task, data);
                generatedCode = codeGenerator.generateCode();  // Retry if JSONException occurs

                // Check if the generated code is null or empty
                if (generatedCode == null || generatedCode.isEmpty()) {
                    return "Failed to generate valid code.";
                }

                // Clear StringBuilder to prevent content accumulation
                formattedContent.setLength(0);
                importAndDataDefine.setLength(0);

                // Process the 'data' part
                if (generatedCode.has("data")) {
                    JSONArray dataArray = generatedCode.getJSONArray("data");
                    for (int i = 0; i < dataArray.length(); i++) {
                        importAndDataDefine.append(dataArray.getString(i)).append("\n");
                    }
                }

                // Append the 'data' part to the formattedContent
                formattedContent.append(importAndDataDefine);

                // Process the 'code' part
                if (generatedCode.has("code")) {
                    JSONArray codeArray = generatedCode.getJSONArray("code");
                    for (int i = 0; i < codeArray.length(); i++) {
                        formattedContent.append(codeArray.getString(i)).append("\n");
                    }
                }

                // Merge 'data' and 'code' into one Python script
                pythonCode = formattedContent.toString().trim();
                System.out.println("Generated Python Code:\n" + pythonCode);

                // Execute the generated Python code and capture exceptions
                String currentDir = System.getProperty("user.dir");
                String directoryPath = currentDir + "/IDE/src/tmp";
                String scriptName = "code.py";

                PythonFileWriter writer = new PythonFileWriter();
                DockerExecutor executor = new DockerExecutor("rita6667/gemini-app:latest");

                PythonFileExecutor pythonExecutor = new PythonFileExecutor(writer, executor);
                result = pythonExecutor.executePythonCode(pythonCode, directoryPath, scriptName);

                // Print execution result
                System.out.println("Execution Result:\n" + result.toString());

                // If stdout is not empty, return the generated code
                if (!Objects.equals(result.get("stdout").toString(), "")) {
                    correctOutput = result;
                    return generatedCode.toString();
                }

                // If stdout is empty, restart the generation process
                System.out.println("Empty stdout, retrying code generation...");

            } catch (JSONException e) {
                System.out.println("JSON error encountered: " + e.getMessage());
                // Retry the logic
            } catch (Exception exp) {
                System.out.println("Error executing Python code: " + exp.getMessage());
                return "Error executing Python code: " + exp.getMessage();
            }
        }
    }


    @GetMapping("/hint")
    public String getHint() {
        while (true) { // Start the retry loop
            try {
                // 使用 scenario, task 和 pythonCode 生成提示
                PythonHintsGenerator hintGenerator = new PythonHintsGenerator(scenario, task, pythonCode);
                JSONObject generatedHint = hintGenerator.generateHint();  // Generate hint

                // 打印生成的提示
                System.out.println(generatedHint.toString());

                // 返回提示 JSON 字符串
                return generatedHint.toString();

            } catch (JSONException e) {
                System.out.println("JSON error encountered: " + e.getMessage());
                // Retry the logic automatically
            } catch (Exception e) {
                System.out.println("Error generating hint: " + e.getMessage());
                return "Failed to generate hint: " + e.getMessage();
            }
        }
    }


    @PostMapping("/submit")
    public String handleSubmit(@RequestBody SubmitRequest request) {
        System.out.println("Received data: " + request.getPreDefine() + request.getRequestBody());

        JSONArray list1 = new JSONArray(request.getPreDefine());
        JSONArray list2 = new JSONArray(request.getRequestBody());

        StringBuilder formattedContent = new StringBuilder();
        for (int j = 0; j < list1.length(); j++) {
            String content = list1.getString(j);
            formattedContent.append(content).append("\n");
        }
        for (int i = 0; i < list2.length(); i++) {
            JSONObject item = list2.getJSONObject(i);
            String content = item.getString("content");
            int position = item.getInt("position");

            for (int j = 0; j < position; j++) {
                formattedContent.append(" ");  // indentation
            }
            formattedContent.append(content).append("\n");
        }

        System.out.println(formattedContent);
        String pythonCode = formattedContent.toString().trim();
        String currentDir = System.getProperty("user.dir");
        String directoryPath = currentDir + "IDE/src/tmp";
        String scriptName = "code.py";

        PythonFileWriter writer = new PythonFileWriter();
        DockerExecutor executor = new DockerExecutor("rita6667/gemini-app:latest");
        PythonFileExecutor pythonExecutor = new PythonFileExecutor(writer, executor);
        JSONObject result = pythonExecutor.executePythonCode(pythonCode, directoryPath, scriptName);
        if (result.get("stdout") == correctOutput.get("stdout")) {
            result.append("correct", true);
        } else {
            result.append("correct", false);
        }
        return result.toString(); // return execution result
    }
}
