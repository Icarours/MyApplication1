package com.syl.googleplay3.manager;


import com.syl.googleplay3.bean.DownLoadInfo;
import com.syl.googleplay3.bean.ItemInfoBean;
import com.syl.googleplay3.config.Constants;
import com.syl.googleplay3.factory.ThreadPoolProxyFactory;
import com.syl.googleplay3.utils.CommonUtils;
import com.syl.googleplay3.utils.FileUtils;
import com.syl.googleplay3.utils.HttpUtils;
import com.syl.googleplay3.utils.IOUtils;
import com.syl.googleplay3.utils.UIUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by j3767 on 2016/12/11.
 *
 * @Describe 下载管理器, 处理和下载相关的逻辑
 * 1.定义常量,需要时刻记录当前的状态
 * 2.根据一个ItemInfoBean(一个应用的相关信息)-->返回一个DownLoadInfo-->得到任何应用当前的最新状态
 * 3.下载过程必须时刻发布最新的DownLoadInfo给外界UI-->被观察者,发布消息的人
 * @Called
 */

public class DownloadManger {
    private static DownloadManger instance;
    public static final int STATE_UNDOWNLOAD = 0;//未下载
    public static final int STATE_DOWNLOADING = 1;//下载中
    public static final int STATE_PAUSEDOWNLOAD = 2;//暂停下载
    public static final int STATE_WAITINGDOWNLOAD = 3;//等待下载
    public static final int STATE_DOWNLOADFAILED = 4;//下载失败
    public static final int STATE_DOWNLOADED = 5;//下载完成
    public static final int STATE_INSTALLED = 6;//已安装
    /**
     * 使用集合保存用户点击下载按钮之后产生的downLoadInfo
     */
    private Map<String, DownLoadInfo> mCacheDownLoadInfo = new HashMap<>();

    public DownloadManger() {
    }

    /**
     * DownloadManger 只需要一个对象就可以了
     *
     * @return
     */
    public static DownloadManger getInstance() {
        if (instance == null) {
            synchronized (DownloadManger.class) {
                if (instance == null) {
                    instance = new DownloadManger();
                }
            }
        }
        return instance;
    }

    /**
     * @param downLoadInfo
     * @des 开始下载
     * @called 用户点击了下载按钮之后就开始下载
     */
    public void downLoad(DownLoadInfo downLoadInfo) {
        //保存DownLoadInfo
        mCacheDownLoadInfo.put(downLoadInfo.packageName, downLoadInfo);
        //当前状态:未下载
        downLoadInfo.state = STATE_UNDOWNLOAD;
        //downLoadInfo发生改变,通知所有观察者
        notifyObservers(downLoadInfo);

        //当前状态:等待下载
        downLoadInfo.state = STATE_WAITINGDOWNLOAD;
        //downLoadInfo发生改变,通知所有观察者
        notifyObservers(downLoadInfo);

        /*在提交任务给线程池之前,预先把状态设置为等待状态
        因为,一个task提交给线程池,无非两种情况:
        1.立马执行任务-->预先设置状态会被修改为 下载中
        2.等待执行任务-->状态不会马上修改为下载中,那么就会继续保留之前的
        状态:等待中.*/

        DownLoadTask task = new DownLoadTask(downLoadInfo);
        downLoadInfo.task = task;
        ThreadPoolProxyFactory.getDownloadThreadPoolProxy().execute(task);
    }

    private class DownLoadTask implements Runnable {
        private final DownLoadInfo mDownLoadInfo;

        public DownLoadTask(DownLoadInfo downLoadInfo) {
            this.mDownLoadInfo = downLoadInfo;
        }

