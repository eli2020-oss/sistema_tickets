package com.example.sistema_tickets;

public class categoria {



    private String cate_id;
    private String t_categoria;
    private  String estado;
    public categoria() {
    }
    public String toString() {
        return t_categoria;
    }


    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public String getT_categoria() {
        return t_categoria;
    }

    public void setT_categoria(String t_categoria) {
        this.t_categoria = t_categoria;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    public categoria(String cate_id, String t_categoria, String estado) {
        this.cate_id = cate_id;
        this.t_categoria = t_categoria;
        this.estado = estado;
    }


}
