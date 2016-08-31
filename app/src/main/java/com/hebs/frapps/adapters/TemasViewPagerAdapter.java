package com.hebs.frapps.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hebs.frapps.models.modelsRealm.Temas;
import com.hebs.frapps.views.FragmentClasificacionTemas;

import java.util.ArrayList;
import java.util.HashMap;

//Hace el tratamiento de los fragmentes del viewpagar de las categorias
public class TemasViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<String> _titulos;
    private HashMap<String, ArrayList<Temas>> _data;

    public TemasViewPagerAdapter(FragmentManager fm, ArrayList<String> _titulos, HashMap<String, ArrayList<Temas>> _data) {
        super(fm);
        this._titulos = _titulos;
        this._data = _data;
    }

    @Override
    public int getCount() {
        return _titulos.size();
    }

    @Override
    public Fragment getItem(int position) {

        Fragment f = null;

        String _titulo = _titulos.get(position);
        ArrayList<Temas> _apps = _data.get(_titulo);


        f = FragmentClasificacionTemas.newInstance(_titulo, _apps);

        return f;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return _titulos.get(position);
    }
}