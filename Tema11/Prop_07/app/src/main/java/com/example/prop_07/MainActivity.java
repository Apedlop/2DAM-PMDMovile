package com.example.prop_07;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private SurfaceView surface;
    private Button btnGrabar, btnDetener, btnPlay;
    private MediaRecorder grabador;
    private MediaPlayer reproductor;
    private SurfaceHolder surfaceHolder;
    private String ruta;
    private boolean grabando = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enlazar elementos del layout con los objetos en Java
        surface = findViewById(R.id.videoView);
        btnGrabar = findViewById(R.id.btnGrabar);
        btnDetener = findViewById(R.id.btnDetener);
        btnPlay = findViewById(R.id.btnPlay);

        // Inicializar MediaRecorder y MediaPlayer
        grabador = new MediaRecorder();
        reproductor = new MediaPlayer();

        surface.getHolder().addCallback(this);
        surfaceHolder = surface.getHolder();
        surfaceHolder.addCallback(this);  // Añadir callback al SurfaceHolder
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        btnGrabar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (grabador != null) {
                    grabador.release(); // Liberar recursos previos antes de reinicializar
                }

                grabador = new MediaRecorder();

                try {
                    grabador.setAudioSource(MediaRecorder.AudioSource.MIC);
                    grabador.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                    grabador.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4); // Debe ir antes que los encoders
                    grabador.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                    grabador.setVideoEncoder(MediaRecorder.VideoEncoder.H264); // H264 es más compatible

                    ruta = getExternalFilesDir(null).getAbsolutePath() + "/mivideo.mp4";
                    grabador.setOutputFile(ruta);
                    grabador.setPreviewDisplay(surfaceHolder.getSurface()); // Importante para video

                    grabador.prepare();
                    grabador.start();
                    grabando = true;

                    // Desactivar grabación y reproducción, activar parada
                    btnGrabar.setEnabled(false);
                    btnPlay.setEnabled(false);
                    btnDetener.setEnabled(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnDetener.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (grabando) {
                    grabador.stop();
                    grabador.release();
                    grabador = null;
                    grabando = false;
                } else {
                    reproductor.stop();
                    reproductor.release();
                    reproductor = null;
                    btnPlay.setText("PLAY");
                }

                btnGrabar.setEnabled(true);
                btnDetener.setEnabled(false);
                btnPlay.setEnabled(true);
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!reproductor.isPlaying()) {
                    try {
                        reproductor.reset(); // Reinicia el MediaPlayer
                        reproductor.setDataSource(ruta); // Establece la ruta del archivo
                        reproductor.setDisplay(surfaceHolder); // Asocia el SurfaceHolder
                        reproductor.prepare(); // Prepara el MediaPlayer
                        reproductor.start(); // Inicia la reproducción
                        btnPlay.setText("PAUSE"); // Cambia el texto del botón
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    reproductor.pause(); // Pausa la reproducción
                    btnPlay.setText("PLAY"); // Cambia el texto del botón
                }
            }
        });

        // Añadir el listener de finalización al MediaPlayer
        reproductor.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btnPlay.setText("PLAY"); // Cambia el texto del botón a "PLAY" cuando termine la reproducción
            }
        });
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (reproductor == null) {
            reproductor = new MediaPlayer();
        }
        reproductor.setDisplay(holder); // Asocia el SurfaceHolder al MediaPlayer
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Aquí se manejarían cambios en la superficie
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // Aquí se liberan los recursos cuando se destruye la superficie
        if (grabador != null) {
            grabador.release();
            grabador = null;
        }

        if (reproductor != null) {
            reproductor.release();
            reproductor = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // También liberamos los recursos aquí por seguridad
        if (grabador != null) {
            grabador.release();
            grabador = null;
        }

        if (reproductor != null) {
            reproductor.release();
            reproductor = null;
        }
    }
}