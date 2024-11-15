package org.example.module1;

import java.sql.SQLOutput;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;

class animals{
    public animals() {

    }
}
class cat extends animals{
    cat() {
        System.out.println("我是一只小猫");
    }
}

class dog extends animals{
    dog() {
        System.out.println("我是一只小狗 ");
    }
}
public class circle {
    public static void main(String[] args) {
        Object a=new cat();
        cat b=new cat();
        cat c = (cat)a;
        dog d = (dog)b;


    }


}
