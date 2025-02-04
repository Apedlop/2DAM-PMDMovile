package com.example.prop_02_bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUrl;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUrl = findViewById(R.id.editTextUrl);
        imageView = findViewById(R.id.imageView);
        Button btnCargarImagen = findViewById(R.id.btnCargarImagen);

        btnCargarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editTextUrl.getText().toString().trim();
                if (!url.isEmpty()) {
                    new CargarImagenTask().execute(url);
                } else {
                    Toast.makeText(MainActivity.this, "Ingrese una URL válida", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Clase AsyncTask para descargar la imagen en segundo plano
    private class CargarImagenTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap imagen = null;
            try {
                InputStream input = new URL(url).openStream();
                imagen = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return imagen;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                Toast.makeText(MainActivity.this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
