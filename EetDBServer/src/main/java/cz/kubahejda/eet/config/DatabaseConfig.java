package cz.kubahejda.eet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.sql.DataSource;
import java.security.Key;

/**
 * Created by Kuba on 23.3.2017.
 */

@Configuration
@ComponentScan({"cz.kubahejda.eet.web"})
public class DatabaseConfig {

    @Bean
    public String getKey() {
        return "Bar12345Bar12345";
    }

    @Bean
    public DataSource dataSource() {
        JndiDataSourceLookup lookup = new JndiDataSourceLookup();
        DataSource dataSource = lookup.getDataSource("java:/comp/env/jdbc/eetDBSserver");
        if (dataSource == null)
            throw new ExceptionInInitializerError("Could not load data source");
        return dataSource;
    }

    @Autowired
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }




}
