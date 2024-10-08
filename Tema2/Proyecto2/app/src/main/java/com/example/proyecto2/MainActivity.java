package com.example.proyecto2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("EJMPLO2", "Esto es un onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("EJEMPLO2", "Esto es un onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("EJEMPLO2", "Esto es un onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("EJEMPLO2", "Esto es un onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("EJEMPLO2", "Esto es un onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("EJEMPLO2", "Esto es un onDestroy");
        Intent ejemplo = new Intent(Intent.ACTION_VIEW);
        ejemplo.setData(Uri.parse("https://google.com"));
        startActivity(ejemplo);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("EJEMPLO2", "Esto es un onStop");
    }
}