        /**
         * 文件读写操作的标准代码
         */
        @Override
        public void run() {
            //文件已有长度
            long initRange = 0;
            String savePath = mDownLoadInfo.savePath;
            File saveFile = new File(savePath);
            //只要存在,能来到这里saveFile.length()一定小于mDownLoadInfo.max()
            if (saveFile.exists()) {
                initRange = saveFile.length();
            }

            //③初始化mDownLoadInfo中的当前进度值
            mDownLoadInfo.progress = initRange;

            //当前状态:下载中
            mDownLoadInfo.state = STATE_DOWNLOADING;
            //mDownLoadInfo状态改变,通知观察者
            notifyObservers(mDownLoadInfo);
            //在子线程中真正开始下载数据
            InputStream in = null;
            FileOutputStream out = null;
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                //http://localhost:8080/GooglePlayServer/download?name=app/com.itheima.www/com.itheima.www.apk
                String url = Constants.URLS.DOWNLOADBASEURL + "?";

                HashMap<String, Object> parmas = new HashMap<>();
                parmas.put("name", mDownLoadInfo.downLoadUrl);
                parmas.put("range", initRange);//①发起请求的时候range要进行修改

                String urlParamsByMap = HttpUtils.getUrlParamsByMap(parmas);
                url = url + urlParamsByMap;

                Request request = new Request.Builder().get().url(url).build();
                Response response = okHttpClient.newCall(request).execute();
                if (response.isSuccessful()) {
                    File file = new File(mDownLoadInfo.savePath);
                    //inputStream-->apk
                    in = response.body().byteStream();
                    out = new FileOutputStream(file, true);//②以追加的方式读写文件

                    //1024*1024*8 = 1m;
                    int len ;
                    byte[] buffer = new byte[1024 * 1024 * 8];
                    boolean isPause = false;
                    while ((len = in.read(buffer)) != -1) {
                        //如果mDownLoadInfo.state变为STATE_PAUSEDOWNLOAD,就跳出循环
                        if (mDownLoadInfo.state == STATE_PAUSEDOWNLOAD) {
                            isPause = true;
                            break;
                        }
                        out.write(buffer, 0, len);
                        //当前状态:下载中
                        mDownLoadInfo.state = STATE_DOWNLOADING;
                        //更新mDownLoadInfo里面的progress
                        mDownLoadInfo.progress += len;

                        //mDownLoadInfo状态改变,通知观察者
                        notifyObservers(mDownLoadInfo);

                        /*
                        读写文件操作okHttp
                        以前文件读写完毕,再读一次,如果是-1===>就跳出循环
                        如果使用OkHttp读写文件,再读一次,如果是-1===>就跳出抛出异常
                        throw new ProtocolException("unexpected end of stream");

                        断点下载的时候-->有异常
                        正常下载的时候-->没有异常
                        可能和服务器有关系
                         */
                        //如果当前文件的长度==max就跳出当前循环
                        if (saveFile.length() == mDownLoadInfo.max) {
                            break;
                        }
                    }

                    if (isPause) {//用户点击了暂停按钮,来到了这个地方

                    } else {//APP下载完成,来到这个地方
                        //数据写完,文件下载完毕
                        //当前状态:下载完成
                        mDownLoadInfo.state = STATE_DOWNLOADED;
                        //mDownLoadInfo状态改变,通知观察者
                        notifyObservers(mDownLoadInfo);
                    }
                } else {
                    //当前状态:下载失败
                    mDownLoadInfo.state = STATE_DOWNLOADFAILED;
                    //mDownLoadInfo状态改变,通知观察者
                    notifyObservers(mDownLoadInfo);
                }
            } catch (IOException e) {
                e.printStackTrace();
                //如果下载过程中出现异常.当前状态:下载失败
                mDownLoadInfo.state = STATE_DOWNLOADFAILED;
                //mDownLoadInfo状态改变,通知观察者
                notifyObservers(mDownLoadInfo);
            } finally {
                IOUtils.close(out);
                IOUtils.close(in);
            }
        }
    }

    /**
     * @param itemInfoBean 详情页面的所有数据
     * @return
     * @des 根据详情页面的所有数据返回一个downloadInfo
     */
    public DownLoadInfo getDownLoadInfo(ItemInfoBean itemInfoBean) {
        DownLoadInfo downLoadInfo = new DownLoadInfo();
        //常规赋值,可以由itemInfoBean里面的属性决定
        downLoadInfo.downLoadUrl = itemInfoBean.getDownloadUrl();
        String dir = FileUtils.getDir("apk");
        String fileName = itemInfoBean.getPackageName() + ".apk";
        File saveFile = new File(dir, fileName);
        downLoadInfo.savePath = saveFile.getAbsolutePath();
        downLoadInfo.packageName = itemInfoBean.getPackageName();
        downLoadInfo.max = itemInfoBean.getSize();
        downLoadInfo.progress = 0;

        //这是时刻变化的一个赋值
        //1.先考虑是否安装 STATE_INSTALLED = 6;//已安装
        if (CommonUtils.isInstalled(UIUtils.getContext(), itemInfoBean.getPackageName())) {
            downLoadInfo.state = STATE_INSTALLED;
            return downLoadInfo;
        }
        //2.考虑是否下载完成,但是没有安装 STATE_DOWNLOADED = 5;//下载完成
        if (saveFile.exists() && saveFile.length() == itemInfoBean.getSize()) {
            downLoadInfo.state = STATE_DOWNLOADED;
            return downLoadInfo;
        }
        //该应用,某一时刻用户点击了下载按钮
        /*
        STATE_DOWNLOADING = 1;//下载中
        STATE_PAUSEDOWNLOAD = 2;//暂停下载
        STATE_WAITINGDOWNLOAD = 3;//等待下载
        STATE_DOWNLOADFAILED = 4;//下载失败
         */
        if (mCacheDownLoadInfo.containsKey(itemInfoBean.getPackageName())) {
            downLoadInfo = mCacheDownLoadInfo.get(itemInfoBean.getPackageName());
            return downLoadInfo;
        }
        // STATE_UNDOWNLOAD = 0;//未下载
        downLoadInfo.state = STATE_UNDOWNLOAD;
        return downLoadInfo;
    }

    /*------------------ 自己实现观察者模式 -----------------*/
    //1.定义接口,以及接口变量
    public interface DownLoadInfoObserver {
        void onDownLoadInfoUpdate(DownLoadInfo downLoadInfo);
    }

    //2.定义集合,保存接口对象
    public List<DownLoadInfoObserver> mObservers = new ArrayList<>();

    //3.实现几个必须实现的方法(添加观察者,移除观察者,通知观察者)

    /**
     * 添加观察者
     *
     * @param observer
     */
    public synchronized void addObserver(DownLoadInfoObserver observer) {
        if (observer == null)
            throw new NullPointerException();
        if (!mObservers.contains(observer)) {
            mObservers.add(observer);
        }
    }

    /**
     * 从观察者集合中移除
     *
     * @param observer
     */
    public synchronized void deleteObserver(DownLoadInfoObserver observer) {
        mObservers.remove(observer);
    }

    /**
     * 通知所有观察者,数据已经发生改变
     *
     * @param downLoadInfo
     */
    public void notifyObservers(DownLoadInfo downLoadInfo) {
        //遍历观察者集合中所有的对象,并给通知所有的对象
        for (DownLoadInfoObserver observer :
                mObservers) {
            observer.onDownLoadInfoUpdate(downLoadInfo);
        }
    }

    /**
     * @param downLoadInfo
     * @des 暂停下载
     * @called 正在下载中用户点击了下载按钮
     */
    public void pauseDownLoad(DownLoadInfo downLoadInfo) {
        //当前状态立刻变为暂停状态
        downLoadInfo.state = STATE_PAUSEDOWNLOAD;
        notifyObservers(downLoadInfo);
    }

    /**
     * @param downLoadInfo
     * @des 取消下载
     * @called 点击等待下载按钮
     */
    public void cancelDownLoad(DownLoadInfo downLoadInfo) {
        //当前状态:未下载
        downLoadInfo.state = STATE_UNDOWNLOAD;
        notifyObservers(downLoadInfo);
        //从线程池任务队列中移除任务
        ThreadPoolProxyFactory.getDownloadThreadPoolProxy().remove(downLoadInfo.task);
    }
}
