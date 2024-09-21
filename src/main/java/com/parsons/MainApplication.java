package com.parsons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.parsons.aigeneration.AiDriver;
import com.parsons.ide.IDEDriver;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);

        // 启动 AI_Generation 模块
        AiDriver.main(args);

        // 启动 IDE 模块
        IDEDriver.main(args);

        // 启动 UserDatabase 模块
        DatabaseApp.main(args);
    }
}
