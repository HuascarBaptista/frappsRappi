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
import com.hebs.frapps.presenters.TemasGeneralesPresenter;

import org.androidannotations.annotations.EFragment;
import org.parceler.Parcels;

import java.util.ArrayList;


@EFragment
public class FragmentClasificacionTemas extends Fragment {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter recyclerAdapter;
    private ArrayList<Temas> _data;
    private String _categoria;
    private Activity_Temas_Generales _actividad;
    private TemasGeneralesPresenter temasGeneralesPresenter;

    public FragmentClasificacionTemas() {
        // Required empty public constructor
    }

    public static FragmentClasificacionTemas newInstance(String _titulo, ArrayList<Temas> _apps) {
        FragmentClasificacionTemas fragment = new FragmentClasificacionTemas();
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
        View view = inflater.inflate(R.layout.fragment_clasificacion_temas, container, false);
        Bundle args = getArguments();
        //Saco la data para mostrar
        _data = Parcels.unwrap((Parcelable) args.get("Data"));
        _categoria = (String) args.get("Categoria");

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        temasGeneralesPresenter = new TemasGeneralesPresenter(_actividad);
        temasGeneralesPresenter.cargarDataFragment(_actividad, _data, _categoria, this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            _actividad = (Activity_Temas_Generales) context;
            if (temasGeneralesPresenter != null) {

                temasGeneralesPresenter.set_view(_actividad);
            }
        }

    }

}
