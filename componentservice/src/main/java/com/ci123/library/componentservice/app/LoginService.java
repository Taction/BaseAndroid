package com.ci123.library.componentservice.app;

import android.app.Activity;

/**
 * 用户登陆退出时的处理
 * Created by zc on 2018/4/9.
 */

public interface LoginService {
    String TAG = "LoginService";
    /**
     * 当用户进行登陆操作时，用于app进行登陆后的自定义初始化操作
     * 登陆聊天
     * @param activity 当前活动activity
     */
    void onLogin(Activity activity);
    /**
     * 当用户进行退出登陆操作时，用于app进行退出登陆后的自定义操作
     * 退出聊天
     * @param activity 当前活动activity
     */
    void onLogout(Activity activity);
}
