package com.vivian.apputil.view;

import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;

import com.vivian.apputil.R;
import com.vivian.apputil.util.camera.CameraManager;


public class FullscreenCameraActivity extends BaseActivity {


    private View mContentView;
    private TextureView cameraTextureView;
    private String recordPath;
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
    }

    @Override
    public void initEventData() {
        cameraManager = CameraManager.getInstance(getApplication());
//        cameraManager.setCameraType(isSupportRecord ? 1 : 0);

    }


    @Override
    public void bindEvent() {

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
            if (recordPath != null) {

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


}
