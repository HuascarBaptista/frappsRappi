package com.hebs.frapps.views;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.hebs.frapps.R;
import com.hebs.frapps.adapters.CategoriasViewPagerAdapter;
import com.hebs.frapps.models.modelsRealm.Temas;
import com.hebs.frapps.presenters.CategoriasDetallePresenter;
import com.hebs.frapps.utils.MyShared_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.HashMap;

@Fullscreen
@WindowFeature({Window.FEATURE_NO_TITLE})
@EActivity(R.layout.activity_categorias_detalle)
public class Activity_Categorias_Detalle extends BaseActivity {


    public RecyclerView.Adapter recyclerAdapter;

    @ViewById
    TabLayout appbartabs;

    @ViewById(R.id.viewpager)
    ViewPager viewPager;


    ArrayList<String> _titulos;
    HashMap<String, ArrayList<Temas>> _data;

    @StringRes(R.string.servicio_reddit)
    public
    String servicio_reddit;
    @ViewById(R.id.relative_superior)
    public View vista_superior;
    @Pref
    public MyShared_ myPrefs;

    @ViewById
    LinearLayout linear_actualizar;
    @ViewById
    AppBarLayout appbarLayout;


    private CategoriasDetallePresenter categoriasDetallePresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupWindowInAnimations();


    }


    @AfterViews
    public void cargarData() {


        cargandoCustomDialog(true);
        cargarInformacion();


    }

    public void cargarInformacion() {
        if (categoriasDetallePresenter == null)
            categoriasDetallePresenter = new CategoriasDetallePresenter(this);
        categoriasDetallePresenter.obtenerInformacion();
    }

    public void informacionActualizada(boolean data) {

        if (data) {
            linear_actualizar.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            appbarLayout.setVisibility(View.VISIBLE);

        } else {
            linear_actualizar.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.GONE);
            appbarLayout.setVisibility(View.GONE);

        }

        cargandoCustomDialog(false);
    }

    public void informacionCargada() {
        _titulos = new ArrayList<>();
        _data = new HashMap<>();

        categoriasDetallePresenter.manejarInformacion(getBaseContext(), _titulos, _data);
        if (_titulos.size() > 0) {
            informacionActualizada(true);


            super.setDrawer(drawer);
            super.cargarAppBar(false, getString(R.string.categorias));




            //Sete el viewpager del los fragmnets
            viewPager.setAdapter(new CategoriasViewPagerAdapter(
                    getSupportFragmentManager(), _titulos, _data));

            //La animacion del viewpager
            viewPager.setPageTransformer(true, new CubeOutTransformer());


            //Asigno q sea scroll horizontal y q agarre los titulos del viewpagar
            appbartabs.setupWithViewPager(viewPager);
            appbartabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        }else{
            informacionActualizada(false);

        }


    }

}
