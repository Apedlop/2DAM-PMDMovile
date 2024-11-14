package com.example.prueba_ex3_ev1;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView textoResultado;
    private String numeroActual = "";
    private String operador = "";
    private double resultado = 0;
    private ArrayList<Operacion> historialOperaciones;
    private OperacionAdapter operacionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoResultado = findViewById(R.id.textViewResultado);
        historialOperaciones = new ArrayList<>();
        operacionAdapter = new OperacionAdapter(this, historialOperaciones);

        ListView historial = findViewById(R.id.listViewHistorial);
        historial.setAdapter(operacionAdapter);

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

        configurarListenerOperador(R.id.botonSumar, "+");
        configurarListenerOperador(R.id.botonRestar, "-");
        configurarListenerOperador(R.id.botonMultiplicar, "*");
        configurarListenerOperador(R.id.botonDividir, "/");
        configurarListenerOperador(R.id.botonPotencia, "^");

        findViewById(R.id.botonIgual).setOnClickListener(v -> calcularResultado());
        findViewById(R.id.botonBorrar).setOnClickListener(v -> borrarPantalla());

        historial.setOnItemClickListener((adapter, view, position, id) -> editarOperacion(position));
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
            String operacionCompleta;

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
                        resultado = Math.pow(resultado, numero);
                        operacionCompleta = resultado + " ^ " + numero + " = " + resultado;
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
                    default:
                        operacionCompleta = "";
                        break;
                }

                // Agregar la operación al historial
                Operacion operacion = new Operacion(operacionCompleta);
                historialOperaciones.add(operacion);
                operacionAdapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
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

    private void editarOperacion(int position) {
        Operacion operacionSeleccionada = historialOperaciones.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar operación");

        final EditText input = new EditText(this);
        input.setText(operacionSeleccionada.getOperacion());
        builder.setView(input);

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String nuevaOperacion = input.getText().toString();
            operacionSeleccionada.setOperacion(nuevaOperacion);
            operacionAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Operación actualizada", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
