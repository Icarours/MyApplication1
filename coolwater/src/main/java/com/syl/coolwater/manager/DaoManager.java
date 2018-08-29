package com.syl.coolwater.manager;

import com.syl.coolwater.config.MyApplication;
import com.syl.coolwater.greendao.DaoMaster;
import com.syl.coolwater.greendao.DaoSession;

/**
 * Created by Bright on 2018/8/29.
 *
 * @Describe greenDao管理类
 * @Called
 */
public class DaoManager {
    private static DaoManager mInstance;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private DaoManager() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(MyApplication.getContext(), "user_db", null);
        DaoMaster mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoMaster getMaster() {
        return mDaoMaster;
    }

    public DaoSession getSession() {
        return mDaoSession;
    }

    public static DaoManager getInstance() {
        if (mInstance == null) {
            mInstance = new DaoManager();
        }
        return mInstance;
    }
}
