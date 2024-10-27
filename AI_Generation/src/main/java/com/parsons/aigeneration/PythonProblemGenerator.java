package com.parsons.aigeneration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONObject;

public class PythonProblemGenerator {
    private String topic;
    private String context;

    // Constructor
    public PythonProblemGenerator(String topic, String context) {
        this.topic = topic;
        this.context = context;
    }

    // Getter for topic
    public String getTopic() {
        return topic;
    }

    // Invoke the Python script to generate the problem
    public JSONObject generateProblem() {
        List<String> command = new ArrayList<>();
        command.add("docker");
        command.add("run");
        command.add("--platform");
        command.add("linux/arm64");
        command.add("--rm");
        command.add("rita6667/gemini-app:latest");  // Docker image name
        command.add("src/model/Generate_Question.py");
        command.add(topic);
        command.add(context);

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        try {
            Process process = processBuilder.start();

            // Read the script's standard output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder outputBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                outputBuilder.append(line);
            }

            // Wait for the process to complete
            process.waitFor();

            // Parse the JSON output
            JSONObject json = new JSONObject(outputBuilder.toString());

            // Replace extra "python" and strip whitespace from the 'data' field
            String dataContent = json.optString("data").replace("python", "").strip();

            // Update the 'data' field in the JSON object
            json.put("data", dataContent);

            // Check if the 'data' field contains CSV information
            if (!dataContent.isEmpty()) {
                // Check if the topic is 'read/write csv files'
                if (topic.equalsIgnoreCase("Reading/Writing CSV files")) {
                    // Expected format:
                    // data: filename.csv
                    // CSV data...

                    // Split the data content into lines
                    String[] dataLines = dataContent.split("\n", 2);
                    if (dataLines.length == 2) {
                        String filenameLine = dataLines[0].trim();
                        String csvData = dataLines[1];

                        // Extract the filename
                        String filename;
                        if (filenameLine.startsWith("data:")) {
                            filename = filenameLine.substring(5).trim();
                        } else {
                            filename = filenameLine;
                        }

                        // Save the CSV data to the specified directory, ignoring empty lines
                        saveCsvToFile(filename, csvData);
                        
                    }
                }
            }

            return json;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to save CSV data to a file in the specified directory, ignoring empty lines
    private void saveCsvToFile(String filename, String csvData) {
        // Get the current working directory
        String currentDir = System.getProperty("user.dir");

        // Build the file path
        String filePath = currentDir + "/IDE/src/tmp/" + filename;
        // String filePath = "/Users/yan_g/Desktop/IT_Project/backend/IDE/src/tmp" + filename;


        try (FileWriter writer = new FileWriter(new File(filePath))) {
            // Split the CSV data into lines
            String[] lines = csvData.split("\n");

            // Process each line and write non-empty lines to the file
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    writer.write(line + "\n");
                }
            }

            System.out.println("CSV data saved to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
