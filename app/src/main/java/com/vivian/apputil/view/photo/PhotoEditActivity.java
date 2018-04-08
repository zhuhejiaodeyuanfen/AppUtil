package com.vivian.apputil.view.photo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.vivian.apputil.R;
import com.vivian.apputil.adapter.Photo_Edit_Adapter;
import com.vivian.apputil.bean.LocalPhotoBean;
import com.vivian.apputil.view.BaseActivity;
import com.vivian.apputil.widget.recyclerviewpagerlib.RecyclerViewPager;

import java.util.ArrayList;

public class PhotoEditActivity extends BaseActivity {
    private RecyclerViewPager rvPhoto;
    private Photo_Edit_Adapter photo_edit_adapter;
    private int position;
    private ArrayList<LocalPhotoBean> list;


    /**
     * 跳转到指定编辑页面
     *
     * @param list
     * @param position
     * @param baseActivity
     */
    public static void laucherPhotoEdit(ArrayList<LocalPhotoBean> list, int position, BaseActivity baseActivity) {
        Bundle args = new Bundle();
        args.putSerializable("imgList", list);
        args.putInt("position", position);
        baseActivity.launcher(baseActivity, PhotoEditActivity.class, args);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_edit);
    }

    @Override
    public void initView() {
        rvPhoto = findViewById(R.id.rvPhoto);
        rvPhoto.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        photo_edit_adapter = new Photo_Edit_Adapter(mContext);
        rvPhoto.setAdapter(photo_edit_adapter);

    }

    @Override
    public void initEventData() {
        position = getIntent().getIntExtra("position", 0);
        list = (ArrayList<LocalPhotoBean>) getIntent().getSerializableExtra("imgList");
    }

    @Override
    public void bindEvent() {

    }

    @Override
    public void loadData() {
        if (list != null && list.size() > 0) {
            photo_edit_adapter.addData(list);
        }

    }
}
