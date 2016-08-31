package com.hebs.frapps.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.hebs.frapps.R;
import com.hebs.frapps.models.modelsRealm.Temas;
import com.hebs.frapps.presenters.BasePresenter;
import com.hebs.frapps.utils.RecyclerViewHolders;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;


public class TemasGeneralesAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {
    private final Animation animFadeIn;
    public Activity _context;
    private ArrayList<Temas> mDataList;
    private int mRowIndex = -1;
    private int lastPosition = -1;
    private Animation shake;
    private View _vista;
    private String categoria = "";

    public TemasGeneralesAdapter(Activity context, String categoria, ArrayList<Temas> data) {
        this._context = context;
        animFadeIn = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        this.categoria = categoria;
        if (mDataList != data) {
            mDataList = data;
            notifyDataSetChanged();
        }
        //Animacion de shake cuando se le da click
        shake = AnimationUtils.loadAnimation(context, R.anim.shakeanim);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                //Functionality here
                BasePresenter.irDetalleApp(_context, (int) _vista.getTag());

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ;
    }

    public void setData(ArrayList<Temas> data) {
        if (mDataList != data) {
            mDataList = data;
            notifyDataSetChanged();
        }
    }

    public void setRowIndex(int index) {
        mRowIndex = index;
    }


    //La vista de categoria
    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_card_app, parent, false);
        RecyclerViewHolders holder = new RecyclerViewHolders(itemView, false);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders rawHolder, int position) {


        RecyclerViewHolders holder = (RecyclerViewHolders) rawHolder;
        holder.titulo.setText(mDataList.get(position).get_nombre());
        if(categoria.equals(_context.getString(R.string.todas))){
            holder.informacionAdicional.setVisibility(View.GONE);

        }
        if(categoria.equals(_context.getString(R.string.m_s_recientes))){
            holder.informacionAdicional.setVisibility(View.VISIBLE);
            PrettyTime p = new PrettyTime();
            holder.informacionAdicional.setText(p.format(new Date(1000L*mDataList.get(position).get_fechaCreacion())));

        }
        if(categoria.equals(_context.getString(R.string.participantes))){
            holder.informacionAdicional.setVisibility(View.VISIBLE);
            if(mDataList.get(position).get_participantes()==1){

                holder.informacionAdicional.setText(mDataList.get(position).get_participantes()+" "+_context.getString(R.string.participante));
            }else{
                holder.informacionAdicional.setText(mDataList.get(position).get_participantes()+" "+_context.getString(R.string.participantesM) );

            }

        }

        //Para crear animacion del scroll up
        Animation animation = AnimationUtils.loadAnimation(_context,
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_up);
        holder.itemView.startAnimation(animation);

        String avatar_ = (mDataList.get(position).get_icono().equals("") ? mDataList.get(position).get_imagen_pequena() :mDataList.get(position).get_icono());

        //.name("benotto.realm")
        if (!avatar_.equals("")){
            Ion.with(holder.icono)
                    .animateIn(animFadeIn)
                    .load(avatar_).setCallback(new FutureCallback<ImageView>() {
                @Override
                public void onCompleted(Exception e, ImageView result) {
                    if(result!=null)
                    (((View) result.getParent()).findViewById(R.id.progressbar)).setVisibility(View.GONE);
                }
            });
        }else{
            (((View) holder.icono.getParent()).findViewById(R.id.progressbar)).setVisibility(View.GONE);

        }


        holder.itemView.setTag(mDataList.get(position).get_idNumerico());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                _vista = view;
                view.startAnimation(shake);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (mDataList != null)
            return mDataList.size();
        return 0;
    }

    //Para crear animacion del scroll up
    @Override
    public void onViewDetachedFromWindow(RecyclerViewHolders holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }
}
