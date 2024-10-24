package com.example.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private StringBuilder input = new StringBuilder();
    private String operator = "";
    private double operacion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
        display.setText(R.string.visor); // Texto inicial

        // Botones numéricos
        setupButton(R.id.button_1, "1");
        setupButton(R.id.button_2, "2");
        setupButton(R.id.button_3, "3");
        setupButton(R.id.button_4, "4");
        setupButton(R.id.button_5, "5");
        setupButton(R.id.button_6, "6");
        setupButton(R.id.button_7, "7");
        setupButton(R.id.button_8, "8");
        setupButton(R.id.button_9, "9");
        setupButton(R.id.button_0, "0");
        setupButton(R.id.coma, ",");

        // Botones de operación
        setupOperatorButton(R.id.sumar, "+");
        setupOperatorButton(R.id.restar, "-");
        setupOperatorButton(R.id.multiplicar, "*");
        setupOperatorButton(R.id.dividir, "/");
        setupOperatorButton(R.id.potencia, "^");

        // Botón de igual
        Button botonIgual = findViewById(R.id.igual);
        botonIgual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               calcularResultado();
               operator = "";
            }
        });

        // Botón de limpiar
        Button buttonClear = findViewById(R.id.borrar);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiar();
            }
        });
    }

    public void limpiar() {
        StringBuilder cons= new StringBuilder(display.getText());
        cons.delete(0,display.getText().length());
        cons.insert(0,0);
        display.setText(cons);
        operator="";
    }

    public void setupButton(int id, String valor) {
        Button button = findViewById(id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recupararNum(valor);
            }
        });
    }

    public void setupOperatorButton(int id, String op) {
        Button button = findViewById(id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOperador(op);
            }
        });
    }

    public void recupararNum(String number) {
        input.append(number);
        display.setText(input.toString());
    }

    public void setOperador(String op) {
        if (input.length() > 0) {
            operacion = Double.parseDouble(input.toString());
            operator = op;
            input.setLength(0); // Limpiar el input para el siguiente número
        }
    }

    public void calcularResultado() {
        if (input.length() > 0 && operator.length() > 0) {
            StringBuilder text = new StringBuilder();
            double num2 = Double.parseDouble(input.toString());
            double resultado = 0;

            switch (operator) {
                case "+":
                    text.insert(0,operacion + num2);
                    display.setText(text);
                    break;
                case "-":
                    text.insert(0,operacion - num2);
                    display.setText(text);
                    break;
                case "*":
                    text.insert(0,operacion * num2);
                    display.setText(text);
                    break;
                case "/":
                    text.insert(0,operacion / num2);
                    display.setText(text);
                    break;
                case "^":
                    text.insert(0, Math.pow(operacion, num2));
                    display.setText(text);
                    break;
                default:
                    System.out.println("ERROR");
            }
        }
    }
}
