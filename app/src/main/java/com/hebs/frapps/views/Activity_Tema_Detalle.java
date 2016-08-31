package com.hebs.frapps.views;

import android.animation.Animator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hebs.frapps.R;
import com.hebs.frapps.presenters.TemaDetallePresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

@Fullscreen
@WindowFeature({Window.FEATURE_NO_TITLE})
@EActivity(R.layout.activity_tema_detalle)
public class Activity_Tema_Detalle extends BaseActivity {

    @ViewById
    ImageView icono_app;
    @ViewById
    TextView titulo_app;
    @ViewById
    TextView url_app;
    @ViewById
    TextView desarrollador_app;
    @ViewById
    TextView descripcion;
    @ViewById
    FloatingActionButton compartir;

    @Extra
    int idApp;


    TemaDetallePresenter temaDetallePresenter;

    @AfterViews
    public void cargarInformacionAppBar() {
        super.setDrawer(drawer);
        super.cargarAppBar(true, "");

        cargandoDialog(true);
        temaDetallePresenter = new TemaDetallePresenter(this);
        //Lleno toda la info y seteo los clicks
        boolean _cargo = temaDetallePresenter.llenarInformacion(idApp, titulo_app, icono_app, desarrollador_app, descripcion, compartir, url_app);
        cargandoDialog(false);

        animarFloatingButton();

        if (!_cargo) {
            Toast.makeText(this, getString(R.string.aplicacion_invalida), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    //Animo el boton de action floating
    private void animarFloatingButton() {

        compartir.setScaleX(0);
        compartir.setScaleY(0);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            final Interpolator interpolador = AnimationUtils.loadInterpolator(getBaseContext(),
                    android.R.interpolator.fast_out_slow_in);

            compartir.animate()
                    .scaleX(1)
                    .scaleY(1)
                    .setInterpolator(interpolador)
                    .setDuration(600)
                    .setStartDelay(1000)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupWindowInAnimations();

    }

}



