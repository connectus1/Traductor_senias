package com.gn.translateseas.Chat.AddFriend;
public class Amigo {
    private int id;
    private String usuario;
    private String correo;

    public Amigo(int id, String usuario, String correo) {
        this.id = id;
        this.usuario = usuario;
        this.correo = correo;
    }

    public Amigo() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
