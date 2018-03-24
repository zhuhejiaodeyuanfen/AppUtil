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
 * 仿微信聊天时间格式化显示。
 * 在同一年的显示规则：
 * 如果是当天显示格式为 HH:mm 例：14:45
 * 如果是昨天,显示格式为 昨天 HH:mm 例：昨天 13:12
 * 如果是在同一周 显示格式为 周一 HH:mm 例：周一14:05
 * 如果不是同一周则显示格式为 M月d日 早上或者其它 HH:mm 例： 2月5日 早上10:10
 * <p>
 * 不在同一年的显示规则：
 * 显示格式为 yyyy年M月d日 晚上或者其它 HH:mm 例：2016年2月5日 晚上18:05
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
