package com.hebs.frapps.presenters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.SearchView;

import com.hebs.frapps.R;
import com.hebs.frapps.models.BusquedaModel;
import com.hebs.frapps.models.TemasModel;
import com.hebs.frapps.models.modelsRealm.ParBusqueda;
import com.hebs.frapps.models.modelsRealm.Temas;
import com.hebs.frapps.views.Activity_Tema_Detalle_;
import com.hebs.frapps.views.Activity_Temas_Busqueda_;
import com.hebs.frapps.views.Activity_Temas_Generales_;
import com.hebs.frapps.views.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;


//Clase general q tiene metodos comunes
public class BasePresenter {
    ArrayList<ParBusqueda> _sugerencias;
    private BaseActivity _view;

    public BasePresenter(BaseActivity view) {
        this._view = view;

    }

    //Obterngo toda la informacion de las aplicaciones, con jerarquia de categorias
    public static void obtenerInformacionEnHash(Context context, ArrayList<String> titulos, HashMap<String, ArrayList<Temas>> data) {
        titulos.add(context.getString(R.string.todas));
        titulos.add(context.getString(R.string.m_s_recientes));
        titulos.add(context.getString(R.string.participantes));

        ArrayList<Temas> _todas = TemasModel.temasOrdenadosNombre(context);
        ArrayList<Temas> _recientes = TemasModel.temasOrdenadosFecha(context);
        ArrayList<Temas> _participantes = TemasModel.temasOrdenadosParticipantes(context);

       data.put(titulos.get(0),_todas);
       data.put(titulos.get(1),_recientes);
       data.put(titulos.get(2),_participantes);
    }


    public static void irCategorias(Activity _context, String tag) {
        Activity_Temas_Generales_.intent(_context).start();
        BaseActivity.setupWindowAnimations(_context);


    }

    private static void irBusquedaApp(Activity _context, String query) {
        Activity_Temas_Busqueda_.intent(_context).nombreApp(query).start();
        BaseActivity.setupWindowAnimations(_context);
    }

    public static void irDetalleApp(Activity _context, int tag) {
        Activity_Tema_Detalle_.intent(_context).idApp(tag).start();
        BaseActivity.setupWindowAnimations(_context);


    }

    public BaseActivity get_view() {
        return _view;
    }

    public void set_view(BaseActivity _view) {
        this._view = _view;
    }

    //Cargo la barra de busqueda y que debe hacer cuando se le hace submit o se le da click a un elemento
    public void cargarBuscador(final SearchView searchView) {


        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {

            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {


                if (_sugerencias.get(position).get_id()==-1) {
                    //Le dio click a una sugerencia
                    BusquedaModel.crearActualizarBusqueda(get_view(), _sugerencias.get(position).getNombre());

                    irBusquedaApp(get_view(), _sugerencias.get(position).getNombre());
                    //Ir a la pagina de busqueda de apps
                } else {
                    //Le dio click a una app
                    Activity_Tema_Detalle_.intent(get_view().getBaseContext()).idApp(_sugerencias.get(position).get_id()).flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();
                    ((Activity) get_view()).overridePendingTransition(0, 0); //0 for no animation

                    //Ir a la pagina de la app
                }

                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                //Guardo la busqueda y voy a la pantalla de buscar
                BusquedaModel.crearActualizarBusqueda(get_view(), query);
                irBusquedaApp(get_view(), query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                String[] columnNames = {"_id", "nombre"};
                MatrixCursor cursor = new MatrixCursor(columnNames);

                //Lleno el arreglo de sugerencias con nombre de aplicaciones y con sugerencias ya buscadas
                _sugerencias = BusquedaModel.obtenerTodasNombre(get_view(), query, cursor);


                String[] from = {"text"};
                SimpleCursorAdapter busStopCursorAdapter = new SimpleCursorAdapter(get_view(),
                        android.R.layout.simple_list_item_1,
                        cursor,
                        new String[]{"nombre"},
                        new int[]{android.R.id.text1}, 1);

                SearchView.SearchAutoComplete autoCompleteTextView = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
                if (autoCompleteTextView != null) {

                    autoCompleteTextView.setDropDownBackgroundDrawable(ContextCompat.getDrawable(get_view(), R.drawable.abc_popup_background_mtrl_mult));
                }

                searchView.setSuggestionsAdapter(busStopCursorAdapter);

                return false;
            }


        });
    }


}
