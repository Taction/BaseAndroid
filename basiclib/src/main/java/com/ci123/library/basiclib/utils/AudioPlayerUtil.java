package com.ci123.library.basiclib.utils;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.os.Message;



import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class AudioPlayerUtil implements OnBufferingUpdateListener, OnCompletionListener, MediaPlayer.OnPreparedListener{

    private MediaPlayer mediaPlayer;

    private boolean isPlaying = false;
    private AnimationDrawable anim;

    public AudioPlayerUtil()
    {
        init();

        Timer mTimer = new Timer();
        mTimer.schedule(mTimerTask, 0, 1000);
    }

    private void init(){
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过定时器和Handler来更新进度条
     */
    private TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            if(mediaPlayer==null)
                return;
            if (mediaPlayer.isPlaying() ) {
                handleProgress.sendEmptyMessage(0);
            }
        }
    };

    private Handler handleProgress = new Handler() {
        public void handleMessage(Message msg) {
            int position = mediaPlayer.getCurrentPosition();
            int duration = mediaPlayer.getDuration();
        };
    };

    public void play()
    {
        mediaPlayer.start();
    }

    public void playUrl(String videoUrl, Context context, AnimationDrawable anim, boolean isNetResource)
    {
        if (mediaPlayer == null){
            init();
        }
        this.anim = anim;
        isPlaying = true;
        try {
            mediaPlayer.reset();
//            if (isNetResource) {
//                HttpProxyCacheServer proxy = MyChatConfig.getProxy(context);
//                if (videoUrl.substring(0, 4).equals("http")) {
//                    mediaPlayer.setDataSource(proxy.getProxyUrl(videoUrl));
//                } else {
//                    mediaPlayer.setDataSource(proxy.getProxyUrl(MyChatConfig.getChatUrl() + videoUrl));
//                }
//            } else {
                mediaPlayer.setDataSource(videoUrl);
//            }
            mediaPlayer.prepare();//prepare之后自动播放
            //mediaPlayer.start();
        } catch (IllegalArgumentException | IllegalStateException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void pause()
    {
        isPlaying = false;
        mediaPlayer.pause();
    }

    public void stop()
    {
        if (anim.isRunning()){
            anim.selectDrawable(0);
            anim.stop();
        }
        if (mediaPlayer != null) {
            isPlaying = false;
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /**
     * 通过onPrepared播放
     */
    @Override
    public void onPrepared(MediaPlayer arg0) {
        arg0.start();
    }

    @Override
    public void onCompletion(MediaPlayer arg0) {
        isPlaying = false;
        if (anim.isRunning()) {
            anim.selectDrawable(0);
            anim.stop();
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer arg0, int bufferingProgress) {
    }

    public boolean isPlaying(){
        return isPlaying;
    }

}
