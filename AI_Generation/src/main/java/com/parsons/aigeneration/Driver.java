package com.parsons.aigeneration;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Driver {
    public static void main(String[] args) {
        // 实例化问题生成器
        PythonProblemGenerator problemGenerator = new PythonProblemGenerator("Linear Regression", "Predicting Sales Based on Advertising Spend");
        JSONObject problemDetails = problemGenerator.generateProblem();
        if (problemDetails == null) {
            System.out.println("Failed to generate problem details.");
            return;
        }

        String scenario = problemDetails.getString("scenario");
        String task = problemDetails.getString("task");

        // 替换掉多余的"python"并清除空白字符
        String data = problemDetails.optString("data").replace("python", "").strip();

        System.out.println("scenario: " + scenario);
        System.out.println("task: " + task);
        System.out.println("data: " + data);

        // 实例化代码生成器
        PythonCodeGenerator codeGenerator = new PythonCodeGenerator(scenario, task, data);
        JSONObject generatedCode = codeGenerator.generateCode();

        if (generatedCode == null) {
            System.out.println("Failed to generate code.");
            return;
        }

        // 打印生成的代码
        System.out.println(generatedCode);
//        for (String line : generatedCode) {
//            System.out.println(line);
//        }

        // 创建一个JSON对象来存储结果
//        JSONObject outputJson = new JSONObject();
//        outputJson.put("scenario", scenario);
//        outputJson.put("task", task);
//        outputJson.put("data", data);
//
//        // 将代码行存入JSONArray中
//        JSONArray codeArray = new JSONArray(generatedCode);
//        outputJson.put("code", codeArray);
//        System.out.println((outputJson));
//
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter("generated_code.json"))) {
//            writer.write(outputJson.toString(4));  // 格式化JSON输出，缩进4个空格
//            System.out.println("Generated code has been written to 'generated_code.json'");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
