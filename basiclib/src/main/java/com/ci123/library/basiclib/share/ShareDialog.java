package com.ci123.library.basiclib.share;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.ci123.library.basiclib.R;
import com.ci123.library.basiclib.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2015/11/5.
 */
public class ShareDialog extends Dialog {

    @BindView(R2.id.share_wx_friend_circle)
    RelativeLayout shareWxFriendCircle;
    @BindView(R2.id.share_wx_friend)
    RelativeLayout shareWxFriend;
    @BindView(R2.id.share_qq)
    RelativeLayout shareQq;
    @BindView(R2.id.share_qzone)
    RelativeLayout shareQzone;
    @BindView(R2.id.cancel)
    RelativeLayout cancel;

    Unbinder unbinder;

    private DialogCallback mDialogCallback;


    public ShareDialog(Context context, int theme) {
        super(context, theme);
        setContentView(R.layout.dialog_share);
        unbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        try {
            Window dialogWindow = getWindow();
            dialogWindow.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            //这句就是设置dialog横向满屏了。
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialogWindow.setAttributes(lp);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R2.id.cancel)
    public void onCancel(View view) {
        if (mDialogCallback != null)
        mDialogCallback.onCancel();
        dismiss();
    }

    @OnClick(R2.id.share_wx_friend_circle)
    public void onShareWXCircle() {
        if (mDialogCallback != null)
        mDialogCallback.onShare(ShareConstant.CHANNEL_WECHAT_CIRCLE);
        dismiss();
    }
    @OnClick(R2.id.share_wx_friend)
    public void onShareWXFriend() {
        if (mDialogCallback != null)
        mDialogCallback.onShare(ShareConstant.CHANNEL_WECHAT);
        dismiss();
    }
    @OnClick(R2.id.share_qq)
    public void onShareQQ() {
        if (mDialogCallback != null)
        mDialogCallback.onShare(ShareConstant.CHANNEL_QQ);
        dismiss();
    }
    @OnClick(R2.id.share_qzone)
    public void onShareQZone() {
        if (mDialogCallback != null)
        mDialogCallback.onShare(ShareConstant.CHANNEL_QZONE);
        dismiss();
    }

    public interface DialogCallback {
        void onShare(int channel);

        void onCancel();
    }

    public void setDialogCallback(DialogCallback dialogCallback) {
        this.mDialogCallback = dialogCallback;
    }
}
