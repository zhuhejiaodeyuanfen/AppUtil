package com.vivian.apputil.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.vivian.apputil.R;
import com.vivian.apputil.bean.LocalPhotoBean;
import com.vivian.apputil.util.image.GlideUtils;


/**
 * 课程编辑的要求页面
 * 少于8张 显示继续添加的图标
 */
public class GridImageAdapter extends BaseRecyclerViewAdapter<LocalPhotoBean> {
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;
    private int selectMax = 9;
    private Context context;


    public GridImageAdapter(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    public int getLayoutId() {
        return R.layout.gv_filter_image;
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }

    @Override
    public int getItemCount() {
        if (listAllData.size() < selectMax) {
            return listAllData.size() + 1;
        } else {
            return listAllData.size();
        }
    }

    public int getTrueItemCount() {
        return listAllData.size();
    }


    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        ImageView mImg = (ImageView) holder.getView(R.id.fiv, true);
        //少于8张，显示继续添加的图标
        if (getItemViewType(position) == TYPE_CAMERA) {
            mImg.setImageResource(R.mipmap.ic_add_require);
        } else {
            LocalPhotoBean item = getItem(position);
            GlideUtils.loadImageView(context, item.getUrl(), mImg);
        }

    }

    private boolean isShowAddItem(int position) {
        int size = listAllData.size() == 0 ? 0 : listAllData.size();
        return position == size;
    }


}
