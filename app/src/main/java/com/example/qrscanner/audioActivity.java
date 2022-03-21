package com.example.qrscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gauravk.audiovisualizer.visualizer.BarVisualizer;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class audioActivity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;
    private BarVisualizer visualizer;

    private Button mButtonPlay;
    private ImageView imageView;

    private Button mButtonFastfarward;
    private Button mButtonrewind;

    private SeekBar mSeekBar;

    private TextView mPass;
    private TextView mDuration;
    private TextView mDue;

    private MediaPlayer mPlayer;
    private Handler mHandler;
    private Runnable mRunnable;
    float x1,x2,y1,y2;
    int length;
    int audioid;

   /* @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    protected void onDestroy() {
        if (visualizer != null) {
            visualizer.release();
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        if(mPlayer!=null){
            mPlayer.stop();
            mPlayer.release();// this will clear memory
            mPlayer = null;
        }
        super.onStop();
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("aagg","wslna");
        setContentView(R.layout.audioactivity);
        Intent i = getIntent();
        String msg = i.getStringExtra("audio");
        String[] msg1= msg.split(",,,");

        mContext = getApplicationContext();
        mActivity = audioActivity.this;
        mButtonPlay = findViewById(R.id.playbtn);
        //visualizer= findViewById(R.id.blast);
        mButtonFastfarward = findViewById(R.id.forwardbtn);
        imageView = findViewById(R.id.imgageveiw);
        Picasso.get().load(msg1[1]).into(imageView);
        mButtonrewind = findViewById(R.id.rewinddbtn);
        mSeekBar = findViewById(R.id.seekbar);
        mPass = findViewById(R.id.txtstart);
        mDuration = findViewById(R.id.txtstop);
        //mDue = findViewById(R.id.tv_due);
        mHandler = new Handler();
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(msg1[0]);
            mPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.start();
        getAudioStats();
        initializeSeekBar();

        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mPlayer.seekTo(0);
                mButtonPlay.setBackgroundResource(R.drawable.ic_play);
                mPlayer.pause();
            }
        });
        mButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mPlayer.isPlaying()){
                    mButtonPlay.setBackgroundResource(R.drawable.ic_play);
                    mPlayer.pause();


                }
                else{
                    mButtonPlay.setBackgroundResource(R.drawable.ic_pause);
                    mPlayer.start();
                }

            }
        });
        /*audioid = mPlayer.getAudioSessionId();

        Log.d("yarb",String.valueOf(audioid));
        if(audioid != -1){
            visualizer.setAudioSessionId(audioid);
        }*/

        mButtonFastfarward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.seekTo(mPlayer.getCurrentPosition()+5000);
            }
        });

        mButtonrewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.seekTo(mPlayer.getCurrentPosition()-5000);
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
        });
    }

    protected void stopPlaying() {
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
        //mDue.setText("" + due + " seconds");
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
                mHandler.postDelayed(mRunnable, 300);
            }
        };
        mHandler.postDelayed(mRunnable, 300);
    }
    public boolean onTouchEvent(@NonNull MotionEvent touchevent){
        switch (touchevent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchevent.getX();
                y1 = touchevent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchevent.getX();
                y2 = touchevent.getY();
                if(x1<x2){
                    Log.d("aaaaa","ya rbbbb");
                    Intent i = new Intent(this.mContext, result.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    mContext.startActivity(i);
                    break;
                }

        }
        return false;
    }


}

