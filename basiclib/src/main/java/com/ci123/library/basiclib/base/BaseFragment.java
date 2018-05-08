package com.ci123.library.basiclib.base;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.webkit.ValueCallback;

import com.ci123.library.basiclib.BaseApplication;
import com.ci123.library.basiclib.R;
import com.ci123.sdk.myokhttp.MyOkHttp;
import com.ci123.sdk.myutil.LogUtils;

import com.umeng.analytics.MobclickAgent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * Created by ZhangChao on 2017/9/19.
 */

public class BaseFragment extends Fragment implements EasyPermissions.PermissionCallbacks {
    private Map<Integer, PermissionCallback> mPermissonCallbacks = new HashMap<>();

    protected String TAG = getClass().getSimpleName();
    public ValueCallback<Uri> mUploadMessage;
    protected final int RC_STORAGE = 1;
    public final static int FILECHOOSER_RESULTCODE = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtils.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        MyOkHttp okHttp = ((BaseApplication)getActivity().getApplication()).getMyOkhttp();
        okHttp.cancel(this);

        super.onDestroy();
        LogUtils.i(TAG, "onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.i(TAG, "onDestroyView");
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }



    /*******permission request********/
    public interface RequestPermissionCallback {
        void onHave();
        void onDenied();
    }

    protected void permissionStorage(final RequestPermissionCallback callback) {
        //相册需要读写文件权限
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        performCodeWithPermission("需要读写文件权限", RC_STORAGE, perms, new PermissionCallback() {
            @Override
            public void hasPermission() {
                callback.onHave();
            }

            @Override
            public void noPermission(Boolean hasPermanentlyDenied) {
                if(hasPermanentlyDenied) {
                    alertAppSetPermission("打开应用程序设置修改应用程序的存储权限");
                }
                callback.onDenied();
            }
        });
    }

    protected void permissionAudioRecord(final RequestPermissionCallback callback) {
        //相册需要读写文件权限
        String[] perms = {Manifest.permission.RECORD_AUDIO};

        performCodeWithPermission("需要录音权限", RC_STORAGE, perms, new PermissionCallback() {
            @Override
            public void hasPermission() {
                callback.onHave();
            }

            @Override
            public void noPermission(Boolean hasPermanentlyDenied) {
                if(hasPermanentlyDenied) {
                    alertAppSetPermission("打开应用程序设置修改应用程序的录音权限");
                }
                callback.onDenied();
            }
        });
    }

    /**
     * 权限回调接口
     */
    protected interface PermissionCallback {
        /**
         * 成功获取权限
         */
        void hasPermission();

        /**
         * 没有权限
         * @param hasPermanentlyDenied 是否点击不再询问被设置为永久拒绝权限
         */
        void noPermission(Boolean hasPermanentlyDenied);
    }

    /**
     * 请求权限操作
     * @param rationale 请求权限提示语
     * @param permissionRequestCode 权限requestCode
     * @param perms 申请的权限列表
     * @param callback 权限结果回调
     */
    protected void performCodeWithPermission(@NonNull String rationale,
                                             final int permissionRequestCode, @NonNull String[] perms, @NonNull PermissionCallback callback) {
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            callback.hasPermission();
        } else {
            mPermissonCallbacks.put(permissionRequestCode, callback);
            EasyPermissions.requestPermissions(this, rationale, permissionRequestCode, perms);
        }
    }

    /**
     * 跳转设置弹框 建议在权限被设置为不在询问时弹出 提示用户前往设置页面打开权限
     * @param tips 提示信息
     */
    protected void alertAppSetPermission(String tips) {
        new AppSettingsDialog.Builder(this)
                .setRationale(tips)
                .setTitle(getString(R.string.permission_deny_again_title))
                .setPositiveButton(getString(R.string.permission_deny_again_positive))
                .setNegativeButton(getString(R.string.permission_deny_again_nagative))
                .build()
                .show();
    }

    /**
     * 跳转设置弹框 建议在权限被设置为不在询问时弹出 提示用户前往设置页面打开权限
     * @param tips 提示信息
     * @param requestCode 页面返回时onActivityResult的requestCode
     */
    protected void alertAppSetPermission(String tips, int requestCode) {
        new AppSettingsDialog.Builder(this)
                .setRationale(tips)
                .setTitle(getString(R.string.permission_deny_again_title))
                .setPositiveButton(getString(R.string.permission_deny_again_positive))
                .setNegativeButton(getString(R.string.permission_deny_again_nagative))
                .setRequestCode(requestCode)
                .build()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        PermissionCallback callback = mPermissonCallbacks.get(requestCode);
        if(callback != null) {
            callback.hasPermission();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        PermissionCallback callback = mPermissonCallbacks.get(requestCode);
        if(callback != null) {
            if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
                callback.noPermission(true);
            } else {
                callback.noPermission(false);
            }
        }
    }
}
