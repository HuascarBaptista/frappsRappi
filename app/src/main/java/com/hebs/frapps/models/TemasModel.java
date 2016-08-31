package com.hebs.frapps.models;

import android.content.Context;

import com.hebs.frapps.models.modelsRealm.Temas;
import com.hebs.frapps.views.Activity_Tema_Detalle;

import java.util.ArrayList;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class TemasModel {
    public static Temas crearTema(final Context context,
                                  final String _id,
                                  final String _nombre,
                                  final String _descripcion,
                                  final String _link,
                                  final long _fechaCreacion,
                                  final int _participantes,
                                  final String _icono,
                                  final String _imagen,
                                  final String _imagen_pequena) {


        Realm realm = UniversalModel.crearConexion(context);

        //Guardo la informacion del servidor, o la actualizo dependeineod
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Temas result = realm.where(Temas.class).equalTo("_id", _id).findFirst();
                Temas tema;
                if (result == null) {
                    tema = realm.createObject(Temas.class);
                    tema.set_id(_id);
                    tema.set_idNumerico(getNextKey(context));
                } else {
                    tema = result;
                }
                tema.set_nombre(_nombre);
                tema.set_descripcion(_descripcion);
                tema.set_link(_link);
                tema.set_fechaCreacion(_fechaCreacion);
                tema.set_participantes(_participantes);
                tema.set_icono(_icono);
                tema.set_imagen(_imagen);
                tema.set_imagen_pequena(_imagen_pequena);
            }
        });


        return obtenerTemaPorId(context, _id);
    }
    public static int getNextKey(Context context)
    {
        Realm realm = UniversalModel.crearConexion(context);
        return realm.where(Temas.class).max("_idNumerico").intValue() + 1;
    }
    public static Temas obtenerTemaPorId(Context context, String id) {
        Realm realm = UniversalModel.crearConexion(context);

        Temas _resultado = realm.where(Temas.class).equalTo("_id", id).findFirst();

        return _resultado;
    }

    public static RealmResults<Temas> obtenerTemasPorNombre(Context context, String nombre) {
        Realm realm = UniversalModel.crearConexion(context);

        return realm.where(Temas.class).contains("_nombre", nombre, Case.INSENSITIVE).findAllSorted("_nombre");
    }


    public static RealmResults<Temas> obtenerTodas(Context context) {
        Realm realm = UniversalModel.crearConexion(context);

        RealmResults<Temas> _resultado = realm.where(Temas.class).findAll();

        return _resultado;

    }


    public static ArrayList<Temas> temasOrdenadosNombre(Context context) {
        Realm realm = UniversalModel.crearConexion(context);

        RealmResults<Temas> _resultado = realm.where(Temas.class).findAllSorted("_nombre");
        ArrayList<Temas> _Temas = new ArrayList<>();
        for (int i = 0; i < _resultado.size(); i++) {
            _Temas.add(_resultado.get(i));
        }
        return _Temas;

    }

    public static ArrayList<Temas> temasOrdenadosFecha(Context context) {
        Realm realm = UniversalModel.crearConexion(context);

        RealmResults<Temas> _resultado = realm.where(Temas.class).findAllSorted("_fechaCreacion",Sort.DESCENDING);
        ArrayList<Temas> _Temas = new ArrayList<>();
        for (int i = 0; i < _resultado.size(); i++) {
            _Temas.add(_resultado.get(i));
        }
        return _Temas;

    }

    public static ArrayList<Temas> temasOrdenadosParticipantes(Context context) {
        Realm realm = UniversalModel.crearConexion(context);

        RealmResults<Temas> _resultado = realm.where(Temas.class).findAllSorted("_participantes");

        ArrayList<Temas> _Temas = new ArrayList<>();
        for (int i = 0; i < _resultado.size(); i++) {
            _Temas.add(_resultado.get(i));
        }
        return _Temas;
    }

    public static Temas obtenerTemaPorIdNumerico(Activity_Tema_Detalle context, int idApp) {
        Realm realm = UniversalModel.crearConexion(context);

        Temas _resultado = realm.where(Temas.class).equalTo("_idNumerico", idApp).findFirst();

        return _resultado;
    }
}
