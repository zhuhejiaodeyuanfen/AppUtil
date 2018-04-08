package com.vivian.apputil.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.vivian.apputil.R;
import com.vivian.apputil.adapter.ChatListAdapter;
import com.vivian.apputil.adapter.EmoJiContainerAdapter;
import com.vivian.apputil.bean.WeChatBean;
import com.vivian.apputil.util.EmoJiHelper;
import com.vivian.apputil.util.KeyBoardUtils;

import java.sql.Timestamp;

/**
 * 仿微信聊天页面
 * 涉及到聊天功能
 */
public class WeChatActivity extends BaseActivity {
    private EditText etInput;
    private RecyclerView rvChatView;
    private ChatListAdapter chatListAdapter;
    private ImageView ivSwitchExpress;
    private View viewExpress;
    private boolean isEmoji;
    private ViewPager vPager;
    private TextView tvSend;

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
        ivSwitchExpress = findViewById(R.id.ivSwitchExpress);
        viewExpress = findViewById(R.id.viewExpress);
        vPager=findViewById(R.id.vPager);
        tvSend=findViewById(R.id.tvSend);

    }

    @Override
    public void initEventData() {
        chatListAdapter = new ChatListAdapter(mContext);
        rvChatView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvChatView.setAdapter(chatListAdapter);

        EmoJiHelper emojiHelper = new EmoJiHelper(2, mContext, etInput);
        EmoJiContainerAdapter mAdapter = new EmoJiContainerAdapter(emojiHelper.getPagers());
        vPager.setAdapter(mAdapter);



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
        //发送表情的事件
        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMsg((etInput.getText().toString()));

            }
        });
        //表情键盘的点击事件
        ivSwitchExpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmoji) {
                    viewExpress.setVisibility(View.GONE);
                    KeyBoardUtils.showKeyBoard(mContext, etInput);

                    etInput.requestFocus();
                    isEmoji = false;
                } else {
                    isEmoji = true;
                    KeyBoardUtils.hideKeyBoard(mContext, etInput);
                    etInput.clearFocus();
                    viewExpress.setVisibility(View.VISIBLE);
                }


            }
        });
        etInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    viewExpress.setVisibility(View.GONE);
                    KeyBoardUtils.showKeyBoard(mContext, etInput);

                    etInput.requestFocus();
                    isEmoji = false;
                } else {
                    isEmoji = true;
                    KeyBoardUtils.hideKeyBoard(mContext, etInput);
                    etInput.clearFocus();
                    viewExpress.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    /**
     * 用户点击确认按钮后发送文本消息
     *
     * @param text
     */
    public void sendMsg(String text) {
        if (!text.trim().equals("")) {
            //无空白字符,有效字符
            WeChatBean weChatBean = new WeChatBean(ChatListAdapter.ITEM_MY_TEXT);
            weChatBean.setTextMsg(text);
            weChatBean.setSendTime(new Timestamp(System.currentTimeMillis()));
            etInput.setText("");
            chatListAdapter.addData(weChatBean);
        }


    }

    @Override
    public void loadData() {

    }
}
