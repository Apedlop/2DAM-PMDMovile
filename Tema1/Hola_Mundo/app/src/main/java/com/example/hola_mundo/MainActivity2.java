package com.example.hola_mundo;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.i("EJEMPLO", "Estoy onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("EJEMPLO", "onStart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("EJEMPLO", "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("EJEMPLO", "onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("EJEMPLO", "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("EJEMPLO", "onResume");
    }

}