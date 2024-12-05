package com.example.gremioapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class Menu extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventoAdapter eventoAdapter;
    private DatabaseController databaseController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_add) {
                abrirTelaEvento();
            }
            return true;
        });

        recyclerView = findViewById(R.id.recyclerViewEventos);
        databaseController = new DatabaseController(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<EventoClass> eventos = databaseController.listarEventos();

        if (eventos.isEmpty()) {
            Toast.makeText(this, "Nenhum evento encontrado.", Toast.LENGTH_SHORT).show();
        } else {
            eventoAdapter = new EventoAdapter(eventos);
            recyclerView.setAdapter(eventoAdapter);
            eventoAdapter.notifyDataSetChanged();
        }

    }

    private void abrirTelaEvento() {
        Intent telaMenu = new Intent(this, Evento.class);
        startActivity(telaMenu);
    }

}