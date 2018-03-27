package com.vivian.apputil.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangqi on 2017/3/26.
 * App权限检查工具类
 */

public class AppPermissionUtil {


    private int mRequestCode = -1;

    private OnPermissionListener mOnPermissionListener;

    public interface OnPermissionListener {

        void onPermissionGranted();//授权

        void onPermissionDenied();//拒绝
    }


    public synchronized boolean isVoicePermission() {
        return true;

    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissions(Object object, int requestCode, String[] permissions, OnPermissionListener listener) {
        if (Build.VERSION.SDK_INT <= 22) {
            //SDK小于22之前的版本之前发返回权限允许
            mOnPermissionListener = listener;
            if (mOnPermissionListener != null)
                mOnPermissionListener.onPermissionGranted();
        } else {
            mOnPermissionListener = listener;
            List<String> deniedPermissions = getDeniedPermissions(object, permissions);
            if (deniedPermissions.size() > 0) {
                mRequestCode = requestCode;
                (getActivity(object)).requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);

            } else {
                if (mOnPermissionListener != null)
                    mOnPermissionListener.onPermissionGranted();
            }


        }
    }


    /**
     * 检查用户的权限列表
     * @param object
     * @param permission
     * @return
     */
    public boolean checkPermission(Object object, String permission) {
        return ContextCompat.checkSelfPermission(getActivity(object), permission) == PackageManager.PERMISSION_GRANTED;
    }


    /**
     * 获取请求权限中需要授权的权限
     */
    private List<String> getDeniedPermissions(Object object, String... permissions) {
        List<String> deniedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getActivity(object), permission) == PackageManager.PERMISSION_DENIED) {
                deniedPermissions.add(permission);
            }
        }
        return deniedPermissions;
    }

    /**
     * 请求权限结果，对应Activity中onRequestPermissionsResult()方法。
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (mRequestCode != -1 && requestCode == mRequestCode && mOnPermissionListener != null) {
            if (verifyPermissions(grantResults)) {
                mOnPermissionListener.onPermissionGranted();
            } else {
                mOnPermissionListener.onPermissionDenied();
            }
        }

    }

    @TargetApi(11)
    private Activity getActivity(Object object) {
        if (object instanceof Activity) {
            return ((Activity) object);
        } else if (object instanceof android.support.v4.app.Fragment) {
            return ((android.support.v4.app.Fragment) object).getActivity();
        } else if (object instanceof android.app.Fragment) {
            return ((android.app.Fragment) object).getActivity();
        } else {
            return null;
        }
    }

    /**
     * 验证所有权限是否都已经授权
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    /**
     * 显示提示对话框
     */
    public void showTipsDialog(final Context context, String msg) {
        new AlertDialog.Builder(context)
                .setTitle("提示信息")
                .setMessage(msg)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings(context);
                    }
                }).show();
    }

    /**
     * 启动当前应用设置页面
     */
    public void startAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
        ((AppCompatActivity) context).finish();//推掉当前页面
    }
}
