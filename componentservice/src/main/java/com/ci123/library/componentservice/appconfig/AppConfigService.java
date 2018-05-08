package com.ci123.library.componentservice.appconfig;

import android.app.Application;

/**
 * Created by zc on 2018/3/19.
 */

public interface AppConfigService {
    /**
     * 获取中台AppKey
     * @return
     */
    String getAppKey();
    /**
     * 获取中台channel
     * @return
     */
    String getChannel();
    /**
     * 获取中台domain
     * @return
     */
    String getMiddleDomain();
    /**
     * 获取自己服务器domain
     * @return
     */
    String getOwnDomain();
    /**
     * 获取网页domain
     * @return
     */
    String getH5Domain();

    /**
     * 获取weex远端地址目录
     * @return
     */
    String getWeexDomain();
    /**
     * 获取全局ApplicationContext
     * @return
     */
    Application getApplication();
}
