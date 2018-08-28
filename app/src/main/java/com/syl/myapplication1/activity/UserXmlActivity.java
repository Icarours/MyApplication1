package com.syl.myapplication1.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Xml;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.syl.myapplication1.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * xml文件的生成和解析,多级结构的xml文件解析参见 NewsActivity
 */
public class UserXmlActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    RadioGroup rgGender;
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_xml);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        rgGender = findViewById(R.id.rg_gender);
        tvContent = findViewById(R.id.tv_content);
        findViewById(R.id.btn_save_xml).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveXml();
            }
        });
        findViewById(R.id.btn_query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query();
            }
        });
    }

    //按照姓名查询用户信息,将信息从xml文件中读出,展示在手机上
    private void query() {
        String username = etUsername.getText().toString();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "按用户名搜索,用户名不能为空..", Toast.LENGTH_SHORT).show();
        }
        //        /data/data/包名/files/qiqi.xml
        File file = new File(getFilesDir(), username + "-info.xml");//用户信息
        InputStream in = null;
        if (file.exists() && file.length() > 0) {//用户信息存在
            try {
                XmlPullParser pullParser = Xml.newPullParser();//获得解析器
                in = new FileInputStream(file);
                pullParser.setInput(in, "UTF-8");//将信息读到内存中
                int eventType = pullParser.getEventType();
                String str = "";
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {//如果是开始标签
                        if ("username".equals(pullParser.getName())) {//pullParser.getName()//获取标签名称
                            String susername = pullParser.nextText();
                            str = str + susername + "-";
                        } else if ("password".equals(pullParser.getName())) {
                            String spassword = pullParser.nextText();
                            str = str + spassword + "-";
                        } else if ("gender".equals(pullParser.getName())) {
                            String sgender = pullParser.nextText();
                            str = str + sgender;
                        }
                    }
                    //要将指针往下挪, 并且赋值给 type, 否则就是 死循环
                    eventType = pullParser.next();//下一行,下一个事件,一行就是一个事件
                }
                tvContent.setText(str);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();//关流
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            Toast.makeText(this, "用户不存在..", Toast.LENGTH_SHORT).show();
        }
    }

    //保存信息到xml文件
    private void saveXml() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "用户名或者密码不能为空..", Toast.LENGTH_SHORT).show();
            return;
        }
        int id = rgGender.getCheckedRadioButtonId();//获取用户选择的id
        String gender;
        switch (id) {
            case R.id.rb_male:
                gender = "男";
                break;
            case R.id.rb_female:
                gender = "女";
                break;
            default:
                gender = "男";
                break;
        }
        /**
         <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
         <student>
         <name city="guangzhou">qiqi</name>
         <number>2</number>
         <sex>female</sex>
         </student>
         */
        //将用户名,密码,性别等信息保存起来.//  /data/data/包名/files/qiqi.xml
        OutputStream out = null;
        try {
            XmlSerializer serializer = Xml.newSerializer();//将信息写出去,保存到指定文件
            File file = new File(getFilesDir(), username + "-info.xml");//将信息写出到指定文件
            out = new FileOutputStream(file);//输出流
            serializer.setOutput(out, "UTF-8");
            serializer.startDocument("UTF-8", true);
            serializer.startTag(null, "user");

            serializer.startTag(null, "username");
            serializer.text(username);
            serializer.endTag(null, "username");

            serializer.startTag(null, "password");
            serializer.text(password);
            serializer.endTag(null, "password");

            serializer.startTag(null, "gender");
            serializer.text(gender);
            serializer.endTag(null, "gender");

            serializer.endTag(null, "user");
            serializer.endDocument();

            Toast.makeText(this, "信息保存成功..", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "信息保存失败..", Toast.LENGTH_SHORT).show();
        } finally {
            if (out != null) {
                try {
                    out.close();//关流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
