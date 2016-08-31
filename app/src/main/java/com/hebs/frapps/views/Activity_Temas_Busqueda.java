package com.hebs.frapps.views;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.hebs.frapps.R;
import com.hebs.frapps.models.modelsRealm.Temas;
import com.hebs.frapps.presenters.TemaBusquedaPresenter;
import com.hebs.frapps.utils.MyShared_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;

@Fullscreen
@WindowFeature({Window.FEATURE_NO_TITLE})
@EActivity(R.layout.activity_temas_busqueda)
public class Activity_Temas_Busqueda extends BaseActivity {

    @ViewById(R.id.recyclerview)
    public RecyclerView recyclerView;

    public RecyclerView.Adapter recyclerAdapter;

    @ViewById(R.id.relative_superior)
    public View vista_superior;

    @Pref
    public MyShared_ myPrefs;

    ArrayList<Temas> _data;

    @Extra
    String nombreApp;

    private TemaBusquedaPresenter temaBusquedaPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupWindowInAnimations();


    }

    @UiThread
    public void informacionActualizada() {


        cargandoCustomDialog(false);
    }


    //Me busca l informacion de la bd, si no existe la app me salgo
    @AfterViews
    public void cargarInformacionAppBar() {
        if (nombreApp.equals("")) {
            Toast.makeText(this, getString(R.string.aplicacion_invalida), Toast.LENGTH_SHORT).show();
            finish();
        }

        temaBusquedaPresenter = new TemaBusquedaPresenter(this);

        super.setDrawer(drawer);
        super.cargarAppBar(true, nombreApp);

        //  cargandoCustomDialog(true);
        _data = new ArrayList<>();

        temaBusquedaPresenter.manejarInformacion(getBaseContext(), _data, nombreApp);


    }


}
