package com.example.gestionmascotas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class InicioSesion extends AppCompatActivity {

    EditText nomUsuario, nomContraseña;
    TextView inicioNulo;
    Button btInicio;
    HashMap<String, String> usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        nomUsuario = findViewById(R.id.nomUsuario);
        nomContraseña = findViewById(R.id.contraseña);
        btInicio = findViewById(R.id.botonInicio);

        // Añadir mapa de usuarios
        usuarios = new HashMap<>();
        usuarios.put("usuario", "usuario");
        usuarios.put("admin", "admin");
        usuarios.put("angela", "angela");

        // Configuración del botón
        btInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obetenr texto ingresado en los EditText
                String usuario = nomUsuario.getText().toString();
                String contraseña = nomContraseña.getText().toString();

                // Validar usuario y contraseña
                if (usuarios.containsKey(usuario) && usuarios.get(usuario).equals(contraseña)) {
                    Intent intent = new Intent(InicioSesion.this, ListadoMascotas.class);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                    Toast.makeText(InicioSesion.this, "Incio de sesión exitoso", Toast.LENGTH_SHORT).show();
                } else {
                    inicioNulo = findViewById(R.id.inicioNulo);
                    inicioNulo.setText("Usuario o contraseña incorrectos");
                }
            }
        });

    }
}