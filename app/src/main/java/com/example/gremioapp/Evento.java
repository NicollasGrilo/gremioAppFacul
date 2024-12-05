package com.example.gremioapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InterfaceAddress;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class Evento extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button buttonImg, btnRegistrarEvento;
    private ImageView imageView;
    private Uri selectedImg;
    private byte[] imageBytes;

    EditText etTitulo, etDescricao, etLocal;

    String txtTitulo, txtDescricao, txtLocal;



    private final ActivityResultLauncher<String> pickImage =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    imageView.setImageURI(uri);

                    try {
                        imageBytes = pegarImagemDoURI(uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Erro ao carregar a imagem.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    public Evento() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);

        buttonImg = findViewById(R.id.btnImagem);
        imageView = findViewById(R.id.imageView);
        btnRegistrarEvento = findViewById(R.id.btnRegistrarEvento);


        buttonImg.setOnClickListener(v -> {
            pickImage.launch("image/*");
        });

        btnRegistrarEvento.setOnClickListener(this);
    }
    public Integer getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("usuarioPrefs", MODE_PRIVATE);
        return sharedPreferences.getInt("id", -1);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btnRegistrarEvento) {
            // carregar a tela do menu
            if (verificaDados()) {
                Intent telaMenu = new Intent(this, Menu.class);
                startActivity(telaMenu);
            } else {
                Toast.makeText(getApplicationContext(), "erro", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean verificaDados() {
        txtTitulo = etTitulo.getText().toString();
        txtDescricao = etDescricao.getText().toString();
        txtLocal = etLocal.getText().toString();


        if (txtTitulo.isEmpty()) {
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

        Integer userId = getUserId();

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(now);

        DatabaseController db = new DatabaseController(getBaseContext());
        String resultado;

        if (imageBytes != null) {
            resultado = db.novoEvento(txtTitulo, txtDescricao, txtLocal, formattedDate, userId, imageBytes);
        } else {
            resultado = "Nenhuma imagem selecionada";
        }

        Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();

        return false;
    }

    private byte[] pegarImagemDoURI(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];

        int length;

        while ((length = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, length);
        }

        inputStream.close();

        return byteArrayOutputStream.toByteArray();
    }
}
