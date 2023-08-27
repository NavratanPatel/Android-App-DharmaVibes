package com.project.navratan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;

public class Activity2 extends AppCompatActivity {
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        updateSeek.interrupt();
    }

    public Button button2,button3;
    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar1;
    private ImageView imgView;
    Thread updateSeek;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        //bar color
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#9516E2"));
        assert actionBar != null;
        actionBar.setBackgroundDrawable(colorDrawable);

        //view xml to java
        imgView=findViewById(R.id.imgView);
        surfaceView=findViewById(R.id.surfaceView);
        seekBar1=findViewById(R.id.seekBar1);
        mediaPlayer=MediaPlayer.create(this,R.raw.mahabharat);

        ///Video Playing
        surfaceView.setKeepScreenOn(true);
        SurfaceHolder surfaceHolder=surfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                mediaPlayer.setDisplay(surfaceHolder);
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

            }
        });

        //Using Glide For Thumbnail

        ///seek bar setup
        seekBar1.setMax(mediaPlayer.getDuration());
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean user) {
                if(user)
                {
                    mediaPlayer.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        //Updating Seek bar Using Java Thread
        updateSeek = new Thread(){
            @Override
            public void run() {
                int currentPosition = 0;
                try{
                    while(currentPosition<mediaPlayer.getDuration()){
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekBar1.setProgress(currentPosition);
                        sleep(800);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        updateSeek.start();

    }
    public void openUrl(View v1)
    {
        button2=findViewById(R.id.button2);
        String url="https://www.youtube.com/@NavratanPatel";
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    //next button
    public void moveNext(View v1)
    {
        mediaPlayer.stop();
        button3=findViewById(R.id.button3);
        Intent intent=new Intent(this,Activity3.class);
        startActivity(intent);
    }

    public void vidAction(View v2)
    {
        if(mediaPlayer.isPlaying())
        {
            mediaPlayer.pause();
            imgView.setImageResource(R.drawable.play_circle);
        }
        else
        {
            mediaPlayer.start();
            imgView.setImageResource(R.drawable.pause_arrow);
        }
    }
}