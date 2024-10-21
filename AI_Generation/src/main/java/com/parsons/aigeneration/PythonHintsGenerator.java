package com.parsons.aigeneration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PythonHintsGenerator {
    private String scenario;
    private String task;
    private String originalCode;

    // 构造函数
    public PythonHintsGenerator(String scenario, String task, String originalCode) {
        this.scenario = scenario;
        this.task = task;
        this.originalCode = originalCode;
    }

    // 生成提示的方法
    public JSONObject generateHint() {
        List<String> command = new ArrayList<>();
        command.add("docker");
        command.add("run");
        command.add("--rm");
        command.add("rita6667/gemini-app:latest");  // Docker 镜像名
        command.add("src/model/Generate_Hint.py");  // Python 生成提示的脚本
        command.add(scenario);
        command.add(task);
        command.add(originalCode);

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        try {
            Process process = processBuilder.start();

            // 读取脚本的标准输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }
            process.waitFor();

            JSONObject json = new JSONObject(output.toString());
            String hint = json.getString("hint");
            String[] hintsArray = hint.split("\n");
            JSONArray hintJsonArray = new JSONArray();

            for (String hintPart : hintsArray) {
                hintJsonArray.put(hintPart.trim());  // Trim to remove extra spaces
            }

// Create a new JSONObject to store the JSONArray
            JSONObject result = new JSONObject();
            result.put("hints", hintJsonArray);

            return result;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
