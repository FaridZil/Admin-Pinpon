package com.example.estadisticastorneo.bean;

public class User {
    private String nombre;
    private String userID;
    private String email;
    private String password;
    private boolean active;

    public User(String nombre, String userID, String email, String password, boolean active) {
        this.nombre = nombre;
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.active = active;
        int x = 0;
    }

    public User() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
