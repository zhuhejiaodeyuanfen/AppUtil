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
    private TextView tvRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
    }

    @Override
    public void initView() {
        initTitle("权限管理");
        tvRecord = findViewById(R.id.tvRecord);

    }

    @Override
    public void initEventData() {

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

                    }

                    @Override
                    public void onPermissionDenied() {
                        showToast("用户拒绝录音权限");

                    }
                });
            }
        });

    }

    @Override
    public void loadData() {

    }
}
