package com.example.qrscanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {


    String[] items = {"English","Arabic","French","German","Russian"};
    Button scan;
    public static String language;
    Context context;
    Resources resources;

    AutoCompleteTextView auto_completeTxt;
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scan=findViewById(R.id.scan);
        auto_completeTxt=findViewById(R.id.auto_complete_txt);
        arrayAdapter = new ArrayAdapter<String>(this,R.layout.drop_down,items);
        auto_completeTxt.setAdapter(arrayAdapter);
        getSupportActionBar().setTitle("QR Scan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        auto_completeTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                arrayAdapter = new ArrayAdapter<String>(MainActivity.this,R.layout.drop_down,items);
                auto_completeTxt.setAdapter(arrayAdapter);
                 language = parent.getItemAtPosition(position).toString();

                if (language=="English") {
                    setTitle("Home");
                    context = LocaleHelper1.setLocale(MainActivity.this, "en");
                    resources= context.getResources();
                    scan.setText(resources.getString(R.string.language));
                }
                else if (language=="Arabic") {
                    setTitle("الصفحة الرئيسية");
                    context = LocaleHelper1.setLocale(MainActivity.this, "ar");
                    resources= context.getResources();
                    scan.setText(resources.getString(R.string.language));
                }
                else if (language=="French") {
                    setTitle("page d'accueil");
                    context = LocaleHelper1.setLocale(MainActivity.this, "fr");
                    resources= context.getResources();
                    scan.setText(resources.getString(R.string.language));
                }
                else if (language=="German") {
                    setTitle("Startseite");
                    context = LocaleHelper1.setLocale(MainActivity.this, "de");
                    resources= context.getResources();
                    scan.setText(resources.getString(R.string.language));
                }
                else if (language=="Russian") {
                    setTitle("домашняя страница");
                    context = LocaleHelper1.setLocale(MainActivity.this, "ru");
                    resources= context.getResources();
                    scan.setText(resources.getString(R.string.language));
                }
                //Toast.makeText(getApplicationContext(), "lan:" + language, Toast.LENGTH_SHORT).show();
            }
        });
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scan(view);
            }
        });

    }
    public void scan(View v){
        IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
        intentIntegrator.setPrompt("For flash use volume up key");
        intentIntegrator.setCameraId(0);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setCaptureActivity(capture.class);
        intentIntegrator.initiateScan();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult.getContents()!=null){
            //Intent i =new Intent(this, result.class);
            //i.putExtra("cool",intentResult.getContents());
            //startActivity(i);
            //((TextView)findViewById(R.id.textView)).setText(intentResult.getContents());
            Backend backend= new Backend(this);
            backend.execute(intentResult.getContents(),language);


        }
    }
}