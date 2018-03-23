package com.vivian.apputil.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.vivian.apputil.R;
import com.vivian.apputil.adapter.ChatListAdapter;
import com.vivian.apputil.bean.WeChatBean;

import java.sql.Timestamp;

/**
 * 仿微信聊天页面
 * 涉及到聊天功能
 */
public class WeChatActivity extends BaseActivity {
    private EditText etInput;
    private RecyclerView rvChatView;
    private ChatListAdapter chatListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat);
    }

    @Override
    public void initView() {
        initTitle("聊天");
        etInput = findViewById(R.id.etInput);
        rvChatView = findViewById(R.id.rvChatView);

    }

    @Override
    public void initEventData() {
        chatListAdapter = new ChatListAdapter(mContext);
        rvChatView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvChatView.setAdapter(chatListAdapter);

    }

    @Override
    public void bindEvent() {
        etInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    sendMsg(etInput.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 用户点击确认按钮后发送文本消息
     *
     * @param text
     */
    public void sendMsg(String text) {
        WeChatBean weChatBean = new WeChatBean(ChatListAdapter.ITEM_MY_TEXT);
        weChatBean.setTextMsg(text);
        weChatBean.setSendTime(new Timestamp(System.currentTimeMillis()));
        chatListAdapter.addData(weChatBean);


    }

    @Override
    public void loadData() {

    }
}
