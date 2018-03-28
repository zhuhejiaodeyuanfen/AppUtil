package com.vivian.apputil.util.camera;

import android.app.Application;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.util.Log;

import com.vivian.apputil.util.file.FileUtils;
import com.vivian.apputil.view.BaseActivity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by vivianWQ on 2018/3/24
 * Mail: wangqi_vivian@sina.com
 * desc: 相机拍摄管理类
 * Version: 1.0
 */
public class CameraManager {

    private Application context;
    /**
     * 视频录制
     */
    private MediaRecorder mMediaRecorder;
    private Camera.Parameters mParameters;
    /**
     * 相机闪光状态
     */
    private int cameraFlash;
    /**
     * camera
     */
    private Camera mCamera;
    /**
     * 前后置状态
     */
    private int cameraFacing = Camera.CameraInfo.CAMERA_FACING_BACK;
    /**
     * 是否支持前置摄像,是否支持闪光
     */
    private boolean isSupportFrontCamera, isSupportFlashCamera;
    /**
     * 录制视频的相关参数
     */
    private CamcorderProfile mProfile;

    /**
     * 0为拍照, 1为录像
     */
    private int cameraType;

    public CameraManager(Application context) {
        this.context = context;
        isSupportFrontCamera = CameraUtils.isSupportFrontCamera();
        isSupportFlashCamera = CameraUtils.isSupportFlashCamera(context);
        if (isSupportFrontCamera) {
            cameraFacing = CameraUtils.getCameraFacing(context, Camera.CameraInfo.CAMERA_FACING_BACK);
        }
        if (isSupportFlashCamera) {
            cameraFlash = CameraUtils.getCameraFlash(context);
        }
    }

    private static CameraManager INSTANCE;

