package com.example.wordle;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String[] palabras = {"Mundo", "Claro", "Cielo", "Nunca", "Silla", "Campo", "Verde", "Rapido", "Fuego", "Lugar", "Sombra", "Arbol", "Playa", "Flores", "Finca", "Oveja", "Perro", "Poema", "Nieve", "Sueño"};
    private TextView palabraText;
    private String palabraAleatoria;
    private int intentos = 0;
    private TextView[][] cuadrosTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        palabraText = findViewById(R.id.palabra_texto); // Correcto ID para mostrar la palabra introducida
        palabraAleatoria = obtenerPalabraAleatoria();
        cuadrosTexto = new TextView[6][5]; // 6 intentos, 5 letras
        inicializarCuadrosTexto();

        int[] idButton = {
                R.id.Q, R.id.W, R.id.E, R.id.R, R.id.T, R.id.Y, R.id.U, R.id.I, R.id.O, R.id.P,
                R.id.A, R.id.S, R.id.D, R.id.F, R.id.G, R.id.H, R.id.J, R.id.K, R.id.L, R.id.Ñ,
                R.id.Z, R.id.X, R.id.C, R.id.V, R.id.B, R.id.N, R.id.M
        };

        for (int buttonId : idButton) {
            Button letras = findViewById(buttonId);
            if (letras != null) {
                letras.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String currentText = palabraText.getText().toString();
                        if (currentText.length() < 5) {
                            palabraText.setText(currentText + letras.getText().toString());
                        }
                    }
                });
            }
        }

        Button enter = findViewById(R.id.Intro);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enter();
            }
        });
    }

    public void enter() {
        String palabraActual = palabraText.getText().toString();

        if (palabraActual.length() == 5) {
            if (isCorrecta(palabraActual, palabraAleatoria)) {
                Toast.makeText(this, "¡Correcto!", Toast.LENGTH_SHORT).show();
                mostrarResultados(palabraActual);
                palabraAleatoria = obtenerPalabraAleatoria();
                intentos = 0;
                palabraText.setText("");
            } else {
                mostrarResultados(palabraActual);
                intentos++;
                if (intentos >= 6) {
                    Toast.makeText(this, "¡Perdiste! La palabra era " + palabraAleatoria, Toast.LENGTH_LONG).show();
                    palabraAleatoria = obtenerPalabraAleatoria();
                    intentos = 0;
                    palabraText.setText("");
                }
            }
        } else {
            Toast.makeText(this, "La palabra debe tener 5 letras.", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarResultados(String palabraActual) {
        for (int i = 0; i < palabraActual.length(); i++) {
            cuadrosTexto[intentos][i].setText(String.valueOf(palabraActual.charAt(i)));
            if (palabraActual.charAt(i) == palabraAleatoria.charAt(i)) {
                cuadrosTexto[intentos][i].setBackgroundColor(Color.GREEN);
            } else if (palabraAleatoria.contains(String.valueOf(palabraActual.charAt(i)))) {
                cuadrosTexto[intentos][i].setBackgroundColor(Color.YELLOW);
            } else {
                cuadrosTexto[intentos][i].setBackgroundColor(Color.GRAY);
            }
        }
    }

    private void inicializarCuadrosTexto() {
        GridLayout gridLayout = findViewById(R.id.palabras_grid); // ID corregido
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                TextView cuadroTexto = new TextView(this);
                cuadroTexto.setLayoutParams(new GridLayout.LayoutParams());
                cuadroTexto.setTextSize(24);
                cuadroTexto.setTextColor(Color.BLACK);
                cuadroTexto.setBackgroundColor(Color.WHITE);
                cuadroTexto.setWidth(100);
                cuadroTexto.setHeight(100);
                cuadrosTexto[i][j] = cuadroTexto;
                gridLayout.addView(cuadroTexto);
            }
        }
    }

    public String obtenerPalabraAleatoria() {
        Random random = new Random();
        int i = random.nextInt(palabras.length);
        return palabras[i];
    }

    public boolean isCorrecta(String palabra, String correcta) {
        return palabra.equalsIgnoreCase(correcta);
    }
}
