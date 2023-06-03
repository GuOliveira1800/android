package com.example.myapplication;

public class Note {

    int codigo;
    String titulo;
    String texto;

    public Note(int codigo, String titulo, String texto) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.texto = texto;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
