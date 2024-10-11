package com.parsons.controller;

import com.parsons.aigeneration.PythonHintsGenerator;
import com.parsons.aigeneration.PythonCodeGenerator;
import com.parsons.aigeneration.PythonProblemGenerator;
import com.parsons.ide.DockerExecutor;
import com.parsons.ide.PythonFileExecutor;
import com.parsons.ide.PythonFileWriter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class ProblemController {

    // 恢复类级别变量以保存 getProblemDetails() 生成的结果
    private String scenario = "";
    private String task = "";
    private String data = "";
    private String pythonCode = ""; // 存储生成的 Python 代码

    @GetMapping("/problem")
    public String getProblemDetails(@RequestParam String variable1, @RequestParam String variable2) {
        // 初始化问题生成器
        PythonProblemGenerator problemGenerator = new PythonProblemGenerator(variable1, variable2);
        JSONObject problemDetails;

        // 尝试生成问题
        try {
            problemDetails = problemGenerator.generateProblem();
        } catch (Exception e) {
            System.out.println("Error generating problem: " + e.getMessage());
            return "Failed to generate problem details: " + e.getMessage();
        }

        // 检查生成的问题是否为空或无效
        if (problemDetails == null || problemDetails.isEmpty()) {
            return "Failed to generate problem details: Empty or invalid response.";
        }

        // 保存 scenario, task 和 data 到类级别变量中，以便后续请求使用
        scenario = problemDetails.optString("scenario", "");
        task = problemDetails.optString("task", "");
        data = problemDetails.optString("data", "").replace("python", "").strip();

        // 如果 variable1 是 "read/write csv files"，将 data 设置为 null
        if ("read/write csv files".equals(variable1)) {
            problemDetails.put("data", JSONObject.NULL);
        }

        // 返回问题的 JSON 字符串
        return problemDetails.toString();
    }

    @GetMapping("/code")
    public String getCode(@RequestParam String variable1, @RequestParam String variable2) {
        // 初始化 StringBuilder
        StringBuilder formattedContent = new StringBuilder();
        StringBuilder importAndDataDefine = new StringBuilder();

        while (true) {
            // 使用 scenario, task 和 data 生成代码
            PythonCodeGenerator codeGenerator = new PythonCodeGenerator(scenario, task, data);
            JSONObject generatedCode = codeGenerator.generateCode();

            // 清空 StringBuilder 以防止内容叠加
            formattedContent.setLength(0);
            importAndDataDefine.setLength(0);

            // 处理 data 部分
            if (generatedCode.has("data")) {
                JSONArray dataArray = generatedCode.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    importAndDataDefine.append(dataArray.getString(i)).append("\n");
                }
            }

            // 将 data 部分添加到 formattedContent
            formattedContent.append(importAndDataDefine);

            // 处理 code 部分
            if (generatedCode.has("code")) {
                JSONArray codeArray = generatedCode.getJSONArray("code");
                for (int i = 0; i < codeArray.length(); i++) {
                    formattedContent.append(codeArray.getString(i)).append("\n");
                }
            }

            // 将 data 和 code 合并为一个 Python 脚本
            pythonCode = formattedContent.toString().trim();
            System.out.println("Generated Python Code:\n" + pythonCode);

            try {
                // 定义路径和脚本名
                String currentDir = System.getProperty("user.dir");
                String directoryPath = currentDir + "/IDE/src/tmp";
                String scriptName = "code.py";

                // 使用 PythonFileWriter 和 DockerExecutor 来运行 Python 代码
                PythonFileWriter writer = new PythonFileWriter();
                DockerExecutor executor = new DockerExecutor("rita6667/gemini-app:latest");

                PythonFileExecutor pythonExecutor = new PythonFileExecutor(writer, executor);
                JSONObject result = pythonExecutor.executePythonCode(pythonCode, directoryPath, scriptName);

                // 打印执行结果
                System.out.println("Execution Result:\n" + result.toString());

                // 如果有 stdout 输出，返回生成的代码
                if (!result.get("stdout").toString().isEmpty()) {
                    return generatedCode.toString();
                }
            } catch (Exception exp) {
                System.out.println("Error occurred: " + exp.getMessage());
            }

            // 退出循环的条件
            break;
        }
        return "No valid code generated.";
    }

    @GetMapping("/hint")
    public String getHint() {
        // 使用 scenario, task 和 pythonCode 生成提示
        PythonHintsGenerator hintGenerator = new PythonHintsGenerator(scenario, task, pythonCode);
        JSONObject generatedHint = hintGenerator.generateHint();

        // 打印生成的提示
        System.out.println(generatedHint.toString());

        // 返回提示 JSON 字符串
        return generatedHint.toString();
    }
}
