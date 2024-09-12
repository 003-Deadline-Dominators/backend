import org.json.JSONObject;

public class Driver {

    public static void main(String[] args) {
        // Create instances of PythonFileWriter and DockerExecutor
        PythonFileWriter writer = new PythonFileWriter();
        DockerExecutor executor = new DockerExecutor("rita6667/gemini-app:latest");

        // Create instance of PythonFileExecutor
        PythonFileExecutor pythonExecutor = new PythonFileExecutor(writer, executor);

        // Example Python code as a string with proper indentation
//        String pythonCode =
//                "def greet(name):\n" +
//                        "    if name:\n" +
//                        "        return 'Hello, ' + name + '!'\n" +
//                        "    else:\n" +
//                        "        return 'Hello, World!'\n\n" +
//                        "name = 'Deadline Dominators'\n" +
//                        "print(greet(name))\n";

//        String pythonCode =
//                "def add_numbers(a, b):\n" +
//                        "    return a + b\n\n" +
//                        "num1 = 5\n" +
//                        "num2 = 10\n" +
//                        "result = add_numbers(num1, num2)\n" +
//                        "print('The sum of', num1, 'and', num2, 'is:', result)\n";

        // Missing closing parenthesis
//        String pythonCode =
//                "def greet(name):\n" +
//                        "    if name:\n" +
//                        "        return 'Hello, ' + name + '!'\n" +
//                        "    else:\n" +
//                        "        return 'Hello, World!'\n\n" +
//                        "name = 'ChatGPT'\n" +
//                        "print(greet(name)\n";

        // division zero
        String pythonCode =
                "def divide(a, b):\n" +
                        "    return a / b\n\n" +
                        "num1 = 10\n" +
                        "num2 = 0  # Dividing by zero\n" +
                        "result = divide(num1, num2)\n" +
                        "print('Result:', result)\n";

        // import error
//        String pythonCode =
//                "import non_existent_module\n" +  // Module doesn't exist
//                        "print('This will not run due to the import error.')\n";

        // type error
//        String pythonCode =
//                "def add(a, b):\n" +
//                        "    return a + b\n\n" +
//                        "result = add(10, '5')  # Trying to add a number and a string\n" +
//                        "print('Result:', result)\n";

        // read file error
//        String pythonCode =
//                "def read_file(filepath):\n" +
//                        "    with open(filepath, 'r') as f:\n" +
//                        "        return f.read()\n\n" +
//                        "content = read_file('/app/non_existent_file.txt')  # File doesn't exist\n" +
//                        "print(content)\n";

        // index error
//        String pythonCode =
//                "def get_element(lst, index):\n" +
//                        "    return lst[index]\n\n" +
//                        "my_list = [1, 2, 3]\n" +
//                        "print('Element at index 5:', get_element(my_list, 5))  # Index out of range\n";


        // Define the directory and script name
        String currentDir = System.getProperty("user.dir");
        String directoryPath = currentDir + "/IDE/src/tmp";
        String scriptName = "code.py";  // The script name

        // Execute the Python code and get the result
        JSONObject result = pythonExecutor.executePythonCode(pythonCode, directoryPath, scriptName);

        // Print the result
        if (result != null) {
            System.out.println("Stdout:");
            System.out.println(result.getString("stdout"));

            System.out.println("Stderr:");
            System.out.println(result.getString("stderr"));
        } else {
            System.out.println("Execution failed.");
        }
    }
}
