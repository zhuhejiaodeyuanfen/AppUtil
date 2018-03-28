package com.vivian.apputil.util.picture.ipicture;

import com.vivian.apputil.bean.LocalPhotoBean;

import java.util.List;

/**
 * Created by vivianWQ on 2018/3/28
 * Mail: wangqi_vivian@sina.com
 * desc: 照片查询接口
 * Version: 1.0
 */
public interface IPictureQuery {

    interface QueryCallBack {
        void getAlbumSuccess(List<LocalPhotoBean> lists);

        void getAlbumFail();
    }

    void queryAlbumStart(QueryCallBack queryCallBack);
}
