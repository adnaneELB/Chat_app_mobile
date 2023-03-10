package com.example.models;

public class Contactos {
    private String id;
    private String nombre;
    int foto,unseenMsgs;
    private String lastmsg;
    private String chatKey;

    public int getUnseenMsgs() {
        return unseenMsgs;
    }

    public String getLastmsg() {
        return lastmsg;
    }

    public String getNombre() {
        return nombre;
    }

    public int getFoto() {
        return foto;
    }

    public Contactos() {
    }

    public Contactos(String nombre, int foto, int unseenMsgs, String lastmsg,String chatKey,String id) {
        this.nombre = nombre;
        this.foto = foto;
        this.unseenMsgs = unseenMsgs;
        this.lastmsg = lastmsg;
        this.chatKey=chatKey;
        this.id=id;

    }

    public String getId() {
        return id;
    }

    public String getChatKey() {
        return chatKey;
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
