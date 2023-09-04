package com.gn.translateseas.Chat;

public class Mensaje {
    private int id_destinatario;
    private String destinatario;
    private String remitente;

    private String fecha;
    private String mensaje;

    public Mensaje(int id_destinatario, String destinatario, String remitente, String fecha, String mensaje) {
        this.destinatario = destinatario;
        this.remitente = remitente;
        this.fecha = fecha;
        this.mensaje = clearMessage(mensaje);
        this.id_destinatario = id_destinatario;
    }

    public Mensaje() {}

    private String clearMessage(String mensaje){
        return mensaje.trim();
    }

    /* ---- GETTER AND SETTER ---- */
    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setId_destinatario(int id_destinatario) {
        this.id_destinatario = id_destinatario;
    }
}
