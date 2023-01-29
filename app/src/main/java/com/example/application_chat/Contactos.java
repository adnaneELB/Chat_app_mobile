package com.example.application_chat;

public class Contactos {
    private String nombre;
    int foto;

    public String getNombre() {
        return nombre;
    }

    public int getFoto() {
        return foto;
    }

    public Contactos() {
    }

    public Contactos(String nombre, int foto) {
        this.nombre = nombre;
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Contactos{" +
                "nombre='" + nombre + '\'' +
                ", foto=" + foto +
                '}';
    }
}
