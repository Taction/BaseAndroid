package com.ci123.library.basiclib.share;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.ci123.library.basiclib.BaseApplication;
import com.ci123.library.basiclib.R;
import com.ci123.library.basiclib.base.BaseInteractor;
import com.ci123.sdk.myutil.BitmapUtils;
import com.ci123.sdk.myutil.LogUtils;

import com.tsy.sdk.social.PlatformType;
import com.tsy.sdk.social.SocialApi;
import com.tsy.sdk.social.listener.ShareListener;
import com.tsy.sdk.social.share_media.ShareWebMedia;


/**
 * Created by zc on 2017/12/15.
 */

public class ShareInteractor extends BaseInteractor implements ShareContract.Interactor{
    SocialApi mSocialApi;
    public ShareInteractor() {
        mSocialApi = SocialApi.get(BaseApplication.getInstance());
    }

    @Override
    public void shareUrlWithDialog(final String url, final String title, final String description, String imageCoverUrl, final Activity activity, final ShareContract.ShareWithDialogCallback callback) {
        if (activity == null) return;
//        mSocialApi = SocialApi.get(activity.getApplicationContext());
        ShareDialog shareDialog = new ShareDialog(activity, R.style.shareDialog);
        shareDialog.setDialogCallback(new ShareDialog.DialogCallback() {
            @Override
            public void onShare(final int channel) {
                shareUrl(url, title, description, ShareConstant.changeToPlatformType(channel), activity, new ShareContract.ShareCallback() {
                    @Override
                    public void onSuccess(PlatformType platformType) {
                        callback.onSuccess(channel);
                    }

                    @Override
                    public void onFailed(PlatformType platformType, String message) {
                        LogUtils.w(TAG, message);
                        if ("wx not install".equals(message)) {
                            message = "当前手机未安装微信";
//                            ToastUtils.showShort(MyApplication.getInstance(), "当前手机未安装微信");
                        }

                        callback.onFailed(channel, message);
                    }

                    @Override
                    public void onCancel(PlatformType platformType) {
                        callback.onCancel(channel);
                    }
                });
            }

            @Override
            public void onCancel() {
                callback.onCancel(ShareConstant.CHANNEL_UNKNOWN);
            }
        });
        shareDialog.show();
    }

    @Override
    public void shareUrl(String url, String title, String description, final PlatformType platformType, Activity activity, final ShareContract.ShareCallback callback) {

        ShareWebMedia shareMedia = new ShareWebMedia();
        shareMedia.setTitle(title);
        shareMedia.setDescription(description);
        shareMedia.setWebPageUrl(url);
        shareMedia.setThumb(drawableBitmapOnWhiteBg(mContext, BitmapUtils.readBitMap(mContext, R.mipmap.ic_launcher)));

        mSocialApi.doShare(activity, platformType, shareMedia, new ShareListener() {
            @Override
            public void onComplete(PlatformType platform_type) {
                LogUtils.i(TAG, "share onComplete");
                callback.onSuccess(platform_type);
            }

            @Override
            public void onError(PlatformType platform_type, String err_msg) {
                LogUtils.i(TAG, "share onError:" + err_msg);
                callback.onFailed(platform_type, err_msg);
            }

            @Override
            public void onCancel(PlatformType platform_type) {
                LogUtils.i(TAG, "share onCancel");
                callback.onCancel(platform_type);
            }
        });
    }

    private Bitmap drawableBitmapOnWhiteBg(Context context, Bitmap bitmap){
        Bitmap newBitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(context.getResources().getColor(android.R.color.white));
        Paint paint=new Paint();
        canvas.drawBitmap(bitmap, 0, 0, paint); //将原图使用给定的画笔画到画布上
        return newBitmap;
    }
}
