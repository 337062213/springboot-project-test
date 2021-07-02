 package com.springboot.test.model.vo;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString()
@NoArgsConstructor()
@AllArgsConstructor()
@XmlRootElement(name = "UserIdRequest")
public class UserIdRequest {
    @Getter 
    @Setter  
    private String userId;
     
    @Getter 
    @Setter 
    private long timeStamp;

}
