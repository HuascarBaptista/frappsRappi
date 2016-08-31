package com.hebs.frapps.models;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class UniversalModel {
    //Creo la conexion a la bd
    public static Realm crearConexion(Context context) {
        RealmConfiguration config = new RealmConfiguration.Builder(context)
                .name("rappihebs.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        // Use the config
        return Realm.getInstance(config);
    }


}
