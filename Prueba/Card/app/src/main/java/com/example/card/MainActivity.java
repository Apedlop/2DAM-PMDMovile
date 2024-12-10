package com.example.card;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PetAdapter petAdapter;
    private List<Pet> petList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewPets);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Crear una lista de mascotas de ejemplo
        petList = new ArrayList<>();
        petList.add(new Pet("Rex", "Pastor Alemán"));
        petList.add(new Pet("Bella", "Golden Retriever"));
        petList.add(new Pet("Luna", "Bulldog Francés"));
        petList.add(new Pet("Simba", "Gato de raza Savannah"));

        petAdapter = new PetAdapter(this, petList);
        recyclerView.setAdapter(petAdapter);
    }
}
