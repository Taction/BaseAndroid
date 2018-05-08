package com.ci123.library.basiclib.loadcallback;


import android.content.Context;
import android.view.View;

import com.ci123.library.basiclib.R;
import com.kingja.loadsir.callback.Callback;


/**
 * Description: loadsir loading view define
 * Create Time:2017/9/4 10:20
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class ErrorCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_error;
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return super.onReloadEvent(context, view);
    }
}
