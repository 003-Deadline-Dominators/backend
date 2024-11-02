import java.io.File;

public class Main {
    public static void main(String[] args) {
        // 获取当前工作目录
        String currentDir = System.getProperty("user.dir");
        System.out.println("当前工作目录: " + currentDir);

        // 创建 File 对象
        File currentDirectory = new File(currentDir);

        // 向上退两层目录
        File parentDir = currentDirectory.getParentFile(); // 第一层父目录
        File grandparentDir = parentDir.getParentFile(); // 第二层父目录

        // 获取最终的父路径
        String finalPath = grandparentDir.getAbsolutePath();
        System.out.println("向上退两层后的路径: " + finalPath);

//        // 检查 tmp 目录是否存在，如果不存在则创建它
//        File tmpDirectory = new File(directoryPath);
//        if (!tmpDirectory.exists()) {
//            boolean created = tmpDirectory.mkdirs(); // 创建目录
//            if (created) {
//                System.out.println("目录成功创建: " + directoryPath);
//            } else {
//                System.out.println("无法创建目录: " + directoryPath);
//            }
//        } else {
//            System.out.println("目录已存在: " + directoryPath);
//        }
    }
}
