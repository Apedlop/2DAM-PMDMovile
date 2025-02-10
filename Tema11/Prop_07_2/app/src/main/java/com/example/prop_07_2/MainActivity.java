package com.example.prop_07_2;

import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

        requestPermissions();  // Solicitar permisos al inicio

        surface.getHolder().addCallback(this);
        surfaceHolder = surface.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        btnGrabar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (grabador != null) {
                    grabador.release();
                }

                grabador = new MediaRecorder();
                Camera camera = Camera.open(0);  // Usa la cámara trasera (0 es la cámara principal)

                // Configurar cámara y parámetros
                Camera.Parameters params = camera.getParameters();
                camera.setParameters(params);

                try {
                    camera.unlock();  // Desbloqueamos la cámara
                    grabador.setCamera(camera);

                    // Configuración de grabación de audio y video
                    grabador.setAudioSource(MediaRecorder.AudioSource.MIC);
                    grabador.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                    grabador.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                    grabador.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                    grabador.setVideoEncoder(MediaRecorder.VideoEncoder.H264);

                    ruta = getExternalFilesDir(null).getAbsolutePath() + "/mivideo.mp4";
                    grabador.setOutputFile(ruta);
                    grabador.setPreviewDisplay(surfaceHolder.getSurface());
                    grabador.setOrientationHint(90);  // Ajusta la orientación

                    grabador.prepare();
                    grabador.start();
                    grabando = true;

                    btnGrabar.setEnabled(false);
                    btnPlay.setEnabled(false);
                    btnDetener.setEnabled(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error al iniciar la grabación", Toast.LENGTH_SHORT).show();
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
                try {
                    // Comprobamos si el reproductor es nulo o no está inicializado
                    if (reproductor == null) {
                        reproductor = new MediaPlayer();
                        reproductor.setDataSource(ruta);
                        reproductor.setDisplay(surfaceHolder);

                        reproductor.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mp.start();  // Comienza a reproducir una vez que está preparado
                                btnPlay.setText("PAUSE");
                            }
                        });

                        reproductor.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                            @Override
                            public boolean onError(MediaPlayer mp, int what, int extra) {
                                Toast.makeText(getApplicationContext(), "Error al reproducir el archivo", Toast.LENGTH_SHORT).show();
                                Log.e("ReproductorError", "Código de error: " + what + ", Extra: " + extra);
                                return true;
                            }
                        });

                        reproductor.prepareAsync();  // Usa prepareAsync para evitar bloquear el hilo principal
                    } else if (reproductor.isPlaying()) {
                        reproductor.pause();
                        btnPlay.setText("PLAY");
                    } else {
                        reproductor.start();  // Aquí se asegura que solo se inicie si está listo
                        btnPlay.setText("PAUSE");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error al preparar el archivo", Toast.LENGTH_SHORT).show();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Estado incorrecto del reproductor", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
        reproductor.setDisplay(holder);  // Asocia el SurfaceHolder al MediaPlayer
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Aquí se manejarían cambios en la superficie
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // Liberar recursos cuando se destruye la superficie
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
        // Liberar recursos al destruir la actividad
        if (grabador != null) {
            grabador.release();
            grabador = null;
        }

        if (reproductor != null) {
            reproductor.release();
            reproductor = null;
        }
    }

    // Solicitar permisos al iniciar la aplicación
    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                // Solicita permisos
                requestPermissions(new String[] {
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 100); // 100 es un código de solicitud personalizado
            }
        }
    }

    // Manejo de la respuesta de los permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            boolean cameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean audioPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            boolean storagePermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;

            if (cameraPermission && audioPermission && storagePermission) {
                Toast.makeText(this, "Permisos concedidos", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Se requieren permisos para usar la cámara y grabar audio", Toast.LENGTH_SHORT).show();
                finish();  // Termina la actividad si los permisos no son concedidos
            }
        }
    }
}