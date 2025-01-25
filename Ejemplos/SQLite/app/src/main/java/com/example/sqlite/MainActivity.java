package com.example.sqlite;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editTextMessage;
    private Button buttonSend;
    private LinearLayout listViewMessages;
    private Adaptador adaptador;
    private MessageDatabaseHelper dbHelper; // Se declara el helper de la base de datos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de vistas y el helper de la base de datos
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);
        listViewMessages = findViewById(R.id.listViewMessages);
        dbHelper = new MessageDatabaseHelper(this); // Inicializamos el helper de la base de datos

        // Cargamos los mensajes de la base de datos
        ArrayList<String> messages = dbHelper.getAllMessages();
        adaptador = new Adaptador(messages, listViewMessages);

        // Listener para el botón de enviar
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTextMessage.getText().toString().trim();
                if (!message.isEmpty()) {
                    dbHelper.addMessage(message); // Guardamos el mensaje en la base de datos
                    adaptador.addMessage(message); // Actualizamos la vista con el nuevo mensaje
                    editTextMessage.setText(""); // Limpiamos el campo de texto
                }
            }
        });
    }

    /**
     * Clase estática Adaptador para gestionar los mensajes y el LinearLayout.
     */
    public static class Adaptador {
        private ArrayList<String> messages;
        private LinearLayout container;

        public Adaptador(ArrayList<String> messages, LinearLayout container) {
            this.messages = messages;
            this.container = container;
            actualizarVista();
        }

        /**
         * Añade un mensaje a la lista y actualiza el contenedor.
         */
        public void addMessage(String message) {
            messages.add(message);
            actualizarVista();
        }

        /**
         * Actualiza la vista del LinearLayout dinámicamente.
         */
        private void actualizarVista() {
            container.removeAllViews(); // Limpia todas las vistas
            for (String message : messages) {
                TextView textView = new TextView(container.getContext());
                textView.setText(message);
                textView.setTextSize(16);
                textView.setPadding(16, 16, 16, 16);
                container.addView(textView); // Añade cada mensaje como una vista
            }
        }
    }
}
