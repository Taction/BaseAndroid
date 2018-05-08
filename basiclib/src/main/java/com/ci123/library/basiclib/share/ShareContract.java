package com.ci123.library.basiclib.share;

import android.app.Activity;

import com.tsy.sdk.social.PlatformType;

/**
 * Created by zc on 2017/12/15.
 */

public interface ShareContract {
    interface Interactor {
        /**
         * 分享url
         * @param title
         * @param description
         * @param platformType
         * @param activity
         * @param callback
         */
        void shareUrl(String url, String title, String description, PlatformType platformType, Activity activity, ShareCallback callback);

        void shareUrlWithDialog(String url, String title, String description, String imageCoverUrl, Activity activity, ShareWithDialogCallback callback);
    }

    interface ShareCallback {
        void onSuccess(PlatformType platformType);
        void onFailed(PlatformType platformType, String message);
        void onCancel(PlatformType platformType);
    }

    interface ShareWithDialogCallback {
        void onSuccess(int channelType);
        void onFailed(int channelType, String message);
        void onCancel(int channelType);
    }

    interface ShareDialogCallback {
        void onShare(int shareChannel);
        void onCancel();
        void onDismiss();
    }
}
