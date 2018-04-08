package com.vivian.apputil.bean;

/**
 * Created by vivianWQ on 2017/9/11
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class FaceEmojiBean {

    private int emojiTag;

    private String descrp = "";

    public FaceEmojiBean(int emojiTag, String resourse) {
        this.emojiTag = emojiTag;
        this.resourse = resourse;
    }

    private String resourse;

    public int getEmojiTag() {
        return emojiTag;
    }

    public void setEmojiTag(int emojiTag) {
        this.emojiTag = emojiTag;
    }

    public String getDescrp() {
        return descrp;
    }

    public void setDescrp(String descrp) {
        this.descrp = descrp;
    }

    public FaceEmojiBean(int emojiTag) {
        this.emojiTag = emojiTag;
    }

    public FaceEmojiBean(String descrp) {
        this.descrp = descrp;
    }

    public String getResourse() {
        return resourse;
    }

    public void setResourse(String resourse) {
        this.resourse = resourse;
    }
}
