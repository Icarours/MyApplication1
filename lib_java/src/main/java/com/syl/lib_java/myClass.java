package com.syl.lib_java;

import com.syl.lib_java.observer.Student1;
import com.syl.lib_java.observer.Student2;
import com.syl.lib_java.observer.Student3;
import com.syl.lib_java.observer.Student4;
import com.syl.lib_java.observer.Teacher;

import org.junit.Test;

public class myClass {
    /**
     * 测试观察者和被观察者
     */
    @Test
    public void test1(){
        Teacher teacher = new Teacher();
        teacher.addObserver(new Student1());
        teacher.addObserver(new Student2());
        teacher.addObserver(new Student3());
        teacher.addObserver(new Student4());
        teacher.publishMessage("2018年8月4日19:10:55");
    }

}
