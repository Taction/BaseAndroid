package com.ci123.library.componentservice.user;


import android.text.TextUtils;

import org.json.JSONArray;

/**
 * Created by zc on 2018/3/19.
 */

public class UserInfo {
    String name;//名字
    String token;//乐果token
    String middleToken;//中台token
    String account;//中台accountID
    String avatar;//头像
    String phone;//手机号
    String userType;//用户类型-老师学生
    String acls;//用户权限
    String extraInfo;//额外信息，本app用来存储用户原始数据。

    public UserInfo () {}

    public UserInfo(String name, String token, String middleToken, String account, String avatar, String phone, String userType, String extraInfo) {
        this.name = name;
        this.token = token;
        this.middleToken = middleToken;
        this.account = account;
        this.avatar = avatar;
        this.phone = phone;
        this.userType = userType;
        this.extraInfo = extraInfo;
    }

    public UserInfo(String name, String token, String middleToken, String account, String avatar, String phone) {
        this.name = name;
        this.token = token;
        this.middleToken = middleToken;
        this.account = account;
        this.avatar = avatar;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMiddleToken() {
        return middleToken;
    }

    public void setMiddleToken(String middleToken) {
        this.middleToken = middleToken;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public boolean isTeacher() {
        return "MEMBER".equals(userType);
    }

    public boolean isStudent() {
        return "VIP".equals(userType);
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String getAcls() {
        return acls;
    }

    public void setAcls(String acls) {
        this.acls = acls;
    }

    public boolean hasAcl(String acl) {
        if (TextUtils.isEmpty(acl) || TextUtils.isEmpty(acls)) return false;
        try {
            JSONArray aclJsonArr = new JSONArray(acls);
            for (int i = 0; i < aclJsonArr.length(); i++) {
                if (acl.equals(aclJsonArr.getString(i))) return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", middleToken='" + middleToken + '\'' +
                ", account='" + account + '\'' +
                ", avatar='" + avatar + '\'' +
                ", phone='" + phone + '\'' +
                ", userType='" + userType + '\'' +
                ", extraInfo='" + extraInfo + '\'' +
                '}';
    }
}
