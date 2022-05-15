package com.example.qrscanner;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class adapter extends FragmentStateAdapter {
public String msg;
public String[] submsg;
public String[] Results;
public String audio;
public String[] img_links;
public String final_result;
public String stringtext1;
    public adapter(@NonNull FragmentActivity fragmentActivity, String data,String stringtext)
    {

        super(fragmentActivity);
        msg=data;
        stringtext1=stringtext;
        submsg =msg.split("separator");
        String language= submsg[1];
        Results = submsg[0].split("///");
        Log.d("separator",language);
        audio =Results[1];
        img_links=Results[2].split(",");
        final_result = audio+",,,"+img_links[0];
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                 fragment = Text_fragment.newInstance(stringtext1);
                 return fragment;
            case 1:
                 fragment = Audio_fragment.newInstance(final_result);
                return fragment;
            case 2:
                fragment = Image_fragment.newInstance(Results[2]);
                return fragment;

        }
        return Text_fragment.newInstance(Results[0]);
    }
    @Override
    public int getItemCount() {return 3; }
}
