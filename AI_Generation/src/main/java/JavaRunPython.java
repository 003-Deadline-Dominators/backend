import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.json.JSONObject;

public class JavaRunPython {
    public static void main(String[] args) {
        Process proc;
        try {
            // 传递参数
            String topic = "Linear Regression";
            String context = "Koala";

            // 你需要确保使用的是正确的 Python 解释器路径和 Python 脚本的绝对路径
            String[] command = new String[]{
                    "ai/bin/python", // 替换为你的 python 解释器路径
                    "src/model/Generate_Question.py", // 替换为你的 Python 脚本路径
                    topic,
                    context
            };

            // 调用 Python 脚本
            proc = Runtime.getRuntime().exec(command);

            // 读取 Python 脚本的输出
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                output.append(line);
            }
            in.close();

            // 解析JSON数据
            JSONObject json = new JSONObject(output.toString());
            String scenario = json.getString("scenario");
            String task = json.getString("task");
            String data = json.optString("data", "No data provided");  // 使用optString避免null

            System.out.println("Scenario: " + scenario);
            System.out.println("Task: " + task);
            System.out.println("Data: " + data);

            // 捕获并打印 Python 脚本的错误输出
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                System.err.println("Error: " + line);
            }
            errorReader.close();

            // 等待 Python 脚本执行完毕
            proc.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
