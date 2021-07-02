package com.springboot.test.model.enums;

public class EnumTest {  
	
    public static void main(String[] args) {  
        forEnum();  
        useEnumInJava();  
    }  
  
    private static void forEnum() {  
        for (SeasonsEnum simpleEnum : SeasonsEnum.values()) {  
            System.out.println(simpleEnum + "  ordinal  " + simpleEnum.ordinal());  
        }  
        System.out.println("------------------");  
        for (TYPE type : TYPE.values()) {  
            System.out.println("type = " + type + "    type.name = " + type.name() + "   typeName = " + type.getTypeName() + "   ordinal = " + type.ordinal());  
        }  
    }  
  
    private static void useEnumInJava() {  
        String typeName = "f5";  
        TYPE type = TYPE.fromTypeName(typeName);  
        if (TYPE.BALANCE.equals(type)) {  
            System.out.println("根据字符串获得的枚举类型实例跟枚举常量一致");  
        } else {  
            System.out.println("大师兄代码错误");  
        }  
  
    }  
 
    private enum SeasonsEnum {  
        SPRING,  
        SUMMER,  
        AUTUMN,  
        WINTER  
    }  

    private enum TYPE {      	
        FIREWALL("firewall"),  
        SECRET("secretMac"),  
        BALANCE("f5");    
        private String typeName;   
        TYPE(String typeName) {  
            this.typeName = typeName;  
        }                 
        public static TYPE fromTypeName(String typeName) {  
            for (TYPE type : TYPE.values()) {  
                if (type.getTypeName().equals(typeName)) {  
                    return type;  
                }  
            }  
            return null;  
        }  
  
        public String getTypeName() {  
            return this.typeName;  
        }  
    }    
}  
