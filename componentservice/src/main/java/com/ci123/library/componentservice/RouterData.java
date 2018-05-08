package com.ci123.xmh.componentservice;

import android.os.Bundle;

/**
 * Created by zc on 2018/3/27.
 */

public class RouterData {
    private String uri;//页面地址，不带参数信息
    private Bundle bundle;//参数信息

    public RouterData(String uri, Bundle bundle) {
        this.uri = uri;
        this.bundle = bundle;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
