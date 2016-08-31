package com.hebs.frapps.models.modelsRealm;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.TemasRealmProxy;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

@Parcel(implementations = {TemasRealmProxy.class},
        value = Parcel.Serialization.BEAN,
        analyze = {Temas.class})
public class Temas extends RealmObject {
    @PrimaryKey
    private String _id;
    private int _idNumerico;

    @Required
    @Index
    private String _nombre;
    private String _descripcion;
    private String _link;
    private long _fechaCreacion;
    private int _participantes;
    private String _icono;
    private String _imagen;
    private String _imagen_pequena;

    public Temas() {

    }

    public Temas(String _id, String _nombre, String _descripcion, String _link, long _fechaCreacion, int _participantes, String _icono, String _imagen, String _imagen_pequena) {
        this._id = _id;
        this._nombre = _nombre;
        this._descripcion = _descripcion;
        this._link = _link;
        this._fechaCreacion = _fechaCreacion;
        this._participantes = _participantes;
        this._icono = _icono;
        this._imagen = _imagen;
        this._imagen_pequena = _imagen_pequena;
    }

    public String get_id() {

        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_nombre() {
        return _nombre;
    }

    public void set_nombre(String _nombre) {
        this._nombre = _nombre;
    }

    public String get_descripcion() {
        return _descripcion;
    }

    public void set_descripcion(String _descripcion) {
        this._descripcion = _descripcion;
    }

    public String get_link() {
        return _link;
    }

    public void set_link(String _link) {
        this._link = _link;
    }

    public long get_fechaCreacion() {
        return _fechaCreacion;
    }

    public void set_fechaCreacion(long _fechaCreacion) {
        this._fechaCreacion = _fechaCreacion;
    }

    public int get_participantes() {
        return _participantes;
    }

    public void set_participantes(int _participantes) {
        this._participantes = _participantes;
    }

    public String get_icono() {
        return _icono;
    }

    public void set_icono(String _icono) {
        this._icono = _icono;
    }

    public String get_imagen() {
        return _imagen;
    }

    public void set_imagen(String _imagen) {
        this._imagen = _imagen;
    }

    public String get_imagen_pequena() {
        return _imagen_pequena;
    }

    public void set_imagen_pequena(String _imagen_pequena) {
        this._imagen_pequena = _imagen_pequena;
    }

    public int get_idNumerico() {
        return _idNumerico;
    }

    public void set_idNumerico(int _idNumerico) {
        this._idNumerico = _idNumerico;
    }
}
