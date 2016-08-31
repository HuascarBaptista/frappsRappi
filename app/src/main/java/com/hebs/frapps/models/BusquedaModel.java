package com.hebs.frapps.models;

import android.content.Context;
import android.database.MatrixCursor;

import com.hebs.frapps.models.modelsRealm.Busquedas;
import com.hebs.frapps.models.modelsRealm.ParBusqueda;
import com.hebs.frapps.models.modelsRealm.Temas;

import java.util.ArrayList;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class BusquedaModel {
    public static Busquedas crearActualizarBusqueda(Context context, final String busqueda) {
        Realm realm = UniversalModel.crearConexion(context);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Busquedas result = realm.where(Busquedas.class).equalTo("_nombre", busqueda).findFirst();
                Busquedas creador;
                if (result == null) {


                    creador = realm.createObject(Busquedas.class);
                    creador.set_nombre(busqueda);
                } else {
                    creador = result;
                }
                creador.set_time(System.currentTimeMillis());
            }
        });


        return obtenerBusquedaPorNombre(context, busqueda);
    }

    public static Busquedas obtenerBusquedaPorNombre(Context context, String nombre) {
        Realm realm = UniversalModel.crearConexion(context);


        Busquedas _resultado = realm.where(Busquedas.class).contains("_nombre", nombre, Case.INSENSITIVE).findFirst();

        return _resultado;
    }

    //Busco por nombres de aplicaciones y por busquedas realizadas, si son aplicaciones les coloco el id para saber a dnd voy si le da click
    public static ArrayList<ParBusqueda> obtenerTodasNombre(Context context, String nombre, MatrixCursor cursor) {
        Realm realm = UniversalModel.crearConexion(context);

        RealmResults<Busquedas> _resultado = realm.where(Busquedas.class).contains("_nombre", nombre, Case.INSENSITIVE).findAllSorted("_nombre", Sort.ASCENDING);
        RealmResults<Temas> _resultado2 = realm.where(Temas.class).contains("_nombre", nombre, Case.INSENSITIVE).findAllSorted("_nombre", Sort.ASCENDING);

        ArrayList<ParBusqueda> _aux = new ArrayList<>();

        int j = 0;
        int i = 0;
        String _temp[] = new String[2];

        while (j < _resultado.size() && i < _resultado2.size()) {
            if (_resultado.get(j).get_nombre().compareToIgnoreCase(_resultado2.get(i).get_nombre()) > 0) {
                _temp[0] = _resultado2.get(i).get_idNumerico() + "";
                _temp[1] = _resultado2.get(i).get_nombre() + "";

                cursor.addRow(_temp);
                _aux.add(new ParBusqueda(_resultado2.get(i).get_idNumerico(), _temp[1]));
                i++;


            } else {
                //Simulando el id y me sirve cmo flag
                _temp[0] = "-1";
                _temp[1] = _resultado.get(j).get_nombre() + "";

                cursor.addRow(_temp);
                _aux.add(new ParBusqueda(-1, _temp[1]));

                j++;

            }
        }

        while (j < _resultado.size()) {
            _temp[0] = "-1";
            _temp[1] = _resultado.get(j).get_nombre() + "";

            cursor.addRow(_temp);
            _aux.add(new ParBusqueda(-1, _temp[1]));

            j++;
        }

        while (i < _resultado2.size()) {
            _temp[0] = _resultado2.get(i).get_idNumerico() + "";
            _temp[1] = _resultado2.get(i).get_nombre() + "";
            _aux.add(new ParBusqueda(_resultado2.get(i).get_idNumerico(), _temp[1]));

            cursor.addRow(_temp);
            i++;
        }


        return _aux;

    }

    public static RealmResults<Busquedas> obtenerTodasXTiempo(Context context, boolean asc) {
        Realm realm = UniversalModel.crearConexion(context);

        RealmResults<Busquedas> _resultado = realm.where(Busquedas.class).findAllSorted("_time", (asc ? Sort.ASCENDING : Sort.DESCENDING));

        return _resultado;

    }


}
