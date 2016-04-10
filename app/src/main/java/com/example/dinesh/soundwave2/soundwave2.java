package com.example.dinesh.soundwave2;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.sql.Time;

import android.app.Activity;
import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dinesh.soundwave2.R;

import org.w3c.dom.Text;


public class soundwave2 extends Activity {
    public static final int SAMPLE_RATE = 8000;

    private AudioRecord mRecorder;
    private File mRecording;
    private short[] mBuffer;
    private final String startRecordingLabel = "Start recording";
    private final String stopRecordingLabel = "Stop recording";
    private boolean mIsRecording = false;
    private ProgressBar mProgressBar;
    private String data = "";
    private TextView t;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soundwave2);
        t=(TextView)findViewById(R.id.data);
        initRecorder();

        final Button start = (Button) findViewById(R.id.btnStart);
        start.setText(startRecordingLabel);

        //final Button stop = (Button) findViewById(R.id.btnStop);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (!mIsRecording) {
                    start.setText(stopRecordingLabel);
                    data="";
                    mIsRecording = true;
                    mRecorder.startRecording();
                    // mRecording = getFile("raw");
                    startBufferedWrite();
                } else {
                    start.setText(startRecordingLabel);
                    t.setText(data);
                    mIsRecording = false;
                    mRecorder.stop();
                }
            }
        });
    }
        public void onDestroy() {
            mRecorder.release();
            super.onDestroy();
        }

    private void initRecorder() {
        int bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT);
        mBuffer = new short[bufferSize];
        mRecorder = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT, bufferSize);
    }

    private void startBufferedWrite() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DataOutputStream output = null;

                int count1=0,count2=0,count3=0;
                //String data="";
                while (mIsRecording) {
                        double sum = 0;
                        int readSize = mRecorder.read(mBuffer, 0, mBuffer.length);
//                    int duration = Toast.LENGTH_SHORT;
//
//                    Toast toast = Toast.makeText(soundwave2.this, readSize, duration);
//                    toast.show();

                    for (int i = 0; i < readSize; i++) {
                           // output.writeShort(mBuffer[i]);
                            sum += mBuffer[i] * mBuffer[i];
                       // pcm = pcm.concat(String.valueOf(mBuffer[i]));


                        }




                    if (readSize > 0) {
                            final double amplitude = sum / readSize;
                        if(amplitude>=1000000&&amplitude<=9000000) {
                            count1++;
                            count2=0;
                            count3=0;
                            if(count1>=5) {
                                data = data.concat("1");
                                count1=0;
                            }
                        }
                        else if(amplitude<1000000){
                            count2++;
                            count1=0;
                            count3=0;
                            if(count2>=5){
                                data = data.concat("0");
                                count2=0;
                            }
                        }
                        else{
                            count1=0;count2=0;count3++;
                            if(count3>=7)
                                break;
                        }
                        Log.d("myTag", String.valueOf(amplitude));
                        }

                }
//                int duration = Toast.LENGTH_SHORT;
//                Context context = getApplicationContext();
//                Toast toast = Toast.makeText(context, data, duration);
//                toast.show();

                int dat = Integer.parseInt(data,2);
                data = Integer.toString(dat);
                Log.d("myTag", data);

            }
        }).start();

    }


    }
