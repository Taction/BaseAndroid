package com.ci123.library.basiclib.base;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ci123.library.basiclib.R;
import com.ci123.library.basiclib.BaseApplication;
import com.ci123.sdk.myokhttp.MyOkHttp;
import com.ci123.sdk.myutil.LogUtils;
import com.kingja.loadsir.core.LoadService;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class BaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    protected String TAG = getClass().getSimpleName();
    protected LoadService loadService;
    protected final int RC_STORAGE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "onCreate");

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
//                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);// View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN full screen show
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (BaseApplication.getInstance().getSocialApi() != null)
        BaseApplication.getInstance().getSocialApi().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        MyOkHttp okHttp = ((BaseApplication) getApplication()).getMyOkhttp();
        okHttp.cancel(this);
        LogUtils.d(TAG, "onDestroy");
        super.onDestroy();
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
            public void hasPermission(List<String> allPerms) {
                callback.onHave();
            }

            @Override
            public void noPermission(List<String> deniedPerms, List<String> grantedPerms, Boolean hasPermanentlyDenied) {
                if (hasPermanentlyDenied) {
                    alertAppSetPermission("打开应用程序设置修改应用程序的存储权限");
                }
                callback.onDenied();
            }
        });
    }

    protected void permissionPhoneState(final RequestPermissionCallback callback) {
        //相册需要读写文件权限
        String[] perms = {Manifest.permission.READ_PHONE_STATE};

        performCodeWithPermission("需要获取手机类型信息权限", RC_STORAGE, perms, new PermissionCallback() {
            @Override
            public void hasPermission(List<String> allPerms) {
                callback.onHave();
            }

            @Override
            public void noPermission(List<String> deniedPerms, List<String> grantedPerms, Boolean hasPermanentlyDenied) {
                if (hasPermanentlyDenied) {
                    alertAppSetPermission("打开应用程序设置修改应用程序的存储权限");
                }
                callback.onDenied();
            }
        });
    }


    private Map<Integer, PermissionCallback> mPermissonCallbacks = null;
    private Map<Integer, String[]> mPermissions = null;

    protected interface PermissionCallback {
        /**
         * has all permission
         *
         * @param allPerms all permissions
         */
        void hasPermission(List<String> allPerms);
        //List<String> allPerms

        /**
         * denied some permission
         *
         * @param deniedPerms          denied permission
         * @param grantedPerms         granted permission
         * @param hasPermanentlyDenied has permission denied permanently
         */
        void noPermission(List<String> deniedPerms, List<String> grantedPerms, Boolean hasPermanentlyDenied);
        //List<String> deniedPerms,
        //List<String> grantedPerms,
    }

    /**
     * request permission
     *
     * @param rationale   if denied first, next request rationale
     * @param requestCode requestCode
     * @param perms       permissions
     * @param callback    callback
     */
    protected void performCodeWithPermission(@NonNull String rationale,
                                             final int requestCode, @NonNull String[] perms, @NonNull PermissionCallback callback) {
        if (EasyPermissions.hasPermissions(this, perms)) {
            callback.hasPermission(Arrays.asList(perms));
        } else {
            if (mPermissonCallbacks == null) {
                mPermissonCallbacks = new HashMap<>();
            }
            mPermissonCallbacks.put(requestCode, callback);

            if (mPermissions == null) {
                mPermissions = new HashMap<>();
            }
            mPermissions.put(requestCode, perms);

            EasyPermissions.requestPermissions(this, rationale, requestCode, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (mPermissonCallbacks == null || !mPermissonCallbacks.containsKey(requestCode)) {
            return;
        }
        if (mPermissions == null || !mPermissions.containsKey(requestCode)) {
            return;
        }

        // 100% granted permissions
        if (mPermissions.get(requestCode).length == perms.size()) {
            mPermissonCallbacks.get(requestCode).hasPermission(Arrays.asList(mPermissions.get(requestCode)));
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (mPermissonCallbacks == null || !mPermissonCallbacks.containsKey(requestCode)) {
            return;
        }
        if (mPermissions == null || !mPermissions.containsKey(requestCode)) {
            return;
        }

        //granted permission
        List<String> grantedPerms = new ArrayList<>();
        for (String perm : mPermissions.get(requestCode)) {
            if (!perms.contains(perm)) {
                grantedPerms.add(perm);
            }
        }

        //check has permission denied permanently
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            mPermissonCallbacks.get(requestCode).noPermission(perms, grantedPerms, true);
        } else {
            mPermissonCallbacks.get(requestCode).noPermission(perms, grantedPerms, false);
        }
    }

    /**
     * alert AppSet Permission
     *
     * @param rationale alert setting rationale
     */
    protected void alertAppSetPermission(String rationale) {
        new AppSettingsDialog.Builder(this)
                .setRationale(rationale)
                .setTitle(getString(R.string.permission_deny_again_title))
                .setPositiveButton(getString(R.string.permission_deny_again_positive))
                .setNegativeButton(getString(R.string.permission_deny_again_nagative))
                .build()
                .show();
    }

    /**
     * alert AppSet Permission
     *
     * @param rationale   alert setting rationale
     * @param requestCode onActivityResult requestCode
     */
    protected void alertAppSetPermission(String rationale, int requestCode) {
        new AppSettingsDialog.Builder(this)
                .setRationale(rationale)
                .setTitle(getString(R.string.permission_deny_again_title))
                .setPositiveButton(getString(R.string.permission_deny_again_positive))
                .setNegativeButton(getString(R.string.permission_deny_again_nagative))
                .setRequestCode(requestCode)
                .build()
                .show();
    }
}

