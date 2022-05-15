package com.example.qrscanner;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.gauravk.audiovisualizer.visualizer.BarVisualizer;
import com.squareup.picasso.Picasso;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Audio_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Audio_fragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
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
    private TextView back_to_text;
    private MediaPlayer mPlayer;
    private Handler mHandler;
    private Runnable mRunnable;
    float x1,x2,y1,y2;
    int length;
    int audioid;
    public String msg;
    public static String[] msg1;


    public Audio_fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters

  /*  @Override
    public void onStop() {
        if(mPlayer!=null){
            mPlayer.stop();
            mPlayer.release();// this will clear memory
            mPlayer = null;
        }
        super.onStop();
    }*/
    @Override
    public void onPause() {
        super.onPause();
        mButtonPlay.setBackgroundResource(R.drawable.ic_play);
        mPlayer.pause();
    }


    public static Audio_fragment newInstance(String param1) {
        Audio_fragment fragment = new Audio_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onResume(){
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Audio");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            msg = getArguments().getString(ARG_PARAM1);
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_audio_fragment, container, false);
        msg1= msg.split(",,,");
       // mContext = mContext.getApplicationContext();
        mButtonPlay = v.findViewById(R.id.playbtn);
        //visualizer= findViewById(R.id.blast);
        mButtonFastfarward = v.findViewById(R.id.forwardbtn);
        imageView = v.findViewById(R.id.imgageveiw);
        Picasso.get().load(msg1[1]).into(imageView);
        mButtonrewind = v.findViewById(R.id.rewinddbtn);
        mSeekBar = v.findViewById(R.id.seekbar);
        mPass = v.findViewById(R.id.txtstart);
        mDuration = v.findViewById(R.id.txtstop);
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

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Audio");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
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
        return v;
    }
   /* protected void stopPlaying() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
            Toast.makeText(mContext, "Stop playing.", Toast.LENGTH_SHORT).show();
            if (mHandler != null) {
                mHandler.removeCallbacks(mRunnable);
            }
        }
    }*/

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
}