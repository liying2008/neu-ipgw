package com.liying.ipgw.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liying.ipgw.R;
import com.liying.ipgw.application.AccountApp;
import com.liying.ipgw.utils.Constants;
import com.liying.ipgw.utils.ProgramUrlUtils;

import cc.duduhuo.applicationtoast.AppToast;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/5/20 13:15
 * 版本：1.0
 * 描述：流媒体播放Activity(使用Android自带播放器API)
 * 备注：不知是不是服务器端的原因，经常会无法播放
 * =======================================================
 */
public class M3U8Player2 extends BaseActivity implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener,
    MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnVideoSizeChangedListener, SurfaceHolder.Callback, MediaPlayer.OnBufferingUpdateListener {
    private Display currDisplay;
    private SurfaceView surfaceView;
    private SurfaceHolder holder;
    private MediaPlayer player;
    private int vWidth, vHeight;    // 视频宽度、高度
    private int pWidth, pHeight;    // 屏幕宽度、高度
    private View mVolumeBrightnessLayout;
    private ImageView mOperationBg;
    private ImageView mOperationPercent;
    private AudioManager mAudioManager;
    private LinearLayout mLoadingView;
    private TextView tvBufferUpdatePercent;
    private TextView tvPlayTime;
    private LinearLayout llPlayOrPause;
    private LinearLayout llStretchOrOri;
    private LinearLayout llScreenLock;
    private ImageView ivLock;
    private ImageView ivPlayOrPause;
    private ImageView ivStretchOrOri;
    private RelativeLayout mySimpleController;
    private boolean isFullScreen = true;    // 是否是全屏播放
    private boolean isPaused = false;       // 是否处于暂停状态
    /** 最大声音 */
    private int mMaxVolume;
    /** 当前声音 */
    private int mVolume = -1;
    /** 当前亮度 */
    private float mBrightness = -1f;
    /** 是否锁定屏幕 */
    private boolean mScreenLocked = false;
    private GestureDetector mGestureDetector;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置全屏
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 隐藏虚拟按键
        WindowManager.LayoutParams params = window.getAttributes();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            params.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }
        window.setAttributes(params);
        initView();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        setContentView(R.layout.activity_m3u8_player2);
        AccountApp.activityList.add(this);
        surfaceView = (SurfaceView) this.findViewById(R.id.video_view);
        //给SurfaceView添加CallBack监听
        holder = surfaceView.getHolder();
        holder.addCallback(this);

        mLoadingView = (LinearLayout) findViewById(R.id.video_loading);
        tvBufferUpdatePercent = (TextView) findViewById(R.id.tvBufferUpdatePercent);
        mVolumeBrightnessLayout = findViewById(R.id.operation_volume_brightness);
        mOperationBg = (ImageView) findViewById(R.id.operation_bg);
        mOperationPercent = (ImageView) findViewById(R.id.operation_percent);
        tvPlayTime = (TextView) findViewById(R.id.tvPlayTime);
        llPlayOrPause = (LinearLayout) findViewById(R.id.llPlayOrPause);
        llStretchOrOri = (LinearLayout) findViewById(R.id.llStretchOrOri);
        ivPlayOrPause = (ImageView) findViewById(R.id.ivPlayOrPause);
        ivStretchOrOri = (ImageView) findViewById(R.id.ivStretchOrOri);
        mySimpleController = (RelativeLayout) findViewById(R.id.mySimpleController);
        llScreenLock = (LinearLayout) findViewById(R.id.llScreenLock);
        ivLock = (ImageView) findViewById(R.id.ivLock);
    }

    private final static int HIDE_CONTROLLER = 1;
    private final static int CHANGE_TIME_MSG = 0;
    Handler myHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case HIDE_CONTROLLER:
                    hideController();
                    break;
                case CHANGE_TIME_MSG:
                    // 设置播放时间
                    if (player != null) {
                        int time = player.getCurrentPosition();
                        int i = time / 1000;
                        int minute = i / 60;
                        int hour = minute / 60;
                        int second = i % 60;
                        minute %= 60;
                        tvPlayTime.setText(String.format("%02d:%02d:%02d", hour, minute, second));
                        NeuHdtvListActivity.watchTime = time;
                        myHandler.sendEmptyMessageDelayed(CHANGE_TIME_MSG, 1000);
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    /**
     * 初始化数据
     */
    private void initData() {
        Intent intentData = getIntent();
        String[] programInfo = intentData.getStringArrayExtra(Constants.PROGRAM);
        String path = ProgramUrlUtils.getProgramPathFromInfo(programInfo);
        if ("".equals(path)) {
            AppToast.showToast("path or url is unavailable");
            return;
        }
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        mGestureDetector = new GestureDetector(this, new MyGestureListener());
        // 设置播放URL
        try {
             /* 重置MediaPlayer */
            //下面开始实例化MediaPlayer对象
            player = new MediaPlayer();
//            player = MediaPlayer.create(this, Uri.parse(path), holder);
            player.reset();
            player.setDataSource(M3U8Player2.this, Uri.parse(path));
            player.setDisplay(holder);
            player.setScreenOnWhilePlaying(true);
            player.prepareAsync();
//            player.prepare();
            player.setOnCompletionListener(this);
            player.setOnErrorListener(this);
            player.setOnInfoListener(this);
            player.setOnPreparedListener(this);
            player.setOnSeekCompleteListener(this);
            player.setOnVideoSizeChangedListener(this);
            player.setOnBufferingUpdateListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //然后，我们取得当前Display对象
        currDisplay = this.getWindowManager().getDefaultDisplay();

        llPlayOrPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelDelayHide();
                if (isPaused) {
                    player.start();
                    ivPlayOrPause.setBackgroundResource(R.mipmap.ic_button_pause);
                    hideControllerDelay();
                } else {
                    player.pause();
                    ivPlayOrPause.setBackgroundResource(R.mipmap.ic_button_play);
                    hideControllerDelay();
                }
                isPaused = !isPaused;   // 播放暂停状态反转
            }
        });

        llStretchOrOri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFullScreen) {
                    setOriginal();
                    ivStretchOrOri.setBackgroundResource(R.mipmap.ic_zoom_stretch);
                } else {
                    setFullScreen();
                    ivStretchOrOri.setBackgroundResource(R.mipmap.ic_zoom_inside);
                }
            }
        });
        llScreenLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 锁定/解锁屏幕
                if (mScreenLocked) {
                    lockScreen(false);
                    ivLock.setBackgroundResource(R.mipmap.unlock_screen);
                    mScreenLocked = false;
                } else {
                    lockScreen(true);
                    ivLock.setBackgroundResource(R.mipmap.lock_screen);
                    mScreenLocked = true;
                }
            }
        });
    }

    /**
     * 锁定/解锁屏幕方向
     *
     * @param lockScreen
     */
    public void lockScreen(boolean lockScreen) {
        if (lockScreen) {
            Configuration config = getResources().getConfiguration();
            if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                AppToast.showToast("横屏已锁定");
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                AppToast.showToast("竖屏已锁定");
            }
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
            AppToast.showToast("屏幕锁定已解除");
        }
    }

    private void hideController() {
        if (mySimpleController.getVisibility() == View.VISIBLE) {
            mySimpleController.setVisibility(View.GONE);
        }
    }

    private void hideControllerDelay() {
        myHandler.sendEmptyMessageDelayed(HIDE_CONTROLLER, 3000);
    }

    private void cancelDelayHide() {
        myHandler.removeMessages(HIDE_CONTROLLER);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // 当Surface尺寸等参数改变时触发
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // 当SurfaceView中的Surface被创建的时候被调用
        initData(); // 初始化数据
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        // 当video大小改变时触发
        // 这个方法在设置player的source后至少触发一次
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        // seek操作完成时触发
        player.seekTo(player.getCurrentPosition());
    }

    @Override
    public void onPrepared(MediaPlayer player) {
        // 当prepare完成后，该方法触发，在这里我们播放视频
        //首先取得video的宽和高
        vWidth = player.getVideoWidth();
        vHeight = player.getVideoHeight();
        // 获取屏幕的宽和高
        pWidth = currDisplay.getWidth();
        pHeight = currDisplay.getHeight();

        float wRatio = (float) vWidth / (float) pWidth;
        float hRatio = (float) vHeight / (float) pHeight;
        //选择大的一个进行缩放
        if (wRatio > hRatio) {
            vHeight = pWidth * vHeight / vWidth;
            vWidth = pWidth;
        } else {
            try {
                vWidth = pHeight * vWidth / vHeight;
            } catch (ArithmeticException e) {
                // 防止除0异常
                e.printStackTrace();
                AppToast.showToast("流媒体读取失败。");
            }
            vHeight = pHeight;
        }

        setFullScreen();
        // 隐藏控制条
        mySimpleController.setVisibility(View.GONE);
        // 开始播放计时
        myHandler.sendEmptyMessage(CHANGE_TIME_MSG);
        // 强行开启屏幕旋转效果
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        // 开始播放视频
        player.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event))
            return true;
        // 处理手势结束
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                endGesture();
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 手势结束
     */
    private void endGesture() {
        mVolume = -1;
        mBrightness = -1f;

        // 隐藏
        mDismissHandler.removeMessages(0);
        mDismissHandler.sendEmptyMessageDelayed(0, 500);
    }

    private void setOriginal() {
        //设置surfaceView的布局参数
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(vWidth, vHeight);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        surfaceView.setLayoutParams(layoutParams);
        isFullScreen = false;
    }

    /**
     * 设置全屏播放
     */
    private void setFullScreen() {
        surfaceView.setLayoutParams(new RelativeLayout.LayoutParams(pWidth, pHeight));
        isFullScreen = true;
    }

    @Override
    public boolean onInfo(MediaPlayer player, int whatInfo, int extra) {
        // 当一些特定信息出现或者警告时触发
//            System.out.println("INFO = " + whatInfo + "/" + extra);
        switch (whatInfo) {
            case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:

                break;
            case MediaPlayer.MEDIA_INFO_METADATA_UPDATE:

                break;
            case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:

                break;
            case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
                System.out.println("INFO:MEDIA_INFO_NOT_SEEKABLE");
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (player.isPlaying()) {
                    player.pause();
                    mLoadingView.setVisibility(View.VISIBLE);
                }
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                player.start();
                mLoadingView.setVisibility(View.GONE);
                break;
            case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                mLoadingView.setVisibility(View.GONE);
                break;
        }

        return false;

    }

    @Override
    public boolean onError(MediaPlayer player, int whatError, int extra) {
//            System.out.println("onError called");
        switch (whatError) {
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                System.out.println("MEDIA_ERROR_SERVER_DIED");

                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:

                System.out.println("MEDIA_ERROR_UNKNOWN");
                break;

            default:
                break;
        }
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer player) {
        // 当MediaPlayer播放完成后触发
//        System.out.println("onComletion called");
        this.finish();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        tvBufferUpdatePercent.setText("正在缓冲 " + percent + "%");
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        /**
         * 单击
         */
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (mySimpleController.getVisibility() == View.GONE) {
                mySimpleController.setVisibility(View.VISIBLE);
                hideControllerDelay();
            } else {
                cancelDelayHide();
                hideController();
            }
            return true;
        }

        /**
         * 双击
         */
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (isFullScreen)
                setOriginal();
            else
                setFullScreen();
            return true;
        }

        /**
         * 滑动
         */
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float mOldX = e1.getX(), mOldY = e1.getY();
            int x = (int) e2.getRawX();
            int y = (int) e2.getRawY();
            Display disp = getWindowManager().getDefaultDisplay();
            int windowWidth = disp.getWidth();
            int windowHeight = disp.getHeight();

            if (mOldX > windowWidth * 4.0 / 5)// 右边滑动
                onVolumeSlide((mOldY - y) / windowHeight);
            else if (mOldX < windowWidth / 5.0)// 左边滑动
                onBrightnessSlide((mOldY - y) / windowHeight);
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }

    /**
     * 定时隐藏
     */
    private Handler mDismissHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            mVolumeBrightnessLayout.setVisibility(View.GONE);
            return false;
        }
    });

    /**
     * 滑动改变声音大小
     *
     * @param percent
     */

    private void onVolumeSlide(float percent) {
        if (mVolume == -1) {
            mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (mVolume < 0)
                mVolume = 0;

            // 显示
            mOperationBg.setImageResource(R.mipmap.video_volumn_bg);
            mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
        }

        int index = (int) (percent * mMaxVolume) + mVolume;
        if (index > mMaxVolume)
            index = mMaxVolume;
        else if (index < 0)
            index = 0;

        // 变更声音
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);

        // 变更进度条
        ViewGroup.LayoutParams lp = mOperationPercent.getLayoutParams();
        lp.width = findViewById(R.id.operation_full).getLayoutParams().width * index / mMaxVolume;
        mOperationPercent.setLayoutParams(lp);
    }

    /**
     * 滑动改变亮度
     *
     * @param percent
     */
    private void onBrightnessSlide(float percent) {
        if (mBrightness < 0) {
            mBrightness = getWindow().getAttributes().screenBrightness;
            if (mBrightness <= 0.00f)
                mBrightness = 0.50f;
            if (mBrightness < 0.01f)
                mBrightness = 0.01f;

            // 显示
            mOperationBg.setImageResource(R.mipmap.video_brightness_bg);
            mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
        }
        WindowManager.LayoutParams lpa = getWindow().getAttributes();
        lpa.screenBrightness = mBrightness + percent;
        if (lpa.screenBrightness > 1.0f)
            lpa.screenBrightness = 1.0f;
        else if (lpa.screenBrightness < 0.01f)
            lpa.screenBrightness = 0.01f;
        getWindow().setAttributes(lpa);

        ViewGroup.LayoutParams lp = mOperationPercent.getLayoutParams();
        lp.width = (int) (findViewById(R.id.operation_full).getLayoutParams().width * lpa.screenBrightness);
        mOperationPercent.setLayoutParams(lp);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.start();
        }
    }

    /***
     * 双击Back键退出
     */
    private long lastBackKeyTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                // 按下返回键
                long delay = Math.abs(System.currentTimeMillis() - lastBackKeyTime);
                if (delay > 3000) {
                    // 双击退出程序
                    AppToast.showToast(R.string.toast_key_backplay);
                    lastBackKeyTime = System.currentTimeMillis();
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
        AccountApp.activityList.remove(this);
    }
}
