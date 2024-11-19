package com.example.gremioapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateDB extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "banco_gremio.db";
    private static final int VERSAO = 1;

    public CreateDB(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabela usuários
        String sqlUsers = "CREATE TABLE users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nome TEXT NOT NULL,"
                + "email TEXT UNIQUE NOT NULL,"
                + "password TEXT NOT NULL,"
                + "roles INTEGER,"
                + "FOREIGN KEY (roles) REFERENCES roles(roleId)"
                + ");";
        db.execSQL(sqlUsers);

        // Tabela roles
        String sqlRoles = "CREATE TABLE roles ("
                + "roleId INTEGER PRIMARY KEY,"
                + "name TEXT CHECK(name in ('ADMIN', 'BASIC')) NOT NULL"
                + ");";
        db.execSQL(sqlRoles);

        // Tabela eventos
        String sqlEventos = "CREATE TABLE eventos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "titulo TEXT NOT NULL,"
                + "descricao TEXT,"
                + "local TEXT,"
                + "localDateTime TEXT,"
                + "user INTEGER,"
                + "image TEXT,"
                + "FOREIGN KEY (user) REFERENCES users(id)"
                + ");";
        db.execSQL(sqlEventos);

        String roleAdmin = "INSERT INTO roles (roleId, name) VALUES (1, 'ADMIN');";
        String roleBasic = "INSERT INTO roles (roleId, name) VALUES (2, 'BASIC');";
        db.execSQL(roleAdmin);
        db.execSQL(roleBasic);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS roles");
        db.execSQL("DROP TABLE IF EXISTS eventos");
        onCreate(db);
    }
}
