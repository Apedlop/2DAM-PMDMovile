package com.example.prop_01;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.barra_nuevo){
            mensaje(R.string.cadena_nuevo);
            return true;
        } else if (item.getItemId() == R.id.barra_editar) {
            mensaje(R.string.cadena_editar);
            return true;
        }else if (item.getItemId() == R.id.barra_configurar) {
            mensaje(R.string.cadena_configurar);
            return true;
        }else if (item.getItemId() == R.id.barra_ayuda) {
            mensaje(R.string.cadena_ayuda);
            return true;
        }else if (item.getItemId() == R.id.barra_acerca) {
            mensaje(R.string.cadena_acerca);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    private void mensaje(int resid){
        Toast.makeText(this, getText(resid), Toast.LENGTH_SHORT).show();
    }
}