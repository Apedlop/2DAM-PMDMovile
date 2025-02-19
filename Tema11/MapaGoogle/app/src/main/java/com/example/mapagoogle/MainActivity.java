package com.example.mapagoogle;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap miGoogleMap;
    private EditText busqueda;
    private Button btnBuscar, btnCambiarMapa;
    private FusedLocationProviderClient fusedLocationClient;
    private Marker miMarkerUbicacion; // Marker para tu ubicación
    private static final String API_KEY = "AIzaSyBFe3cMarZMD9rtT3OQZHgtY06IoV2ZtNE";  // Reemplázalo con tu API Key
    private List<Marker> marcadorList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment miMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.miMapa);
        if (miMapFragment != null) {
            miMapFragment.getMapAsync(this);
        }

        // Inicializar elementos de la interfaz
        busqueda = findViewById(R.id.busqueda);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnCambiarMapa = findViewById(R.id.cambiarMapa);

        // Cliente de ubicación
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        miGoogleMap = googleMap;

        // Inicializar el cliente de ubicación
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Obtener la ubicación
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // Si obtuviste la ubicación, actualiza el mapa
                            LatLng miUbicacion = new LatLng(location.getLatitude(), location.getLongitude());

                            // Convertir el VectorDrawable a Bitmap
                            Bitmap bitmap = vectorToBitmap(R.drawable.mi_ubicacion);

                            // Agregar marcador de tu ubicación si no está agregado aún
                            if (miMarkerUbicacion == null) {
                                miMarkerUbicacion = miGoogleMap.addMarker(new MarkerOptions()
                                        .position(miUbicacion)
                                        .title("Mi Ubicación")
                                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));  // Usar el bitmap convertido
                            } else {
                                miMarkerUbicacion.setPosition(miUbicacion); // Actualiza la posición del marcador
                            }

                            miGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(miUbicacion, 15));
                        }
                    }
                });

        // Botón de búsqueda
        btnBuscar.setOnClickListener(v -> obtenerUbicacionYBuscar(busqueda.getText().toString()));

        // Botón para cambiar el mapa
        btnCambiarMapa.setOnClickListener(v -> cambiarTipoMapa());
    }

    // Método para pasar de vector a BitMap
    private Bitmap vectorToBitmap(int vectorResourceId) {
        // Cargar el vector drawable
        VectorDrawableCompat vectorDrawable = VectorDrawableCompat.create(getResources(), vectorResourceId, null);

        // Crear un bitmap y un canvas para dibujar el vector en el bitmap
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888); // Establecer el tamaño del bitmap
        Canvas canvas = new Canvas(bitmap);

        // Dibujar el vector sobre el canvas
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);

        return bitmap; // Devolver el bitmap resultante
    }

    private void obtenerUbicacionYBuscar(String tipoLugar) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                buscarLugaresCercanos(lat, lng, tipoLugar);
            }
        });
    }

    private void cambiarTipoMapa() {
        if (miGoogleMap != null) {
            if (miGoogleMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
                miGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            } else {
                miGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        }
    }

    private void buscarLugaresCercanos(double lat, double lng, String tipoLugar) {
        new Thread(() -> {
            try {
                String urlString = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                        "location=" + lat + "," + lng +
                        "&radius=5000" +  // Radio de búsqueda en metros (5 km)
                        "&type=" + tipoLugar.toLowerCase() +  // Tipo de lugar (bar, restaurant, hotel, etc.)
                        "&key=" + API_KEY;

                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Procesar la respuesta JSON
                JSONObject jsonObject = new JSONObject(response.toString());
                JSONArray results = jsonObject.getJSONArray("results");

                runOnUiThread(() -> {
                    // Limpiar los marcadores previos antes de agregar nuevos
                    for (Marker marker : marcadorList) {
                        marker.remove();
                    }
                    marcadorList.clear(); // Limpiar la lista de marcadores

                    // Agregar nuevos marcadores para la búsqueda
                    for (int i = 0; i < results.length(); i++) {
                        try {
                            JSONObject place = results.getJSONObject(i);
                            String name = place.getString("name");
                            JSONObject location = place.getJSONObject("geometry").getJSONObject("location");
                            double placeLat = location.getDouble("lat");
                            double placeLng = location.getDouble("lng");

                            // Agregar marcador para el lugar de la búsqueda
                            Marker marker = miGoogleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(placeLat, placeLng))
                                    .title(name));
                            marcadorList.add(marker);  // Añadir el marcador a la lista
                        } catch (Exception e) {
                            Log.e("MapError", "Error procesando lugar", e);
                        }
                    }
                    miGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 14));
                });

            } catch (Exception e) {
                Log.e("MapError", "Error obteniendo lugares", e);
            }
        }).start();
    }

}
