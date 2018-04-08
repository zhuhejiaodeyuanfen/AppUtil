package com.vivian.apputil.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vivian.apputil.R;
import com.vivian.apputil.adapter.BaseRecyclerViewAdapter;
import com.vivian.apputil.adapter.HomeListAdapter;
import com.vivian.apputil.view.photo.SelectPhotoActivity;

import java.util.Arrays;
import java.util.List;

public class HomeActivity extends BaseActivity {
    private RecyclerView rvHome;
    private HomeListAdapter homeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void initView() {
        rvHome = findViewById(R.id.rvHome);

    }

    @Override
    public void initEventData() {

        String items[] = this.getResources().getStringArray(R.array.home_res_list);
        List<String> list = Arrays.asList(items);
        rvHome.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        homeListAdapter = new HomeListAdapter(mContext);
        rvHome.setAdapter(homeListAdapter);
        homeListAdapter.addData(list);


    }

    @Override
    public void bindEvent() {
        homeListAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.IOnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        launcher(mContext, PermissionActivity.class);
                        break;
                    case 1:
                        launcher(mContext, WeChatActivity.class);
                        break;
                    case 2:
                        launcher(mContext, VideoPlayChoiceActivity.class);
                        break;
                    case 8:
                        launcher(mContext, SelectPhotoActivity.class);
                        break;
                    case 9:
                        launcher(mContext, MyCameraActivity.class);
                        break;
                }

            }
        });

    }

    @Override
    public void loadData() {


    }
}
