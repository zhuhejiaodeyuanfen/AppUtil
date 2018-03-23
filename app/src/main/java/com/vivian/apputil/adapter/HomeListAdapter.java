package com.vivian.apputil.adapter;

import android.content.Context;
import android.widget.TextView;

import com.vivian.apputil.R;

/**
 * Created by vivianWQ on 2018/3/22
 * Mail: wangqi_vivian@sina.com
 * desc: 首页数据适配器
 * Version: 1.0
 */
public class HomeListAdapter extends BaseRecyclerViewAdapter<String> {
    public HomeListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        String item = getItem(position);
        TextView tvTitle = (TextView) holder.getView(R.id.tvTitle, true);
        tvTitle.setText(item);

    }

    @Override
    public int getLayoutId() {
        return R.layout.list_item_home_res;
    }
}
