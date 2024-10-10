package com.parsons.controller;
import com.parsons.aigeneration.PythonHintsGenerator;
import com.parsons.ide.DockerExecutor;
import com.parsons.ide.PythonFileExecutor;
import com.parsons.ide.PythonFileWriter;
import org.json.JSONArray;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.parsons.aigeneration.PythonCodeGenerator;
import com.parsons.aigeneration.PythonProblemGenerator;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class ProblemController {
    private String data = "";
    private String scenario = "";
    private String task = "";
    private String code = "";

    @GetMapping("/problem")
    public String getProblemDetails(@RequestParam String variable1, @RequestParam String variable2) {
        PythonProblemGenerator problemGenerator = new PythonProblemGenerator(variable1, variable2);
        JSONObject problemDetails = problemGenerator.generateProblem();

        if (problemDetails == null) {
            return "Failed to generate problem details.";
        }
        scenario = problemDetails.getString("scenario");
        task = problemDetails.getString("task");
        // Store the data internally
        data = problemDetails.optString("data").replace("python", "").strip();

        // If variable1 is "read/write csv files", set the "data" field in problemDetails to null
        if ("read/write csv files".equals(variable1)) {
            problemDetails.put("data", JSONObject.NULL);
        }

        return problemDetails.toString(); // This returns a JSON response without the data
    }

    @GetMapping("/code")
    public String getCode(@RequestParam String variable1, @RequestParam String variable2) {
        while (true) {
            PythonCodeGenerator codeGenerator = new PythonCodeGenerator(variable1, variable2, data);
            JSONObject generatedCode = codeGenerator.generateCode();
            JSONArray list2 = generatedCode.getJSONArray("code");
            JSONArray data = generatedCode.getJSONArray("data");
            JSONArray modifiedCode = new JSONArray();
            StringBuilder formattedContent = new StringBuilder();
            for(int j = 0; j<data.length(); j++) {
                formattedContent.append(data.getString(j)).append("\n");
            }

            for (int i = 0; i < list2.length(); i++) {
                formattedContent.append(list2.getString(i)).append("\n");
                String codeLine = list2.getString(i);
                // Use regex to remove leading spaces in multiples of 4
                String trimmedCodeLine = codeLine.replaceAll("^(\\s{4})+", "");
                // Add the trimmed code line to the modifiedCode array
                modifiedCode.put(trimmedCodeLine);
            }
            generatedCode.put("code", modifiedCode);
            code = formattedContent.toString();
            System.out.println(formattedContent);

            try {
                String pythonCode = formattedContent.toString().trim();
                String currentDir = System.getProperty("user.dir");
                String directoryPath = currentDir + "/IDE/src/tmp";
                String scriptName = "code.py";

                PythonFileWriter writer = new PythonFileWriter();
                DockerExecutor executor = new DockerExecutor("rita6667/gemini-app:latest");

                PythonFileExecutor pythonExecutor = new PythonFileExecutor(writer, executor);
                JSONObject result = pythonExecutor.executePythonCode(pythonCode, directoryPath, scriptName);
                System.out.println(result.toString());
                if (result.get("stdout") != "") {
                    return generatedCode.toString();
                }
            } catch (Exception exp) {
                System.out.println("Error occured: "
                        + exp.getMessage());
            }
        }
    }
    @GetMapping("hint")
    public String getHint() {
        PythonHintsGenerator hintGenerator = new PythonHintsGenerator(scenario, task, code);
        JSONObject generatedHint = hintGenerator.generateHint();
        System.out.println(generatedHint.toString());
        return generatedHint.toString();
    }

}
