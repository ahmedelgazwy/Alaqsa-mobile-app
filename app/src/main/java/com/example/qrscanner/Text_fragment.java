package com.example.qrscanner;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;


public class Text_fragment extends Fragment {
    private static  String ARG_PARAM1 ;
    private static  String ARG_PARAM2 ;
    private String text;
    private String img;
    private ImageSlider imageSlider;
    public String[] img_links;



    public Text_fragment() {
        // Required empty public constructor
    }
    public static Text_fragment newInstance(String text) {
        Text_fragment fragment = new Text_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, text);

        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onResume(){
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Text");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            text = getArguments().getString(ARG_PARAM1);

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_text_fragment, container, false);
        ((TextView) v.findViewById(R.id.Result)).setText(text);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Text");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

         return v;
    }
}