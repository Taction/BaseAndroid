package com.ci123.library.basiclib.base;

import android.content.Context;

import com.ci123.library.basiclib.BaseApplication;

/**
 * Created by ZhangChao on 2017/10/12.
 */

public class BaseInteractor {
    protected String TAG = getClass().getSimpleName();
    protected Object mTag;
    protected Context mContext;

    public BaseInteractor() {
        mContext = BaseApplication.getInstance().getContext();
    }
}
