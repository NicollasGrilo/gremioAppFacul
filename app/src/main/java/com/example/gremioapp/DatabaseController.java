package com.example.gremioapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    public String novoEvento(String _Titulo, String _Descricao, String _Local, String _LocalTime,
                             Integer _User, String _Image) {
        if (_User == -1){
            return "Usuário não logado!";
        }

        ContentValues valores;

        long resultado;

        db = banco.getWritableDatabase();

        valores = new ContentValues();
        valores.put("titulo", _Titulo);
        valores.put("descricao", _Descricao);
        valores.put("local", _Local);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = java.sql.Timestamp.valueOf(String.valueOf(_LocalTime));
        String formattedDateTime = sdf.format(date);
        valores.put("localDateTime", formattedDateTime);

        valores.put("user", _User);
        valores.put("image", _Image);

        resultado = db.insert("eventos", null, valores);
        db.close();

        if (resultado == -1){
            return "Erro ao publicar o evento.";
        } else {
            return "Evento publicado!";
        }
    }

    public Integer getLogin(String _email, String _senha) {
        Integer userId = -1;
        Cursor cursor = null;
        String[] campos = { "id", "nome", "email", "password", "roles" };
        String where = "email = '" + _email + "' and password = '" + _senha + "'";

        db = banco.getReadableDatabase();

        cursor = db.query("users", campos, where, null, null, null,
                null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex("id");

                if (columnIndex >= 0){
                    userId = cursor.getInt(columnIndex);
                } else {
                    Log.e("getLogin", "Coluna 'id' não encontrada.");
                }
            }
            cursor.close();
        } else {
            Log.e("getLogin", "Nenhum dado encontrado na consulta.");
        }



        db.close();
        return userId;
    }

    public String getUsername(String _email, String _senha) {
        Cursor cursor = db.query("users", new String[]{"nome"}, "email = ? and password = ?", new String[]{_email, _senha},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String usuario = String.valueOf(cursor.getColumnIndex("nome"));
            cursor.close();
            return usuario;
        } else {
            cursor.close();
            return null;
        }
    }

}
