package com.syl.lib_java.observer;

import java.util.Observable;

/**
 * Created by Bright on 2018/8/4.
 *
 * @Describe 被观察者
 * 观察者和被观察者模式可以起到通知全局的作用
 * @Called
 */

public class Teacher extends Observable {

    /**
     * 发生变化
     * @param message
     */
    public void publishMessage(String message){
        System.out.println(Teacher.class.getSimpleName()+"--"+message);
        setChanged();//标记被观察对象已经改变
        notifyObservers("老师说--"+message);//通知改变
    }
}
