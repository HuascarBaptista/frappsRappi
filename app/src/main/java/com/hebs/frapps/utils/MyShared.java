package com.hebs.frapps.utils;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

//Clase que verifica si la data no ha cambiado

@SharedPref(SharedPref.Scope.UNIQUE)
public interface MyShared {

    // The field name will have default value "John"
    @DefaultString("SinInfo")
    String json_reddit();

}