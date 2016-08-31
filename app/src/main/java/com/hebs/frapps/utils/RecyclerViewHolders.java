package com.hebs.frapps.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hebs.frapps.R;

//El recyclerview
public class RecyclerViewHolders extends RecyclerView.ViewHolder {

    public TextView titulo;
    public ImageView icono;
    public TextView informacionAdicional;

    public RecyclerViewHolders(View itemView, boolean categoria) {
        super(itemView);
        titulo = (TextView) itemView.findViewById(R.id.titulo);
        icono = (ImageView) itemView.findViewById(R.id.icono);
        informacionAdicional = (TextView) itemView.findViewById(R.id.informacion_adicional);
        if (categoria) {
            informacionAdicional.setVisibility(View.GONE);
        }
    }

}