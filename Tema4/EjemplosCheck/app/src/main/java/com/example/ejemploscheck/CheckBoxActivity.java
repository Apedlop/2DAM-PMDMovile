package com.example.ejemploscheck;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CheckBoxActivity extends AppCompatActivity {

    private CheckBox checkBox;
    private TextView statusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box);

        // Referencias a los widgets
        checkBox = findViewById(R.id.miCheckBox);
        statusTextView = findViewById(R.id.statusTextView);

        // Configura el listener para cambios de estado
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Actualiza el texto del TextView dependiendo del estado del CheckBox
                if (isChecked) {
                    statusTextView.setText("Estado: Seleccionado");
                } else {
                    statusTextView.setText("Estado: No seleccionado");
                }
            }
        });
    }
}
