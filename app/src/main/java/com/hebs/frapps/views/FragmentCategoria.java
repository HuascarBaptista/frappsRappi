package com.hebs.frapps.views;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hebs.frapps.R;
import com.hebs.frapps.models.modelsRealm.Temas;
import com.hebs.frapps.presenters.CategoriasDetallePresenter;

import org.androidannotations.annotations.EFragment;
import org.parceler.Parcels;

import java.util.ArrayList;


@EFragment
public class FragmentCategoria extends Fragment {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter recyclerAdapter;
    private ArrayList<Temas> _data;
    private String _categoria;
    private Activity_Categorias_Detalle _actividad;
    private CategoriasDetallePresenter categoriasDetallePresenter;

    public FragmentCategoria() {
        // Required empty public constructor
    }

    public static FragmentCategoria newInstance(String _titulo, ArrayList<Temas> _apps) {
        FragmentCategoria fragment = new FragmentCategoria();
        Bundle args = new Bundle();
        args.putString("Categoria", _titulo);
        args.putParcelable("Data", Parcels.wrap(_apps));


        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorias_detalle, container, false);
        Bundle args = getArguments();
        //Saco la data para mostrar
        _data = Parcels.unwrap((Parcelable) args.get("Data"));
        _categoria = (String) args.get("Categoria");

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        categoriasDetallePresenter = new CategoriasDetallePresenter(_actividad);
        categoriasDetallePresenter.cargarDataFragment(_actividad, _data, _categoria, this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            _actividad = (Activity_Categorias_Detalle) context;
            if (categoriasDetallePresenter != null) {

                categoriasDetallePresenter.set_view(_actividad);
            }
        }

    }

}
