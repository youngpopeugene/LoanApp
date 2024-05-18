package com.youngpopeugene.mainservice.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import jakarta.transaction.SystemException;
import org.postgresql.xa.PGXADataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration
@EnableTransactionManagement
public class TransactionConfig {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Bean(initMethod = "init", destroyMethod = "close")
    public AtomikosDataSourceBean myDataSource() {
        PGXADataSource pgxaDataSource = new PGXADataSource();
        pgxaDataSource.setUrl(url);
        pgxaDataSource.setUser(username);
        pgxaDataSource.setPassword(password);
        AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();
        dataSource.setXaDataSource(pgxaDataSource);
        dataSource.setUniqueResourceName("postgres");
        return dataSource;
    }

    @Bean(destroyMethod = "close", initMethod = "init")
    public UserTransactionManager atomikosTransactionManager() throws SystemException {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        userTransactionManager.setTransactionTimeout(300);
        return userTransactionManager;
    }

    @Bean
    public UserTransactionImp userTransaction() throws SystemException {
        UserTransactionImp userTransaction = new UserTransactionImp();
        userTransaction.setTransactionTimeout(300);
        return userTransaction;
    }

    @Bean(name = "transactionManager")
    public JtaTransactionManager transactionManager(UserTransactionImp userTransaction, UserTransactionManager atomikosTransactionManager) {
        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager(userTransaction, atomikosTransactionManager);
        jtaTransactionManager.setDefaultTimeout(300);
        return jtaTransactionManager;
    }
}