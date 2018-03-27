package com.vivian.apputil.view;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.vivian.apputil.R;
import com.vivian.apputil.util.camera.CameraManager;
import com.vivian.apputil.util.camera.MediaPlayerManager;
import com.vivian.apputil.util.file.FileUtils;
import com.vivian.apputil.util.image.GlideUtils;
import com.vivian.apputil.widget.button.CaptureLayout;
import com.vivian.apputil.widget.camera.CameraProgressBar;
import com.vivian.apputil.widget.listener.ClickListener;
import com.vivian.apputil.widget.listener.ReturnListener;

import java.io.File;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class FullscreenCameraActivity extends BaseActivity {


    private View mContentView;
    private CaptureLayout llCapture;
    private TextureView cameraTextureView;
    private String recorderPath;
    private CameraProgressBar cameraProgressBar;
    private String photoPath;
    private boolean isPhotoTakingState;
    private MediaPlayerManager playerManager;
    private Button btnBack;
    private ImageView ivBack;
    /**
     * 是否正在录制
     */
    private boolean isRecording;
    /**
     * 录制视频的时间,毫秒
     */
    private int recordSecond;

    /**
     * 最长录制时间
     */
    private static final int MAX_RECORD_TIME = 15 * 1000;
    /**
     * 最小录制时间
     */
    private static final int MIN_RECORD_TIME = 1 * 1000;
    /**
     * 刷新进度的间隔时间
     */
    private static final int PLUSH_PROGRESS = 100;
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
    private int max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_camera);

    }

    @Override
    public void initView() {
        mContentView = findViewById(R.id.fullscreen_content);
        cameraTextureView = findViewById(R.id.cameraTextureView);
//        rlVideo = findViewById(R.id.rlVideo);
//        btnBack = findViewById(R.id.btnBack);
        llCapture = findViewById(R.id.llCapture);
        ivBack = findViewById(R.id.ivBack);
    }

    @Override
    public void initEventData() {
        cameraManager = CameraManager.getInstance(getApplication());
        playerManager = MediaPlayerManager.getInstance(getApplication());
        cameraManager.setCameraType(isSupportRecord ? 1 : 0);
        max = MAX_RECORD_TIME / PLUSH_PROGRESS;
//        rlVideo.setVisibility(View.GONE);


    }


    @Override
    public void bindEvent() {

        cameraProgressBar = llCapture.getBtn_capture();

        llCapture.setReturnListener(new ReturnListener() {
            @Override
            public void onReturn() {
                //返回监听
                finishActivity(mContext);
            }
        });

        //拍摄结束后左边返回按钮
        llCapture.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {

                if (recorderPath != null) {
                    //有拍摄好的正在播放,重新拍摄
                    FileUtils.deleteFiles(new File(recorderPath));
                    recorderPath = null;
                    recordSecond = 0;
                    playerManager.stopMedia();
                    setTakeButtonShow(true);
                    cameraManager.openCamera(cameraTextureView.getSurfaceTexture(), cameraTextureView.getWidth(), cameraTextureView.getHeight());
                } else if (isPhotoTakingState) {
                    isPhotoTakingState = false;
                    setTakeButtonShow(true);
                    cameraManager.restartPreview();
                }

                /**
                 * 重新初始化布局
                 */
                llCapture.resetCaptureLayout();
                ivBack.setVisibility(View.GONE);
                cameraTextureView.setVisibility(View.VISIBLE);
            }
        });

        llCapture.setCaptureLisenter(new CameraProgressBar.OnProgressTouchListener() {
            @Override
            public void onClick(CameraProgressBar progressBar) {
                cameraManager.takePhoto(callback, FullscreenCameraActivity.this);
                isSupportRecord = false;
            }

            @Override
            public void onLongClick(final CameraProgressBar progressBar) {

                isSupportRecord = true;
                cameraManager.setCameraType(1);
                recorderPath = FileUtils.getUploadVideoFile(mContext);
                cameraManager.startMediaRecord(recorderPath);
                isRecording = true;
                progressSubscription = Observable.interval(100, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread()).take(max).subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {


                        stopRecorder(true);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        progressBar.setProgress(progressBar.getProgress() + 1);
                    }
                });


            }

            @Override
            public void onZoom(boolean zoom) {

            }

            @Override
            public void onLongClickUp(CameraProgressBar progressBar) {
                cameraManager.setCameraType(0);

                stopRecorder(true);
                if (progressSubscription != null) {
                    progressSubscription.unsubscribe();
                }

            }

            @Override
            public void onPointerDown(float rawX, float rawY) {

            }
        });

    }


    /**
     * 停止拍摄
     */
    private void stopRecorder(boolean play) {

        isRecording = false;
        cameraManager.stopMediaRecord();
        recordSecond = cameraProgressBar.getProgress() * PLUSH_PROGRESS;//录制多少毫秒
        cameraProgressBar.reset();
        if (recordSecond < MIN_RECORD_TIME) {//小于最小录制时间作废
            if (recorderPath != null) {
                FileUtils.deleteFiles(new File(recorderPath));
                recorderPath = null;
                recordSecond = 0;
            }
//            setTakeButtonShow(true);
        } else if (play && cameraTextureView != null && cameraTextureView.isAvailable()) {
            setTakeButtonShow(false);
//            mProgressbar.setVisibility(View.GONE);
//            iv_choice.setVisibility(View.VISIBLE);
//            cancel.setVisibility(View.VISIBLE);
//            iv_close.setVisibility(View.GONE);
            llCapture.startTypeBtnAnimator();
            cameraManager.closeCamera();
            playerManager.playMedia(new Surface(cameraTextureView.getSurfaceTexture()), recorderPath);
        }
    }

    /**
     * 是否显示录制按钮
     *
     * @param isShow
     */
    private void setTakeButtonShow(boolean isShow) {
        if (isShow) {
            cameraProgressBar.setVisibility(View.VISIBLE);
//            rlVideo.setVisibility(View.GONE);
//            rl_camera.setVisibility(cameraManager.isSupportFlashCamera() || cameraManager.isSupportFrontCamera() ? View.VISIBLE : View.GONE);
        } else {
//            rlVideo.setVisibility(View.VISIBLE);
        }
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
                setTakeButtonShow(false);
                playerManager.playMedia(new Surface(surfaceTexture), recorderPath);

            } else {
                //拍摄路径为空,打开摄像头播放当前拍摄界面
                setTakeButtonShow(true);
                cameraManager.openCamera(cameraTextureView.getSurfaceTexture(), cameraTextureView.getWidth(), cameraTextureView.getHeight());

            }

        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

        }
    };

    private Camera.PictureCallback callback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(final byte[] data, Camera camera) {
            setTakeButtonShow(false);
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
                        /**
                         * 用户拍摄相片完成后返回
                         */
                        //
//                        iv_choice.setVisibility(View.VISIBLE);
//                        cancel.setVisibility(View.VISIBLE);
//                        iv_close.setVisibility(View.GONE);

                        ivBack.setVisibility(View.VISIBLE);
                        cameraTextureView.setVisibility(View.GONE);
                        llCapture.startTypeBtnAnimator();
                        GlideUtils.loadImageView(FullscreenCameraActivity.this, photoPath, ivBack);
                    } else {
                        /**
                         * 仍然在录制状态
                         */
                        setTakeButtonShow(true);
                    }
                }
            });
        }
    };


}
