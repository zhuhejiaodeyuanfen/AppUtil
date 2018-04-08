package com.vivian.apputil.view.photo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.vivian.apputil.R;
import com.vivian.apputil.adapter.PhotoDetailViewAdapter;
import com.vivian.apputil.bean.LocalPhotoBean;
import com.vivian.apputil.view.BaseActivity;
import com.vivian.apputil.view.PhotoListActivity;
import com.vivian.apputil.widget.recyclerviewpagerlib.RecyclerViewPager;

import static com.vivian.apputil.view.PhotoListActivity.RESULT_PHOTO;
import static com.vivian.apputil.view.PhotoListActivity.RETURN_PHOTO_CODE;

public class PhotoDetailViewActivity extends BaseActivity {

    private RecyclerViewPager rvPage;
    private PhotoDetailViewAdapter photoDetailViewAdapter;
    private TextView tvLeft, tvRight;
    private int position;


    public static void launcherDetailView(int position, BaseActivity baseActivity) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        baseActivity.launcherResult(RESULT_PHOTO,baseActivity, PhotoDetailViewActivity.class, args);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail_view);
    }

    @Override
    public void initView() {
        rvPage = findViewById(R.id.rvPage);
        tvLeft = findViewById(R.id.tvLeft);
        tvRight = findViewById(R.id.tvRight);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    @Override
    public void initEventData() {
        position = getIntent().getIntExtra("position", 0);
        photoDetailViewAdapter = new PhotoDetailViewAdapter(mContext);
        rvPage.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rvPage.setAdapter(photoDetailViewAdapter);
        photoDetailViewAdapter.addData(PhotoListActivity.localPhotoBeans);
        rvPage.scrollToPosition(position);

        if (PhotoListActivity.localPhotoBeans.get(position).isSelect()) {
            tvRight.setText(PhotoListActivity.localPhotoBeans.get(position).getIndex() + "");
        } else {
            tvRight.setText("");
        }

    }

    @Override
    public void bindEvent() {
        rvPage.addOnPageChangedListener(new RecyclerViewPager.OnPageChangedListener() {
            @Override
            public void OnPageChanged(int oldPosition, int newPosition) {
                position=newPosition;
                if (PhotoListActivity.localPhotoBeans.get(newPosition).isSelect()) {
                    tvRight.setText(PhotoListActivity.localPhotoBeans.get(newPosition).getIndex() + "");
                } else {
                    tvRight.setText("");
                }
            }
        });
        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity(mContext);
            }
        });
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalPhotoBean item = photoDetailViewAdapter.getItem(position);
                if (item.isSelect()) {
                    item.setSelect(false);
                    tvRight.setText("");
                    PhotoListActivity.selectList.remove(item.getIndex() - 1);
                    PhotoListActivity.resortList(item.getIndex() - 1);
                    photoDetailViewAdapter.notifyDataSetChanged();
                } else {
                    //list执行增加操作
                    if (PhotoListActivity.selectList.size() < PhotoListActivity.limit) {
                        item.setSelect(true);
                        item.setIndex(PhotoListActivity.selectList.size() + 1);
                        tvRight.setText(PhotoListActivity.selectList.size() + 1 + "");
                        PhotoListActivity.selectList.add(item);
                        photoDetailViewAdapter.notifyDataSetChanged();
                    } else {
                        showToast("您已超过最大选择限制!");
                    }
                }
            }
        });

    }

    @Override
    public void loadData() {

    }


    @Override
    public void finish() {
        setResult(RETURN_PHOTO_CODE);
        super.finish();
    }


}
