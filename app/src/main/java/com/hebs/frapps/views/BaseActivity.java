package com.hebs.frapps.views;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.hebs.frapps.R;
import com.hebs.frapps.presenters.BasePresenter;
import com.wang.avi.AVLoadingIndicatorView;

import org.androidannotations.annotations.EActivity;


@EActivity
public class BaseActivity extends AppCompatActivity {
    public ProgressDialog progressDialog;
    public Dialog dialog;
    protected BasePresenter basePresenter;

    DrawerLayout drawer;
    NavigationView navigationView;

    //Animacion de una nueva actividad
    public static void setupWindowAnimations(Activity view) {
        if (view != null && view instanceof Activity) {
            view.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        }
        basePresenter = new BasePresenter(this);
    }

    //FUncion de dialogo simple de cargA
    void cargandoDialog(boolean activo) {
        if (activo) {
            progressDialog = progressDialog.show(this, getString(R.string.cargando),
                    getString(R.string.espere), true);
        } else {
            if (progressDialog != null)
                progressDialog.dismiss();
        }
    }

    //El dialog custom que indica que esta descargando data del servidor
    public void cargandoCustomDialog(boolean activo) {
        if (activo) {
            dialog = new Dialog(this, R.style.myCoolDialog);
            // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_cargando_informacion);
            //  dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setTitle(getString(R.string.cargando));
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();


            AVLoadingIndicatorView avi = (AVLoadingIndicatorView) dialog.findViewById(R.id.avi);
            avi.smoothToShow();
        } else {
            if (dialog != null)
                dialog.dismiss();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dialog != null)
            dialog.dismiss();

        if (progressDialog != null)
            progressDialog.dismiss();
    }

    //Cambiar el titulo del toolbar
    public void cambiarAppBarTitle(String titulo) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(titulo);
    }

    //Caegar el toolbar y el menu, tambien agrega el titulo
    public void cargarAppBar(boolean atras, String titulo) {
        //App bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        cambiarAppBarTitle(titulo);

        if (atras) {

            //Para cambiar el menu por la flecha atras
            toolbar.setNavigationIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.abc_ic_ab_back_material));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //What to do on back clicked
                    onBackPressed();
                }
            });


        }
    }

    @Override
    public void onBackPressed() {

        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);


        }
    }

    public void setDrawer(DrawerLayout drawer) {
        this.drawer = drawer;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_toolbar, menu);

        // Para asociar la busqueda al toolbar
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        basePresenter.cargarBuscador(searchView);


        return true;
    }


    //Animacion de salida de actividad
    public void setupWindowInAnimations() {
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

    }
}
