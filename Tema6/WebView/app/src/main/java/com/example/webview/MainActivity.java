package com.example.webview;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private EditText urlEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
        webView = findViewById(R.id.webView);
        urlEditText = findViewById(R.id.urlEditText);
        Button loadButton = findViewById(R.id.loadButton);

        // Configurar WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Habilitar JavaScript

        // Configurar para que las URLs se abran en el WebView y no en el navegador
        webView.setWebViewClient(new WebViewClient());

        // Acción al presionar el botón
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = urlEditText.getText().toString().trim();

                // Validar URL
                if (!url.isEmpty()) {
                    // Agregar "https://" si falta
                    if (!url.startsWith("http://") && !url.startsWith("https://")) {
                        url = "https://" + url;
                    }
                    // Cargar URL en el WebView
                    webView.loadUrl(url);
                } else {
                    // Mostrar error si el campo está vacío
                    Toast.makeText(MainActivity.this, "Please enter a URL", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Permitir navegación hacia atrás en el WebView
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}