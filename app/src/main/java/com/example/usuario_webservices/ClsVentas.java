package com.example.usuario_webservices;

public class ClsVentas {

    private String cod_usuario;
    private String fecha;
    private String  usuarioVenta;

    public ClsVentas() {
    }

    public String getCod_usuario() {
        return cod_usuario;
    }

    public void setCod_usuario(String cod_usuario) {
        this.cod_usuario = cod_usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUsuarioVenta() {
        return usuarioVenta;
    }

    public void setUsuarioVenta(String usuarioVenta) {
        this.usuarioVenta = usuarioVenta;
    }
}
