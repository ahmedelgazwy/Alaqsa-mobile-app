package com.example.qrscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.io.IOException;
import java.util.ArrayList;


public class result extends AppCompatActivity {
    static String msg=null;
    private Context mContext;
    private Activity mActivity;
    private ImageSlider imageSlider;
    private Button mButtonPlay;
    private Button mButtonPause;
    private Button mButtonResume;
    private Button mButtonStop;

    private SeekBar mSeekBar;

    private TextView mPass;
    private TextView mDuration;
    private TextView mDue;

    private MediaPlayer mPlayer;
    private Handler mHandler;
    private Runnable mRunnable;

    int length;
    float x1,x2,y1,y2;
    public String audio;
    public String[] img_links;
    public String final_result;
    public String[] submsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent i = getIntent();
        if(i.getStringExtra("cool")!=null) {
            msg = i.getStringExtra("cool");
        }
        submsg =msg.split("separator");
        String language= submsg[1];
        String[] Results = submsg[0].split("///");
        Log.d("separator",language);
        audio =Results[1];
        if(language.equals("English"))
        {setTitle("Text and Images");}
        else if(language.equals("Arabic"))
        {Log.d("offf","ana hna");
        setTitle("النصوص والصور");}
        else if (language.equals("French"))
        {setTitle("Texte et images");}
        else if(language.equals("German"))
        {setTitle("Text und Bilder");}
        else if(language.equals("Russian"))
        {setTitle("Текст и изображения");}

        img_links=Results[2].split(",");
        ((TextView) findViewById(R.id.Result)).setText(Results[0]);
        //setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        mActivity = result.this;
        imageSlider = findViewById(R.id.imgslider);
        ArrayList<SlideModel> images = new ArrayList<>();
        for(String iterator  : img_links )
        {
            images.add(new SlideModel(iterator,null));
        }
        images.add(new SlideModel(R.drawable.background,null));
        images.add(new SlideModel("https://wegotthiscovered.com/wp-content/uploads/2021/07/Ultra-Instinct-Goku-640x321.jpg",null));
        images.add(new SlideModel(R.drawable.background3,null));
        imageSlider.setImageList(images);
        //Log.v("okkkk",  audio);
        /*mButtonPlay = findViewById(R.id.btn_play);
        mButtonPause = findViewById(R.id.btn_pause);
        mButtonResume = findViewById(R.id.btn_res);
        mButtonStop = findViewById(R.id.btn_stop);
        mSeekBar = findViewById(R.id.seek_bar);
        mPass = findViewById(R.id.tv_pass);
        mDuration = findViewById(R.id.tv_duration);
        mDue = findViewById(R.id.tv_due);
        mHandler = new Handler();
        mButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPlaying();
                //String audioUrl = '';
                String audioUrl = Results[1];
                mPlayer = new MediaPlayer();
                try {
                    mPlayer.setDataSource(audioUrl);
                    mPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mPlayer.start();
                Toast.makeText(mContext, "Media Player is playing.", Toast.LENGTH_SHORT).show();
                getAudioStats();
                initializeSeekBar();
            }
        });

        mButtonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.pause();
                length = mPlayer.getCurrentPosition();
            }
        });
        mButtonResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.seekTo(length);
                mPlayer.start();
            }
        });

        mButtonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPlaying();
            }
        });
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (mPlayer != null && b) {
                    mPlayer.seekTo(i * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });*/
    }



    /*protected void stopPlaying() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
            Toast.makeText(mContext, "Stop playing.", Toast.LENGTH_SHORT).show();
            if (mHandler != null) {
                mHandler.removeCallbacks(mRunnable);
            }
        }
    }

    protected void getAudioStats() {
        int duration = mPlayer.getDuration() / 1000; // In milliseconds
        int due = (mPlayer.getDuration() - mPlayer.getCurrentPosition()) / 1000;
        int pass = duration - due;
        mPass.setText("" + pass + " seconds");
        mDuration.setText("" + duration + " seconds");
        mDue.setText("" + due + " seconds");
    }

    protected void initializeSeekBar() {
        mSeekBar.setMax(mPlayer.getDuration() / 1000);
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (mPlayer != null) {
                    int mCurrentPosition = mPlayer.getCurrentPosition() / 1000; // In milliseconds
                    mSeekBar.setProgress(mCurrentPosition);
                    getAudioStats();
                }
                mHandler.postDelayed(mRunnable, 1000);
            }
        };
        mHandler.postDelayed(mRunnable, 1000);
    }*/

    public boolean onTouchEvent(@NonNull MotionEvent touchevent){
        switch (touchevent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchevent.getX();
                y1 = touchevent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchevent.getX();
                y2 = touchevent.getY();
                if(x1>x2){
                    Log.d("aaaaa","ya rbbbb");
                    Intent i = new Intent(this.mContext, audioActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //i.putExtra("audio",audio);
                    final_result = audio+",,,"+img_links[0];

                    i.putExtra("audio",final_result);
                    mContext.startActivity(i);
                    break;
                }

        }
        return false;
    }
}


