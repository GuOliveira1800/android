package com.example.recycleview;

import android.graphics.Bitmap;
import android.media.Image;
import android.widget.TextView;

public class Pessoa {

    private String name;
    private Bitmap imagem;

    public Pessoa(String name, Bitmap imagem) {
        this.name = name;
        this.imagem = imagem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImagem() {
        return imagem;
    }

    public void setImagem(Bitmap imagem) {
        this.imagem = imagem;
    }
}
