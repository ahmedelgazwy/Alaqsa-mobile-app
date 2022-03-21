package com.example.qrscanner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Backend extends AsyncTask<String,Void,String> {
    AlertDialog dialog;
    Context context;
    public Backend(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog= new AlertDialog.Builder(context).create();
        dialog.setTitle("output");
    }

    @Override
    protected void onPostExecute(String s) {
        //dialog.setMessage(s);
        //dialog.show();
        Log.d("dawd",s);
        //dialog.getWindow().setLayout(2000, 2000)
        Intent i =new Intent(this.context, result.class);
        i.putExtra("cool",s);
        context.startActivity(i);
    }

    @Override
    protected String doInBackground(String... voids) {

        String result="";
        String qr = voids[0];
        String language = voids[1];
        Log.d("aaaa",language);
        String connstr = "http://199.212.33.168:8889/index.php";
        try {
            URL url= new URL(connstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setDoInput(true);
            OutputStream ops = http.getOutputStream();
            BufferedWriter writer= new BufferedWriter(new OutputStreamWriter(ops, StandardCharsets.UTF_8));
            String data = URLEncoder.encode("QR_code","UTF-8")+"="+URLEncoder.encode(qr,"UTF-8")
                    +"&&"+URLEncoder.encode("Language","UTF-8")+"="+URLEncoder.encode(language,"UTF-8")  ;
            //+"&&"+URLEncoder.encode("Language","UTF-8")+"="+URLEncoder.encode(language,"UTF-8")
            writer.write(data);
            writer.flush();
            writer.close();
            ops.close();

            InputStream ips = http.getInputStream();
            BufferedReader reader= new BufferedReader(new InputStreamReader(ips, "UTF-8"));
            String line="";
            while ((line= reader.readLine()) != null){
                result+=line;
            }
            Log.d("lmans",result);
            reader.close();
            ips.close();
            http.disconnect();
            return result+"separator"+language;
        } catch (MalformedURLException e) {
            result= e.getMessage();
        } catch (IOException e) {
            result=e.getMessage();
        }
        return result;
    }
}
