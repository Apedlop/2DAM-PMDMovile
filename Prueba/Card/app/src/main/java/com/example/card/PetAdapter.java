package com.example.card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    private List<Pet> petList;
    private Context context;

    public PetAdapter(Context context, List<Pet> petList) {
        this.context = context;
        this.petList = petList;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pet, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        Pet pet = petList.get(position);
        holder.petName.setText(pet.getName());
        holder.petBreed.setText(pet.getBreed());

        holder.viewDetailsButton.setOnClickListener(v -> {
            // Aquí puedes manejar la acción de ver detalles
            Toast.makeText(context, "Ver detalles de " + pet.getName(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    public static class PetViewHolder extends RecyclerView.ViewHolder {

        TextView petName;
        TextView petBreed;
        Button viewDetailsButton;

        public PetViewHolder(View itemView) {
            super(itemView);
            petName = itemView.findViewById(R.id.tvPetName);
            petBreed = itemView.findViewById(R.id.tvPetBreed);
            viewDetailsButton = itemView.findViewById(R.id.btnViewDetails);
        }
    }
}
