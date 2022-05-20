package org.jxch.capital.breath.a.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "org.jxch.capital.breath.a.mapper")
public class MybatisConfig {
}
