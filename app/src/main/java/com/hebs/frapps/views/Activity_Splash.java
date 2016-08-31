package com.hebs.frapps.views;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.Window;

import com.hebs.frapps.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

@Fullscreen
@WindowFeature({ Window.FEATURE_NO_TITLE})
@EActivity(R.layout.activity_splash)
public class Activity_Splash extends Activity {

    @ViewById(R.id.relative_superior)
    public View relativeSuperior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @AfterViews
    protected void iniciarAnimacion() {

        if (relativeSuperior != null) {


        relativeSuperior.animate()
                .alpha(0.0f)
                .scaleX(0.1f)
                .scaleY(0.1f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setDuration(1000)
                .setStartDelay(2000)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    //Cuando se termina el splash cargo las categorias
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Activity_Temas_Generales_.intent(getBaseContext()).flags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NEW_TASK).start();
                        overridePendingTransition(0, 0); //0 for no animation
                        finish();

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                })
                .start();
        }
    }
}
