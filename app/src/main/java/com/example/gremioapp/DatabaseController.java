package com.example.gremioapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDateTime;

public class DatabaseController {

    private SQLiteDatabase db;

    private CreateDB banco;

    public DatabaseController(Context context){
        banco = new CreateDB(context);
    }

    public String cadastrarUsuario(String _Nome, String _Email, String _Senha) {
        ContentValues valores;

        long resultado;

        db = banco.getWritableDatabase();

        valores = new ContentValues();
        valores.put("nome", _Nome);
        valores.put("email", _Email);
        valores.put("password", _Senha);

        resultado = db.insert("users", null, valores);
        db.close();

        if (resultado == -1){
            return "Erro ao efetuar o cadastro";
        } else {
            return "Cadastro realizado com sucesso!!!";
        }
    }

    public String novoEvento(String _Titulo, String _Descricao, String _Local, LocalDateTime _LocalTime,
                             Integer _User, String _Image) {
        ContentValues valores;

        long resultado;

        db = banco.getWritableDatabase();

        valores = new ContentValues();
        valores.put("titulo", _Titulo);
        valores.put("descricao", _Descricao);
        valores.put("local", _Local);
        valores.put("localTime", String.valueOf(_LocalTime));
        valores.put("user", _User);
        valores.put("imagem", _Image);

        resultado = db.insert("eventos", null, valores);
        db.close();

        if (resultado == -1){
            return "Erro ao publicar o evento.";
        } else {
            return "Evento publicado!";
        }
    }

    public Cursor getLogin(String _email, String _senha) {
        Cursor cursor;
        String[] campos = { "id", "nome", "email", "password", "roles" };
        String where = "email = '" + _email + "' and password = '" + _senha + "'";
        db = banco.getReadableDatabase();
        cursor = db.query("users", campos, where, null, null, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

}
