package com.vivian.apputil.bean;

/**
 * Created by vivianWQ on 2018/3/28
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class LocalPhotoBean {
    private String url;
    private int index;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    private boolean isSelect;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
