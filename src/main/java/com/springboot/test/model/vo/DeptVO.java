 package com.springboot.test.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * department value object 
 * @author EDZ
 * @date 2021/06/22
 */
@ToString()
@NoArgsConstructor()
@AllArgsConstructor()
public class DeptVO { 
    /** id */
    @Getter @Setter private String id;
    /** deptName */
    @Getter @Setter private String deptName; 
    /** just for transfer parameter that is encoded or decoded */
    @Getter @Setter private String encryptJson;   
}
