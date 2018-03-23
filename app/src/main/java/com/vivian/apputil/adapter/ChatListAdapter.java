package com.vivian.apputil.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.vivian.apputil.R;
import com.vivian.apputil.bean.WeChatBean;
import com.vivian.apputil.util.AppDateUtil;

/**
 * Created by vivianWQ on 2018/3/23
 * Mail: wangqi_vivian@sina.com
 * desc: 聊天数据适配器
 * Version: 1.0
 * <p>
 * 时间显示规则
 * 当天的消息，以每5分钟为一个跨度的显示时间；
 * 消息超过1天、小于1周，显示星期+收发消息的时间；
 * 消息大于1周，显示手机收发时间的日期。
 */
public class ChatListAdapter extends MultiRecyclerViewAdapter<WeChatBean> {


    public static final int ITEM_MY_IMAGE = 0;
    public static final int ITEM_MY_TEXT = 1;

    public ChatListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        WeChatBean item = getItem(position);
        if (getItemViewType(position) == ITEM_MY_TEXT) {
            TextView tvContent = (TextView) holder.getView(R.id.tvContent, false);
            tvContent.setText(item.getTextMsg());
            TextView tvTime = (TextView) holder.getView(R.id.tvTime, false);
            if (position == 0)
                tvTime.setText(AppDateUtil.getNewChatTime(item.getSendTime().getTime(), 0));
            else {
                String newChatTime = AppDateUtil.getNewChatTime(item.getSendTime().getTime(), listAllData.get(0).getSendTime().getTime());
                if (newChatTime.equals(AppDateUtil.NO_COMPARE_RESULT)) {
                    tvTime.setVisibility(View.GONE);
                } else {
                    tvTime.setVisibility(View.VISIBLE);
                    tvTime.setText(newChatTime);
                }
            }

        }

    }

    @Override
    public int getLayoutId(int viewType) {
        if (viewType == ITEM_MY_TEXT)
            return R.layout.list_item_my_text;
        else
            return R.layout.list_item_my_image;
    }

    @Override
    public int getItemViewType(int position) {
        return listAllData.get(position).getChatType();
    }
}
