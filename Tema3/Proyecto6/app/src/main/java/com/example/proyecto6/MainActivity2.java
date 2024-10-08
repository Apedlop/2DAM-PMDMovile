package com.example.proyecto6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    private static final int RESULT_OK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void boton2(View v) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("resultKey", "Hola desde SecondActivity"); // Aquí estaba el error
        setResult(RESULT_OK, returnIntent);
        finish();
    }

}