package com.parsons.ide;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PythonFileWriter {

    // Method to save Python code as a .py file
    public String savePythonScript(String pythonCode, String filePath) throws IOException {
        File file = new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write(pythonCode);
        }
        return filePath; // Return the file path of the saved Python script
    }
}
