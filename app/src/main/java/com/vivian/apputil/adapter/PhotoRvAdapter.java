package com.vivian.apputil.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vivian.apputil.R;
import com.vivian.apputil.bean.LocalPhotoBean;

/**
 * Created by vivianWQ on 2018/3/28
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class PhotoRvAdapter extends BaseRecyclerViewAdapter<LocalPhotoBean> {
    private Context context;

    public PhotoRvAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        LocalPhotoBean item = getItem(position);
        ImageView ivPhoto = (ImageView) holder.getView(R.id.ivPhoto, false);
        TextView tvIndex = (TextView) holder.getView(R.id.tvIndex, true);
        if(item.isSelect()&&item.getIndex()>-1)
        {
            tvIndex.setText(""+item.getIndex());
        }else{
            tvIndex.setText("");
        }
        Glide.with(context).load(item.getUrl())
                .override(100, 100)
                .into(ivPhoto);

    }

    @Override
    public int getLayoutId() {
        return R.layout.list_item_photo;
    }
}
