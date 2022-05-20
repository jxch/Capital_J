package org.jxch.capital.breath.a;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class CapitalBreathAApp {
    public static void main(String[] args) {
        SpringApplication.run(CapitalBreathAApp.class, args);
    }
}

// todo 重构 等数据采集微服务将数据采集到Oracle数据库后, 就需要将股票采集接口变为从数据库提取, 将Breath数据从MySQL转为存储到Oracle