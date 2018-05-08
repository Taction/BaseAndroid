package com.ci123.library.basiclib.share;

import com.tsy.sdk.social.PlatformType;

/**
 * Created by zc on 2018/2/5.
 */

public class ShareConstant {
    public static final int CHANNEL_UNKNOWN = 0;
    public static final int CHANNEL_WECHAT_CIRCLE = 1;
    public static final int CHANNEL_WECHAT = 2;
    public static final int CHANNEL_QZONE = 3;
    public static final int CHANNEL_QQ = 4;

    public static PlatformType changeToPlatformType(int channel) {
        switch (channel) {
            case CHANNEL_WECHAT:
                return PlatformType.WEIXIN;
            case CHANNEL_QQ:
                return PlatformType.QQ;
            case CHANNEL_QZONE:
                return PlatformType.QZONE;
            case CHANNEL_WECHAT_CIRCLE:
                return PlatformType.WEIXIN_CIRCLE;
            default:
                return PlatformType.QQ;
        }
    }
}
