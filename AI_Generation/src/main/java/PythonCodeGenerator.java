import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
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
    public JSONObject generateCode() {
        List<String> command = new ArrayList<>();
        command.add("docker");
        command.add("run");
        command.add("--rm");
        command.add("gemini-python-app");  // Docker 镜像名
        command.add("src/model/Generate_Code.py");
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

            JSONArray codeArray = new JSONArray();
            String[] lines = code.split("\n");
            for (String codeLine : lines) {
                codeArray.put(codeLine);
            }

            JSONObject result = new JSONObject();
            result.put("code", codeArray);

            return result;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

}
