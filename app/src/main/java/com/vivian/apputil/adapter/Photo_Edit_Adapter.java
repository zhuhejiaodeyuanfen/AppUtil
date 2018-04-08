package com.vivian.apputil.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.vivian.apputil.R;
import com.vivian.apputil.bean.LocalPhotoBean;
import com.vivian.apputil.util.image.GlideUtils;

/**
 * Created by vivianWQ on 2018/3/30
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class Photo_Edit_Adapter extends BaseRecyclerViewAdapter<LocalPhotoBean> {
    private Context context;
    public Photo_Edit_Adapter(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        LocalPhotoBean item = getItem(position);
        ImageView ivBack = (ImageView) holder.getView(R.id.ivBack, false);
        GlideUtils.loadImageView(context, item.getUrl(), ivBack);

    }

    @Override
    public int getLayoutId() {
        return R.layout.list_item_detail_view;
    }
}
