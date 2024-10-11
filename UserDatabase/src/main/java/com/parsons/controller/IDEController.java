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
import com.parsons.pojo.SubmitRequest;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class IDEController {

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
        String directoryPath = currentDir + "/IDE/src/tmp";
        String scriptName = "code.py";

        PythonFileWriter writer = new PythonFileWriter();
        DockerExecutor executor = new DockerExecutor("rita6667/gemini-app:latest");
        PythonFileExecutor pythonExecutor = new PythonFileExecutor(writer, executor);
        JSONObject result = pythonExecutor.executePythonCode(pythonCode, directoryPath, scriptName);

        return result.toString(); // return execution result
    }
}



