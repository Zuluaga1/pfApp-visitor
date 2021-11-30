package com.example.visitor.models;


public class User {
    //private String pro_user;
    private String pro_nombre;
    private String pro_placa;
    private String  usuario;
    private String  contraseña;
    private String fullname;
    private String placa;
    private String rol;

    public User(String usuario, String contraseña) {
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    public User (String usuario, String contraseña, String fullname, String placa, String rol){
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.fullname = fullname;
        this.placa  = placa;
        this.rol = rol;
    }



    public String getPro_nombre() {
        return pro_nombre;
    }

    public void setPro_nombre(String pro_nombre) {
        this.pro_nombre = pro_nombre;
    }

    public String getPro_placa() {
        return pro_placa;
    }

    public void setPro_placa(String pro_placa) {
        this.pro_placa = pro_placa;
    }
}