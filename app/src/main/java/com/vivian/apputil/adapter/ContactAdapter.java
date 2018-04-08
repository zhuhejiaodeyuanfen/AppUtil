package com.vivian.apputil.adapter;

import android.content.Context;
import android.widget.TextView;

import com.vivian.apputil.R;
import com.vivian.apputil.bean.ContactInfo;

/**
 * Created by vivianWQ on 2018/4/8
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class ContactAdapter extends BaseRecyclerViewAdapter<ContactInfo> {
    public ContactAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        TextView tvName = (TextView) holder.getView(R.id.tvName, false);
        tvName.setText(getItem(position).getName());

    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_list_contact;
    }
}
