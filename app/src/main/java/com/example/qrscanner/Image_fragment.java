package com.example.qrscanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;


public class Image_fragment extends Fragment {
    private static  String ARG_PARAM1 ;
    private static  String ARG_PARAM2 ;
    private String text;
    private String img;
    private ImageSlider imageSlider;
    public String[] img_links;



    public Image_fragment() {
        // Required empty public constructor
    }
    public static Image_fragment newInstance(String img) {
        Image_fragment fragment = new Image_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM2, img);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onResume(){
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Image");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            img = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_image_fragment, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Image");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        img_links=img.split(",");
        imageSlider = v.findViewById(R.id.imgslider);
        ArrayList<SlideModel> images = new ArrayList<>();
        for(String iterator  : img_links )
        {
            images.add(new SlideModel(iterator,null));
        }
        imageSlider.setImageList(images);
         return v;
    }
}