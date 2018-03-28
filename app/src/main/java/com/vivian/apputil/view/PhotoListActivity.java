package com.vivian.apputil.view;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vivian.apputil.R;
import com.vivian.apputil.adapter.BaseRecyclerViewAdapter;
import com.vivian.apputil.adapter.PhotoRvAdapter;
import com.vivian.apputil.bean.LocalPhotoBean;
import com.vivian.apputil.util.FullyGridLayoutManager;
import com.vivian.apputil.util.picture.PictureQuery;
import com.vivian.apputil.util.picture.ipicture.IPictureQuery;

import java.util.LinkedList;
import java.util.List;

/**
 * 自定义相册查询
 */
public class PhotoListActivity extends BaseActivity {
    private RecyclerView rvPhoto;
    private PhotoRvAdapter photoRvAdapter;
    private int checkSum;
    private LinkedList<LocalPhotoBean> selectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);
    }

    @Override
    public void initView() {
        initTitle("相册");
        rvPhoto = findViewById(R.id.rvPhoto);


    }

    @Override
    public void initEventData() {
        photoRvAdapter = new PhotoRvAdapter(mContext);
        rvPhoto.setLayoutManager(new FullyGridLayoutManager(mContext, 4, GridLayoutManager.VERTICAL, false));
        rvPhoto.setAdapter(photoRvAdapter);
        selectList = new LinkedList<>();


    }

    public void resortList(int fromIndex) {
        for (int i = fromIndex; i < selectList.size(); i++) {
            selectList.get(i).setIndex(i + 1);
        }
    }

    @Override
    public void bindEvent() {
        photoRvAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.IOnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                LocalPhotoBean item = photoRvAdapter.getItem(position);
                switch (view.getId()) {
                    case R.id.tvIndex:
                        if (item.isSelect()) {
                            item.setSelect(false);
                            selectList.remove(item.getIndex() - 1);
                            resortList(item.getIndex() - 1);
                            photoRvAdapter.notifyDataSetChanged();
                        } else {
                            //list执行增加操作
                            item.setSelect(true);
                            item.setIndex(selectList.size() + 1);
                            selectList.add(item);
                            photoRvAdapter.notifyDataSetChanged();

                        }

                        break;
                }
            }
        });

    }

    @Override
    public void loadData() {
        PictureQuery.newInstance().queryAlbumStart(new IPictureQuery.QueryCallBack() {
            @Override
            public void getAlbumSuccess(List<LocalPhotoBean> lists) {
                photoRvAdapter.addData(lists);

            }

            @Override
            public void getAlbumFail() {

            }
        });

    }
}
