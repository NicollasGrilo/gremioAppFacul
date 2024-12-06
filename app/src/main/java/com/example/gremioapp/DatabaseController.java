package com.example.gremioapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
                             String _User, byte[] _Image) {
        if (_User == null){
            return "Usuário não logado!";
        }

        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();

        valores = new ContentValues();
        valores.put("titulo", _Titulo);
        valores.put("descricao", _Descricao);
        valores.put("local", _Local);
        valores.put("localDateTime", _LocalTime);
        valores.put("user", _User);

        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Date date = java.sql.Timestamp.valueOf(String.valueOf(_LocalTime));
        //String formattedDateTime = sdf.format(date);
        //valores.put("localDateTime", formattedDateTime);


        if (_Image != null) {
            valores.put("image", _Image);
        }

        resultado = db.insert("eventos", null, valores);
        db.close();

        if (resultado == -1){
            Log.e("Database", "Erro ao inserir evento.");
            return "Erro ao publicar o evento.";
        } else {
            Log.i("Database", "Evento inserido com sucesso!!!");
            return "Evento publicado!";
        }
    }

    public List<EventoClass> listarEventos() {
        List<EventoClass> eventos = new ArrayList<>();
        Cursor cursor = null;
        String[] campos = {"user", "titulo", "descricao", "local", "localDateTime", "image"};

        db = banco.getReadableDatabase();

        // Consulta para buscar todos os eventos
        cursor = db.query("eventos", campos, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Pega os dados do cursor e cria um objeto Evento
                String user = cursor.getString(cursor.getColumnIndexOrThrow("user"));
                String titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"));
                String descricao = cursor.getString(cursor.getColumnIndexOrThrow("descricao"));
                String local = cursor.getString(cursor.getColumnIndexOrThrow("local"));
                String localDateTime = cursor.getString(cursor.getColumnIndexOrThrow("localDateTime"));
                byte[] imagem = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));

                // Cria um novo Evento e adiciona à lista
                eventos.add(new EventoClass(user, titulo, descricao, local, localDateTime, imagem));
            } while (cursor.moveToNext());

            cursor.close();
            Log.i("Database", "Eventos encontrados: " + eventos.size());
        } else {
            Log.i("Database", "Nenhum evento encontrado.");
        }

        //db.close();
        return eventos;
    }

    public String getLogin(String _email, String _senha) {
        String username = null;
        Cursor cursor = null;
        String[] campos = { "id", "nome", "email", "password", "roles" };
        String where = "email = '" + _email + "' and password = '" + _senha + "'";

        db = banco.getReadableDatabase();

        cursor = db.query("users", campos, where, null, null, null,
                null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int columnValue = cursor.getColumnIndex("nome");

                if (columnValue >= 0){
                    username = cursor.getString(columnValue);
                } else {
                    Log.e("getLogin", "Coluna 'nome' não encontrada.");
                }
            }
            cursor.close();
        } else {
            Log.e("getLogin", "Nenhum dado encontrado na consulta.");
        }

        //db.close();
        return username;
    }

    public String getUsername(int id) {
        Cursor cursor = db.query("users", new String[]{"nome"}, "id = ?", new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String usuario = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
            cursor.close();
            return usuario;
        } else {
            cursor.close();
            return null;
        }
    }

    public String getEventos(int id) {
        Cursor cursor = db.query("events", new String[]{"titulo", "descricao", "local"},
                "id = ?", new String[]{String.valueOf(id)}, null, null, null);

        StringBuilder teste = new StringBuilder();

        String titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"));
        String descricao = cursor.getString(cursor.getColumnIndexOrThrow("descricao"));
        String local = cursor.getString(cursor.getColumnIndexOrThrow("local"));

        teste.append("Titulo: ").append(titulo)
                .append("\nDescricao: ").append(descricao)
                .append("\nLocal: ").append(local);

        cursor.close();

        return teste.toString();
    }
}
