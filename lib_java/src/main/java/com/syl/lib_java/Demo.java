package com.syl.lib_java;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Bright on 2018/10/13.
 *
 * @Describe
 * @Called 执行周期性任务,当前时间的格式化
 */
public class Demo {
    public static void main(String[] args) {
        executePeriodicityTask();
    }

    /**
     * 执行周期性的任务
     */
    private static void executePeriodicityTask() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Date date2 = new Date();
                DateFormat dateInstance = SimpleDateFormat.getDateInstance();
                dateInstance.format(date2);
                System.out.println("date=="+date2);
                System.out.println("-----------------------");

                Date date = new Date();
                DateFormat formater = DateFormat.getDateInstance(DateFormat.FULL, Locale.CHINA);
                //中国日期
                String string = formater.format(date);
                System.out.println("中国日期：\t" + string);
                //加拿大日期
                formater = DateFormat.getDateInstance(DateFormat.FULL, Locale.CANADA);
                System.out.println("加拿大日期： \t" + formater.format(date));
                //日本日期
                formater = DateFormat.getDateInstance(DateFormat.FULL, Locale.JAPAN);
                System.out.println("日本日期： \t" + formater.format(date));
                //法国日期
                formater = DateFormat.getDateInstance(DateFormat.FULL, Locale.FRANCE);
                System.out.println("法国日期： \t" + formater.format(date));
                //德国日期
                formater = DateFormat.getDateInstance(DateFormat.FULL, Locale.GERMAN);
                System.out.println("德国日期： \t" + formater.format(date));
                //意大利日期
                formater = DateFormat.getDateInstance(DateFormat.FULL, Locale.ITALY);
                System.out.println("意大利日期： \t" + formater.format(date));

                System.out.println("+++++++++++++++++++++++++++++++");

            }
        }, 0, 2 * 1000, TimeUnit.MILLISECONDS);
    }

}
