package com.example.prueba_ex3_ev1;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView textoResultado;
    private String numeroActual = "";
    private String operador = "";
    private double resultado = 0;
    private ArrayList<Operacion> historialOperaciones; // Lista de objetos Operacion
    private OperacionAdapter operacionAdapter; // Adaptador para el ListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoResultado = findViewById(R.id.textViewResultado);
        historialOperaciones = new ArrayList<>();
        operacionAdapter = new OperacionAdapter(this, historialOperaciones);

        ListView listViewHistorial = findViewById(R.id.listViewHistorial);
        listViewHistorial.setAdapter(operacionAdapter);

        // Configurar los listeners de los botones numéricos
        configurarListenerNumero(R.id.boton0, "0");
        configurarListenerNumero(R.id.boton1, "1");
        configurarListenerNumero(R.id.boton2, "2");
        configurarListenerNumero(R.id.boton3, "3");
        configurarListenerNumero(R.id.boton4, "4");
        configurarListenerNumero(R.id.boton5, "5");
        configurarListenerNumero(R.id.boton6, "6");
        configurarListenerNumero(R.id.boton7, "7");
        configurarListenerNumero(R.id.boton8, "8");
        configurarListenerNumero(R.id.boton9, "9");
        configurarListenerNumero(R.id.botonPunto, ".");

        // Configurar los listeners de los operadores
        configurarListenerOperador(R.id.botonSumar, "+");
        configurarListenerOperador(R.id.botonRestar, "-");
        configurarListenerOperador(R.id.botonMultiplicar, "*");
        configurarListenerOperador(R.id.botonDividir, "/");
        configurarListenerOperador(R.id.botonPotencia, "^");

        // Botón de igual
        findViewById(R.id.botonIgual).setOnClickListener(v -> calcularResultado());

        // Botón de borrar (C)
        findViewById(R.id.botonBorrar).setOnClickListener(v -> borrarPantalla());
    }

    private void configurarListenerNumero(int idBoton, String valor) {
        findViewById(idBoton).setOnClickListener(v -> {
            numeroActual += valor;
            textoResultado.setText(numeroActual);
        });
    }

    private void configurarListenerOperador(int idBoton, String operador) {
        findViewById(idBoton).setOnClickListener(v -> {
            if (!numeroActual.isEmpty()) {
                calcularResultado();
                this.operador = operador;
                numeroActual = "";
            }
        });
    }

    private void calcularResultado() {
        if (!numeroActual.isEmpty()) {
            double numero = Double.parseDouble(numeroActual);
            String operacionCompleta = ""; // Para almacenar la operación completa

            if (operador.isEmpty()) {
                resultado = numero;
            } else {
                switch (operador) {
                    case "+":
                        resultado += numero;
                        operacionCompleta = resultado - numero + " + " + numero + " = " + resultado;
                        break;
                    case "-":
                        resultado -= numero;
                        operacionCompleta = resultado + numero + " - " + numero + " = " + resultado;
                        break;
                    case "*":
                        resultado *= numero;
                        operacionCompleta = resultado / numero + " x " + numero + " = " + resultado;
                        break;
                    case "^":
                        operacionCompleta = resultado + " ^ " + numero + " = ";
                        resultado = Math.pow(resultado, numero);
                        operacionCompleta += resultado + "";
                        break;
                    case "/":
                        if (numero != 0) {
                            resultado /= numero;
                            operacionCompleta = resultado * numero + " / " + numero + " = " + resultado;
                        } else {
                            textoResultado.setText("Error");
                            numeroActual = "";
                            operador = "";
                            return;
                        }
                        break;
                }
                // Crear objeto Operacion y agregarlo al historial
                Operacion operacion = new Operacion(operacionCompleta);
                historialOperaciones.add(operacion);
                operacionAdapter.notifyDataSetChanged();
                textoResultado.setText(String.valueOf(resultado));
                numeroActual = "";
                operador = "";
            }


        }
    }

    private void borrarPantalla() {
        numeroActual = "";
        operador = "";
        resultado = 0;
        textoResultado.setText("0");
    }
}
