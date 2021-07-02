package com.springboot.test.config;

import javax.xml.ws.Endpoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import com.spring4all.swagger.EnableSwagger2Doc;
import com.springboot.test.service.impl.BillServiceImpl;

@EnableSwagger2Doc
@ComponentScan(value="com.springboot.test.*")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, TransactionAutoConfiguration.class})
//@MapperScan(value="com.springboot.test.mapper")
@EnableConfigurationProperties
public class TestApplication extends SpringBootServletInitializer {
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TestApplication.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
		String url1 = "http://localhost:8084/webservice/test1?wsdl";
        Endpoint.publish(url1,new BillServiceImpl());
        System.out.println("启动并发布webservice test1 远程服务，服务发布成功....");
	}

}
