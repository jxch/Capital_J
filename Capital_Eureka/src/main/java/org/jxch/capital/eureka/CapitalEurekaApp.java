package org.jxch.capital.eureka;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class CapitalEurekaApp {
    public static void main(String[] args) {
        SpringApplication.run(CapitalEurekaApp.class, args);
    }
}
