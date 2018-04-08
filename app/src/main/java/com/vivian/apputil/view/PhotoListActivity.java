package com.vivian.apputil.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.vivian.apputil.R;
import com.vivian.apputil.adapter.BaseRecyclerViewAdapter;
import com.vivian.apputil.adapter.PhotoRvAdapter;
import com.vivian.apputil.bean.LocalPhotoBean;
import com.vivian.apputil.util.FullyGridLayoutManager;
import com.vivian.apputil.util.picture.PictureQuery;
import com.vivian.apputil.util.picture.ipicture.IPictureQuery;
import com.vivian.apputil.view.photo.PhotoDetailViewActivity;

import java.util.LinkedList;
import java.util.List;

/**
 * 自定义相册查询
 */
public class PhotoListActivity extends BaseActivity {
    private RecyclerView rvPhoto;
    private PhotoRvAdapter photoRvAdapter;
    public static int limit;
    private TextView tvFinish;
    public static LinkedList<LocalPhotoBean> selectList;
    public static List<LocalPhotoBean> localPhotoBeans;
    public static final int RESULT_PHOTO = 2008;
    public static final int RETURN_PHOTO_CODE = 2010;

    public static void launcherPhotoList(int limit, BaseActivity baseActivity) {
        Bundle args = new Bundle();
        args.putInt("limit", limit);
        baseActivity.launcherResult(RESULT_PHOTO, baseActivity, PhotoListActivity.class, args);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);
    }

    @Override
    public void initView() {
        initTitle("相册");
        rvPhoto = findViewById(R.id.rvPhoto);
        tvFinish = findViewById(R.id.tvFinish);


    }


    @Override
    public void initEventData() {
        limit = getIntent().getIntExtra("limit", 12);
        photoRvAdapter = new PhotoRvAdapter(mContext);
        rvPhoto.setLayoutManager(new FullyGridLayoutManager(mContext, 4, GridLayoutManager.VERTICAL, false));
        rvPhoto.setAdapter(photoRvAdapter);
        selectList = new LinkedList<>();
    }

    public static void resortList(int fromIndex) {
        for (int i = fromIndex; i < selectList.size(); i++) {
            selectList.get(i).setIndex(i + 1);
        }
    }

    @Override
    public void bindEvent() {
        //完成后发送
        tvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("imgList", selectList);
                setResult(RETURN_PHOTO_CODE, intent);
                finishActivity(mContext);

            }
        });
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
                            if (selectList.size() < limit) {
                                item.setSelect(true);
                                item.setIndex(selectList.size() + 1);
                                selectList.add(item);
                                photoRvAdapter.notifyDataSetChanged();
                            } else {
                                showToast("您已超过最大选择限制!");
                            }
                        }
                        break;
                    case R.id.rlBase:
                        PhotoDetailViewActivity.launcherDetailView(position, mContext);
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
                localPhotoBeans = lists;

            }

            @Override
            public void getAlbumFail() {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RETURN_PHOTO_CODE) {
            photoRvAdapter.notifyDataSetChanged();
        }
    }
}
