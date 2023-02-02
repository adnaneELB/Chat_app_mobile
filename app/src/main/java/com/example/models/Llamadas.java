package com.example.models;

import java.util.ArrayList;

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
