package com.vivian.apputil.bean;

import java.sql.Timestamp;

/**
 * Created by vivianWQ on 2018/3/23
 * Mail: wangqi_vivian@sina.com
 * desc: 聊天基本数据实体类
 * Version: 1.0
 */
public class WeChatBean {
    private int chatType;
    private String textMsg;
    private Timestamp sendTime;

    public Timestamp getSendTime() {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    public String getTextMsg() {
        return textMsg;
    }

    public WeChatBean(int chatType) {
        this.chatType = chatType;
    }

    public void setTextMsg(String textMsg) {
        this.textMsg = textMsg;
    }

    public int getChatType() {
        return chatType;
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }
}
