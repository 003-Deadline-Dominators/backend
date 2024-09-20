package com.parsons.ide;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class DockerExecutor {
    private String dockerImageName;

    // Constructor to set Docker image name
    public DockerExecutor(String dockerImageName) {
        this.dockerImageName = dockerImageName;
    }

    // Method to execute Python file in Docker container
    public JSONObject executePythonFile(String directoryPath, String scriptName) {
        // Convert relative path to absolute path
        File directory = new File(directoryPath);
        String absoluteDirectoryPath = directory.getAbsolutePath();  // Ensure we have an absolute path

        List<String> command = new ArrayList<>();
        command.add("docker");
        command.add("run");
        command.add("--rm");

        // Mount the local directory to the Docker container
        command.add("-v");
        command.add(absoluteDirectoryPath + ":/app"); // Mount the whole directory

        // Use the predefined Docker image
        command.add(dockerImageName);

        // Specify the script to run inside the container
        command.add("/app/" + scriptName);  // Example: /app/code.py

        // Start the process and capture output
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        try {
            Process process = processBuilder.start();

            // Capture standard output (stdout)
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Capture error output (stderr)
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder errorOutput = new StringBuilder();
            while ((line = errorReader.readLine()) != null) {
                errorOutput.append(line).append("\n");
            }

            process.waitFor();

            // Create a JSONObject to store stdout and stderr
            JSONObject result = new JSONObject();
            result.put("stdout", output.toString());
            result.put("stderr", errorOutput.toString());

            return result;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
