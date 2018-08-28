package com.syl.myapplication1;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void test1(){
        Random random = new Random();
        System.out.println(Math.abs(random.nextInt()));
    }
    @Test
    public void test2(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();
        System.out.println(format.format(System.currentTimeMillis()));
        System.out.println(dateFormat.format(System.currentTimeMillis()));
    }
}