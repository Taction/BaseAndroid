package com.ci123.library.componentservice.user;


/**
 * Created by zc on 2018/3/19.
 */

public interface UserService {
    /**
     * 获取用户信息
     * @return
     */
    UserInfo getUserInfo();

    /**
     * 存储用户信息
     * @param userInfo
     */
    void setUserInfo(UserInfo userInfo);

    /**
     * 清除用户信息
     */
    void clearUserInfo();

    /**
     * 用户是否登陆
     * @return true 登陆 | false 未登陆
     */
    boolean isLogin();

    /**
     * 存储用户权限
     * @param acls
     */
    void setUserAcls(String acls);
}
