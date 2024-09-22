package com.parsons.controller;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.parsons.aigeneration.PythonCodeGenerator;
import com.parsons.aigeneration.PythonProblemGenerator;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class ProblemController {
    private String data = "";

    @GetMapping("/problem")
    public String getProblemDetails(@RequestParam String variable1, @RequestParam String variable2) {
        PythonProblemGenerator problemGenerator = new PythonProblemGenerator(variable1, variable2);
        JSONObject problemDetails = problemGenerator.generateProblem();

        if (problemDetails == null) {
            return "Failed to generate problem details.";
        }

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
        PythonCodeGenerator codeGenerator = new PythonCodeGenerator(variable1, variable2, data);
        JSONObject generatedCode = codeGenerator.generateCode();
        return generatedCode.toString();
    }
}
