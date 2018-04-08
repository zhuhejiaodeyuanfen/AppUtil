package com.vivian.apputil.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.vivian.apputil.R;

public class VideoPlayChoiceActivity extends BaseActivity {
    private TextView tvLocal,tvAppLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play_choice);
    }

    @Override
    public void initView() {
        initTitle("视频播放");
        tvLocal=findViewById(R.id.tvLocal);
        tvAppLocal=findViewById(R.id.tvAppLocal);

    }

    @Override
    public void initEventData() {

    }

    @Override
    public void bindEvent() {
        tvLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //调用系统本地播放器播放
            }
        });

        tvAppLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public void loadData() {

    }
}
