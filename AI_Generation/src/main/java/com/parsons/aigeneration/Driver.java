package com.parsons.aigeneration;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) {
        // Define your variables
        String topic = "read/write csv files";
        String context = "Predicting Sales Based on Advertising Spend";

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

        // If variable1 is "read/write csv files", set the "data" field in problemDetails to null
        if ("read/write csv files".equals(topic)) {
            problemDetails.put("data", JSONObject.NULL);
        }

        // Print the problem details (data will be null if variable1 is "read/write csv files")
        System.out.println("scenario: " + scenario);
        System.out.println("task: " + task);
        System.out.println("data: " + problemDetails.optString("data"));

        System.out.println("Data is:" + data);

        // Instantiate the code generator with the internal data
        PythonCodeGenerator codeGenerator = new PythonCodeGenerator(scenario, task, data);
        JSONObject generatedCode = codeGenerator.generateCode();

        if (generatedCode == null) {
            System.out.println("Failed to generate code.");
            return;
        }

        // Print the generated code
        System.out.println("Generated Code:");
        System.out.println(generatedCode.toString(4)); // Pretty-print with indentation

        // Optionally, write the output to a JSON file (uncomment if needed)
        /*
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("generated_code.json"))) {
            writer.write(generatedCode.toString(4));  // Format JSON output with 4-space indentation
            System.out.println("Generated code has been written to 'generated_code.json'");
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }
}
