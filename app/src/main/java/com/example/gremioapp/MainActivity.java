package com.example.gremioapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLOGAcessar, btnLOGCadastrar;
    EditText txtEmail, txtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.MainActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnLOGAcessar = (Button) findViewById(R.id.btnLogin);
        btnLOGCadastrar = (Button) findViewById(R.id.btnLOGCadastrar);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtSenha = (EditText) findViewById(R.id.txtPassword);


        btnLOGAcessar.setOnClickListener(this);
        btnLOGCadastrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btnLogin) {
            // carregar a tela do menu
            if (VerificaDados()) {
                Intent telaMenu = new Intent(this, Menu.class);
                startActivity(telaMenu);
            }
        }
        if (v.getId()==R.id.btnLOGCadastrar) {
            // carregar a tela do cadastre_se
            Intent telaCadastre_se = new Intent(this, CadastroActivity.class);
            startActivity(telaCadastre_se);
        }
    }

    public boolean VerificaDados() {
        String Email = txtEmail.getText().toString();
        String Senha = txtSenha.getText().toString();
        if (Email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "O campo E_MAIL deve ser preenchido!",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if (Senha.isEmpty()) {
            Toast.makeText(getApplicationContext(), "O campo SENHA deve ser preenchido!",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        DatabaseController bd = new DatabaseController(getBaseContext());

        Cursor dados = bd.getLogin(Email, Senha) ;

        if(dados.moveToFirst()){
            return true;
        }else{
            Toast.makeText(getApplicationContext(), "Usuário / senha não cadastrada!", Toast.LENGTH_LONG).show();
            return false;
        }

    }

}