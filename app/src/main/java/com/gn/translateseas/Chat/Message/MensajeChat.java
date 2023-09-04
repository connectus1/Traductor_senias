package com.gn.translateseas.Chat.Message;
public class MensajeChat {
    private String mensaje;
    private String fecha;
    private boolean flag;

    public MensajeChat(String mensaje, String fecha, boolean flag) {
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.flag = flag;
    }

    public MensajeChat() {
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
