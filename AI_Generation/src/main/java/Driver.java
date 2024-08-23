import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) {
        // 实例化问题生成器
        PythonProblemGenerator problemGenerator = new PythonProblemGenerator("Linear Regression", "Koala");
        JSONObject problemDetails = problemGenerator.generateProblem();
        if (problemDetails == null) {
            System.out.println("Failed to generate problem details.");
            return;
        }

        String scenario = problemDetails.getString("scenario");
        String task = problemDetails.getString("task");
        String data = problemDetails.optString("data");

        System.out.println("scenario:" + scenario);
        System.out.println("task:" + task);
        System.out.println("data:" + data);


        // 实例化代码生成器
        PythonCodeGenerator codeGenerator = new PythonCodeGenerator(scenario, task, data);
        String generatedCode = codeGenerator.generateCode();
        System.out.println("Generated Code:\n" + generatedCode);

        // 将生成的代码写入txt文件
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("generated_code.txt"))) {
            writer.write(generatedCode);
            System.out.println("Generated code has been written to 'generated_code.txt'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
