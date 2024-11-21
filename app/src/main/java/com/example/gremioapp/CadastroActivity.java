package com.example.gremioapp;

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

public class CadastroActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnRegistrar;

    EditText etNome, etEmail, etSenha;

    String txtNome, txtEmail, txtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Cadastro), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        etNome = (EditText) findViewById(R.id.etNome);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etSenha = (EditText) findViewById(R.id.etSenha);

        btnRegistrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Boolean erro = false;

        txtNome = etNome.getText().toString();
        txtEmail = etEmail.getText().toString();
        txtSenha = etSenha.getText().toString();

        erro = verificaDados();
        if(!erro){
            DatabaseController db = new DatabaseController(getBaseContext());
            String resultado;

            resultado = db.cadastrarUsuario(txtNome, txtEmail, txtSenha);

            Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
        }
    }

    public boolean verificaDados() {
        if (txtNome.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Atenção - O campo NOME deve ser preenchido!", Toast.LENGTH_LONG).show();
            return true;
        }
        if (txtEmail.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Atenção - O campo E-MAIL deve ser preenchido!", Toast.LENGTH_LONG).show();
            return true;
        }
        if (txtSenha.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Atenção - O campo SENHA deve ser preenchido!", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

}