package com.example.edittext;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        // Teclado telefónico 1
        Button bt1 = (Button) findViewById(R.id.bt1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bt1 = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(bt1);
            }
        });

        // Teclado telefónico 2
        Button bt2 = (Button) findViewById(R.id.bt2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bt2 = new Intent(MainActivity.this, MainActivity3.class);
                startActivity(bt2);
            }
        });

        // Teclado alfabético
        Button bt3 = (Button) findViewById(R.id.bt3);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bt3 = new Intent(MainActivity.this, MainActivity4.class);
                startActivity(bt3);
            }
        });

        // Teclado numérico
        Button bt4 = (Button) findViewById(R.id.bt4);
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bt4 = new Intent(MainActivity.this, MainActivity5.class);
                startActivity(bt4);
            }
        });
    }
}