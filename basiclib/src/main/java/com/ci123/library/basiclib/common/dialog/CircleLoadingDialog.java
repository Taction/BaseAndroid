package com.ci123.library.basiclib.common.dialog;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ci123.library.basiclib.R;
import com.ci123.library.basiclib.R2;
import com.ci123.library.basiclib.base.BaseDialog;
import com.ci123.sdk.myutil.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zc on 2018/2/8.
 */

public class CircleLoadingDialog extends BaseDialog {
    String text;
    @BindView(R2.id.img_loading)
    ImageView imgLoading;
    @BindView(R2.id.rel_loading_item)
    RelativeLayout relLoadingItem;
    @BindView(R2.id.text_loading)
    TextView textLoading;
    @BindView(R2.id.dialog_body)
    LinearLayout dialogBody;

    public CircleLoadingDialog(Context context) {
        this(context, null);
    }

    public CircleLoadingDialog(Context context, String text) {
        super(context, R.style.commonDialogStyle);
        this.text = text;
        initView();
    }

    private void initView() {
        setContentView(R.layout.dialog_circle_loading);
        ButterKnife.bind(this);
        if (!StringUtils.isEmpty(text)) {
            textLoading.setText(text);
        }
    }

    @Override
    public void show() {
        //显示之前先加载动画
        AnimationDrawable animationDrawable = ((AnimationDrawable) imgLoading.getDrawable());
        animationDrawable.start();

        super.show();
        /**
         * 设置居中显示，要设置在show的后面
         */
        try {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.gravity = Gravity.CENTER;
            getWindow().setAttributes(layoutParams);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
