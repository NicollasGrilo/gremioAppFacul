package com.example.gremioapp;

public class EventoClass {

    private String titulo;
    private String descricao;
    private String local;
    private byte[] imagem; // Imagem armazenada como byte array (pode ser alterado para String caso seja uma URL)

    // Construtor
    public EventoClass(String titulo, String descricao, String local, byte[] imagem) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.local = local;
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

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
}
