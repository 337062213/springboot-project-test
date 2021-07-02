package com.springboot.test.controller.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.springboot.test.model.po.User;

 
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MockServletContext.class)
@WebAppConfiguration
public class HelloWorldControllerTest {
    private MockMvc mock;
    @Autowired  
    private WebApplicationContext wac; 
     
    @Before
    public void setUp() throws Exception {
    	this.mock = MockMvcBuilders.webAppContextSetup(wac).build();
    }
 
    @Test
    public void testHome() throws Exception {
    	
    	User user = new User();
    	user.setName("Tom");
    	user.setPassword("1234");
    	ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(user);

        mock.perform(
        	post("/springboot/hello")
	             .contentType(MediaType.APPLICATION_JSON_VALUE)
	             .contentType("application/json")
	             .content(requestJson).param("name", "Tom").param("password", "w4354"))
            .andDo(print())
            .andExpect(status().is4xxClientError());
    }
}
