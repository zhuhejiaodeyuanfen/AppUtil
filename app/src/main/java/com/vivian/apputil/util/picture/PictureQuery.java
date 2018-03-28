package com.vivian.apputil.util.picture;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.vivian.apputil.AppApplication;
import com.vivian.apputil.bean.LocalPhotoBean;
import com.vivian.apputil.util.picture.ipicture.IPictureQuery;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vivianWQ on 2018/3/28
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class PictureQuery implements IPictureQuery {


    private static PictureQuery pictureQuery;

    public static PictureQuery newInstance() {
        if (pictureQuery == null) {
            pictureQuery = new PictureQuery();
        }
        return pictureQuery;
    }

    @Override
    public void queryAlbumStart(final QueryCallBack queryCallBack) {





        Observable.create(new Observable.OnSubscribe<List<LocalPhotoBean>>() {
            @Override
            public void call(Subscriber<? super List<LocalPhotoBean>> subscriber) {

                List<LocalPhotoBean> entities = new ArrayList<>();
                Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                String[] mediaColumns = {
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.SIZE,
                        MediaStore.Images.Media.DATE_MODIFIED};
                Cursor c = AppApplication.getInstance().getContentResolver().query(uri, mediaColumns, null, null, MediaStore.Video.Media.DATE_MODIFIED + " desc");

                if (c != null) {
                    while (c.moveToNext()) {
                        String path = c.getString(c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                        long size = c.getLong(c.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE));
                        long lastModified = c.getLong(c.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED));
                        LocalPhotoBean entity = new LocalPhotoBean();
                        entity.setUrl(path);
////                                entity.setDate(newDate + " " + date.substring(date.indexOf(" "), date.length()));
                        entities.add(entity);
                        Log.i("数据啊", path);
                    }
                    c.close();
                }
                subscriber.onNext(entities);
            }
        })
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<List<LocalPhotoBean>>() {
                    @Override
                    public void onNext(List<LocalPhotoBean> list) {

                        queryCallBack.getAlbumSuccess(list);
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });


    }
}
