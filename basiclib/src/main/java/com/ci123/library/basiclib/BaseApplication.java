package com.ci123.library.basiclib;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.tsy.sdk.social.SocialApi;

/**
 * Created by ZhangChao on 2017/9/18.
 */

public abstract class BaseApplication extends MultiDexApplication {
    protected Context mContext;
    protected SocialApi mSocialApi;
    protected static BaseApplication mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mInstance = this;
    }


    /**
     * 获取ApplicationContext
     * @return
     */
    public Context getContext() {
        return mContext;
    }

    public SocialApi getSocialApi() {
        return mSocialApi;
    }
    /**
     * 获取全局Application
     * @return
     */
    public static synchronized BaseApplication getInstance() {
        return mInstance;
    }
}
