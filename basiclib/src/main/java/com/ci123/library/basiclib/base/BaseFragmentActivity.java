package com.ci123.library.basiclib.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.ci123.library.basiclib.BaseApplication;
import com.ci123.sdk.myokhttp.MyOkHttp;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by ZhangChao on 2017/9/18.
 */

public class BaseFragmentActivity extends FragmentActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        try {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                Window window = getWindow();
//                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//                window.setStatusBarColor(getResources().getColor(R.color.white));
//
//                //底部导航栏
//                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    protected String TAG = getClass().getSimpleName();

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);       //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        MyOkHttp okHttp = ((BaseApplication)getApplication()).getMyOkhttp();
        okHttp.cancel(this);

        super.onDestroy();
    }
}