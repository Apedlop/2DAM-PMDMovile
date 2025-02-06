package com.example.prop_06;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private ImageButton btnRecord, btnStop, btnPlay;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String audio;
    private boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRecord = findViewById(R.id.btnRecord);
        btnStop = findViewById(R.id.btnStop);
        btnPlay = findViewById(R.id.btnPlay);

        // Desabilitar botones
        btnStop.setEnabled(false);
        btnPlay.setEnabled(false);

        // Para localizar el auido de una ruta de almacenaje externo
        audio = getExternalFilesDir(null).getAbsolutePath() + "/audio.3gp";

        // En el caso de que la aplicación no tenga los permisos requeridos, mostrarlos
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        // Acción de los botones
        btnRecord.setOnClickListener(v -> startRecording());
        btnStop.setOnClickListener(v -> stopRecording());
        btnPlay.setOnClickListener(v -> playRecording());
    }

    // Iniciar grabación
    private void startRecording() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); // Micrófono del teléfono como fuente de audio
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); // Formato de salida del archivo de audio (común)
        mediaRecorder.setOutputFile(audio); // Ruta del archivo de audio
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); // Establecer el codificador de audio (AMR_NB es un formato comprimido para voz

        try {
            mediaRecorder.prepare(); // Preparar la instancia
            mediaRecorder.start(); // Inciar la grabación del audio
            isRecording = true; // Indicar que la grabación está en curso
            Toast.makeText(this, "Grabación iniciada", Toast.LENGTH_SHORT).show();
            btnRecord.setEnabled(false);
            btnStop.setEnabled(true); // Activar botón para parar el audio
            btnPlay.setEnabled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        if (isRecording) {
            mediaRecorder.stop(); // Parar la grabación de audio
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false; // Indicar que la grabación ha terminado
            Toast.makeText(this, "Grabación detenida", Toast.LENGTH_SHORT).show();
            btnRecord.setEnabled(true);
            btnStop.setEnabled(false);
            btnPlay.setEnabled(true); // Activar botón de reproducir el audio
        }
    }

    private void playRecording() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audio); // Archivo de audio que se va a reproducir
            mediaPlayer.prepare(); // Preparar la reproducción del audio
            mediaPlayer.start(); // Iniciar la reproducción del audio
            Toast.makeText(this, "Reproduciendo grabación", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
