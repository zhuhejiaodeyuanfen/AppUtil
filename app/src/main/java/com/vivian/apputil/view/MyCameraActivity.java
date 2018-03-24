package com.vivian.apputil.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vivian.apputil.R;

/**
 * 自定义相机画面
 */
public class MyCameraActivity extends BaseActivity {
    private Button btnSnap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_camera);
    }

    @Override
    public void initView() {
        initTitle("自定义相机");
        btnSnap = findViewById(R.id.btnSnap);

    }

    @Override
    public void initEventData() {

    }

    @Override
    public void bindEvent() {
        btnSnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher(mContext, FullscreenCameraActivity.class);
            }
        });

    }

    @Override
    public void loadData() {

    }
}
