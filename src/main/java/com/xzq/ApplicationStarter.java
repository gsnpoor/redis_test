package com.xzq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
/**
 * @Author
 * @Date 2022/3/14 14:02
 * @Version 1.0
 * <p>@Description</p>
 */
@ComponentScan("com.xzq.*")
@SpringBootApplication
public class ApplicationStarter {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class,args);
    }
}
