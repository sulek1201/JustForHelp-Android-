package com.example.andy.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity{
    private final int REQ_CODE = 100;
    TextView textView;
    String durakName = null;
    String[] durakList = {"Mecidiyeköy", "Okmeydanı", "Topkapı", "Zincirlikuyu"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        ImageView speak = findViewById(R.id.speak);
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        "Need to speak");
                try {
                    startActivityForResult(intent, REQ_CODE);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            "Sorry your device not supported",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean isNameTrue = true;
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    durakName = result.get(0);
                    for(int j = 0; j < durakList.length; j++){
                        if(durakName.equals(durakList[j])){
                            textView.setText(result.get(0));
                            isNameTrue = false;
                        }
                    }
                }
                if(isNameTrue){
                    textView.setText("Böyle bir durak bulunamadı. Lütfen tekrar deneyin");
                }
                break;
            }
        }
        for(int i = 0; i < durakList.length; i++){
            if(durakName.equals(durakList[i])){
                isNameTrue = true;
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                intent.putExtra("durakİsmi", durakList[i]);
                startActivity(intent);
            }
        }
        if(isNameTrue){
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}