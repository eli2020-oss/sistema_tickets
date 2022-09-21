package com.example.sistema_tickets;

public class filial
{
    private int  id_filial;
    private String nombre;

    @Override
    public String toString() {
        return nombre;
    }


    public int getId_filial() {
        return id_filial;
    }

    public void setId_filial(int id_filial) {
        this.id_filial = id_filial;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public filial(int id_filial, String nombre) {
        this.id_filial = id_filial;
        this.nombre = nombre;
    }
    public filial() {
    }


}
