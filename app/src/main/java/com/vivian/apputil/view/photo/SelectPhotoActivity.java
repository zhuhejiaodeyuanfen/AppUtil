package com.vivian.apputil.view.photo;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vivian.apputil.R;
import com.vivian.apputil.adapter.BaseRecyclerViewAdapter;
import com.vivian.apputil.adapter.GridImageAdapter;
import com.vivian.apputil.util.FullyGridLayoutManager;
import com.vivian.apputil.view.BaseActivity;
import com.vivian.apputil.view.PhotoListActivity;
import com.vivian.apputil.widget.dialog.FromBottomDialog;

import static com.vivian.apputil.adapter.GridImageAdapter.TYPE_CAMERA;

public class SelectPhotoActivity extends BaseActivity {
    private RecyclerView rvPhoto;
    private GridImageAdapter gridImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_photo);
    }

    @Override
    public void initView() {
        initTitle("选择照片");
        rvPhoto = findViewById(R.id.rvPhoto);
        gridImageAdapter = new GridImageAdapter(mContext);
        rvPhoto.setLayoutManager(new FullyGridLayoutManager(mContext, 4, GridLayoutManager.VERTICAL, false));
        rvPhoto.setAdapter(gridImageAdapter);

    }

    @Override
    public void initEventData() {

    }

    @Override
    public void bindEvent() {
        gridImageAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.IOnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                switch (view.getId()) {
                    case R.id.fiv:
                        if (gridImageAdapter.getItemViewType(position) == TYPE_CAMERA) {
                            showToast("添加信息");
                            new FromBottomDialog(mContext)
                                    .addOption("拍照", new FromBottomDialog.OnOptionClickListener() {
                                        @Override
                                        public void onOptionClick() {

                                        }
                                    })
                                    .addOption("从相册选择", new FromBottomDialog.OnOptionClickListener() {
                                        @Override
                                        public void onOptionClick() {
                                            PhotoListActivity.launcherPhotoList(12,mContext);

                                        }
                                    })
                                    .show();

                        }
                        break;
                }
            }
        });

    }

    @Override
    public void loadData() {

    }
}
