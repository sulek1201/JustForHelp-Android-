package com.example.andy.myapplication;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetData extends AsyncTask<String,String,String> {

    public AsyncResponse delegate = null;
    @Override
    protected String doInBackground(String... strings) {
        System.out.println("deneme deneme");
        String urlString = MainActivity2.jsonPath;
        System.out.println("1201 " + urlString);
        String current = "";
        URL url;
        HttpURLConnection urlConnection = null;
        try{
            try {
                url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(in);
                int data = isr.read();
                while(data != -1){
                    current += (char) data;
                    data = isr.read();
                }
                return current;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
            }
        }
        catch(Exception e){
            System.out.println("sebebi bu:" + e);
        }
        return current;
    }

    @Override
    protected void onPostExecute(String s) {
        try{
            try {
                JSONObject jsonObject = new JSONObject(s);
                delegate.processFinish(jsonObject.toString());
                System.out.println("deneme6 " + jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        catch (Exception e){
            System.out.println("2. sebep: " + e);
        }
    }
}
