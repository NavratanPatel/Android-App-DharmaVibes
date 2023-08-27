package com.project.navratan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;


public class Activity3 extends AppCompatActivity {
    @Override
    protected void onDestroy() {
        super.onDestroy();
        music.stop();
        music.release();
        updateSeek.interrupt();
    }

    private Button button4;
    private ImageButton imgBtn;
    private SeekBar seekBar;
    private MediaPlayer music;
    private Thread updateSeek;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        //bar color
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#9516E2"));
        actionBar.setBackgroundDrawable(colorDrawable);

        //Music
        imgBtn=findViewById(R.id.imgBtn);
        seekBar=findViewById(R.id.seekBar);
        music=MediaPlayer.create(this,R.raw.krishna);
        //music.start();
        seekBar.setMax(music.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean user) {
                if(user)
                {
                    music.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                music.seekTo(seekBar.getProgress());
            }
        });
        //Updating Seek bar Using Java Thread
        updateSeek = new Thread(){
            @Override
            public void run() {
                int currentPosition = 0;
                try{
                    while(currentPosition<music.getDuration()){
                        currentPosition = music.getCurrentPosition();
                        seekBar.setProgress(currentPosition);
                        sleep(800);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        updateSeek.start();

        ///Music Button
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(music.isPlaying())
                {
                    music.pause();
                    imgBtn.setImageResource(R.drawable.play);
                }
                else
                {
                    music.start();
                    imgBtn.setImageResource(R.drawable.pause);
                }
            }
        });
    }

    public void callUs(View v1)
    {
        button4=findViewById(R.id.button4);
        String phoneNumber="9636804831";
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void emailUs(View v2)
    {
        String []addresses={"navratan091@gmail.com"};
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback About Us");
        //intent.putExtra(Intent.EXTRA_STREAM, "");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}