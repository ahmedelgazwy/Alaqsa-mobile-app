package com.example.qrscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
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
    private TextView text_to_audio;
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
    public String stringText;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private String[] titles = new String[] {"Text","Audio","Images"};
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
        Log.d("separator",Results[0]);
        audio =Results[1];
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL textUrl;
                try{
                    textUrl= new URL(Results[0]);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(textUrl.openStream()));
                    String SreingBuffer;
                    while ((SreingBuffer = bufferedReader.readLine())!=null){
                        stringText += SreingBuffer;
                    }
                    bufferedReader.close();
                }catch (IOException e){
                    Log.d("error",e.toString());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView) findViewById(R.id.Result)).setText(stringText);
                    }
                });
            }
        }).start();


        tabLayout = findViewById(R.id.tablayout);
        viewPager2 = findViewById(R.id.view_pager);
        viewPager2.setUserInputEnabled(false);
        //</ get elements >
        adapter adapter = new adapter(this,msg,stringText);



        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(titles[position]);
                    }
                }).attach();


    }

}


