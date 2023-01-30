package com.example.models;

public class Llamadas extends Contactos {

    int Callfoto;

    public Llamadas() {

    }

    public Llamadas(String nombre, int foto, int callfoto) {
        super(nombre, foto);
        Callfoto = callfoto;
    }

    public int getCallfoto() {
        return Callfoto;
    }
}
