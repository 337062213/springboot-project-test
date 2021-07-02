 package com.springboot.test.model.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.springboot.test.model.po.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString()
@NoArgsConstructor()
@AllArgsConstructor()
@XmlRootElement(name = "UserResponse")
public class UserResponse {
     
    @Getter @Setter private User user;
     
    @Getter @Setter private long timeStamp;

}
