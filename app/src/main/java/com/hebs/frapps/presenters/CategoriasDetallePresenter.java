package com.hebs.frapps.presenters;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hebs.frapps.R;
import com.hebs.frapps.adapters.CategoriaDetalleAdapter;
import com.hebs.frapps.models.TemasModel;
import com.hebs.frapps.models.modelsRealm.Temas;
import com.hebs.frapps.utils.GridAutofitLayoutManager;
import com.hebs.frapps.views.Activity_Categorias_Detalle;
import com.hebs.frapps.views.FragmentCategoria;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.novoda.merlin.MerlinsBeard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import jp.wasabeef.recyclerview.animators.LandingAnimator;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

//Este es el presentador de las categorias q se muestran en general, con las pestañas
public class CategoriasDetallePresenter {

    private Activity_Categorias_Detalle _view;
    private MerlinsBeard merlinsBeard;


    public CategoriasDetallePresenter(Activity_Categorias_Detalle view) {
        this._view = view;

    }


    public Activity_Categorias_Detalle get_view() {
        return _view;
    }

    public void set_view(Activity_Categorias_Detalle _view) {
        this._view = _view;
    }


    public void cargarDataFragment(Activity_Categorias_Detalle view, ArrayList<Temas> data, String categoria, FragmentCategoria _este) {
        _este.recyclerView.setHasFixedSize(true);


        boolean _grid = false;
        //Si es tablet entonces grid si no entonces linear

        if (_este.getResources().getBoolean(R.bool.portrait_only)) {
            _grid = false;
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(_este.getContext(), LinearLayoutManager.VERTICAL, false);
            _este.recyclerView.setLayoutManager(layoutManager);
            _este.recyclerView.setItemAnimator(new SlideInLeftAnimator());

        } else {
            _grid = true;
            GridAutofitLayoutManager layoutManager = new GridAutofitLayoutManager(_este.getContext(), 0);
            _este.recyclerView.setLayoutManager(layoutManager);
            _este.recyclerView.setItemAnimator(new LandingAnimator());


        }


        _este.recyclerAdapter = new CategoriaDetalleAdapter(get_view(),categoria, data);
        _este.recyclerView.setAdapter(_este.recyclerAdapter);

    }

    public void manejarInformacion(Context baseContext, ArrayList<String> _titulos, HashMap<String, ArrayList<Temas>> _data) {
        BasePresenter.obtenerInformacionEnHash(baseContext, _titulos, _data);
    }

    //Pregunto si tengo internet y me descargo la data del server
    public void obtenerInformacion() {

        merlinsBeard = MerlinsBeard.from(this._view.getBaseContext());
        if (merlinsBeard.isConnected()) {
            //Descargar información del servidor
            Ion.with(get_view().getBaseContext())
                    .load(get_view().servicio_reddit)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {

                            if (e == null) {
                                if (result != null) {
                                    // Si no hubo error entonces compruebo si la info la habia almacenado o si el json es diferente
                                    if (!get_view().myPrefs.json_reddit().get().equals(result.toString())) {
                                        //No tengo esta info, procedo a guardarla
                                        guardarInformacion(result);

                                    }
                                } else {
                                    Toast.makeText(get_view(), get_view().getString(R.string.servicio_no_emitio), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(get_view(), get_view().getString(R.string.servicio_error) + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            manejarInformacion();


                        }
                    });
        } else {
            Snackbar.make(get_view().vista_superior, get_view().getString(R.string.sin_conexion), Snackbar.LENGTH_LONG)
                    .show();
            //Llamar a la vista para que actualice
            manejarInformacion();
        }
    }

    private void manejarInformacion() {
        get_view().informacionCargada();

    }

    //Guardo toda la info de las apps en una bd local
    private void guardarInformacion(JsonObject s) {
        if (s.has("data"))
            if (s.getAsJsonObject("data").has("children")) {
                JsonArray _apps = s.getAsJsonObject("data").getAsJsonArray("children");
                JsonObject _app;
                String id = "";
                int participantes = -1;
                String nombre = "";
                String descripcion = "";
                String link = "";
                String fechaString = "";
                long fechaCreacion;
                String icono = "";
                String imagen = "";
                String imagen_pequena="";

                for (int i = 0; i < _apps.size(); i++) {
                    _app = _apps.get(i).getAsJsonObject();
                    if(_app.has("data")){
                        _app=_app.get("data").getAsJsonObject();
                    }
                    id = "";
                    participantes = -1;
                    nombre = "";
                    descripcion = "";
                    link = "";
                    fechaString = "";
                    fechaCreacion = 0;
                    icono = "";
                    imagen = "";
                    imagen_pequena = "";
                    icono = "";

                  

                    if (_app.has("id") && !_app.get("id").isJsonNull()) {

                        id = _app.get("id").getAsString();

                    }
                    if (_app.has("title") && !_app.get("title").isJsonNull()) {

                        nombre = _app.get("title").getAsString();

                    }
                   
                    if (_app.has("public_description") && !_app.get("public_description").isJsonNull() && !_app.get("public_description").getAsString().equals("")) {

                        descripcion = _app.get("public_description").getAsString();

                    }else{
                        if (_app.has("description") && !_app.get("description").isJsonNull()) {

                            descripcion = _app.get("description").getAsString();

                        }
                    }
                    if (_app.has("url") && !_app.get("url").isJsonNull()) {
                        link = _app.get("url").getAsString();

                    }
                    if (_app.has("created") && !_app.get("created").isJsonNull()) {
                        fechaCreacion = _app.get("created").getAsLong();

                    }

                    if (_app.has("banner_img") && !_app.get("banner_img").isJsonNull()) {
                        imagen = _app.get("banner_img").getAsString();

                    }
                    if (_app.has("icon_img") && !_app.get("icon_img").isJsonNull()) {
                        icono = _app.get("icon_img").getAsString();

                    }
                    if (_app.has("header_img") && !_app.get("header_img").isJsonNull()) {
                        imagen_pequena = _app.get("header_img").getAsString();

                    }
                    if (_app.has("subscribers") && !_app.get("subscribers").isJsonNull()) {
                        participantes = _app.get("subscribers").getAsInt();

                    }
                    TemasModel.crearTema(get_view(),id,
                    nombre,
                    descripcion,
                    link,
                    fechaCreacion,
                    participantes,
                    icono,
                    imagen,
                            imagen_pequena);

                }
                get_view().myPrefs.json_reddit().put(s.toString());
            }

    }
}
