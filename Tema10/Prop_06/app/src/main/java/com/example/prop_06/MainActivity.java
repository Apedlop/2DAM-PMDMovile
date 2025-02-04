package com.example.prop_06;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button btnRecord, btnStop, btnPlay;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String filePath;
    private boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRecord = findViewById(R.id.btnRecord);
        btnStop = findViewById(R.id.btnStop);
        btnPlay = findViewById(R.id.btnPlay);

        btnStop.setEnabled(false);
        btnPlay.setEnabled(false);

        filePath = getExternalFilesDir(null).getAbsolutePath() + "/recorded_audio.3gp";

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        btnRecord.setOnClickListener(v -> startRecording());
        btnStop.setOnClickListener(v -> stopRecording());
        btnPlay.setOnClickListener(v -> playRecording());
    }

    private void startRecording() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(filePath);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            isRecording = true;
            Toast.makeText(this, "Grabación iniciada", Toast.LENGTH_SHORT).show();
            btnRecord.setEnabled(false);
            btnStop.setEnabled(true);
            btnPlay.setEnabled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        if (isRecording) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
            Toast.makeText(this, "Grabación detenida", Toast.LENGTH_SHORT).show();
            btnRecord.setEnabled(true);
            btnStop.setEnabled(false);
            btnPlay.setEnabled(true);
        }
    }

    private void playRecording() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            mediaPlayer.start();
            Toast.makeText(this, "Reproduciendo grabación", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
