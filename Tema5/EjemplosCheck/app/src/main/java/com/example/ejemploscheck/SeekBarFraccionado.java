package com.example.ejemploscheck;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;

public class SeekBarFraccionado extends AppCompatActivity {
    private TextView letter;
    private TextView tempLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_bar_fraccionado);

        letter = findViewById(R.id.letter);
        tempLevel = findViewById(R.id.temp_level);

        setUpContinuousSeekBar();
        setUpDiscreteSeekBar();
    }

    private void createAndAddSeekBar() {
        ConstraintLayout parent = findViewById(R.id.constraint_layout);
        SeekBar seekbar = new SeekBar(this);
        seekbar.setId(ViewCompat.generateViewId());
        seekbar.setMax(60);
        seekbar.setProgress(45);
        seekbar.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT));

        parent.addView(seekbar);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(parent);
        constraintSet.connect(seekbar.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(seekbar.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSet.connect(seekbar.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSet.applyTo(parent);
    }

    private void setUpContinuousSeekBar() {
        SeekBar continuousSeekBar = findViewById(R.id.continuous_seekbar);
        tintDrawable(continuousSeekBar.getThumb(), R.color.red_700);
        tintDrawable(continuousSeekBar.getProgressDrawable(), R.color.red_500);

        continuousSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                letter.setAlpha(progress / 60f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // No action needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // No action needed
            }
        });
    }

    private void setUpDiscreteSeekBar() {
        SeekBar discreteSeekBar = findViewById(R.id.discrete_seekbar);
        discreteSeekBar.setMax(4); // Establecer el máximo para 5 niveles
        discreteSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // No action needed
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // No action needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tempLevel.setText(getTemperatureLabel(seekBar.getProgress())); // Usar getProgress() aquí
            }
        });
    }

    private String getTemperatureLabel(int progress) {
        switch (progress) {
            case 0:
                return "Glacial";
            case 1:
                return "Páramo";
            case 2:
                return "Frío";
            case 3:
                return "Templado";
            case 4:
                return "Cálido";
            default:
                return "Desconocido";
        }
    }

    private void tintDrawable(Drawable drawable, @ColorRes int color) {
        DrawableCompat.setTint(drawable, ContextCompat.getColor(this, color));
    }
}
