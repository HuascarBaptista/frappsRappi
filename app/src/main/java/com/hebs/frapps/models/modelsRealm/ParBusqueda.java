package com.hebs.frapps.models.modelsRealm;

public class ParBusqueda {
    private int _id;
    private String nombre;

    public ParBusqueda() {
    }

    public ParBusqueda(int _id, String nombre) {
        this._id = _id;
        this.nombre = nombre;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}