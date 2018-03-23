package com.vivian.apputil.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vivian.apputil.R;
import com.vivian.apputil.adapter.BaseRecyclerViewAdapter;
import com.vivian.apputil.adapter.HomeListAdapter;

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
                if (position == 0) {
                    launcher(mContext, PermissionActivity.class);
                }
                else if(position==1)
                {
                    launcher(mContext,WeChatActivity.class);
                }
            }
        });

    }

    @Override
    public void loadData() {


    }
}
