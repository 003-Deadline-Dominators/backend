package com.parsons.aigeneration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class PythonCodeGenerator {
    private String topic;
    private String scenario;
    private String task;
    private String data;

    // 构造函数
    public PythonCodeGenerator(String topic, String scenario, String task, String data) {
        this.topic = topic;
        this.scenario = scenario;
        this.task = task;
        this.data = data;
    }

    // 生成代码的方法
    public JSONObject generateCode() {
        List<String> command = new ArrayList<>();
        command.add("docker");
        command.add("run");
        command.add("--platform");
        command.add("linux/arm64");
        command.add("--rm");
        command.add("rita6667/gemini-app:latest");  // Docker 镜像名
        command.add("src/model/Generate_Code.py");
        command.add(topic);
        command.add(scenario);
        command.add(task);
        if (data != null && !data.isEmpty()) {
            command.add(data);
        }

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

            // Parse the JSON response from the Python script
            JSONObject json = new JSONObject(output.toString());
            String importsAndData = json.getString("import and data define");
            String code = json.getString("code");

            // Split the code into lines and convert it into a JSONArray
            JSONArray codeArray = new JSONArray();
            String[] codeLines = code.split("\n");
            for (String codeLine : codeLines) {
                codeArray.put(codeLine);
            }

            // Create the final JSON result with both sections
            JSONArray dataArray = new JSONArray();
            JSONObject result = new JSONObject();
            String[] datalines = importsAndData.split("\n");
            for (String dataline : datalines) {
                dataArray.put(dataline);
            }
            result.put("code", codeArray);
            result.put("data", dataArray);

            return result;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
