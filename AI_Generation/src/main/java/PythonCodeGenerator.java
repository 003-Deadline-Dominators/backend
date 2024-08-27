import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class PythonCodeGenerator {
    private String scenario;
    private String task;
    private String data;

    // 构造函数
    public PythonCodeGenerator(String scenario, String task, String data) {
        this.scenario = scenario;
        this.task = task;
        this.data = data;
    }

    // 生成代码的方法
    public String generateCode() {
        // 构建命令行参数列表
        List<String> command = new ArrayList<>();
        command.add("C:\\Users\\61415\\Downloads\\neat download\\backend\\AI_Generation\\AI\\lib\\python3.12");  // 可能需要替换为 'python3' 或完整的解释器路径
        command.add("src/model/Generate_Code.py");  // 替换为Python脚本的实际路径
        command.add(scenario);
        command.add(task);
        if (data != null && !data.isEmpty()) {
            command.add(data);
        }

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        try {
            Process process = processBuilder.start();

            // 读取脚本的标准输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }
            process.waitFor();

            JSONObject json = new JSONObject(output.toString());
            String code = json.getString("code");

            return code;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

}
