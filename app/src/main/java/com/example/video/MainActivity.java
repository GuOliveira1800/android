package com.example.video;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    SeekBar progressBar, volume;
    TextView texto;
    ImageButton btnPlay,btnStop,btnPause;
    AudioManager audioManager;

    @SuppressLint("ServiceCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this,R.raw.musica);

        btnPlay = findViewById(R.id.imageButton);

        btnPause = findViewById(R.id.imageButton4);

        btnStop = findViewById(R.id.imageButton3);

        texto = findViewById(R.id.texto);

        progressBar = findViewById(R.id.seekBar);

        volume = findViewById(R.id.seekBar3);

        progressBar.setMax(mediaPlayer.getDuration());

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        timer();

        inicializa();

        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                    mediaPlayer.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
            }
        });

    }

    public void timer(){
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                progressBar.setProgress(mediaPlayer.getCurrentPosition());
                //texto.setText(convertDurationMillis(mediaPlayer.getDuration())+"/"+convertDurationMillis(mediaPlayer.getCurrentPosition()));

            }
        },0,1);
    }

    public void inicializa(){
        int volumeMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int atual = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        volume.setMax(volumeMax);
        volume.setProgress(atual);

    }

    public String convertDurationMillis(Integer getDurationInMillis){

        int getDurationMillis = getDurationInMillis;
        String convertHours = String.format("%02d", TimeUnit.MILLISECONDS.toHours(getDurationMillis));
        String convertMinutes = String.format("%02d", TimeUnit.MILLISECONDS.toMinutes(getDurationMillis));
        String convertSeconds = String.format("%02d", TimeUnit.MILLISECONDS.toSeconds(getDurationMillis) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(getDurationMillis)));

        String getDuration = convertHours + ":" + convertMinutes + ":" + convertSeconds;

        return getDuration;
    }
}