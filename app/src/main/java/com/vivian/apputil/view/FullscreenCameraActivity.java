package com.vivian.apputil.view;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;

import com.vivian.apputil.R;
import com.vivian.apputil.util.camera.CameraManager;
import com.vivian.apputil.util.file.FileUtils;
import com.vivian.apputil.widget.camera.CameraProgressBar;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class FullscreenCameraActivity extends BaseActivity {


    private View mContentView;
    private TextureView cameraTextureView;
    private String recorderPath;
    private CameraProgressBar cameraProgressBar;
    private String photoPath;
    private boolean isPhotoTakingState;
    /**
     * true代表视频录制,否则拍照
     */
    private boolean isSupportRecord;
    /**
     * 获取照片订阅, 进度订阅
     */
    private Subscription takePhotoSubscription, progressSubscription;
    /**
     * camera manager
     */
    private CameraManager cameraManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_camera);

    }

    @Override
    public void initView() {
        mContentView = findViewById(R.id.fullscreen_content);
        cameraTextureView = findViewById(R.id.cameraTextureView);
        cameraProgressBar = findViewById(R.id.cameraProgressBar);
    }

    @Override
    public void initEventData() {
        cameraManager = CameraManager.getInstance(getApplication());
        cameraManager.setCameraType(isSupportRecord ? 1 : 0);

    }


    @Override
    public void bindEvent() {
        /**
         * 拍照，拍摄按钮监听
         */
        cameraProgressBar.setOnProgressTouchListener(new CameraProgressBar.OnProgressTouchListener() {
            @Override
            public void onClick(CameraProgressBar progressBar) {
                cameraManager.takePhoto(callback, FullscreenCameraActivity.this);
                isSupportRecord = false;

            }

            @Override
            public void onLongClick(CameraProgressBar progressBar) {
                isSupportRecord=true;
                cameraManager.setCameraType(1);
                recorderPath = FileUtils.getUploadVideoFile(mContext);
                cameraManager.startMediaRecord1(recorderPath);

            }

            @Override
            public void onZoom(boolean zoom) {

            }

            @Override
            public void onLongClickUp(CameraProgressBar progressBar) {

            }

            @Override
            public void onPointerDown(float rawX, float rawY) {

            }
        });

    }

    @Override
    public void loadData() {

    }

    /**
     * 页面可见时 防止用户从后台切换应用
     */
    @Override
    protected void onResume() {
        super.onResume();
        hide();
        if (cameraTextureView.isAvailable()) {

        } else {
            cameraTextureView.setSurfaceTextureListener(textureListener);
        }
    }

    /**
     * 页面不可见时,将摄像头资源释放
     */
    @Override
    protected void onPause() {
        cameraManager.closeCamera();
        super.onPause();

    }

    private void hide() {
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
            if (recorderPath != null) {

            } else {
                //拍摄路径为空,打开摄像头播放当前拍摄界面
                cameraManager.openCamera(cameraTextureView.getSurfaceTexture(), cameraTextureView.getWidth(), cameraTextureView.getHeight());

            }

        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

        }
    };

    private Camera.PictureCallback callback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(final byte[] data, Camera camera) {
//            setTakeButtonShow(false);
            takePhotoSubscription = Observable.create(new Observable.OnSubscribe<Boolean>() {
                @Override
                public void call(Subscriber<? super Boolean> subscriber) {
                    if (!subscriber.isUnsubscribed()) {

                        photoPath = FileUtils.getUploadPhotoFile(mContext);
                        isPhotoTakingState = FileUtils.savePhoto(photoPath, data, cameraManager.isCameraFrontFacing());
                        subscriber.onNext(isPhotoTakingState);
                    }
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Boolean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(Boolean aBoolean) {
                    if (aBoolean != null && aBoolean) {
//                        iv_choice.setVisibility(View.VISIBLE);
//                        cancel.setVisibility(View.VISIBLE);
//                        iv_close.setVisibility(View.GONE);
                    } else {
//                        setTakeButtonShow(true);
                    }
                }
            });
        }
    };


}
