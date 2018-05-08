package com.ci123.library.componentservice.pbread;

import android.support.annotation.NonNull;

import com.ci123.library.basiclib.common.Constant;

/**
 * Created by zc on 2018/3/27.
 */

public class PBReadRouter {
    private final static String HOST = "pbread";
    private final static String PATH_PBREAD_DETAIL = "/pbreadDetail";

    public final static int ID_TYPE_PICTURE_BOOK = 0;   //ID类型为泛读绘本
    public final static int ID_TYPE_RECORD = 1;         //ID类型为学员跟读记录

    public final static String MODE_MIX = "MIX";        //可以播放和跟读
    public final static String MODE_PLAY = "PLAY";      //仅播放模式

    /**
     * 打开绘本泛读详情页面
     * @param id 绘本id或者跟读记录id，类型由idType标记
     * @param idType id的类型 0-ID类型为泛读绘本 1-ID类型为学员跟读记录
     * @param mode 模式
     * @return
     */
    public static String createPBReadDetailUrl(int id, int idType, @NonNull String mode) {
        return Constant.APP_SCHEME + HOST + PATH_PBREAD_DETAIL + "?" + String.format("id=%d&idType=%d&mode=%s", id, idType, mode);
    }
}
