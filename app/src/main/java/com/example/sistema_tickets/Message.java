package com.example.sistema_tickets;

public class Message {
    String deta_id;
    String tickes_id;
    String o_user;
    String d_user;
    String d_descrip;
    String fecha;
    String estado;
    String respuesta;
    String archivo;

    public Message(String tickes_id, String d_descrip, String fecha) {

        this.tickes_id = tickes_id;

        this.d_descrip = d_descrip;
        this.fecha = fecha;

    }

    public Message() {
    }

    public String getDeta_id() {
        return deta_id;
    }

    public void setDeta_id(String deta_id) {
        this.deta_id = deta_id;
    }

    public String getTickes_id() {
        return tickes_id;
    }

    public void setTickes_id(String tickes_id) {
        this.tickes_id = tickes_id;
    }

    public String getO_user() {
        return o_user;
    }

    public void setO_user(String o_user) {
        this.o_user = o_user;
    }

    public String getD_user() {
        return d_user;
    }

    public void setD_user(String d_user) {
        this.d_user = d_user;
    }

    public String getD_descrip() {
        return d_descrip;
    }

    public void setD_descrip(String d_descrip) {
        this.d_descrip = d_descrip;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    @Override
    public String toString() {
        return d_descrip+""+fecha;
    }



}
