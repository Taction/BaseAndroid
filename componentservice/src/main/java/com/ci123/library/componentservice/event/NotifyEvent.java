package com.ci123.library.componentservice.event;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ci123.sdk.myutil.StringUtils;

/**
 * Created by zc on 2018/4/4.
 */

public class NotifyEvent {
    private JSONArray notifyJsonArray;
    private final String mNotifyMsg;
    public static final int NO_EVENT = 0;
    public static final int REFRESH_EVENT = 1;

    public NotifyEvent(String notifyMsg) {
        mNotifyMsg = notifyMsg;
        notifyJsonArray = JSONArray.parseArray(notifyMsg);
    }

    public int getPageEvent(String pageUrl) {
        if (StringUtils.isEmpty(pageUrl) || notifyJsonArray == null || notifyJsonArray.size() == 0) return NO_EVENT;
        for (int i = 0; i < notifyJsonArray.size(); i++) {
            JSONObject jsonObject = notifyJsonArray.getJSONObject(i);
            if (pageUrl.equals(jsonObject.getString("url"))) {
                return jsonObject.getIntValue("code");
            }
        }
        return NO_EVENT;
    }

}
