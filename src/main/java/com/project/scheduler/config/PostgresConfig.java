package com.project.scheduler.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableTransactionManagement
public class PostgresConfig {

//    private static final Logger logger = LoggerFactory.getLogger("PostgresConfig");


    //    @Value("${spring.datasource.jdbc-url}")
    private String pgURL = "jdbc:postgresql://localhost:5433/postgres?currentSchema=tradingjournal";

    //    @Value("${spring.datasource.username}")
    private String pgUserName = "postgres";

    //    @Value("${spring.datasource.password}")
    private String pgPwd = "1234";

    //    @Value("${spring.datasource.driverClassName}")
    private String driverClassName = "org.postgresql.Driver";

    @Bean(name = "postgre")
    @Primary
    public DataSource dataSource() {
        log.info("pgURL = " + pgURL);
        return DataSourceBuilder.create().type(HikariDataSource.class).url(pgURL).driverClassName(driverClassName).username(pgUserName).password(pgPwd).build();
    }
}