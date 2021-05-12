package com.example.andy.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import static java.lang.Thread.sleep;

public class MainActivity2 extends AppCompatActivity implements AsyncResponse {

    String path = "http://20.98.105.229/";
    TextToSpeech tts;
    Button yesButton;
    Button noButton;
    public static String jsonPath;
    TextView textView;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        yesButton = findViewById(R.id.button);
        noButton = findViewById(R.id.button2);
        textView = findViewById(R.id.textView);
        Intent intent = getIntent();
        String durakName = intent.getStringExtra("durakİsmi");
        ImageView i = (ImageView) findViewById(R.id.imageView);
        Picasso.get().load(path + durakName + "/1.jpg").into(i);
        jsonPath = path + durakName + "/1.json";
        tts = new TextToSpeech(MainActivity2.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                // TODO Auto-generated method stub
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("error", "This Language is not supported");
                    } else {
                        ConvertTextToSpeech();
                    }
                } else
                    Log.e("error", "Initilization Failed!");
            }
        });

        GetData getData = new GetData();
        getData.delegate = this;
        getData.execute();
    }

    @Override
    protected void onPause() {
        if(tts != null){
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }

    public void onClick(View v){
        ConvertTextToSpeech();
    }

    private void ConvertTextToSpeech() {
        tts.speak("If you like to see more available stations please click the yes button", TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void processFinish(String output) {
        JSONObject jsonObject = null;
        int kisiSayisi = 0;
        try {
            jsonObject = new JSONObject(output);
            kisiSayisi = jsonObject.getInt("sayi");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        textView.setText("Duraktaki kişi sayısı: " + kisiSayisi);
    }
}