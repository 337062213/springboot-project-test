 package com.springboot.test.test;

import java.util.HashMap;
import java.util.Map;

public class HashcodeTest {

     public static void main(String[] args) {
         Person person1 = new Person("xiaoming");
         Person person2 = new Person("xiaoming");
         Card card1 = new Card("123456789","xiaoai");
         Card card2 = new Card("123456789","xiaoai");

         Map<Person, Integer> hashMapPerson = new HashMap<>();
         hashMapPerson.put(person1, 1);
         Map<Card, Integer> hashMapCard = new HashMap<>();
         hashMapCard.put(card1, 1);
         
         System.out.println("重写equals-----没有重写hascode");
         System.out.println("person1==person2:"+person1.equals(person2));
         System.out.println("hashMap.containsKey():"+hashMapPerson.containsKey(person2));
         System.out.println("重写equals-----重写hascode");
         System.out.println("card1==card2:"+card1.equals(card2));
         System.out.println("hashMap.containsKey():"+hashMapCard.containsKey(card2));
     }

     static class Person {
         private String name;

         public Person(String name) {
             this.name = name;
         }

         @Override
         public boolean equals(Object obj) {
             if (obj instanceof Person) {
                 Person person = (Person) obj;
                 return name.equals(person.name);
             }
             return false;
         }        
     }
     
     static class Card {
         private String name;
         private String id;
         public Card(String name) {
             this.name = name;
         }       

         public Card(String name, String id) {
            super();
            this.name = name;
            this.id = id;
        }

        @Override
         public boolean equals(Object obj) {
             if (obj instanceof Card) {
                 Card card = (Card) obj;
                 return name.equals(card.name) && id.equals(card.id);
             }
             return false;
         }
         @Override
         public int hashCode() {
             return name.hashCode()+13*id.hashCode();
         }
         
     }
 }
