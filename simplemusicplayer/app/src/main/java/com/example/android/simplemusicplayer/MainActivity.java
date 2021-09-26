package com.example.android.simplemusicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
        public boolean isrunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.in_the_end);
        Button playbutton = findViewById(R.id.play_button);
        Button pausebutton = findViewById(R.id.pause_button);
        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
            }
        });
        pausebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                // mediaPlayer.seekTo(0); // when the pause button is pressed and again when play button is clicked then the song will start from starting
            }
        });
        SeekBar seekprog=findViewById(R.id.seekBar);
        seekprog.setMax(mediaPlayer.getDuration());
        // setting automatic progress of seek bar along with music through timer, delay=0 means that there should be no delay to start progress
        //period means after how much time we want to update the progress of seek bar
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                isrunning=true;// this one is used to handle the lagging issue in the music due to updation in progress of seek bar
                seekprog.setProgress(mediaPlayer.getCurrentPosition());
            }
        },0,100);
        // to jump to the different timeline when changed by user
        seekprog.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(!isrunning)
                    mediaPlayer.seekTo(progress);
                else
                    isrunning=!isrunning;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}