package com.example.dinesh.soundwave2;

import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class soundwave2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soundwave2);
        Button button = (Button) findViewById(R.id.button);
        final EditText name = (EditText) findViewById(R.id.editText);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                int biname = Integer.parseInt(name.getText().toString());

               String biname1 = Integer.toBinaryString(biname);

                int length = biname1.length();
                ToneGenerator toneG1 = new ToneGenerator(AudioManager.STREAM_ALARM, ToneGenerator.MAX_VOLUME);
                //ToneGenerator toneG2 = new ToneGenerator(AudioManager.STREAM_ALARM, ToneGenerator.MAX_VOLUME);
                for(int i=0;i<length;i++) {
                    if (biname1.charAt(i)== '1'){
                        toneG1.startTone(ToneGenerator.TONE_DTMF_6);
                        //int a =toneG1.getAudioSessionId();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        toneG1.stopTone();
                    }
                    else {
                        toneG1.startTone(ToneGenerator.TONE_CDMA_DIAL_TONE_LITE);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                      toneG1.stopTone();
                    }
                }

            }
        });

    }
}
