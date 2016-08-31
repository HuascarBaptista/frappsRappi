package com.hebs.frapps.presenters;

import com.hebs.frapps.views.Activity_Splash;


public class SplashPresenter {
    private Activity_Splash _view;

    public SplashPresenter(Activity_Splash view) {
        this._view = view;

    }

    public Activity_Splash get_view() {
        return _view;
    }

    public void set_view(Activity_Splash _view) {
        this._view = _view;
    }


}
