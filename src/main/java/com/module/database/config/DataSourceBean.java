package com.module.database.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


@Configuration
@Component
public class DataSourceBean {

    @Bean
    @Primary
    public DataSource getDataSource() {
        Properties prop = new Properties();

        try {
            InputStream input = new FileInputStream(System.getProperty("user.home") + "/Social Protection Module/database.properties");
            prop.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return DataSourceBuilder
                .create()
                .username(prop.getProperty("spring.datasource.username"))
                .password(prop.getProperty("spring.datasource.password"))
                .url(prop.getProperty("spring.datasource.url"))
                .driverClassName(prop.getProperty("spring.datasource.driver-class-name"))
                .build();
    }
}
