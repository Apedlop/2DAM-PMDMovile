package com.example.gestionmascotas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

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
                // Obtenemos el texto ingresado en los EditText
                String usuario = nomUsuario.getText().toString();
                String contraseña = nomContraseña.getText().toString();

                // Validar usuario y contraseña
                if (usuarios.containsKey(usuario) && usuarios.get(usuario).equals(contraseña)) {
                    if (usuario.equals("admin")) {
                        // Si es admin, abrir actividad para admin
                        Intent intent = new Intent(InicioSesion.this, ListadoMascotasAdmin.class);
                        intent.putExtra("usuario", usuario);
                        startActivity(intent);
                        // Toast personalizado
                        mostrarToast("Inicio de sesión exitoso como Admin");
                    } else {
                        // Si es usuario normal, abrir actividad para usuario
                        Intent intent = new Intent(InicioSesion.this, ListadoMascotasUser.class);
                        intent.putExtra("usuario", usuario);
                        startActivity(intent);
                        // Toast personalizado
                        mostrarToast("Inicio de sesión exitoso como " + usuario);
                    }
                } else {
                    // Si el usuario o la contraseña son incorrectos
                    inicioNulo = findViewById(R.id.inicioNulo);
                    inicioNulo.setText("Usuario o contraseña incorrectos");
                }
            }
        });
    }

    // Método para mostrar toast
    @SuppressLint("MissingInflatedId")
    public void mostrarToast(String mensaje) {
        // Toast personalizado
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.layotu_toast));
        TextView textoToast = layout.findViewById(R.id.textoToast);
        textoToast.setText(mensaje);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}