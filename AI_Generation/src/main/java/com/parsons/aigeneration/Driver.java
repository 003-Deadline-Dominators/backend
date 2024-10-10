package com.parsons.aigeneration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Driver {
    public static void main(String[] args) {
        // Define your variables
        String topic = "Linear Regression";
        String context = "Forecasting Demand Based on Economic Indicators";

        // Instantiate the problem generator
        PythonProblemGenerator problemGenerator = new PythonProblemGenerator(topic, context);

        JSONObject problemDetails = problemGenerator.generateProblem();

        if (problemDetails == null) {
            System.out.println("Failed to generate problem details.");
            return;
        }

        String scenario = problemDetails.getString("scenario");
        String task = problemDetails.getString("task");

        // Extract and store the data internally
        String data = problemDetails.optString("data").replace("python", "").strip();

        // If topic is "read/write csv files", set the "data" field in problemDetails to null
        if ("read/write csv files".equals(topic)) {
            problemDetails.put("data", JSONObject.NULL);
        }

        // Print the problem details
        System.out.println("Scenario: " + scenario);
        System.out.println("Task: " + task);
        System.out.println("Data: " + problemDetails.optString("data"));

        // Instantiate the code generator with the internal data
        PythonCodeGenerator codeGenerator = new PythonCodeGenerator(scenario, task, data);
        JSONObject generatedCode = codeGenerator.generateCode();

        if (generatedCode == null) {
            System.out.println("Failed to generate code.");
            return;
        }

        // Check if the "import and data define" key exists before attempting to print it
        if (generatedCode.has("import and data define")) {
            String importAndDataDefine = generatedCode.getString("import and data define");
            System.out.println("Imports and Data Definition:");
            System.out.println(importAndDataDefine);
        } else {
            System.out.println("\"import and data define\" not found in the generated code.");
        }

        // Check if the "code" key exists before attempting to print it
        if (generatedCode.has("code")) {
            JSONArray codeArray = generatedCode.getJSONArray("code");
            System.out.println("\nGenerated Code:");
            for (int i = 0; i < codeArray.length(); i++) {
                System.out.println(codeArray.getString(i));
            }
        } else {
            System.out.println("\"code\" not found in the generated code.");
        }


        // Extract the generated code from the JSONArray and join it into a single string
        List<String> codeLines = new ArrayList<>();
        if (generatedCode.has("code")) {
            JSONArray codeArray = generatedCode.getJSONArray("code");
            for (int i = 0; i < codeArray.length(); i++) {
                codeLines.add(codeArray.getString(i));  // Ensure each line is a string
            }

            // Join the lines into a complete code string
            String originalCode = String.join("\n", codeLines);

            // Instantiate the hint generator with the original code
            PythonHintsGenerator hintGenerator = new PythonHintsGenerator(scenario, task, originalCode);
            JSONObject generatedHint = hintGenerator.generateHint();

            if (generatedHint == null) {
                System.out.println("Failed to generate hint.");
                return;
            }

            // Print the generated hint
            System.out.println("Generated Hint:");
            System.out.println(generatedHint.toString(4));  // Pretty-print the hint with indentation
        }

        // Optionally, write the output to a JSON file (uncomment if needed)
        /*
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("generated_code.json"))) {
            writer.write(generatedCode.toString(4));  // Format JSON output with 4-space indentation
            System.out.println("Generated code has been written to 'generated_code.json'");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("generated_hint.json"))) {
            writer.write(generatedHint.toString(4));  // Format JSON output with 4-space indentation
            System.out.println("Generated hint has been written to 'generated_hint.json'");
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }
}
