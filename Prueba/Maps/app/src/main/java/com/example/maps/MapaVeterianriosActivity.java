package com.example.maps;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;

public class MapaVeterianriosActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private PlacesClient placesClient;
    private LatLng userLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_veterianrios);

        // Inicializar el fragmento del mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Inicializar Places API
        Places.initialize(getApplicationContext(), "TU_CLAVE_DE_API");
        placesClient = Places.createClient(this);

        // Obtener la ubicación actual
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Obtener ubicación del usuario
        getUserLocation();
    }

    private void getUserLocation() {
        try {
            fusedLocationProviderClient.getLastLocation()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Location location = task.getResult();
                            userLocation = new LatLng(location.getLatitude(), location.getLongitude());

                            // Mover la cámara al usuario
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));

                            // Buscar veterinarios cercanos
                            findNearbyVeterinarians();
                        } else {
                            Toast.makeText(MapaVeterianriosActivity.this, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void findNearbyVeterinarians() {
        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);
        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.builder(placeFields).build();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        placesClient.findCurrentPlace(request).addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                FindCurrentPlaceResponse response = task.getResult();
                for (PlaceLikelihood likelihood : response.getPlaceLikelihoods()) {
                    Place place = likelihood.getPlace(); // Obtenemos el lugar del likelihood
                    if (place.getName() != null && place.getName().toLowerCase().contains("veterinario")) {
                        LatLng latLng = place.getLatLng();
                        if (latLng != null) {
                            Marker marker = mMap.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .title(place.getName())
                                    .snippet(place.getAddress()));

                            mMap.setOnMarkerClickListener(clickedMarker -> {
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("direccion", clickedMarker.getSnippet());
                                setResult(RESULT_OK, returnIntent);
                                finish();
                                return true;
                            });
                        }
                    }
                }
            } else {
                Toast.makeText(MapaVeterianriosActivity.this, "No se encontraron veterinarios cercanos", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
