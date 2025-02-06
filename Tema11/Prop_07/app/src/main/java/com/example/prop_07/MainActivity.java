package com.example.prop_07;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private static final int REQUEST_CODE = 1; // Puedes usar cualquier valor entero

    private Button btnGrabar, btnPausar, btnPlay;
    private VideoView video;
    private MediaRecorder grabador;
    private MediaPlayer reproducir;
    private SurfaceView surface;
    private SurfaceHolder surfaceHolder;
    private String ruta;
    private boolean grabando = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGrabar = findViewById(R.id.btnGrabar);
        btnPausar = findViewById(R.id.btnPausar);
        btnPlay = findViewById(R.id.btnPlay);
        video = findViewById(R.id.video);

        // Configuración del SurfaceView
        surface = findViewById(R.id.surfaceView);
        surfaceHolder = surface.getHolder();
        surfaceHolder.addCallback(this);

        // Inicializar MediaRecorder
        grabador = new MediaRecorder();

        // Inicializar MediaPlayer
        reproducir = new MediaPlayer();

        // Comprobar permisos
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, REQUEST_CODE);
        }

        // Establecer ruta del archivo
        ruta = Environment.getExternalStorageDirectory().getAbsolutePath() + "/video_grabado.mp4";

        btnGrabar.setOnClickListener(v -> {
            // Configuración del grabador solo cuando se presiona el botón de grabar
            if (!grabando) {
                try {
                    grabador.reset(); // Reseteamos el MediaRecorder para asegurar que está limpio

                    grabador.setAudioSource(MediaRecorder.AudioSource.MIC);
                    grabador.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                    grabador.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);  // Usamos un codificador válido
                    grabador.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
                    grabador.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                    grabador.setOutputFile(ruta);

                    // Preparar el grabador
                    grabador.prepare();  // Preparamos el grabador
                    grabador.start();    // Comienza a grabar
                    grabando = true;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        });


        btnPausar.setOnClickListener(v -> {
            if (grabando) {
                grabador.stop();
                grabador.reset();
                grabando = false;
                btnGrabar.setEnabled(true);
                btnPlay.setEnabled(true);
            } else {
                if (reproducir != null) {
                    if (reproducir.isPlaying()) {
                        reproducir.pause();
                    } else {
                        try {
                            reproducir.setDataSource(ruta);
                            reproducir.prepare();
                            reproducir.start();
                            btnPlay.setText("PAUSE");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        btnPlay.setOnClickListener(v -> {
            if (!reproducir.isPlaying()) {
                reproducir.setOnCompletionListener(mp -> {
                    btnGrabar.setEnabled(true);
                    btnPausar.setEnabled(false);
                    btnPlay.setEnabled(true);
                });
                if (reproducir.getCurrentPosition() == reproducir.getDuration()) {
                    try {
                        reproducir.setDataSource(ruta);
                        reproducir.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                reproducir.start();
            } else {
                reproducir.pause();
            }
        });
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (grabador == null) {
            grabador = new MediaRecorder();
            grabador.setPreviewDisplay(holder.getSurface());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (reproducir == null) {
            reproducir = new MediaPlayer();
            reproducir.setDisplay(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        grabador.release();
        reproducir.release();
    }
}
