package com.example.gremioapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.net.InterfaceAddress;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class Evento extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button buttonImg;
    private ImageView imageView;

    EditText etTitulo, etDescricao, etLocal;

    String txtTitulo, txtDescricao, txtLocal;

    private final ActivityResultLauncher<String> pickImage =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    imageView.setImageURI(uri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);

        buttonImg = findViewById(R.id.btnImagem);
        imageView = findViewById(R.id.imageView);

        buttonImg.setOnClickListener(v -> {
            pickImage.launch("image/*");
        });
    }

    public Integer getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("usuarioPrefs", MODE_PRIVATE);
        return sharedPreferences.getInt("id", -1);
    }

    @Override
    public void onClick(View v) {
        Boolean erro = false;

        txtTitulo = etTitulo.getText().toString();
        txtDescricao = etDescricao.getText().toString();
        txtLocal = etLocal.getText().toString();

        erro = verificaDados();

        Integer userId = getUserId();

        if(!erro){
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateFormat.format(now);

            DatabaseController db = new DatabaseController(getBaseContext());
            String resultado;

            resultado = db.novoEvento(txtTitulo, txtDescricao, txtLocal, formattedDate, userId, "image.jpg");

            Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
        }
    }

    public boolean verificaDados() {
        if (txtLocal.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Atenção - O campo TÍTULO deve ser preenchido!", Toast.LENGTH_LONG).show();
            return true;
        }
        if (txtDescricao.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Atenção - O campo DESCRIÇÃO deve ser preenchido!", Toast.LENGTH_LONG).show();
            return true;
        }
        if (txtLocal.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Atenção - O campo LOCAL deve ser preenchido!", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }
}
