package com.parsons.controller;
import org.json.JSONArray;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.parsons.ide.PythonFileExecutor;
import com.parsons.ide.PythonFileWriter;
import com.parsons.ide.DockerExecutor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class IDEController {

    @PostMapping("/submit")
    public JSONObject handleSubmit(@RequestBody String requestBody) {
        // Print the request body to the console
        System.out.println("Received data: " + requestBody);
        try {
            JSONObject jsonObject = new JSONObject(requestBody);
            JSONArray blocksArray = jsonObject.getJSONArray("blocks");

            StringBuilder combinedCode = new StringBuilder();
            for (int i = 0; i < blocksArray.length(); i++) {
                combinedCode.append(blocksArray.getString(i)).append("\n");
            }

            String pythonCode = combinedCode.toString().trim();
            String currentDir = System.getProperty("user.dir");
            String directoryPath = currentDir + "/IDE/src/tmp";
            String scriptName = "code.py";  // The script name
            PythonFileWriter writer = new PythonFileWriter();
            DockerExecutor executor = new DockerExecutor("rita6667/gemini-app:latest");

            // Create instance of com.parsons.ide.PythonFileExecutor
            PythonFileExecutor pythonExecutor = new PythonFileExecutor(writer, executor);


            // Execute the Python code and get the result
            JSONObject result = pythonExecutor.executePythonCode(pythonCode, directoryPath, scriptName);

            // Print the result
            if (result != null) {
                System.out.println("Stdout:");
                System.out.println(result.getString("stdout"));

                System.out.println("Stderr:");
                System.out.println(result.getString("stderr"));
            } else {
                System.out.println("Execution failed.");
            }

            // Write the code to a file

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}


