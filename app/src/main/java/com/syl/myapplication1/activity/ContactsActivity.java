package com.syl.myapplication1.activity;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.syl.myapplication1.R;
import com.syl.myapplication1.adapter.ContactsAdapter;
import com.syl.myapplication1.domain.Contact;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Provider 获取系统的联系人信息
 */
public class ContactsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = ContactsActivity.class.getSimpleName();
    private List<Contact> mContacts;
    @Bind(R.id.btn_contacts_query)
    Button mBtnContactsQuery;
    @Bind(R.id.tv_tips)
    TextView mTvTips;
    @Bind(R.id.lv_contacts)
    ListView mLvContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);
        mBtnContactsQuery.setOnClickListener(this);
        mTvTips.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvTips.setText("Provider 获取系统的联系人信息");
    }

    @Override
    public void onClick(View v) {
        mContacts = queryContacts();
        ContactsAdapter adapter = new ContactsAdapter(this, mContacts);
        mLvContacts.setAdapter(adapter);
//        displayProgress();
        mContacts = null;
    }

    //加载数据的时候,显示loading..
    private void displayProgress() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("loading,please wait ...");
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.show();
        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                progressDialog.dismiss();
            }
        }.start();
    }

    private List<Contact> queryContacts() {
        List<Contact> infoList = new ArrayList<>();
        //联系人的Uri，也就是content://com.android.contacts/contacts
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        //指定获取_id和display_name两列数据，display_name即为姓名
        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        //根据Uri查询相应的ContentProvider，cursor为获取到的数据集
        Cursor cursor = this.getContentResolver().query(uri, projection, null, null, null);
        Contact contact;
        while (cursor.moveToNext()) {
            contact = new Contact();
            Long id = cursor.getLong(0);
            //获取姓名
            String name = cursor.getString(1);
            contact.setName(name);
            //指定获取NUMBER这一列数据
            String[] phoneProjection = new String[]{
                    ContactsContract.CommonDataKinds.Phone.NUMBER
            };

            //根据联系人的ID获取此人的电话号码
            Cursor phonesCursor = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    phoneProjection,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
                    null,
                    null);

            //因为每个联系人可能有多个电话号码，所以需要遍历
            StringBuffer sb = new StringBuffer();
            while (phonesCursor != null && phonesCursor.moveToNext()) {
                sb.append(phonesCursor.getString(0) + "-");
            }
            contact.setPhoneNumber(sb.toString());
            infoList.add(contact);
        }
        return infoList;
    }

}