    public static CameraManager getInstance(Application context) {
        if (INSTANCE == null) {
            synchronized (CameraManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CameraManager(context);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 打开camera
     */
    public void openCamera(SurfaceTexture surfaceTexture, int width, int height) {
        if (mCamera == null) {
            mCamera = Camera.open(cameraFacing);//打开当前选中的摄像头
            mProfile = CamcorderProfile.get(cameraFacing, CamcorderProfile.QUALITY_HIGH);
            try {
                mCamera.setDisplayOrientation(90);//默认竖直拍照
                mCamera.setPreviewTexture(surfaceTexture);
                initCameraParameters(cameraFacing, width, height);
                mCamera.startPreview();
            } catch (Exception e) {
//                LogUtils.i(PictureQuery);
                if (mCamera != null) {
                    mCamera.release();
                    mCamera = null;
                }
            }
        }
    }

    /**
     * 开启预览,前提是camera初始化了
     */
    public void restartPreview() {
        if (mCamera == null) return;
        try {
            Camera.Parameters parameters = mCamera.getParameters();
            int zoom = parameters.getZoom();
            if (zoom > 0) {
                parameters.setZoom(0);
                mCamera.setParameters(parameters);
            }
            mCamera.startPreview();
        } catch (Exception e) {
//            LogUtils.i(PictureQuery);
            if (mCamera != null) {
                mCamera.release();
                mCamera = null;
            }
        }
    }


    /**
     * 释放摄像头
     */
    public void closeCamera() {
        this.cameraType = 0;
        if (mCamera != null) {
            try {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            } catch (Exception e) {
//                LogUtils.i(PictureQuery);
                if (mCamera != null) {
                    mCamera.release();
                    mCamera = null;
                }
            }
        }
    }

    private void initCameraParameters(int cameraId, int width, int height) {
        Camera.Parameters parameters = mCamera.getParameters();
        if (cameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
            List<String> focusModes = parameters.getSupportedFocusModes();
            if (focusModes != null) {
                if (cameraType == 0) {
                    if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                    }
                } else {
                    if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
                        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
                    }
                }
            }
        }
        parameters.setRotation(90);//设置旋转代码,
        switch (cameraFlash) {
            case 0:
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                break;
            case 1:
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                break;
            case 2:
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                break;
        }
        List<Camera.Size> pictureSizes = parameters.getSupportedPictureSizes();
        List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
        if (!isEmpty(pictureSizes) && !isEmpty(previewSizes)) {
            /*for (Camera.Size size : pictureSizes) {
                LogUtils.i("pictureSize " + size.width + "  " + size.height);
            }
            for (Camera.Size size : pictureSizes) {
                LogUtils.i("previewSize " + size.width + "  " + size.height);
            }*/
            Camera.Size optimalPicSize = getOptimalCameraSize(pictureSizes, width, height);
            Camera.Size optimalPreSize = getOptimalCameraSize(previewSizes, width, height);
//            Camera.Size optimalPicSize = getOptimalSize(pictureSizes, width, height);
//            Camera.Size optimalPreSize = getOptimalSize(previewSizes, width, height);
            //          LogUtils.i("TextureSize "+width+" "+height+" optimalSize pic " + optimalPicSize.width + " " + optimalPicSize.height + " pre " + optimalPreSize.width + " " + optimalPreSize.height);
            parameters.setPictureSize(optimalPicSize.width, optimalPicSize.height);
            parameters.setPreviewSize(optimalPreSize.width, optimalPreSize.height);
            FileUtils.MAX_WIDTH=optimalPreSize.height;
            FileUtils.MAX_HEIGHT=optimalPreSize.width;
            mProfile.videoFrameWidth = optimalPreSize.width;
            mProfile.videoFrameHeight = optimalPreSize.height;
            mProfile.videoBitRate = 5000000;//此参数主要决定视频拍出大小
        }
        mCamera.setParameters(parameters);
    }

    /**
     * @param sizes 相机support参数
     * @param w
     * @param h
     * @return 最佳Camera size
     */
    private Camera.Size getOptimalCameraSize(List<Camera.Size> sizes, int w, int h) {
        sortCameraSize(sizes);
        int position = binarySearch(sizes, w * h);
        return sizes.get(position);
    }


    /***
     * 拍照
     * @param callback
     * @param mContext
     */
    public void takePhoto(Camera.PictureCallback callback, BaseActivity mContext) {
        if (mCamera != null) {
            try {
                mCamera.takePicture(null, null, callback);
            } catch (Exception e) {
                mContext.showToast("拍摄失败");

            }
        }
    }

    public boolean isCameraFrontFacing() {
        return cameraFacing == Camera.CameraInfo.CAMERA_FACING_FRONT;
    }

    /**
     * 设置对焦类型
     * @param cameraType
     */
    public void setCameraType(int cameraType) {
        this.cameraType = cameraType;
        if (mCamera != null) {//拍摄视频时
            if (cameraFacing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                Camera.Parameters parameters = mCamera.getParameters();
                List<String> focusModes = parameters.getSupportedFocusModes();
                if (focusModes != null) {
                    if (cameraType == 0) {
                        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                        }
                    } else {
                        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
                            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
                        }
                    }
                }
            }
        }
    }

    /**
     * 开始录制视频
     */
    public void startMediaRecord(String savePath) {
        if (mCamera == null) {
            return;
        }
        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
        } else {
            mMediaRecorder.reset();
        }


        if (isCameraFrontFacing()) {
            mMediaRecorder.setOrientationHint(270);
            Log.i("wujie","front");
        }else
        {
            Log.i("wujie","back");
            mMediaRecorder.setOrientationHint(90);
        }
        mParameters = mCamera.getParameters();
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        // 设置录像参数
        mMediaRecorder.setProfile(mProfile);
        try {
            mMediaRecorder.setOutputFile(savePath);
            mMediaRecorder.prepare();
            mMediaRecorder.start();
        } catch (Exception e) {

        }

    }


    /**
     * 停止录制
     */
    public void stopMediaRecord() {
        this.cameraType = 0;
        stopRecorder();
        releaseMediaRecorder();
    }

    private void stopRecorder() {
        if (mMediaRecorder != null) {
            try {
                mMediaRecorder.stop();
            } catch (Exception e) {
                e.printStackTrace();
//                LogUtils.i(PictureQuery);
            }

        }
    }

    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            try {
                mMediaRecorder.reset();
                mMediaRecorder.release();
                mMediaRecorder = null;
                mCamera.lock();
            } catch (Exception e) {
                e.printStackTrace();
//                LogUtils.i(PictureQuery);
            }
        }
    }






    /**
     * @param sizes
     * @param targetNum 要比较的数
     * @return
     */
    private int binarySearch(List<Camera.Size> sizes, int targetNum) {
        int targetIndex;
        int left = 0, right;
        int length = sizes.size();
        for (right = length - 1; left != right; ) {
            int midIndex = (right + left) / 2;
            int mid = right - left;
            Camera.Size size = sizes.get(midIndex);
            int midValue = size.width * size.height;
            if (targetNum == midValue) {
                return midIndex;
            }
            if (targetNum > midValue) {
                left = midIndex;
            } else {
                right = midIndex;
            }

            if (mid <= 1) {
                break;
            }
        }
        Camera.Size rightSize = sizes.get(right);
        Camera.Size leftSize = sizes.get(left);
        int rightNum = rightSize.width * rightSize.height;
        int leftNum = leftSize.width * leftSize.height;
        targetIndex = Math.abs((rightNum - leftNum) / 2) > Math.abs(rightNum - targetNum) ? right : left;
        return targetIndex;
    }

    /**
     * 排序
     *
     * @param previewSizes
     */
    private void sortCameraSize(List<Camera.Size> previewSizes) {
        Collections.sort(previewSizes, new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size size1, Camera.Size size2) {
                int compareHeight = size1.height - size2.height;
                if (compareHeight == 0) {
                    return (size1.width == size2.width ? 0 : (size1.width > size2.width ? 1 : -1));
                }
                return compareHeight;
            }
        });
    }

    /**
     * 集合不为空
     *
     * @param list
     * @param <E>
     * @return
     */
    private <E> boolean isEmpty(List<E> list) {
        return list == null || list.isEmpty();
    }
}
