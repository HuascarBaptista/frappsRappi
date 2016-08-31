package com.hebs.frapps.models.modelsRealm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Busquedas extends RealmObject {
    private long _time;

    @PrimaryKey
    private String _nombre;

    public long get_time() {
        return _time;
    }

    public void set_time(long _time) {
        this._time = _time;
    }

    public String get_nombre() {
        return _nombre;
    }

    public void set_nombre(String _nombre) {
        this._nombre = _nombre;
    }
}
