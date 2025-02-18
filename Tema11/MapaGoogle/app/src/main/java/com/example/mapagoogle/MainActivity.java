package com.example.mapagoogle;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap miGoogleMap;
    private EditText busqueda;
    private Button btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configura el fragmento del mapa
        SupportMapFragment miMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.miMapa);
        if (miMapFragment != null) {
            miMapFragment.getMapAsync(this);
        }

        // Inicializar Places
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyAV5X5qa9g99iBmXJ3kIzCwtzR0xxAJtgA"); // Reemplaza con tu API Key
        }

        // Referencias a los elementos de la interfaz
        busqueda = findViewById(R.id.busqueda);
        btnBuscar = findViewById(R.id.btnBuscar);

        // Configurar el botón de búsqueda
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarLugares(busqueda.getText().toString());
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        miGoogleMap = googleMap;

        // Inicializar el cliente de ubicación
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Obtener la ubicación
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // Si obtuviste la ubicación, actualiza el mapa
                            LatLng miUbicacion = new LatLng(location.getLatitude(), location.getLongitude());
                            miGoogleMap.addMarker(new MarkerOptions().position(miUbicacion).title("Mi Ubicación"));
                            miGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(miUbicacion, 15));
                        }
                    }
                });

        // Configurar botón para cambiar el tipo de mapa
        findViewById(R.id.cambiarMapa).setOnClickListener(v -> cambiarTipoMapa());
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

    private void buscarLugares(String query) {
        PlacesClient placesClient = Places.createClient(this);

        // Crear la solicitud de autocompletado sin un filtro de tipo
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setQuery(query) // El texto que se busca
                .build();

        // Realizar la búsqueda
        placesClient.findAutocompletePredictions(request)
                .addOnSuccessListener(response -> {
                    for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                        // Verifica si el lugar es de tipo LODGING
                        if (prediction.getTypes().contains(Place.Type.LODGING)) {
                            String hotelName = prediction.getPrimaryText(null).toString();
                            String placeId = prediction.getPlaceId();
                            Log.i("HotelLocation", "Hotel encontrado: " + hotelName + " (ID: " + placeId + ")");

                            // Obtener detalles del lugar (latitud y longitud)
                            fetchPlaceDetails(placeId);
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("HotelLocation", "Error al buscar hoteles", e);
                });
    }

    private void fetchPlaceDetails(String placeId) {
        PlacesClient placesClient = Places.createClient(this);
        placesClient.fetchPlace(FetchPlaceRequest.newInstance(placeId, Arrays.asList(Place.Field.LAT_LNG)))
                .addOnSuccessListener(response -> {
                    Place place = response.getPlace();
                    LatLng location = place.getLatLng();
                    if (location != null) {
                        miGoogleMap.addMarker(new MarkerOptions().position(location).title(place.getName()));
                        miGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("HotelLocation", "Error al obtener detalles del hotel", e);
                });
    }
}
