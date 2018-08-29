package com.syl.coolwater.manager;

import com.syl.coolwater.greendao.UserDao;

/**
 * Created by Bright on 2018/8/29.
 *
 * @Describe
 * @Called
 */

public class UserDaoManager {
    private static UserDaoManager sUserDaoManager;
    public UserDao userDao;

    /**
     * 创建User表实例
     *
     * @return
     */
    public UserDao getUserDao(){
        userDao = DaoManager.getInstance().getSession().getUserDao();
        return userDao;
    }

    /**
     * 创建单例
     *
     * @return
     */
    public static UserDaoManager getInstance() {
        if (sUserDaoManager == null) {
            sUserDaoManager = new UserDaoManager();
        }
        return sUserDaoManager;
    }
}

