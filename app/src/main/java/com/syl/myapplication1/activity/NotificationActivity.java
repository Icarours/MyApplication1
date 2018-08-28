package com.syl.myapplication1.activity;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.syl.myapplication1.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.btn_notification_send)
    Button mBtnNotificationSend;
    @Bind(R.id.btn_remove_all_notification)
    Button mBtnRemoveAllNotification;
    @Bind(R.id.btn_send_notification_id)
    Button mBtnSendNotificationId;
    @Bind(R.id.btn_remove_notification)
    Button mBtnRemoveNotification;
    @Bind(R.id.btn_send_notification_with_tag)
    Button mBtnSendNotificationWithTag;
    @Bind(R.id.btn_remove_notification_with_tag)
    Button mBtnRemoveNotificationWithTag;
    @Bind(R.id.btn_send_ten_notification)
    Button mBtnSendTenNotification;
    @Bind(R.id.btn_send_flag_no_clear_notification)
    Button mBtnSendFlagNoClearNotification;
    @Bind(R.id.btn_send_flag_auto_cancel_notification)
    Button mBtnSendFlagAutoCancelNotification;
    @Bind(R.id.btn_send_flag_ongoing_event_notification)
    Button mBtnSendFlagOngoingEventNotification;
    @Bind(R.id.btn_sound_notification)
    Button mBtnSoundNotification;
    @Bind(R.id.tv_content)
    TextView mTv;
    private NotificationManager mNotificationManager;
    private int DEFAULT_NOTIFICATION_ID = 1;
    public static final String NOTIFICATION_TAG = "NotificationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        mBtnNotificationSend.setOnClickListener(this);
        mBtnRemoveAllNotification.setOnClickListener(this);
        mBtnSendNotificationId.setOnClickListener(this);
        mBtnRemoveNotification.setOnClickListener(this);
        mBtnSendNotificationWithTag.setOnClickListener(this);
        mBtnRemoveNotificationWithTag.setOnClickListener(this);
        mBtnSendTenNotification.setOnClickListener(this);
        mBtnSendFlagNoClearNotification.setOnClickListener(this);
        mBtnSendFlagAutoCancelNotification.setOnClickListener(this);
        mBtnSendFlagOngoingEventNotification.setOnClickListener(this);
        mBtnSoundNotification.setOnClickListener(this);

        mTv.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTv.setText("各种各样的Notification..");
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_notification_send:
                sendNotification();
                break;
            case R.id.btn_remove_all_notification:
                //移除当前 Context 下所有 Notification,包括 FLAG_NO_CLEAR 和 FLAG_ONGOING_EVENT
                mNotificationManager.cancelAll();
                break;
            case R.id.btn_send_notification_id:
                //发送一个 Notification,此处 ID = 1
                sendNotification2();
                break;
            case R.id.btn_remove_notification:
                //移除 ID = 1 的 Notification,注意:该方法只针对当前 Context。
                mNotificationManager.cancel(DEFAULT_NOTIFICATION_ID);
                break;
            case R.id.btn_send_notification_with_tag:
                //发送一个 ID = 1 并且 TAG = littlejie 的 Notification
                //注意:此处发送的通知与 sendNotification() 发送的通知并不冲突
                //因为此处的 Notification 带有 TAG
                sendNotificationWithTag();
                break;
            case R.id.btn_remove_notification_with_tag:
                //移除一个 ID = 1 并且 TAG = littlejie 的 Notification
                //注意:此处移除的通知与 NotificationManager.cancel(int id) 移除通知并不冲突
                //因为此处的 Notification 带有 TAG
                mNotificationManager.cancel(NOTIFICATION_TAG, DEFAULT_NOTIFICATION_ID);
                break;
            case R.id.btn_send_ten_notification:
                //连续发十条 Notification
                sendTenNotifications();
                break;
            case R.id.btn_send_flag_no_clear_notification:
                //发送 ID = 1, flag = FLAG_NO_CLEAR 的 Notification
                //下面两个 Notification 的 ID 都为 1,会发现 ID 相等的 Notification 会被最新的替换掉
                sendFlagNoClearNotification();
                break;
            case R.id.btn_send_flag_auto_cancel_notification:
                sendFlagOngoingEventNotification();
                break;
            case R.id.btn_send_flag_ongoing_event_notification:
                sendFlagAutoCancelNotification();
                break;
            case R.id.btn_sound_notification:
                sendSoundNotification();//带有铃声的Notification
                break;
            default:
                break;
        }
    }

    private void sendSoundNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentTitle("我是伴有铃声效果的通知")
                .setContentText("美妙么?安静听~")
                //调用系统默认响铃,设置此属性后setSound()会无效
                //.setDefaults(Notification.DEFAULT_SOUND)
                //调用系统多媒体裤内的铃声
                //.setSound(Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI,"2"));
                //调用自己提供的铃声，位于 /res/values/raw 目录下
                .setSound(Uri.parse("android.resource://com.syl.myapplication1/" + R.raw.xsy));
        //另一种设置铃声的方法
        //Notification notify = builder.build();
        //调用系统默认铃声
        //notify.defaults = Notification.DEFAULT_SOUND;
        //调用自己提供的铃声
        //notify.sound = Uri.parse("android.resource://com.littlejie.notification/"+R.raw.sound);
        //调用系统自带的铃声
        //notify.sound = Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI,"2");
        //mManager.notify(2,notify);
        mNotificationManager.notify(2, builder.build());
    }

    private void sendFlagAutoCancelNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Send Notification Use FLAG_ONGOING_EVENT")
                .setContentText("Hi,My id is 1,i can't be clear.");
        Notification notification = builder.build();
        //设置 Notification 的 flags = FLAG_NO_CLEAR
        //FLAG_ONGOING_EVENT 表示该通知通知放置在正在运行,不能被手动清除,但能通过 cancel() 方法清除
        //等价于 builder.setOngoing(true);
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        mNotificationManager.notify(DEFAULT_NOTIFICATION_ID, notification);
    }

    /**
     * 设置FLAG_AUTO_CANCEL
     * 该 flag 表示用户单击通知后自动消失
     */
    private void sendFlagOngoingEventNotification() {
        //设置一个Intent,不然点击通知不会自动消失
        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("send notification use FLAG_NO_CLEAR")
                .setContentText("Hi,My id is 1,i can't be clear.")
                .setContentIntent(pendingIntent)//指定跳转的目标Activity
                .setAutoCancel(true)//点击之后自动清除
                .build();
        notification.flags = Notification.FLAG_NO_CLEAR;
        //设置 Notification 的 flags = FLAG_NO_CLEAR
        //FLAG_AUTO_CANCEL 表示该通知能被状态栏的清除按钮给清除掉
        //等价于 builder.setAutoCancel(true);
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(DEFAULT_NOTIFICATION_ID, notification);
    }

    /**
     * 设置FLAG_NO_CLEAR
     * 该 flag 表示该通知不能被状态栏的清除按钮给清除掉,也不能被手动清除,但能通过 cancel() 方法清除
     * Notification.flags属性可以通过 |= 运算叠加效果
     */
    private void sendFlagNoClearNotification() {
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("send notification use FLAG_NO_CLEAR")
                .setContentText("Hi,My id is 1,i can't be clear.")
                .build();
        notification.flags = Notification.FLAG_NO_CLEAR;
        mNotificationManager.notify(DEFAULT_NOTIFICATION_ID, notification);
    }

    /**
     * 循环发送十个通知
     */
    private void sendTenNotifications() {
        for (int i = 0; i < 10; i++) {
            Notification build = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Send Notification Batch")
                    .setContentText("Hi,my id is " + i)
                    .build();
            mNotificationManager.notify(i, build);
        }
    }

    /**
     * 使用notify(String tag, int id, Notification notification)方法发送通知
     * 移除对应通知需使用 cancel(String tag, int id)
     */
    private void sendNotificationWithTag() {
        Notification build = new NotificationCompat.Builder(this)
                .setContentTitle("Send notification with tag")
                .setContentText("Hi, My id is 1,My tag is " + NOTIFICATION_TAG)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
        mNotificationManager.notify(NOTIFICATION_TAG, DEFAULT_NOTIFICATION_ID, build);
    }

    //发送最简单的通知,该通知的ID = 1
    private void sendNotification2() {
//        Notification build = new NotificationCompat.Builder(this,DEFAULT_CHANNEL_ID)//API26之后才会有DEFAULT_CHANNEL_ID
        Notification build = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("send Notification ")
                .setContentText("Hi,my id is 1")
                .build();
        mNotificationManager.notify(DEFAULT_NOTIFICATION_ID, build);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void sendNotification() {
        //1.创建NotificationManager
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        //2. 构建通知 -- 链式调用
        Notification notification = new Notification.Builder(this)
                .setContentTitle("这是通知的标题")
                .setContentText("这是通知的内容")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        //3. 发布通知
        manager.notify(10, notification);
    }
}
