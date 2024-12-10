package com.example.prop_02;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager paginador = findViewById(R.id.contenedor);

        final TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("PRIMERO").setIcon(R.drawable.play));
        tabLayout.addTab(tabLayout.newTab().setText("SEGUNDO").setIcon(R.drawable.pause));
        tabLayout.addTab(tabLayout.newTab().setText("TERCERO").setIcon(R.drawable.previous));

        Adaptador adaptador = new Adaptador(getSupportFragmentManager(), tabLayout.getTabCount());
        paginador.setAdapter(adaptador);
        paginador.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Sincroniza el ViewPager con la pestaña seleccionada
                paginador.setCurrentItem(tab.getPosition());

//                // Crear un fragmento basado en la posición de la pestaña seleccionada
//                Fragment fragmento = null;
//                switch (tab.getPosition()) {
//                    case 0:
//                        fragmento = new Primero();
//                        break;
//                    case 1:
//                        fragmento = new Segundo();
//                        break;
//                    case 2:
//                        fragmento = new Tercero();
//                        break;
//                    default:
//                        throw new IllegalArgumentException("Posición no válida: " + tab.getPosition());
//                }
//
//                // Gestionar la transacción de fragmentos
//                FragmentManager fm = getSupportFragmentManager();
//                FragmentTransaction transaction = fm.beginTransaction();
//                transaction.replace(R.id.contenedor, fragmento); // Reemplaza el fragmento en el contenedor
//                transaction.addToBackStack(null); // Opcional: Añade la transacción al back stack
//                transaction.commit(); // Realiza la transacción
//                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Opcional: Gestionar lo que ocurre cuando la pestaña es deseleccionada
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Opcional: Gestionar lo que ocurre cuando la pestaña es reseleccionada
            }
        });
    }
}
