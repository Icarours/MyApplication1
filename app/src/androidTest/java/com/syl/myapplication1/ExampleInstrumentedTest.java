package com.syl.myapplication1;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.syl.myapplication1.db.dao.StudentDao;
import com.syl.myapplication1.domain.Student;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private Context mContext;

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        mContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.syl.myapplication1", mContext.getPackageName());
        //获得sd卡的内存大小
    }
    @Test
    public void testInsert(){

        StudentDao sdao = new StudentDao(mContext);

        for (int i = 0; i < 500; i++) {
            Student student = new Student();
            student.setName("Lina"+1);
            if (i%2==0){
                student.setGender("男");
            }
            student.setGender("女");
            sdao.addStudent(student);
        }

    }
}
