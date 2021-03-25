package bg.codeacademy.PersonalProject.config;

import oracle.jdbc.pool.OracleConnectionPoolDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DataConfig {
  @Bean
  public DataSource dataSource() throws SQLException
  {
    oracle.jdbc.pool.OracleDataSource dataSource = new OracleConnectionPoolDataSource();
    dataSource.setUser("dimitar_stoykov");
    dataSource.setPassword("dbpass");
    dataSource.setURL("jdbc:oracle:thin:@83.228.124.173:6223/dimitar_stoykov");
    dataSource.setImplicitCachingEnabled(true);
    return dataSource;
  }

  @Bean(name = {"txManager", "transactionManager"})
  public PlatformTransactionManager txManager() throws SQLException
  {
    return new DataSourceTransactionManager(dataSource());
  }

  @Bean(name = "namedTemplate")
  @Primary
  public NamedParameterJdbcOperations namedTemplate() throws SQLException
  {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
    jdbcTemplate.setFetchSize(30);


    jdbcTemplate.setMaxRows(50000);

    NamedParameterJdbcTemplate npjt = new NamedParameterJdbcTemplate(jdbcTemplate);
    npjt.setCacheLimit(384);
    return npjt;
  }

}

