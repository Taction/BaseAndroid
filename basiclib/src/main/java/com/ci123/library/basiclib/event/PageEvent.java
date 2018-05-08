package com.ci123.library.basiclib.event;

import com.alibaba.fastjson.JSONObject;

/**
 * 范例类：
 * 模块间通用事件通知 定义在此处，模块内事件通知定义在模块内
 * Created by ZhangChao on 2018/4/19.
 */

public class PageEvent {
    public enum EventID {
        EVENT_SHOW_LOADING,
        EVENT_HIDE_LOADING,
        EVENT_OPEN_MUSIC,
    }

    /**
     * weex界面
     */
    public static final int PAGE_TYPE_WEEX = 0x00000001;
    /**
     * h5 page
     */
    public static final int PAGE_TYPE_WEB = 0x00000002;
    /**
     * native page
     */
    public static final int PAGE_TYPE_NATIVE = 0x00000004;
    /**
     * 支持向多种页面发送通知
     * 设置时为不同页面类型取或
     */
    private int pageType;
    private String targetIdString;
    private EventID eventId;
    private String eventMessage;
    private JSONObject eventExtraMessage;

    public PageEvent() {

    }

    public PageEvent(int pageType, String targetIdString, EventID eventId) {
        this.pageType = pageType;
        this.targetIdString = targetIdString;
        this.eventId = eventId;
    }

    public int getPageType() {
        return pageType;
    }

    public void setPageType(int pageType) {
        this.pageType = pageType;
    }

    public String getTargetIdString() {
        return targetIdString;
    }

    public void setTargetIdString(String targetIdString) {
        this.targetIdString = targetIdString;
    }

    public EventID getEventId() {
        return eventId;
    }

    public void setEventId(EventID eventId) {
        this.eventId = eventId;
    }

    public String getEventMessage() {
        return eventMessage;
    }

    public void setEventMessage(String eventMessage) {
        this.eventMessage = eventMessage;
    }

    public JSONObject getEventExtraMessage() {
        return eventExtraMessage;
    }

    public void setEventExtraMessage(JSONObject eventExtraMessage) {
        this.eventExtraMessage = eventExtraMessage;
    }

    public boolean isTarget(int pageType, String targetIdString) {
        return  ((pageType & this.pageType) != 0 && this.targetIdString != null && this.targetIdString.equals(targetIdString));
    }

    public boolean isTarget(int pageType, int targetId) {
        return  isTarget(pageType, String.valueOf(this.targetIdString));
    }
}
