package com.vivian.apputil.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.vivian.apputil.R;
import com.vivian.apputil.util.ActivityLauncher;
import com.vivian.apputil.util.AppPermissionUtil;
import com.vivian.apputil.util.hintview.ToastUtils;
import com.vivian.apputil.widget.TitleBar;
import com.vivian.apputil.widget.dialog.PermissionDialog;

/**
 * 自定义baseActivity 封装一些基本场景
 */
public abstract class BaseActivity extends AppCompatActivity {

    public BaseActivity mContext;
    private View mContentView;
    public AppPermissionUtil appPermissionUtil;


    public TitleBar initTitle(String title) {
        TitleBar uiTitleBar = (TitleBar) getWindow().findViewById(R.id.titleBarView);
        uiTitleBar.setTitleText(title);
        return uiTitleBar;
    }

    /**
     * 展示一个toast
     *
     * @param toast
     */
    public void showToast(String toast) {
        ToastUtils.makeText(this, toast).show();
    }

    /**
     * 启动一个activity
     *
     * @param context
     * @param targetActivity
     */
    public static void launcher(Context context, Class<?> targetActivity) {
        ActivityLauncher.activityLauncher(context, targetActivity);
    }

    public static void launcher(Context context, Class<?> targetActivity, Bundle args) {
        ActivityLauncher.launcherExtras(context, targetActivity, args);
    }

    /**
     * 启动一个activity
     *
     * @param requestCode
     * @param context
     * @param targetActivity
     */
    public static void launcherResult(int requestCode, Context context, Class<? extends Activity> targetActivity,Bundle args) {
        ActivityLauncher.launcherResult(requestCode, context, targetActivity, args);
    }
    /**
     * 关闭activity 使用默认动画
     *
     * @param context
     */
    public static void finishActivity(Context context) {
        ActivityLauncher.finishActivity(context);
    }




    /**
     * 自定义布局资源文件后需要有一些自定义方法需要实现
     *
     * @param layoutResID
     */
    @Override
    public void setContentView(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        setContentView(view);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        mContentView = view;
        appPermissionUtil = new AppPermissionUtil();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //每次页面可见的时候需要请求一次存储权限
        appPermissionUtil.requestPermissions(BaseActivity.this, 2001, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new AppPermissionUtil.OnPermissionListener() {

            @Override
            public void onPermissionGranted() {

            }

            @Override
            public void onPermissionDenied() {
                showToast("必须开启存储权限才能正常使用");
                PermissionDialog permissionDialog = new PermissionDialog(mContext);
                //设置标题
                permissionDialog.setDialogContent("必须开启权限才能正常使用")
                        .setSure("确认")
                        .setStyle(PermissionDialog.DIALOG_NO_CANCEL)
                        .addOnClick(new PermissionDialog.OnDialogClick() {
                            @Override
                            public void onCancel() {

                            }

                            @Override
                            public void onSure() {

                                appPermissionUtil.startAppSettings(mContext);
                                finishActivity(mContext);

                            }
                        });
                //最重要的一步，显示
                permissionDialog.show();


            }
        });

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    /**
     * 初始化页面相关实现方法
     */
    public void init() {
        initView();
        initEventData();
        bindEvent();
        loadData();
    }

    /**
     * 初始化view
     */
    public abstract void initView();

    /**
     * 初始化事件数据,可以初始化presenter
     */
    public abstract void initEventData();

    /**
     * 绑定事件
     */
    public abstract void bindEvent();

    /**
     * 载入相关数据
     */
    public abstract void loadData();

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        appPermissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
