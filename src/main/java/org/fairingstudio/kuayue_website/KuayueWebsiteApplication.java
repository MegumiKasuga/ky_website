package org.fairingstudio.kuayue_website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class KuayueWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(KuayueWebsiteApplication.class, args);
    }

}

/*
    SpringBoot 注解
    1. @SpringBootApplication 注解，启动 SpringBoot 项目，该类称为主启动类
        - @SpringBootConfiguration 和 @Configuration 注解一致，有该配置的类就可以当配置文件用，使用 @Bean 注解声明对象并注入容器
        - @EnableAutoConfiguration 启用默认配置，可以把 java 文件配置好并注入到容器中
        - @ComponentScan 通过它找到注解，创建对象，给属性赋值等，默认扫描它所在的类的包和子包中的类

    2. @Component 定义一个 Component
 */
