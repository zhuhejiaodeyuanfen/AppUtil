package com.vivian.apputil.view;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.vivian.apputil.R;
import com.vivian.apputil.constants.AppConstants;
import com.vivian.apputil.util.AppPermissionUtil;


/**
 * 权限管理页面
 */
public class PermissionActivity extends BaseActivity {
    private TextView tvRecord,tvStorage,tvCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
    }

    @Override
    public void initView() {
        initTitle("权限管理");
        tvRecord = findViewById(R.id.tvRecord);
        tvStorage=findViewById(R.id.tvStorage);
        tvCamera=findViewById(R.id.tvCamera);

    }

    @Override
    public void initEventData() {
        tvCamera.setText("照相机"+(appPermissionUtil.checkPermission(mContext,Manifest.permission.CAMERA)?"有权限":"无权限"));
        tvStorage.setText("存储"+(appPermissionUtil.checkPermission(mContext,Manifest.permission.WRITE_EXTERNAL_STORAGE)?"有权限":"无权限"));
        tvRecord.setText("录音"+(appPermissionUtil.checkPermission(mContext,Manifest.permission.RECORD_AUDIO)?"有权限":"无权限"));

    }

    @Override
    public void bindEvent() {
        tvRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appPermissionUtil.requestPermissions(mContext, AppConstants.REQUEST_RECORD, new String[]{Manifest.permission.RECORD_AUDIO}, new AppPermissionUtil.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        showToast("用户允许录音权限");
                        initEventData();

                    }

                    @Override
                    public void onPermissionDenied() {
                        showToast("用户拒绝录音权限");
                        initEventData();

                    }
                });
            }
        });

        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                appPermissionUtil.requestPermissions(mContext, AppConstants.REQUEST_PHOTO, new String[]{Manifest.permission.CAMERA}, new AppPermissionUtil.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        showToast("用户允许录音权限");
                        initEventData();

                    }

                    @Override
                    public void onPermissionDenied() {
                        showToast("用户拒绝录音权限");
                        initEventData();

                    }
                });
            }
        });

        tvStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appPermissionUtil.requestPermissions(mContext, AppConstants.REQUEST_STORAGE, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new AppPermissionUtil.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        showToast("用户允许录音权限");
                        initEventData();

                    }

                    @Override
                    public void onPermissionDenied() {
                        showToast("用户拒绝录音权限");
                        initEventData();

                    }
                });
            }
        });

    }

    @Override
    public void loadData() {

    }
}
