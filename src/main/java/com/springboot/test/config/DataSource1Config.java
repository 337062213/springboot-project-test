 package com.springboot.test.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import com.springboot.test.util.encrypt.JasyptUtils;
import javax.sql.DataSource;
 
@MapperScan(basePackages = "com.springboot.test.mapper.one", sqlSessionTemplateRef  = "test1SqlSessionTemplate")
@Configuration
public class DataSource1Config {
    
    @Autowired
    private Environment env;
    
    @Bean(name = "test1DataSource")
    public DataSource testDataSource() {
        String eusername = env.getProperty("spring.datasource.one.username").substring(4,env.getProperty("spring.datasource.one.username").length()-1);
        String epassword = env.getProperty("spring.datasource.one.password").substring(4,env.getProperty("spring.datasource.one.password").length()-1);
        String username = JasyptUtils.decyptPwd("joe-test-demo", eusername);
        String password = JasyptUtils.decyptPwd("joe-test-demo", epassword);
        return DataSourceBuilder.create().driverClassName(env.getProperty("spring.datasource.one.driver-class-name"))
                                         .url(env.getProperty("spring.datasource.one.jdbc-url"))
                                         .username(username)
                                         .password(password).build();
    }
 
    @Bean(name = "test1SqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("test1DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/one/*.xml"));
        return bean.getObject();
    }
 
    @Bean(name = "test1TransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("test1DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
 
    @Bean(name = "test1SqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("test1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
