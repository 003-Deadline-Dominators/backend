import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class PythonProblemGenerator {
    private String topic;
    private String context;

    // 构造函数
    public PythonProblemGenerator(String topic, String context) {
        this.topic = topic;
        this.context = context;
    }

    // 生成问题的方法
    public JSONObject generateProblem() {
        List<String> command = new ArrayList<>();
        command.add("ai/bin/python");  // 可能需要替换为 'python3' 或完整的解释器路径
        command.add("src/model/Generate_Question.py");  // 替换为Python脚本的实际路径
        command.add(topic);
        command.add(context);

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

            // 解析输出的JSON数据
            JSONObject json = new JSONObject(output.toString());
            //String problem = json.getString("problem");

            // 等待进程结束
            process.waitFor();

            return json;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
