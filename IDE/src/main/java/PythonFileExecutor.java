import org.json.JSONObject;
import java.io.IOException;

public class PythonFileExecutor {
    private PythonFileWriter fileWriter;
    private DockerExecutor dockerExecutor;

    // Constructor that accepts a DockerExecutor and a PythonFileWriter
    public PythonFileExecutor(PythonFileWriter fileWriter, DockerExecutor dockerExecutor) {
        this.fileWriter = fileWriter;
        this.dockerExecutor = dockerExecutor;
    }

    // Method to handle the full process of saving the Python code and executing it
    public JSONObject executePythonCode(String pythonCode, String directoryPath, String scriptName) {
        try {
            // Step 1: Save the Python code as a .py file in the provided directory
            String savedFilePath = fileWriter.savePythonScript(pythonCode, directoryPath + "/" + scriptName);

            // Step 2: Execute the Python file in Docker and get the result
            // Pass the directory and script name to DockerExecutor
            return dockerExecutor.executePythonFile(directoryPath, scriptName);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
