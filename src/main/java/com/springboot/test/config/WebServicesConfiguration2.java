 package com.springboot.test.config;

 import com.springboot.test.service.BillService;
 import org.apache.cxf.Bus;
 import org.apache.cxf.bus.spring.SpringBus;
 import org.apache.cxf.jaxws.EndpointImpl;
 import org.apache.cxf.transport.servlet.CXFServlet;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.boot.web.servlet.ServletRegistrationBean;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import javax.xml.ws.Endpoint;
  
 @Configuration
 public class WebServicesConfiguration2 {
     
    @Autowired
    private Bus bus;
    @Autowired
    BillService billService;
  
    /**
     * http://localhost:8081/api/webservice/test3?wsdl
     * 此方法作用是改变项目中服务名的前缀名，此处127.0.0.1或者localhost不能访问时，请使用ipconfig查看本机ip来访问
     * 此方法被注释后:wsdl访问地址为http://127.0.0.1:8080/services/user?wsdl
     * 去掉注释后：wsdl访问地址为：http://127.0.0.1:8080/soap/user?wsdl
     *
     * @return ServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean disServlet() {
       ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new CXFServlet(), "/webservice/*");
       return servletRegistrationBean;
    }
    
    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
       return new SpringBus();
    }
  
    /** JAX-WS
     * 站点服务
     * **/
    @Bean
    public Endpoint endpoint() {
       EndpointImpl endpoint = new EndpointImpl(bus, billService);
       endpoint.publish("/test3");
       return endpoint;
    }
 }