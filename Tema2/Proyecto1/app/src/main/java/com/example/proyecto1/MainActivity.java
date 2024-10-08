package com.example.proyecto1;

import android.content.Intent;
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
        Log.i("EJMPLO", "Esto es un onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("EJEMPLO", "Esto es un onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("EJEMPLO", "Esto es un onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("EJEMPLO", "Esto es un onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("EJEMPLO", "Esto es un onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("EJEMPLO", "Esto es un onDestroy");
        Intent ejemplo = new Intent(this, MainActivity2.class);
        startActivity(ejemplo);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("EJEMPLO", "Esto es un onStop");
    }

}