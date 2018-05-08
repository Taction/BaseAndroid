package com.ci123.library.basiclib.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ci123.library.basiclib.R;
import com.ci123.library.basiclib.R2;
import com.ci123.sdk.myutil.DeviceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zc on 2018/2/6.
 */

public class ConfirmCancelDialog extends Dialog {
    @BindView(R2.id.first_item)
    LinearLayout firstItem;
    @BindView(R2.id.txt_content)
    TextView txtContent;
    @BindView(R2.id.second_item)
    LinearLayout secondItem;
    @BindView(R2.id.horizontal_divider)
    TextView horizontalDivider;
    @BindView(R2.id.txt_cancel)
    TextView txtCancel;
    @BindView(R2.id.vertical_divider)
    TextView verticalDivider;
    @BindView(R2.id.txt_confirm)
    TextView txtConfirm;
    @BindView(R2.id.bottom_item)
    LinearLayout bottomItem;
    @BindView(R2.id.dialog_body)
    LinearLayout dialogBody;
    private String content, confirm, cancel;
    private DialogCallback dialogCallback;
    private Context context;

    public ConfirmCancelDialog(Context context, int theme, String content) {
        this(context, theme, content, null, null);
    }

    public ConfirmCancelDialog(Context context, int theme, String content, String confirm, String cancel) {
        super(context, theme);
        this.context = context;
        this.content = content;
        this.confirm = (confirm == null ? "确定" : confirm);
        this.cancel = (cancel == null ? "取消" : cancel);
        setContentView(R.layout.confirm_cancle_dialog);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        txtContent.setText(content);
        txtConfirm.setText(confirm);
        txtCancel.setText(cancel);
    }

    public void setSecondItemVisible(boolean visible) {
        if (visible) {
            secondItem.setVisibility(View.VISIBLE);
        } else {
            secondItem.setVisibility(View.GONE);
        }
    }

    public void setCancelVisible(boolean visible) {
        if (visible) {
            txtCancel.setVisibility(View.VISIBLE);
            verticalDivider.setVisibility(View.VISIBLE);
        } else {
            txtCancel.setVisibility(View.GONE);
            verticalDivider.setVisibility(View.GONE);
        }
    }

    public void setContent(int color, String content) {
        if (content != null) {
            txtContent.setText(content);
        }
        if (color < 0x1000000) {
            color += 0xff000000;
        }
        txtContent.setTextColor(color);
    }

    public void setContent(int color) {
        if (color < 0x1000000) {
            color += 0xff000000;
        }
        txtContent.setTextColor(color);
    }

    public void setConfirm(int color, String confirm) {
        if (confirm != null) {
            txtConfirm.setText(confirm);
        }
        if (color < 0x1000000) {
            color += 0xff000000;
        }
        txtConfirm.setTextColor(color);
    }

    public void setCancel(int color, String cancel) {
        if (cancel != null) {
            txtCancel.setText(cancel);
        }
        if (color < 0x1000000) {
            color += 0xff000000;
        }
        txtCancel.setTextColor(color);
    }

    public void setConfirm(String confirm) {
        if (confirm != null) {
            txtConfirm.setText(confirm);
        }
    }

    public void setCancel(String cancel) {
        if (cancel != null) {
            txtCancel.setText(cancel);
        }
    }

    public void setHorizontalDividerParam(int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (DeviceUtils.dip2px(context, 260), DeviceUtils.dip2px(context, 1));
        params.setMargins(left, top, right, bottom);
        horizontalDivider.setLayoutParams(params);
    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置居中显示，要设置在show的后面
         */
        try {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.gravity= Gravity.CENTER;
            getWindow().setAttributes(layoutParams);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R2.id.txt_cancel)
    public void onClickCancel(View view) {
        dialogCallback.dialogCancel();
        dismiss();
    }

    @OnClick(R2.id.txt_confirm)
    public void onClickConfirm(View view) {
        dialogCallback.dialogConfirm();
    }

    public interface DialogCallback {
        void dialogConfirm();

        void dialogCancel();
    }

    public void setDialogCallback(DialogCallback dialogCallback) {
        this.dialogCallback = dialogCallback;
    }
}
