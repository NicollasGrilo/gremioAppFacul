package com.example.gremioapp;

public class EventoClass {

    private int id;
    private String titulo;
    private String descricao;
    private String local;
    private String localDateTime;
    private byte[] imagem;

    // Construtor
    public EventoClass(String titulo, String descricao, String local, String localDateTime, byte[] imagem) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.local = local;
        this.localDateTime = localDateTime;
        this.imagem = imagem;
    }

    // Getters e Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(String localDateTime) {
        this.localDateTime = localDateTime;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
}
