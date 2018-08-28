package com.syl.lib_java.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Bright on 2018/8/4.
 *
 * @Describe 观察者
 * 最好不要在观察中使用被观察者对象发布消息
 * @Called
 */

public class Student implements Observer {
    @Override
    public void update(Observable observable, Object o) {
        Teacher teacher = (Teacher) observable;

        String message = (String) o;
        System.out.println(this.getClass().getSimpleName()+"--"+message);
    }
}

