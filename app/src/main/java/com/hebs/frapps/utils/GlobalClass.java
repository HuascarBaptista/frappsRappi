package com.hebs.frapps.utils;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

public class GlobalClass extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        //Para visualizar Stehto
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this)
                                .withMetaTables()
                                .withLimit(99999)
                                .withDescendingOrder()
                                .build())
                        .build());


    }

}
