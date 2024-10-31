package com.example.wordle;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String[] palabras = {"MUNDO", "CIELO", "SILLA", "PERRO", "RUGIR", "MARZO", "CERCA", "JUGAR", "FUEGO", "LUZCA", "PLAYA", "FLORA", "VOLAR", "VALOR", "TIGRE", "SABOR", "NIEVE", "VERDE", "MUJER", "PAZOS"};
    int[] idButton = new int[27];
    int[] idTextView = new int[30];
    TextView palabraText, cuadroTexto;
    Button[] button = new Button[27];
    Button[] botonesPresionados = new Button[5];  // Guardar los botones presionados para esta palabra
    String palabraAleatoria;
    StringBuilder palabraIntroducida;
    int indice = 0, intentos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        palabraAleatoria = getPalabraAleatoria();
        palabraIntroducida = new StringBuilder();

        idButton = new int[]{
                R.id.Q, R.id.W, R.id.E, R.id.R, R.id.T, R.id.Y, R.id.U, R.id.I, R.id.O, R.id.P,
                R.id.A, R.id.S, R.id.D, R.id.F, R.id.G, R.id.H, R.id.J, R.id.K, R.id.L, R.id.Ñ,
                R.id.Z, R.id.X, R.id.C, R.id.V, R.id.B, R.id.N, R.id.M
        };

        for (int i = 0; i < idButton.length; i++) {
            button[i] = findViewById(idButton[i]);
            button[i].setOnClickListener(this);
        }

        idTextView = new int[]{
                R.id.fila1_1, R.id.fila1_2, R.id.fila1_3, R.id.fila1_4, R.id.fila1_5,
                R.id.fila2_1, R.id.fila2_2, R.id.fila2_3, R.id.fila2_4, R.id.fila2_5,
                R.id.fila3_1, R.id.fila3_2, R.id.fila3_3, R.id.fila3_4, R.id.fila3_5,
                R.id.fila4_1, R.id.fila4_2, R.id.fila4_3, R.id.fila4_4, R.id.fila4_5,
                R.id.fila5_1, R.id.fila5_2, R.id.fila5_3, R.id.fila5_4, R.id.fila5_5,
                R.id.fila6_1, R.id.fila6_2, R.id.fila6_3, R.id.fila6_4, R.id.fila6_5,
        };
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.botonBorrar) {
            borrar();
        } else if (v.getId() == R.id.Intro) {
            enter();
        } else {
            botonLetras(v);
        }
    }

    public void botonLetras(View v) {
        for (int buttonId : idButton) {
            if (v.getId() == buttonId) {
                if (indice < 5) {
                    Button letraBoton = (Button) v;
                    String letra = letraBoton.getText().toString();
                    palabraText = findViewById(idTextView[(intentos * 5) + indice]);
                    palabraText.setText(letra);
                    botonesPresionados[indice] = letraBoton; // Guardar el botón presionado
                    indice++;
                }
                break;
            }
        }
    }

    public void borrar() {
        if (indice > 0) {
            indice--;
            palabraText = findViewById(idTextView[(intentos * 5) + indice]);
            palabraText.setText("");
        }
    }

    public void enter() {
        if (indice == 5) {
            palabraIntroducida.setLength(0);
            for (int i = 0; i < 5; i++) {
                cuadroTexto = findViewById(idTextView[intentos * 5 + i]);
                String letra = cuadroTexto.getText().toString();
                palabraIntroducida.append(letra);
            }

            comprobarLetras();

            if (palabraIntroducida.toString().equalsIgnoreCase(palabraAleatoria)) {
                Toast.makeText(this, "¡Felicidades! Adivinaste la palabra.", Toast.LENGTH_LONG).show();
            } else if (intentos < 5) {
                intentos++;
                indice = 0;
            } else {
                Toast.makeText(this, "Fin del juego. La palabra era: " + palabraAleatoria, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "La palabra debe tener 5 letras", Toast.LENGTH_LONG).show();
        }
    }

    public void comprobarLetras() {
        for (int i = 0; i < 5; i++) {
            cuadroTexto = findViewById(idTextView[intentos * 5 + i]);
            char letraIngresada = palabraIntroducida.charAt(i);
            Button botonActual = botonesPresionados[i]; // Obtener el botón presionado correspondiente

            if (letraIngresada == palabraAleatoria.charAt(i)) {
                cambiarColor(cuadroTexto, Color.GREEN);
                cambiarColorBoton(botonActual, Color.GREEN);
            } else if (palabraAleatoria.contains(String.valueOf(letraIngresada))) {
                cambiarColor(cuadroTexto, Color.YELLOW);
                cambiarColorBoton(botonActual, Color.YELLOW);
            } else {
                cambiarColor(cuadroTexto, Color.GRAY);
                cambiarColorBoton(botonActual, Color.GRAY);
            }
        }
    }

    public void cambiarColorBoton(Button boton, int color) {
        if (boton.getBackground() instanceof GradientDrawable) {
            GradientDrawable background = (GradientDrawable) boton.getBackground();
            background.setColor(color);
        }
        boton.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    public String getPalabraAleatoria() {
        Random random = new Random();
        int indiceAleatorio = random.nextInt(palabras.length);
        return palabras[indiceAleatorio];
    }

    public void cambiarColor(TextView textView, int color) {
        if (textView.getBackground() instanceof GradientDrawable) {
            GradientDrawable background = (GradientDrawable) textView.getBackground();
            background.setColor(color);
        }
    }
}
