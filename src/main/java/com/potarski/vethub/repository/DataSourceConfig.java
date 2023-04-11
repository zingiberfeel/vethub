package com.potarski.vethub.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;

// Фабрика, которая раздает connections

@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {
    private final DataSource dataSource;

    public Connection getConnection(){
        return DataSourceUtils.getConnection(dataSource);
    }
}
