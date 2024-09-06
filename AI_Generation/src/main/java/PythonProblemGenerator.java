import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
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

    // 调用 Python 脚本生成问题
    public JSONObject generateProblem() {
        List<String> command = new ArrayList<>();
        command.add("docker");
        command.add("run");
        command.add("--rm");
        command.add("gemini-python-app");  // Docker 镜像名
        command.add("src/model/Generate_Question.py");
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
